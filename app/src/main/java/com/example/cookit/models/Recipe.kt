package com.example.cookit.models

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.google.gson.annotations.SerializedName

/**
 * Entity data class represents a single row in the database.
 */
@Entity(tableName = "recipes")
data class Recipe(
    @PrimaryKey(autoGenerate = true)
    var roomId: Long = 0,
    val id: Int,
    val title: String?,
    val image: String?,
    val imageType: String?,
    val summary: String?,
    val servings: Int,
    val readyInMinutes: Int,
    val license: String?,
    val sourceName: String?,
    val sourceUrl: String?,
    val spoonacularSourceUrl: String?,
    val aggregateLikes: Int,
    val healthScore: Double,
    val spoonacularScore: Double,
    val pricePerServing: Double,
    val analyzedInstructions: List<String?>,
    val cheap: Boolean,
    val creditsText: String?,
    val cuisines: List<String?>,
    val dairyFree: Boolean,
    val diets: List<String?>,
    val gaps: String?,
    val glutenFree: Boolean,
    val instructions: String?,
    val ketogenic: Boolean,
    val lowFodmap: Boolean,
    val occasions: List<String?>,
    val sustainable: Boolean,
    val vegan: Boolean,
    val vegetarian: Boolean,
    val veryHealthy: Boolean,
    val veryPopular: Boolean,
    val whole30: Boolean,
    val weightWatcherSmartPoints: Int,
    val dishTypes: List<String?>,
    val extendedIngredients: List<ExtendedIngredient>
)

data class ExtendedIngredient(
    val aisle: String?,
    val amount: Double,
    @SerializedName("consitency")
    val consistency: String?,
    val id: Int,
    val image: String?,
    val measures: Map<String, Measure>,
    val meta: List<String?>,
    val name: String?,
    val original: String?,
    val originalName: String?,
    val unit: String?
)

data class Measure(
    val amount: Double,
    val unitLong: String?,
    val unitShort: String?
)

