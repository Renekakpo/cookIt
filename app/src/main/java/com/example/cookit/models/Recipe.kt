package com.example.cookit.models

import androidx.room.Entity
import androidx.room.PrimaryKey
import androidx.room.TypeConverters
import com.example.cookit.models.converters.AnalyzedInstructionsConverters
import com.example.cookit.models.converters.StringListConverters
import com.example.cookit.utils.RECIPE_TABLE_NAME
import com.google.gson.annotations.SerializedName
import kotlinx.serialization.Serializable

/**
 * Entity data class represents a single row in the database.
 */
@Entity(tableName = RECIPE_TABLE_NAME)
@Serializable
data class Recipe(
    @PrimaryKey(autoGenerate = true)
    var roomId: Long = 0,
    val id: Long = 0,
    val title: String? = null,
    val image: String? = null,
    val imageType: String? = null,
    val summary: String? = null,
    val servings: Int? = 0,
    val readyInMinutes: Int? = 0,
    val preparationMinutes: Int? = 0,
    val cookingMinutes: Int? = 0,
    val license: String? = null,
    val sourceName: String? = null,
    val sourceUrl: String? = null,
    val spoonacularSourceUrl: String? = null,
    val aggregateLikes: Int? = 0,
    val healthScore: Double? = 0.0,
    val spoonacularScore: Double? = 0.0,
    val pricePerServing: Double? = 0.0,
    @TypeConverters(AnalyzedInstructionsConverters::class)
    val analyzedInstructions: List<AnalyzedRecipeInstructionsApiRes>? = null,
    val cheap: Boolean? = false,
    val creditsText: String? = null,
    @TypeConverters(StringListConverters::class)
    val cuisines: List<String>? = null,
    val dairyFree: Boolean? = false,
    @TypeConverters(StringListConverters::class)
    val diets: List<String>? = null,
    val gaps: String? = null,
    val glutenFree: Boolean? = false,
    val instructions: String? = null,
    val ketogenic: Boolean? = false,
    val lowFodmap: Boolean? = false,
    @TypeConverters(StringListConverters::class)
    val occasions: List<String>? = null,
    val sustainable: Boolean? = false,
    val vegan: Boolean? = false,
    val vegetarian: Boolean? = false,
    val veryHealthy: Boolean? = false,
    val veryPopular: Boolean? = false,
    val whole30: Boolean? = false,
    val weightWatcherSmartPoints: Int? = 0,
    @TypeConverters(StringListConverters::class)
    val dishTypes: List<String>? = null,
    @TypeConverters(ExtendedIngredient::class)
    val extendedIngredients: List<ExtendedIngredient> = emptyList(),
    @TypeConverters(Nutrition::class)
    val nutrition: Nutrition
)

@Serializable
data class ExtendedIngredient(
    val aisle: String? = null,
    val amount: Double? = 0.0,
    @SerializedName("consitency")
    val consistency: String? = null,
    val id: Int? = 0,
    val image: String? = null,
    val measures: Map<String, Measure>,
    val meta: List<String>?,
    val name: String? = null,
    val nameClean: String? = null,
    val original: String? = null,
    val originalName: String? = null,
    val unit: String? = null
)

@Serializable
data class Measure(
    val amount: Double? = 0.0,
    val unitLong: String? = null,
    val unitShort: String? = null
)

@Serializable
data class Nutrition(
    val nutrients: List<Nutrient>? = null,
    val properties: List<NutProperty>? = null,
    val flavonoids: List<NutFlavonoid>? = null,
    val ingredients: List<NutIngredient>? = null,
    val caloricBreakdown: CaloricBreakdown? = null,
    val weightPerServing: WeightPerServing? = null,
)

@Serializable
data class Nutrient(
    val name: String? = null,
    val amount: Double? = null,
    val unit: String? = null,
    val percentOfDailyNeeds: Double? = null
)

@Serializable
data class NutProperty(
    val name: String? = null,
    val amount: Double? = null,
    val unit: String? = null
)

@Serializable
data class NutFlavonoid(
    val name: String? = null,
    val amount: Double? = null,
    val unit: String? = null
)

