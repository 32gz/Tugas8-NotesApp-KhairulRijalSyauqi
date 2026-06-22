This is a Kotlin Multiplatform project targeting Android, iOS.

<<<<<<< HEAD
A notes application built with Kotlin Multiplatform featuring Koin Dependency Injection, platform-specific features, and clean architecture.
=======
* [/iosApp](./iosApp/iosApp) contains an iOS application. Even if you’re sharing your UI with Compose Multiplatform,
  you need this entry point for your iOS app. This is also where you should add SwiftUI code for your project.
>>>>>>> parent of a25830f (Update README with architecture diagrams and screenshot placeholders)

* [/shared](./shared/src) is for code that will be shared across your Compose Multiplatform applications.
  It contains several subfolders:
  - [commonMain](./shared/src/commonMain/kotlin) is for code that’s common for all targets.
  - Other folders are for Kotlin code that will be compiled for only the platform indicated in the folder name.
    For example, if you want to use Apple’s CoreCrypto for the iOS part of your Kotlin app,
    the [iosMain](./shared/src/iosMain/kotlin) folder would be the right place for such calls.
    Similarly, if you want to edit the Desktop (JVM) specific part, the [jvmMain](./shared/src/jvmMain/kotlin)
    folder is the appropriate location.

<<<<<<< HEAD
- **CRUD Notes**: Create, read, update, and delete notes
- **Koin DI**: Full Dependency Injection setup across the app
- **Device Info**: Platform-specific device information via expect/actual
- **Network Monitor**: Real-time network connectivity indicator
- **Dark Theme**: Toggle between light and dark mode
- **Search & Sort**: Find and organize notes
=======
### Running the apps
>>>>>>> parent of a25830f (Update README with architecture diagrams and screenshot placeholders)

Use the run configurations provided by the run widget in your IDE's toolbar. You can also use these commands and options:

<<<<<<< HEAD
### Clean Architecture

```
┌─────────────────────────────────────────────────────────────┐
│                    PRESENTATION LAYER                         │
│   Screens (NotesList, AddNote, Settings, etc.)              │
│   ViewModels (NotesViewModel, SettingsViewModel)            │
│   UI Components (NoteCard, NetworkStatusIndicator)          │
└─────────────────────────────────────────────────────────────┘
                              ↓
┌─────────────────────────────────────────────────────────────┐
│                      DOMAIN LAYER                           │
│   Models (Note, UserSettings)                               │
│   Interfaces (NotesRepository, DeviceInfo, NetworkMonitor) │
└─────────────────────────────────────────────────────────────┘
                              ↓
┌─────────────────────────────────────────────────────────────┐
│                       DATA LAYER                            │
│   Repository (NotesRepositoryImpl)                          │
│   Database (SQLDelight)                                     │
│   DataStore (Preferences)                                   │
└─────────────────────────────────────────────────────────────┘
```

### Koin Dependency Injection

```
┌─────────────────────────────────────────────────────────────┐
│                     KOIN MODULES                            │
├─────────────────────────────────────────────────────────────┤
│  AppModules.getCommonModules()                              │
│  ├── dataModule                                             │
│  │   └── single<NotesRepository>                            │
│  └── viewModelModule                                        │
│        ├── factory<NotesViewModel>                          │
│        ├── factory<SettingsViewModel>                       │
│        └── factory<NoteDetailViewModel>                     │
├─────────────────────────────────────────────────────────────┤
│  androidModule / iosModule                                  │
│  ├── single<DeviceInfo>        (actual impl)               │
│  ├── single<NetworkMonitor>   (actual impl)               │
│  └── single<SettingsDataStore>                             │
└─────────────────────────────────────────────────────────────┘
```

### expect/actual Pattern

```
┌─────────────────────────────────────────────────────────────┐
│                   expect/actual PATTERN                      │
├─────────────────────────────────────────────────────────────┤
│                                                              │
│  ┌─────────────────────┐     ┌─────────────────────┐        │
│  │   DeviceInfo        │     │   NetworkMonitor    │        │
│  │   (commonMain)      │     │   (commonMain)       │        │
│  │   expect interface │     │   expect interface  │        │
│  └──────────┬──────────┘     └──────────┬──────────┘        │
│             │                            │                   │
│  ┌──────────▼──────────┐     ┌──────────▼──────────┐        │
│  │   AndroidDeviceInfo │     │  AndroidNetwork     │        │
│  │   (androidMain)     │     │  Monitor (android)  │        │
│  │   - platform        │     │  ConnectivityManager │        │
│  │   - osVersion       │     └─────────────────────┘        │
│  │   - deviceModel     │                                     │
│  │   - appVersion      │     ┌─────────────────────┐        │
│  └─────────────────────┘     │  IOSNetworkMonitor  │        │
│                               │  (iosMain)           │        │
│  ┌─────────────────────┐     │  Flow(true)         │        │
│  │   IOSDeviceInfo      │     └─────────────────────┘        │
│  │   (iosMain)         │                                     │
│  │   - platform        │                                     │
│  │   - osVersion       │                                     │
│  │   - deviceModel     │                                     │
│  │   - appVersion      │                                     │
│  └─────────────────────┘                                     │
│                                                              │
└─────────────────────────────────────────────────────────────┘
```