val recipe = Recipe(
    id = 716429,
    title = "Pasta with Garlic, Scallions, Cauliflower & Breadcrumbs",
    image = "https://spoonacular.com/recipeImages/716429-556x370.jpg",
    imageType = "jpg",
    summary = "You can never have too many main course recipes, so give Pasta with Garlic, Scallions, Cauliflower & Breadcrumbs a try. One serving contains <b>543 calories</b>, <b>17g of protein</b>, and <b>16g of fat</b>. For <b>\$1.57 per serving</b>, this recipe <b>covers 22%</b> of your daily requirements of vitamins and minerals. This recipe serves 2. A mixture of butter, white wine, pasta, and a handful of other ingredients are all it takes to make this recipe so yummy. 209 people have tried and liked this recipe. It is brought to you by fullbellysisters.blogspot.com. From preparation to the plate, this recipe takes approximately <b>45 minutes</b>. Taking all factors into account, this recipe <b>earns a spoonacular score of 83%</b>, which is tremendous. If you like this recipe, take a look at these similar recipes: <a href=\\\"https://spoonacular.com/recipes/pasta-with-garlic-scallions-cauliflower-breadcrumbs-1230187\\\">Pasta with Garlic, Scallions, Cauliflower & Breadcrumbs</a>, <a href=\\\"https://spoonacular.com/recipes/pasta-with-garlic-scallions-cauliflower-breadcrumbs-1229807\\\">Pasta with Garlic, Scallions, Cauliflower & Breadcrumbs</a>, and <a href=\\\"https://spoonacular.com/recipes/pasta-with-garlic-scallions-cauliflower-breadcrumbs-1229669\\\">Pasta with Garlic, Scallions, Cauliflower & Breadcrumbs</a>.",
    servings = 2,
    readyInMinutes = 45,
    license = "CC BY-SA 3.0",
    sourceName = "Full Belly Sisters",
    sourceUrl = "http://fullbellysisters.blogspot.com/2012/06/pasta-with-garlic-scallions-cauliflower.html",
    spoonacularSourceUrl = "https://spoonacular.com/pasta-with-garlic-scallions-cauliflower-breadcrumbs-716429",
    aggregateLikes = 209,
    healthScore = 19.0,
    spoonacularScore = 83.0,
    pricePerServing = 163.15,
    analyzedInstructions = emptyList(),
    cheap = false,
    creditsText = "Full Belly Sisters",
    cuisines = emptyList(),
    dairyFree = false,
    diets = emptyList(),
    gaps = "no",
    glutenFree = false,
    instructions = "",
    ketogenic = false,
    lowFodmap = false,
    occasions = emptyList(),
    sustainable = false,
    vegan = false,
    vegetarian = false,
    veryHealthy = false,
    veryPopular = false,
    whole30 = false,
    weightWatcherSmartPoints = 17,
    dishTypes = listOf("lunch", "main course", "main dish", "dinner"),
    extendedIngredients = listOf(
        ExtendedIngredient(
            aisle = "Milk, Eggs, Other Dairy",
            amount = 1.0,
            consistency = "solid",
            id = 1001,
            image = "butter-sliced.jpg",
            measures = mapOf(
                "Metric" to Measure(amount = 1.0, unitLong = "Tbsp", unitShort = "Tbsp"),
                "Us" to Measure(amount = 1.0, unitLong = "Tbsp", unitShort = "Tbsp")
            ),
            meta = emptyList(),
            name = "butter",
            original = "1 tbsp butter",
            originalName = "butter",
            unit = "tbsp"
        ),
        ExtendedIngredient(
            aisle = "Produce",
            amount = 2.0,
            consistency = "solid",
            id = 10011135,
            image = "cauliflower.jpg",
            measures = mapOf(
                "Metric" to Measure(amount = 473.176, unitLong = "milliliters", unitShort = "ml"),
                "Us" to Measure(amount = 2.0, unitLong = "cups", unitShort = "cups")
            ),
            meta = listOf("frozen", "thawed", "cut into bite-sized pieces"),
            name = "cauliflower florets",
            original = "about 2 cups frozen cauliflower florets, thawed, cut into bite-sized pieces",
            originalName = "about frozen cauliflower florets, thawed, cut into bite-sized pieces",
            unit = "cups"
        ),
        ExtendedIngredient(
            aisle = "Produce",
            amount = 2.0,
            consistency = "solid",
            id = 10011135,
            image = "cauliflower.jpg",
            measures = mapOf(
                "Metric" to Measure(amount = 473.176, unitLong = "milliliters", unitShort = "ml"),
                "Us" to Measure(amount = 2.0, unitLong = "cups", unitShort = "cups")
            ),
            meta = listOf("frozen", "thawed", "cut into bite-sized pieces"),
            name = "cauliflower florets",
            original = "about 2 cups frozen cauliflower florets, thawed, cut into bite-sized pieces",
            originalName = "about frozen cauliflower florets, thawed, cut into bite-sized pieces",
            unit = "cups"
        ),
        ExtendedIngredient(
            aisle = "Produce",
            amount = 2.0,
            consistency = "solid",
            id = 10011135,
            image = "cauliflower.jpg",
            measures = mapOf(
                "Metric" to Measure(amount = 473.176, unitLong = "milliliters", unitShort = "ml"),
                "Us" to Measure(amount = 2.0, unitLong = "cups", unitShort = "cups")
            ),
            meta = listOf("frozen", "thawed", "cut into bite-sized pieces"),
            name = "cauliflower florets",
            original = "about 2 cups frozen cauliflower florets, thawed, cut into bite-sized pieces",
            originalName = "about frozen cauliflower florets, thawed, cut into bite-sized pieces",
            unit = "cups"
        ),
        ExtendedIngredient(
            aisle = "Produce",
            amount = 2.0,
            consistency = "solid",
            id = 10011135,
            image = "cauliflower.jpg",
            measures = mapOf(
                "Metric" to Measure(amount = 473.176, unitLong = "milliliters", unitShort = "ml"),
                "Us" to Measure(amount = 2.0, unitLong = "cups", unitShort = "cups")
            ),
            meta = listOf("frozen", "thawed", "cut into bite-sized pieces"),
            name = "cauliflower florets",
            original = "about 2 cups frozen cauliflower florets, thawed, cut into bite-sized pieces",
            originalName = "about frozen cauliflower florets, thawed, cut into bite-sized pieces",
            unit = "cups"
        ),
        ExtendedIngredient(
            aisle = "Produce",
            amount = 2.0,
            consistency = "solid",
            id = 10011135,
            image = "cauliflower.jpg",
            measures = mapOf(
                "Metric" to Measure(amount = 473.176, unitLong = "milliliters", unitShort = "ml"),
                "Us" to Measure(amount = 2.0, unitLong = "cups", unitShort = "cups")
            ),
            meta = listOf("frozen", "thawed", "cut into bite-sized pieces"),
            name = "cauliflower florets",
            original = "about 2 cups frozen cauliflower florets, thawed, cut into bite-sized pieces",
            originalName = "about frozen cauliflower florets, thawed, cut into bite-sized pieces",
            unit = "cups"
        ),
        ExtendedIngredient(
            aisle = "Produce",
            amount = 2.0,
            consistency = "solid",
            id = 10011135,
            image = "cauliflower.jpg",
            measures = mapOf(
                "Metric" to Measure(amount = 473.176, unitLong = "milliliters", unitShort = "ml"),
                "Us" to Measure(amount = 2.0, unitLong = "cups", unitShort = "cups")
            ),
            meta = listOf("frozen", "thawed", "cut into bite-sized pieces"),
            name = "cauliflower florets",
            original = "about 2 cups frozen cauliflower florets, thawed, cut into bite-sized pieces",
            originalName = "about frozen cauliflower florets, thawed, cut into bite-sized pieces",
            unit = "cups"
        ),
        ExtendedIngredient(
            aisle = "Produce",
            amount = 2.0,
            consistency = "solid",
            id = 10011135,
            image = "cauliflower.jpg",
            measures = mapOf(
                "Metric" to Measure(amount = 473.176, unitLong = "milliliters", unitShort = "ml"),
                "Us" to Measure(amount = 2.0, unitLong = "cups", unitShort = "cups")
            ),
            meta = listOf("frozen", "thawed", "cut into bite-sized pieces"),
            name = "cauliflower florets",
            original = "about 2 cups frozen cauliflower florets, thawed, cut into bite-sized pieces",
            originalName = "about frozen cauliflower florets, thawed, cut into bite-sized pieces",
            unit = "cups"
        )
    )
)


val recipeList = List(15) { recipe } // Create a list of repeated Recipe item


