# Tugas 8 - Notes App

* Nama : Khairul Rijal Syauqi
* NIM  : 123140143

Aplikasi catatan sederhana dengan Kotlin Multiplatform untuk Android dan iOS.

## Fitur

- **CRUD Catatan**: Buat, lihat, edit, dan hapus catatan
- **Info Perangkat**: Menampilkan informasi perangkat (Platform, OS, Model, Versi App)
- **Status Jaringan**: Indikator online/offline
- **Dark Theme**: Toggle tema gelap/terang
- **Pencarian & Sortir**: Cari dan urutkan catatan
- **Dependency Injection**: Menggunakan Koin

## Tech Stack

| Komponen | Teknologi |
|----------|------------|
| Framework | Kotlin Multiplatform (KMP) |
| UI | Jetpack Compose Multiplatform |
| DI | Koin 4.0 |
| Database | SQLDelight |
| Preferences | DataStore |
| Navigation | Navigation Compose |

## Architecture

```
┌─────────────────────────────────────────────────────────────┐
│                    PRESENTATION LAYER                       │
│   Screens (NotesList, AddNote, Settings, dll)              │
│   ViewModels (NotesViewModel, SettingsViewModel)            │
│   UI Components (NoteCard, NetworkStatusIndicator)        │
└─────────────────────────────────────────────────────────────┘
                              ↓
┌─────────────────────────────────────────────────────────────┐
│                      DOMAIN LAYER                           │
│   Models (Note, UserSettings)                             │
│   Interfaces (NotesRepository, DeviceInfo, NetworkMonitor)  │
└─────────────────────────────────────────────────────────────┘
                              ↓
┌─────────────────────────────────────────────────────────────┐
│                       DATA LAYER                            │
│   Repository (NotesRepositoryImpl)                         │
│   Database (SQLDelight)                                     │
│   DataStore (Preferences)                                   │
└─────────────────────────────────────────────────────────────┘
```

## expect/actual Pattern

```
DeviceInfo (commonMain)          NetworkMonitor (commonMain)
        │                                │
   expect interface                 expect interface
        │                                │
   ┌────┴────┐                    ┌────┴────┐
   ▼         ▼                    ▼         ▼
Android   iOS                Android     iOS
(actual) (actual)            (actual)   (actual)
```

## Struktur Proyek

```
shared/src/
├── commonMain/kotlin/          # Kode yang dibagikan ke semua platform
│   ├── App.kt                 # Main composable
│   ├── DeviceInfo.kt          # expect interface
│   ├── NetworkMonitor.kt      # expect interface
│   ├── model/                  # Data model (Note, UserSettings)
│   ├── repository/             # Repository
│   ├── datastore/              # DataStore preferences
│   ├── screens/                # UI screens
│   ├── viewmodel/              # ViewModels
│   ├── navigation/             # Navigation
│   ├── ui/components/          # Komponen UI
│   └── di/                     # Koin modules
├── androidMain/kotlin/         # Kode spesifik Android
└── iosMain/kotlin/            # Kode spesifik iOS
```

## Cara Menjalankan

### Android
```bash
./gradlew :androidApp:assembleDebug
```
Lalu install APK yang dihasilkan.

## Screenshots

* Device Info

![gambar](https://github.com/32gz/Tugas8-NotesApp-KhairulRijalSyauqi/blob/main/Screenshot%202026-06-22%20145818.png)

* Indikator jaringan aktif

![gambar](https://github.com/32gz/Tugas8-NotesApp-KhairulRijalSyauqi/blob/main/Screenshot%202026-06-22%20145843.png)

* Indikator jaringan mati

![gambar](https://github.com/32gz/Tugas8-NotesApp-KhairulRijalSyauqi/blob/main/Screenshot%202026-06-22%20145914.png)


## Video Demo

**[Watch Demo Video](https://github.com/user-attachments/assets/19af2be3-e00e-4340-a4f0-3eda90bb87f8)**




