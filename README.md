# MovieInfo

**Version:** 1.0.0  
**Release Date:** 27/10/2025  
**Platform:** Android (Jetpack Compose)  
**Language:** Kotlin  

---

## Overview

MovieInfo is a simple Android application built with Jetpack Compose that allows users to search for movies and TV series using the OMDb API.  
It uses Retrofit for network calls and displays search results in a minimal and responsive interface.
[App Preview](screenshots/MovieInfo.png)

---

## Features

- Search for movies and series by name  
- Fetches data from the OMDb API using Retrofit  
- Simple and lightweight UI built with Jetpack Compose  
- Filtering between movies and series (currently in beta and may contain bugs)  

---

## Tech Stack

| Component | Technology |
|------------|-------------|
| UI | Jetpack Compose |
| Networking | Retrofit2 with Gson |
| Language | Kotlin |
| API | [OMDb API](https://www.omdbapi.com/) |

---

## Setup and Installation

### 1. Get an OMDb API Key
Register for a free API key at:  
[https://www.omdbapi.com/apikey.aspx](https://www.omdbapi.com/apikey.aspx)

### 2. Add your API key
In the project’s root directory, open (or create) the `local.properties` file and add:

```properties```
apiKey=your_api_key_here

### 3. Build and Run
1. Open the project in **Android Studio**.  
2. Sync Gradle files.  
3. Click **Run** to build and install the app on your device or emulator.

---

## Known Issues

- The movie/series filter feature is buggy and shows series in weird positions.  
- Network delays can occur when multiple results are fetched, especially with images.  
- Occasional layout overlaps on smaller screens.

---

## Planned Updates

- Fix and refine the filter functionality.  
- Improve layout and spacing of result cards.
- Ability to press Movie/Series title to fetch information card.
- UI Improvements.

---
