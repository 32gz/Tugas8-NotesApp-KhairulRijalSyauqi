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

## Struktur Proyek

```
shared/src/
├── commonMain/kotlin/          # Kode yang dibagikan ke semua platform
│   ├── App.kt                 # Main composable
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
