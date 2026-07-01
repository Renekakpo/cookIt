package com.example.cookit.ui.screens.recipeItem

import app.cash.turbine.test
import com.example.cookit.data.Resource
import com.example.cookit.data.offline.RecipesRepository
import com.example.cookit.data.offline.datastore.CookItDataStoreRepository
import com.example.cookit.models.Recipe
import com.google.common.truth.Truth.assertThat
import io.mockk.Runs
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.every
import io.mockk.just
import io.mockk.mockk
import io.mockk.slot
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.launch
import kotlinx.coroutines.test.StandardTestDispatcher
import kotlinx.coroutines.test.advanceUntilIdle
import kotlinx.coroutines.test.resetMain
import kotlinx.coroutines.test.runTest
import kotlinx.coroutines.test.setMain
import org.junit.After
import org.junit.Before
import org.junit.Test

/**
 * JVM unit tests for [RecipeInfoViewModel.uiState].
 *
 * uiState is a stateIn(WhileSubscribed(5000)) — a cold flow that only runs its upstream while
 * there is an active collector. Tests therefore either collect via Turbine inside runTest, or
 * launch a backgroundScope collector before reading uiState.value.
 *
 * [RecipesRepository] is mocked so recipeDetail()/getItemStream() return controlled Flows.
 */
@OptIn(ExperimentalCoroutinesApi::class)
class RecipeInfoViewModelTest {

    private val dispatcher = StandardTestDispatcher()

    private lateinit var repository: RecipesRepository
    private lateinit var dataStore: CookItDataStoreRepository
    private lateinit var viewModel: RecipeInfoViewModel

    private val id = 42L
    private val cachedRecipe = Recipe(roomId = 7, id = id, title = "Cached title", cooked = true)
    private val mergedRecipe = Recipe(roomId = 7, id = id, title = "Network title", cooked = true)

    @Before
    fun setUp() {
        Dispatchers.setMain(dispatcher)
        repository = mockk()
        dataStore = mockk(relaxed = true)
        viewModel = RecipeInfoViewModel(repository, dataStore)
    }

    @After
    fun tearDown() {
        Dispatchers.resetMain()
    }

    @Test
    fun `emits favorite success when favorite online`() = runTest {
        // Row present => favorite; recipeDetail emits cache-first then merged network.
        every { repository.getItemStream(id) } returns flowOf(cachedRecipe)
        every { repository.recipeDetail(id) } returns flowOf(
            Resource.Loading,
            Resource.Success(data = cachedRecipe, fromCache = true),
            Resource.Success(data = mergedRecipe, fromCache = false),
        )

        viewModel.load(id)

        viewModel.uiState.test {
            assertThat(awaitItem()).isEqualTo(RecipeDetailsUiState.Loading)

            val cacheState = awaitItem() as RecipeDetailsUiState.Success
            assertThat(cacheState.isFavorite).isTrue()
            assertThat(cacheState.fromCache).isTrue()
            assertThat(cacheState.recipe).isEqualTo(cachedRecipe)

            val networkState = awaitItem() as RecipeDetailsUiState.Success
            assertThat(networkState.isFavorite).isTrue()
            assertThat(networkState.fromCache).isFalse()
            assertThat(networkState.recipe).isEqualTo(mergedRecipe)

            cancelAndIgnoreRemainingEvents()
        }
    }

    @Test
    fun `emits ErrorOfflineNoCache when non favorite offline`() = runTest {
        // No row => non-favorite; offline with no cache surfaces the offline error.
        every { repository.getItemStream(id) } returns flowOf(null)
        every { repository.recipeDetail(id) } returns flowOf(
            Resource.Loading,
            Resource.Error.OfflineNoCache,
        )

        viewModel.load(id)

        viewModel.uiState.test {
            assertThat(awaitItem()).isEqualTo(RecipeDetailsUiState.Loading)
            assertThat(awaitItem()).isEqualTo(RecipeDetailsUiState.ErrorOfflineNoCache)
            cancelAndIgnoreRemainingEvents()
        }
    }

    @Test
    fun `favoriting displayed recipe inserts that recipe`() = runTest {
        every { repository.getItemStream(id) } returns flowOf(null)
        every { repository.recipeDetail(id) } returns flowOf(
            Resource.Loading,
            Resource.Success(data = mergedRecipe, fromCache = false),
        )
        val inserted = slot<Recipe>()
        coEvery { repository.insertItem(capture(inserted)) } just Runs

        // Active collector keeps uiState hot so uiState.value reflects the network recipe.
        backgroundScope.launch { viewModel.uiState.collect {} }
        viewModel.load(id)
        advanceUntilIdle()

        assertThat(viewModel.uiState.value).isInstanceOf(RecipeDetailsUiState.Success::class.java)

        viewModel.updateFavoriteDataState(isFavorite = true)
        advanceUntilIdle()

        coVerify(exactly = 1) { repository.insertItem(any()) }
        assertThat(inserted.captured.id).isEqualTo(mergedRecipe.id)
    }

    @Test
    fun `favorite click on non success state does not insert`() = runTest {
        // No collector => WhileSubscribed upstream never starts => uiState.value stays Loading.
        assertThat(viewModel.uiState.value).isEqualTo(RecipeDetailsUiState.Loading)

        viewModel.updateFavoriteDataState(isFavorite = true) // must return silently, no crash
        advanceUntilIdle()

        coVerify(exactly = 0) { repository.insertItem(any()) }
    }
}