@Serializable
data class NutIngredient(
    val id: Long = 0,
    val name: String? = null,
    val amount: Double? = null,
    val unit: String? = null
)

@Serializable
data class CaloricBreakdown(
    val percentProtein: Double? = null,
    val percentFat: Double? = null,
    val percentCarbs: Double? = null
)

@Serializable
data class WeightPerServing(
    val amount: Double? = null,
    val unit: String? = null
)

val nutrients = listOf(
    Nutrient("Protein", 10.0, "g", 20.0),
    Nutrient("Fat", 5.0, "g", 10.0),
    Nutrient("Carbohydrates", 30.0, "g", 60.0)
)

val properties = listOf(
    NutProperty("Fiber", 3.0, "g"),
    NutProperty("Vitamin C", 20.0, "mg")
)

val flavonoids = listOf(
    NutFlavonoid("Quercetin", 15.0, "mg"),
    NutFlavonoid("Kaempferol", 10.0, "mg")
)

val ingredients = listOf(
    NutIngredient(1, "Ingredient 1", 100.0, "g"),
    NutIngredient(2, "Ingredient 2", 50.0, "g")
)

val caloricBreakdown = CaloricBreakdown(25.0, 30.0, 45.0)

val weightPerServing = WeightPerServing(150.0, "g")

val recipe = Recipe(
    id = 716429,
    title = "Pasta with Garlic, Scallions, Cauliflower & Breadcrumbs",
    image = "https://spoonacular.com/recipeImages/716429-556x370.jpg",
    imageType = "jpg",
    summary = "You can never have too many main course recipes, so give Pasta with Garlic, Scallions, Cauliflower & Breadcrumbs a try. One serving contains <b>543 calories</b>, <b>17g of protein</b>, and <b>16g of fat</b>. For <b>\$1.57 per serving</b>, this recipe <b>covers 22%</b> of your daily requirements of vitamins and minerals. This recipe serves 2. A mixture of butter, white wine, pasta, and a handful of other ingredients are all it takes to make this recipe so yummy. 209 people have tried and liked this recipe. It is brought to you by fullbellysisters.blogspot.com. From preparation to the plate, this recipe takes approximately <b>45 minutes</b>. Taking all factors into account, this recipe <b>earns a spoonacular score of 83%</b>, which is tremendous. If you like this recipe, take a look at these similar recipes: <a href=\\\"https://spoonacular.com/recipes/pasta-with-garlic-scallions-cauliflower-breadcrumbs-1230187\\\">Pasta with Garlic, Scallions, Cauliflower & Breadcrumbs</a>, <a href=\\\"https://spoonacular.com/recipes/pasta-with-garlic-scallions-cauliflower-breadcrumbs-1229807\\\">Pasta with Garlic, Scallions, Cauliflower & Breadcrumbs</a>, and <a href=\\\"https://spoonacular.com/recipes/pasta-with-garlic-scallions-cauliflower-breadcrumbs-1229669\\\">Pasta with Garlic, Scallions, Cauliflower & Breadcrumbs</a>.",
    servings = 2,
    readyInMinutes = 45,
    preparationMinutes = 45,
    cookingMinutes = 45,
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
            nameClean = "butter",
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
            nameClean = "cauliflower florets",
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
            nameClean = "cauliflower florets",
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
            nameClean = "cauliflower florets",
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
            nameClean = "cauliflower florets",
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
            nameClean = "cauliflower florets",
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
            nameClean = "cauliflower florets",
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
            nameClean = "cauliflower florets",
            original = "about 2 cups frozen cauliflower florets, thawed, cut into bite-sized pieces",
            originalName = "about frozen cauliflower florets, thawed, cut into bite-sized pieces",
            unit = "cups"
        )
    ),
    nutrition = Nutrition(
        nutrients = nutrients,
        properties = properties,
        flavonoids = flavonoids,
        ingredients = ingredients,
        caloricBreakdown = caloricBreakdown,
        weightPerServing = weightPerServing
    )
)


val recipeList = List(15) { recipe } // Create a list of repeated Recipe item


