<h1 align="center">Rick and Morty App</h1>

<p align="center">
  <a href="https://android-arsenal.com/api?level=24"><img alt="API" src="https://img.shields.io/badge/API-24%2B-orange.svg?style=flat"/></a>
  <a href="https://kotlinlang.org/"><img alt="Android" src="https://img.shields.io/badge/Android-Kotlin-green.svg?style=flat"/></a>
  <a href="https://github.com/donayd"><img alt="GitHub" src="https://img.shields.io/badge/GitHub-Donayd-blue.svg?style=flat"/></a> 
</p>

<p align="center">  
This is a demo app in which you can search for any character of the show Rick and Morty. This app use the Rick and Morty API:
<a href="https://rickandmortyapi.com/">RickAndMortyAPI</a> 
</br>
</p>
</br>

<img src="/previews/preview.gif" align="right" width="320"/>

## Tech stack
- Minimum SDK level 24
- Target SDK level 34
- [Kotlin](https://kotlinlang.org/) based
- Jetpack
  - [Compose](https://developer.android.com/jetpack/compose): Simplifies and accelerates UI development on Android. 
  - Lifecycle: Observe Android lifecycles and handle UI states upon the lifecycle changes.  
  - [Hilt](https://dagger.dev/hilt/): for dependency injection.
- Architecture
  - MVVM Architecture (View - DataBinding - ViewModel - Model)
- [Retrofit2 & OkHttp3](https://github.com/square/retrofit): Construct the REST APIs and paging network data.
