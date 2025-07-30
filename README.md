# Matches and own events

## Overview
**Matches and own events** is a Kotlin Multiplatform application that displays a list of football matches grouped by leagues and dates. Users can add their own custom events, write notes for specific matches, and browse through past, current, and upcoming games.

### Features:
- 📅 Grouping matches by **date** and **league**
- 📝 Adding **custom events** and **notes** to matches
- 🕓 Viewing **past**, **live**, and **upcoming** games
- 🌐 Built for both **Android** and **iOS**
- 🔁 Supports **pagination** for long match lists
- 🖼️ Asynchronous **image loading**
- 🧭 Built-in **navigation system**
- 🗃️ Uses **Room** and **SQLDelight** for local **database persistence**

---

## Screenshots

<img src="https://i.postimg.cc/PP3cDgQ0/app11.png" width="300"> <img src="https://i.postimg.cc/jWNkGLYr/app12.png" width="300">  
<img src="https://i.postimg.cc/gXWBdmGL/app13.png" width="300"> <img src="https://i.postimg.cc/3kkqFtLN/app14.png" width="300">  
<img src="https://i.postimg.cc/DSRMj1Dg/app15.png" width="300"> <img src="https://i.postimg.cc/KkDp8wYq/app16.png" width="300">

---

## Technologies & Dependencies

This project is built using **Kotlin Multiplatform** and modern libraries:

### UI & Navigation
- **Jetpack Compose** for Android UI
- **Material3** components
- **Precompose** for lifecycle and ViewModel handling
- **Paging Compose** for list pagination
- **Navigation** using precompose navigation system

### Dependency Injection
- **Koin** (core and Android support)

### Networking
- **Ktor Client** (OkHttp for Android, Darwin for iOS)
- **kotlinx.serialization.json** for JSON serialization

### Date & Time
- **kotlinx.datetime** for multiplatform time handling

### Images
- **Kamel** for asynchronous image loading and caching

### Persistence / Database
- **SQLDelight** (Android + Native driver) for multiplatform database
- **Room** (for Android)
- **kvault** for secure key-value storage

### Other
- **Compose WebView Multiplatform** for in-app web browsing
- **Kotlin Coroutines** for asynchronous operations
- **Desugar JDK Libraries** support for Java 8+ APIs

---

## Project Structure

```text
shared/               -> Kotlin Multiplatform shared code (logic, database, models)
androidApp/           -> Android-specific code
iosApp/               -> iOS-specific SwiftUI code