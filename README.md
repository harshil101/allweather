# allweather

# 🌤️ Jetpack Compose Weather App

This is a sample Android Weather App built using **Jetpack Compose**, **Hilt**, **Retrofit**, and **Google Places API**. It fetches the user's current location, displays the weather information using OpenWeatherMap API, and allows users to search cities using a Compose-based search box with Google Places Autocomplete.

---

## 🚀 Features

- 🌐 Fetch weather for current location using GPS
- 🔐 Runtime permission handling for location
- 📍 Show GPS enable dialog (Google-style)
- 📦 Jetpack Compose UI with state handling
- 🔄 Weather data fetched using Retrofit + Kotlin Flow
- 🔁 UI state handling with `UIState<T>` sealed class
- 🧪 MVVM architecture using Hilt + ViewModel + Repository

---

## 📸 Screenshots

| Home Screen | Search City |
|-------------|-------------|
| ![home](screenshots/home.png) | ![search](screenshots/search.png) |

---

## 🛠 Tech Stack

| Tech         | Description                         |
|--------------|-------------------------------------|
| Jetpack Compose | Declarative UI framework           |
| Hilt         | Dependency Injection                |
| Retrofit     | HTTP Networking                     |
| Kotlin Flow  | Reactive streams for data loading   |
| Fused Location | Get current location using GPS     |

---

## 🔑 APIs Used

- [OpenWeatherMap One Call API (v2.5)](https://openweathermap.org/api/one-call-api)

---

## 📦 Project Structure

├── data/
│ └── api/ # Retrofit API interface
│ └── repository/ # WeatherRepository
├── di/ # Hilt modules
├── ui/
│ └── screens/ # HomeScreen, WeatherSearchScreen
│ └── components/ # Reusable composables (e.g., SearchBox)
├── utils/ # GPS check, Location utilities
├── viewmodel/ # HomeViewModel
└── MainActivity.kt # Entry point


## Tools Used
ChatGPT is used to generate figma screen for weather forcasting

