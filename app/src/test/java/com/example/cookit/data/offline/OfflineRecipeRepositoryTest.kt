package com.example.cookit.data.offline

import app.cash.turbine.test
import com.example.cookit.data.Resource
import com.example.cookit.data.network.CookItNetworkRepository
import com.example.cookit.database.RecipeDao
import com.example.cookit.models.Recipe
import com.google.common.truth.Truth.assertThat
import io.mockk.Runs
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.just
import io.mockk.mockk
import io.mockk.slot
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.runTest
import org.junit.Before
import org.junit.Test
import java.io.IOException

/**
 * JVM unit tests for [OfflineRecipeRepository.recipeDetail].
 *
 * Offline-first contract under test:
 *  - presence of a Room row == favorite; that drives the favorite vs non-favorite branch
 *  - cache emitted first (fromCache=true), then network (fromCache=false)
 *  - merge preserves the cached `roomId` and `cooked` flag
 *  - favorites are refreshed with dao.update(); non-favorites are never written
 *  - errors surface ONLY when there is no cache to keep showing
 *
 * DAO + network are mocked so the flow is exercised without real Room/HTTP.
 */
@OptIn(ExperimentalCoroutinesApi::class)
class OfflineRecipeRepositoryTest {

    private lateinit var recipeDao: RecipeDao
    private lateinit var networkRepository: CookItNetworkRepository
    private lateinit var repository: OfflineRecipeRepository

    private val id = 42L

    // Cached favorite: roomId + cooked must survive a network refresh.
    private val cachedRecipe = Recipe(roomId = 7, id = id, title = "Cached title", cooked = true)

    // Fresh network payload: no roomId, cooked=false, different data-driven fields.
    private val networkRecipe = Recipe(roomId = 0, id = id, title = "Network title", cooked = false)

    @Before
    fun setUp() {
        recipeDao = mockk()
        networkRepository = mockk()
        repository = OfflineRecipeRepository(recipeDao, networkRepository)
    }

    @Test
    fun `emits cache then merged network when online favorite`() = runTest {
        coEvery { recipeDao.getItemOnce(id) } returns cachedRecipe
        coEvery { networkRepository.getRecipeInfo(id = id) } returns networkRecipe
        coEvery { recipeDao.update(any()) } just Runs

        repository.recipeDetail(id).test {
            assertThat(awaitItem()).isEqualTo(Resource.Loading)

            val cacheHit = awaitItem()
            assertThat(cacheHit).isInstanceOf(Resource.Success::class.java)
            cacheHit as Resource.Success
            assertThat(cacheHit.fromCache).isTrue()
            assertThat(cacheHit.data).isEqualTo(cachedRecipe)

            val networkHit = awaitItem()
            assertThat(networkHit).isInstanceOf(Resource.Success::class.java)
            networkHit as Resource.Success
            assertThat(networkHit.fromCache).isFalse()
            assertThat(networkHit.data.title).isEqualTo("Network title")

            awaitComplete()
        }

        coVerify(exactly = 1) { recipeDao.update(any()) }
    }

    @Test
    fun `keeps cache and no error when favorite goes offline`() = runTest {
        coEvery { recipeDao.getItemOnce(id) } returns cachedRecipe
        coEvery { networkRepository.getRecipeInfo(id = id) } throws IOException("offline")

        repository.recipeDetail(id).test {
            assertThat(awaitItem()).isEqualTo(Resource.Loading)

            val cacheHit = awaitItem()
            assertThat(cacheHit).isInstanceOf(Resource.Success::class.java)
            cacheHit as Resource.Success
            assertThat(cacheHit.fromCache).isTrue()
            assertThat(cacheHit.data).isEqualTo(cachedRecipe)

            // No error emitted after the cache: offline must not clobber a shown cache.
            awaitComplete()
        }

        coVerify(exactly = 0) { recipeDao.update(any()) }
    }

    @Test
    fun `emits network only and never writes when online non favorite`() = runTest {
        coEvery { recipeDao.getItemOnce(id) } returns null
        coEvery { networkRepository.getRecipeInfo(id = id) } returns networkRecipe

        repository.recipeDetail(id).test {
            assertThat(awaitItem()).isEqualTo(Resource.Loading)

            val networkHit = awaitItem()
            assertThat(networkHit).isInstanceOf(Resource.Success::class.java)
            networkHit as Resource.Success
            assertThat(networkHit.fromCache).isFalse()
            assertThat(networkHit.data).isEqualTo(networkRecipe)

            awaitComplete()
        }

        // Non-favorite is served in-memory only - nothing hits Room.
        coVerify(exactly = 0) { recipeDao.update(any()) }
        coVerify(exactly = 0) { recipeDao.insert(any()) }
    }

    @Test
    fun `emits OfflineNoCache when non favorite offline`() = runTest {
        coEvery { recipeDao.getItemOnce(id) } returns null
        coEvery { networkRepository.getRecipeInfo(id = id) } throws IOException("offline")

        repository.recipeDetail(id).test {
            assertThat(awaitItem()).isEqualTo(Resource.Loading)
            assertThat(awaitItem()).isEqualTo(Resource.Error.OfflineNoCache)
            awaitComplete()
        }
    }

    @Test
    fun `emits Network error when non favorite http fails`() = runTest {
        coEvery { recipeDao.getItemOnce(id) } returns null
        coEvery { networkRepository.getRecipeInfo(id = id) } throws RuntimeException("boom")

        repository.recipeDetail(id).test {
            assertThat(awaitItem()).isEqualTo(Resource.Loading)

            val error = awaitItem()
            assertThat(error).isInstanceOf(Resource.Error.Network::class.java)
            error as Resource.Error.Network
            assertThat(error.message).isEqualTo("boom")

            awaitComplete()
        }
    }

    @Test
    fun `merge preserves cooked and roomId from cache`() = runTest {
        val updated = slot<Recipe>()
        coEvery { recipeDao.getItemOnce(id) } returns cachedRecipe
        coEvery { networkRepository.getRecipeInfo(id = id) } returns networkRecipe
        coEvery { recipeDao.update(capture(updated)) } just Runs

        repository.recipeDetail(id).test {
            skipItems(3) // Loading, cache Success, network Success
            awaitComplete()
        }

        val merged = updated.captured
        // Identity kept from cache...
        assertThat(merged.roomId).isEqualTo(cachedRecipe.roomId)
        assertThat(merged.cooked).isEqualTo(cachedRecipe.cooked)
        // ...data refreshed from network.
        assertThat(merged.title).isEqualTo(networkRecipe.title)
    }

    @Test
    fun `favorite offline emits exactly two items then completes`() = runTest {
        coEvery { recipeDao.getItemOnce(id) } returns cachedRecipe
        coEvery { networkRepository.getRecipeInfo(id = id) } throws IOException("offline")

        repository.recipeDetail(id).test {
            assertThat(awaitItem()).isEqualTo(Resource.Loading)
            assertThat(awaitItem()).isInstanceOf(Resource.Success::class.java)
            awaitComplete()
        }
    }
}
