CookIt
============================================

CookIt is a recipe search app that helps users find and save their favorite recipes. The app uses
the [Spoonacular API](https://spoonacular.com/food-api) to search for recipes based on keywords,
ingredients, and dietary restrictions. Users can also save recipes to their favorites list and view
detailed nutrition information for each recipe.

Features
-------------------------------------------

* Recipe search by keyword, ingredients, and dietary restrictions
* Filter recipes by meal type and cuisine
* Save recipes to favorites list
* View detailed nutrition information for each recipe
* User-friendly interface with intuitive navigation

Technologies Used
-------------------------------------------

* Android SDK
* Kotlin programming language
* Android Architecture Components (ViewModel, LiveData)
* Retrofit for API communication
* Picasso for image loading
* Material Design for UI components

Getting Started
------------------------------------------

1. Clone the repository to your local machine:
   `git clone https://github.com/Renekakpo/CookIt.git`

2. Open the project in Android Studio.
3. Register for a free API key from the [Spoonacular API](https://spoonacular.com/food-api) website.
4. Replace the API key in the `gradle.properties` file:
   `COOKIT_API_KEY=YOUR_API_KEY_HERE`

5. Build and run the app on an Android emulator or device.

Screenshots
------------------------------------------
<div style="display: flex; flex-wrap: wrap; justify-content: space-around;">
    <img src="./screenshots/screenshot_splashscreen.png" alt="Splash Screen" width="225" height="450" />
    <img src="./screenshots/screenshot_onboarding_start.png" alt="Onboarding Start" width="225" height="450" />
    <img src="./screenshots/screenshot_onboarding_end.png" alt="Onboarding End" width="225" height="450" />
    <img src="./screenshots/screenshot_signinscreen.png" alt="Sign In Screen" width="225" height="450" />
    <img src="./screenshots/screenshot_signupscreen.png" alt="Sign Up Screen" width="225" height="450" />
    <img src="./screenshots/screenshot_homescreen.png" alt="Home Screen" width="225" height="450" />
    <img src="./screenshots/screenshot_recipedetails.png" alt="Recipe Details" width="225" height="450" />
    <img src="./screenshots/screenshot_search_filter.png" alt="Search Filter" width="225" height="450" />
    <img src="./screenshots/screenshot_instructions.png" alt="Instructions" width="225" height="450" />
    <img src="./screenshots/screenshot_favoritescreen.png" alt="Favorite Screen" width="225" height="450" />
    <img src="./screenshots/screenshot_settings.png" alt="Settings" width="225" height="450" />
    <img src="./screenshots/screenshot_aboutapp.png" alt="About App" width="225" height="450" />
</div>

Credits
------------------------------------------
The CookIt app was developed by René Kakpo as a personal project.

License
------------------------------------------
The CookIt app is released under
the [MIT License](https://github.com/Renekakpo/cookIt/blob/main/LICENSE).