## Tech Stack

| Component | Technology |
|-----------|------------|
| Framework | Kotlin Multiplatform (KMP) |
| UI | Jetpack Compose Multiplatform |
| Dependency Injection | Koin 4.0 |
| Database | SQLDelight 2.3.2 |
| Preferences | DataStore |
| Navigation | Navigation Compose |

## Screenshots

### Android

| Main Screen (Network Indicator) | Settings Screen (Device Info) |
|----------------------------------|-------------------------------|
| ![Main Screen](screenshots/android/main_network_indicator.png) | ![Settings](screenshots/android/settings_device_info.png) |

### iOS

| Main Screen (Network Indicator) | Settings Screen (Device Info) |
|----------------------------------|-------------------------------|
| ![Main Screen](screenshots/ios/main_network_indicator.png) | ![Settings](screenshots/ios/settings_device_info.png) |

---

## Project Structure

```
composeApp/src/
├── commonMain/kotlin/
│   ├── App.kt                          # Main composable with Koin injection
│   ├── DeviceInfo.kt                   # expect interface
│   ├── NetworkMonitor.kt               # expect interface
│   ├── model/                          # Data models
│   ├── repository/                     # Repository implementation
│   ├── datastore/                      # DataStore preferences
│   ├── screens/                        # UI screens
│   │   ├── NotesListScreen.kt          # Main screen with network indicator
│   │   └── SettingsScreen.kt           # Device info display
│   ├── viewmodel/                      # ViewModels with DI
│   ├── navigation/                     # Navigation setup
│   ├── ui/components/                  # UI components
│   │   └── NetworkStatusIndicator.kt   # Network status badge
│   └── di/                             # Koin modules
│       ├── AppModules.kt
│       ├── DataModule.kt
│       └── ViewModelModule.kt
├── androidMain/kotlin/
│   ├── App.android.kt                  # Koin initialization
│   ├── Platform.android.kt             # DeviceInfo actual
│   ├── NetworkMonitor.android.kt       # NetworkMonitor actual
│   └── di/AndroidModule.kt
└── iosMain/kotlin/
    ├── App.ios.kt                      # Koin initialization
    ├── Platform.ios.kt                 # DeviceInfo actual
    ├── NetworkMonitor.ios.kt           # NetworkMonitor actual
    └── di/IosModule.kt
```

## How to Add Screenshots

1. Create folder structure:
   ```
   screenshots/
   ├── android/
   │   ├── main_network_indicator.png
   │   └── settings_device_info.png
   └── ios/
       ├── main_network_indicator.png
       └── settings_device_info.png
   ```

2. Capture screenshots:
   - **Main Screen**: Take photo showing the network status indicator (green=online, red=offline)
   - **Settings Screen**: Take photo showing device info (Platform, OS Version, Device, App Version)

3. Save screenshots to the folders above

## Video Demo (45 Detik)

Record a demo video showing:

| Time | Content |
|------|---------|
| 0-10s | Show Koin DI initialization in App.android.kt / App.ios.kt |
| 10-25s | Navigate to Settings → Show Device Info (Platform, OS, Device, Version) |
| 25-35s | Show Network Indicator (Online - green) on main screen |
| 35-45s | Toggle Airplane Mode → Show Network Indicator (Offline - red) |

---

## Learn More

- [Kotlin Multiplatform](https://www.jetbrains.com/help/kotlin-multiplatform-dev/get-started.html)
- [Koin Documentation](https://insert-koin.io/docs/)
- [Jetpack Compose](https://developer.android.com/compose)
=======
- Android app: `./gradlew :androidApp:assembleDebug`
- iOS app: open the [/iosApp](./iosApp) directory in Xcode and run it from there.

### Running tests

Use the run button in your IDE's editor gutter, or run tests using Gradle tasks:

- Android tests: `./gradlew :shared:testAndroidHostTest`
- iOS tests: `./gradlew :shared:iosSimulatorArm64Test`

---

Learn more about [Kotlin Multiplatform](https://www.jetbrains.com/help/kotlin-multiplatform-dev/get-started.html)…
>>>>>>> parent of a25830f (Update README with architecture diagrams and screenshot placeholders)
