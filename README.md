# 📱 MobileTask - Pokemon App

A modern Android application built with **Kotlin + Jetpack Compose** that consumes data from the public API **PokeAPI**.
---

## 🚀 Features

### 🔐 Authentication (Local)

* Login & Register using **Local Database (Room/SQLite)**
* User session management
* Profile page displaying logged-in user data

### 🏠 Home Screen

* Display **list of 10 Pokémon**
* Infinite scroll / pagination (load more on scroll)
* Search Pokémon by name
* Navigate to detail screen on click

### 🔍 Pokemon Detail

* Show:

    * Pokémon Name
    * Pokémon Abilities, etc

### 👤 Profile

* Displays current logged-in user information

### 📡 Offline Support (Optional Bonus)

* Pokémon data cached locally
* Accessible when offline

---

## 🧱 Tech Stack

### 📌 Architecture

* Clean Architecture (Layered)
* MVVM Pattern

### 🛠️ Libraries & Tools

| Category       | Technology        |
| -------------- | ----------------- |
| Language       | Kotlin            |
| UI             | Jetpack Compose   |
| Networking     | Retrofit          |
| Async          | Kotlin Coroutines |
| Local Database | Room / SQLite     |
| API Source     | PokeAPI           |

---

## 📂 Project Structure

```
com.fandiaspraja.pokemonapp
│
├── data
│   ├── remote (API service, DTO)
│   ├── local (Room database, DAO)
│   └── repository
│
├── domain
│   ├── model
│   ├── repository interface
│   └── usecase
│
├── presentation
│   ├── ui (Compose screens)
│   ├── viewmodel
│   └── state
│
└── di (Dependency Injection)
```

---

## 🔑 Authentication Flow

1. User registers using email & password
2. Data stored in local database
3. User logs in
4. Session stored locally
5. Redirect to Home screen

---

## 🔄 Pokemon Flow

1. Fetch data from API using Retrofit
2. Map response → Domain Model
3. Cache to local database (Room)
4. Display in Compose UI
5. Load more when user scrolls

---

## 🔍 Search Flow

* User types Pokémon name
* App fetches detail from API
* Navigate directly to Detail screen

---

## ▶️ How to Run

1. Clone repository

```bash
git clone https://github.com/fandiaspraja/pokemon.git
```

2. Open in Android Studio

3. Sync Gradle

4. Run on emulator/device

---

## 👨‍💻 Author

**Fikry Andias Praja**
Mobile Developer (Android & Flutter)
📧 [fandiaspraja@gmail.com](mailto:fandiaspraja@gmail.com)