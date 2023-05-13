# AndroidApp
EMPRESTA.ME Android Application


# Project Info

* Project Utilizes Jetpack Compose.
* MVVM + Repository pattern
* Dependency Injection DaggerHilt 
* Room Databse

#

* Reusable UI components are located under UI folder
* Navigation Components are located under the Navigation folder
* Dependency Injection and Repository pattern code are under the DI folder. Dependency Injection is achieved with Dagger Hilt. Please se the [Android Documentation about the subject](https://developer.android.com/training/dependency-injection/hilt-android) (it is very good)
* Room database and data access object classes are located under the DAO folder
* Components related to accessing API's are under the RemoteAPI Folder

# 
### Features
* **Busines Logic**
  * Located under the use_case folder in each feature folder
* **View and Composable Screens**
  * View Models and Composable Screens are located under each feature folder
  * **View Model**: It exposes state to the UI and encapsulates related business logic. Its principal advantage is that it caches state and persists it through configuration changes. This means that your UI doesn’t have to fetch data again when navigating between activities, or following configuration changes

