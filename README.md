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

### iOS
Buka folder `/iosApp` di Xcode dan jalankan dari sana.

## Screenshots

Tambahkan screenshot aplikasi di folder:
```
screenshots/
├── android/
│   ├── main.png
│   ├── settings.png
│   ├── network_online.png      # Status jaringan online
│   └── network_offline.png     # Status jaringan offline
└── ios/
    ├── main.png
    ├── settings.png
    ├── network_online.png      # Status jaringan online
    └── network_offline.png     # Status jaringan offline
```

**Screenshot yang diperlukan:**
- `settings.png` - Menampilkan Device Info (Platform, OS Version, Device, App Version)
- `network_online.png` - Indikator jaringan aktif (hijau)
- `network_offline.png` - Indikator jaringan mati (merah)

## Video Demo

Rekam video demo (45 detik) yang menunjukkan:

| Waktu | Konten |
|-------|--------|
| 0-15 detik | Inisialisasi Koin DI di App.android.kt / App.ios.kt |
| 15-25 detik | Navigasi ke Settings → Tampilkan Device Info |
| 25-35 detik | Tampilkan Network Indicator (Online - hijau) di layar utama |
| 35-45 detik | Aktifkan Airplane Mode → Tampilkan Network Indicator (Offline - merah) |

Simpan video demo di:
```
screenshots/
├── android/
│   └── demo.mp4
└── ios/
    └── demo.mp4
```
