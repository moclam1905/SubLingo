
# SubLingo Project Structure

This document outlines the standard directory and file structure for the SubLingo project. The project is based on the Kotlin Compose Multiplatform template, as seen in `https://github.com/moclam1905/SubLingo`. The base package name for Kotlin code will be `com.nguyenmoclam.sublingo`.

## Top-Level Directory Structure

```plaintext
sublingo/
├── .github/                    # CI/CD workflows (e.g., GitHub Actions) - Post-MVP
│   └── workflows/
│       └── main.yml
├── .gradle/                    # Gradle wrapper files and caches (gitignored)
├── .idea/                      # IDE-specific settings (gitignored)
├── composeApp/                 # Houses Android-specific code and resources
│   ├── build.gradle.kts        # Build script for the composeApp module
│   └── src/
│       ├── androidMain/
│       │   ├── AndroidManifest.xml
│       │   └── kotlin/com/nguyenmoclam/sublingo/
│       │       ├── MainActivity.kt              # Main entry point for the Android app
│       │       ├── SubLingoApplication.kt     # Custom Application class (if needed)
│       │       ├── core/                      # Core Android functionalities
│       │       │   └── ForegroundAppDetector.kt # Detects foreground app (UsageStatsManager)
│       │       ├── service/                   # Android Services
│       │       │   ├── FloatingBubbleService.kt # Manages the floating bubble
│       │       │   └── SubLingoAccessibilityService.kt # (Conditional - if pursued post-MVP)
│       │       ├── ocr/                       # OCR specific to Android
│       │       │   └── MlKitOcrProvider.kt    # Implements OCR using ML Kit
│       │       ├── tts/                       # TTS specific to Android
│       │       │   └── AndroidTtsProvider.kt  # Implements TTS using native Android engine
│       │       ├── ui/                        # Jetpack Compose UI
│       │       │   ├── theme/                 # AppTheme, Color, Typography
│       │       │   ├── bubble/                # UI for the floating bubble
│       │       │   │   └── BubbleContent.kt
│       │       │   ├── settings/              # UI for the settings screen
│       │       │   │   └── SettingsScreen.kt
│       │       │   └── onboarding/            # UI for onboarding flows
│       │       │       └── OnboardingScreen.kt
│       │       └── util/                      # Android specific utility classes/functions
│       ├── commonMain/ # (Rarely used directly in composeApp for UI in this project type)
│       │   └── kotlin/ # (UI code is typically in androidMain within composeApp)
│       └── androidUnitTest/
│           └── kotlin/com/nguyenmoclam/sublingo/
│               └── ComposeAppAndroidUnitTest.kt   # Placeholder for Android unit tests
├── build/                      # Build output directory (project-level) - gitignored
├── docs/                       # Project documentation (PRD, Arch, Epics, etc.)
│   ├── architecture.md
│   ├── prd.md
│   ├── project-analysis.md
│   ├── tech-stack.md
│   ├── project-structure.md    # This file
│   ├── coding-standards.md
│   ├── data-models.md
│   ├── api-reference.md
│   ├── environment-vars.md
│   ├── testing-strategy.md
│   ├── privacy-policy.md
│   ├── epic1.md
│   ├── epic2.md
│   ├── epic3.md
│   ├── epic4.md
│   ├── epic5.md
│   └── epic6.md
├── gradle/
│   ├── libs.versions.toml      # Gradle version catalog
│   └── wrapper/
│       ├── gradle-wrapper.jar
│       └── gradle-wrapper.properties
├── iosApp/                     # iOS specific application module
│   ├── build.gradle.kts        # Links shared module to iOS app
│   ├── Configuration/
│   │   └── Config.xcconfig
│   ├── iosApp.xcodeproj/       # Xcode project file
│   │   └── project.pbxproj
│   ├── iosApp/                 # iOS source code and resources (Bundle ID: com.nguyenmoclam.sublingo)
│   │   ├── Assets.xcassets     # App icons, images
│   │   ├── Preview Content/
│   │   │   └── Preview Assets.xcassets
│   │   ├── ContentView.swift     # Main SwiftUI view for the container app
│   │   ├── Info.plist
│   │   ├── SubLingoApp.swift     # Main SwiftUI App structure (using '@main')
│   │   ├── core/                 # Core iOS functionalities
│   │   ├── ocr/                  # OCR specific to iOS
│   │   │   └── VisionOcrProvider.swift # Implements OCR using Vision Framework
│   │   ├── tts/                  # TTS specific to iOS
│   │   │   └── IOSTtsProvider.swift    # Implements TTS using AVSpeechSynthesizer
│   │   ├── ui/                   # SwiftUI Views
│   │   │   ├── settings/         # UI for the settings screen
│   │   │   │   └── SettingsView.swift
│   │   │   └── onboarding/       # UI for onboarding flows
│   │   │       └── OnboardingView.swift
│   │   ├── extensions/           # Action and Share extension code
│   │   │   ├── SubLingoActionExtension/    # (Bundle ID: com.nguyenmoclam.sublingo.ActionExtension)
│   │   │   │   ├── ActionViewController.swift
│   │   │   │   └── Info.plist
│   │   │   └── SubLingoShareExtension/     # (Bundle ID: com.nguyenmoclam.sublingo.ShareExtension)
│   │   │   │   ├── ShareViewController.swift
│   │   │   │   └── Info.plist
│   │   └── util/                 # iOS specific utility classes/functions
│   └── iosApp.xcworkspace/     # Xcode workspace (if using CocoaPods etc.)
│       └── xcshareddata/
├── shared/                     # KMP shared module (core logic)
│   ├── build.gradle.kts        # Shared module build script
│   └── src/
│       ├── commonMain/
│       │   └── kotlin/com/nguyenmoclam/sublingo/ # Platform-agnostic Kotlin code
│       │   │   ├── data/                   # Data layer: models, DTOs, mappers, repository interfaces
│       │   │   │   ├── local/              # Offline dictionary related
│       │   │   │   │   ├── dictionary.sq     # SQLDelight schema and queries for offline dictionary
│       │   │   │   │   └── model/          # Data models for dictionary entries (e.g., WordEntity.kt)
│       │   │   │   ├── remote/             # Online translation API related
│       │   │   │   │   ├── model/          # DTOs for API requests/responses (e.g., TranslationRequest.kt)
│       │   │   │   │   └── OpenAIRemoteDataSource.kt # Ktor client for OpenAI/OpenRouter
│       │   │   │   └── repository/         # Repository implementations
│       │   │   │       └── OfflineDictionaryRepositoryImpl.kt
│       │   │   │       └── OnlineTranslationRepositoryImpl.kt
│       │   │   ├── domain/                 # Core business logic and use cases
│       │   │   │   ├── model/              # Domain entities (e.g., TranslationResult.kt, WordDefinition.kt)
│       │   │   │   ├── repository/         # Repository interfaces (e.g., OfflineDictionaryRepository.kt)
│       │   │   │   └── usecase/            # Use cases/Interactors (e.g., GetWordDefinitionUseCase.kt)
│       │   │   ├── presentation/           # Presentation logic / ViewModels (if shared, e.g., for settings)
│       │   │   │   └── SettingsViewModel.kt  # Example shared ViewModel
│       │   │   ├── cache/                  # In-memory caching logic
│       │   │   │   └── TranslationCache.kt
│       │   │   ├── di/                     # Dependency Injection setup for shared module (e.g., using Kodein, Koin)
│       │   │   │   └── SharedModule.kt
│       │   │   ├── platform/               # Expect declarations for platform-specific services
│       │   │   │   ├── TtsPlayer.kt
│       │   │   │   └── DictionaryFileProvider.kt # To get path to bundled dictionary
│       │   │   ├── util/                   # Common utilities, constants
│       │   │   │   └── Constants.kt
│       │   │   └── AppSettings.kt          # Settings management (Multiplatform-Settings)
│       │   └── resources/                # Shared resources (e.g., the bundled dictionary.db file)
│       │       └── sublingo_dictionary.db  # Placeholder for the actual SQLite dictionary file
│       ├── androidMain/
│       │   └── kotlin/com/nguyenmoclam/sublingo/ # Android 'actual' implementations
│       │       ├── AndroidTtsPlayer.kt
│       │       └── AndroidDictionaryFileProvider.kt
│       ├── iosMain/
│       │   └── kotlin/com/nguyenmoclam/sublingo/   # iOS 'actual' implementations
│       │       ├── IosTtsPlayer.kt
│       │       └── IosDictionaryFileProvider.kt
│       ├── androidUnitTest/ # Shared module specific Android tests (run on JVM)
│       │   └── kotlin/com/nguyenmoclam/sublingo/
│       │       └── ExampleSharedAndroidTest.kt
│       ├── commonTest/
│       │   └── kotlin/com/nguyenmoclam/sublingo/
│       │       └── ExampleSharedCommonTest.kt # (e.g., testing Greeting.kt from starter)
│       └── iosTest/
│           └── kotlin/com/nguyenmoclam/sublingo/ # Shared module specific iOS tests (run on iOS simulator/device)
│               └── ExampleSharedIosTest.kt
├── gradlew                     # Gradle wrapper executable (Linux/macOS)
├── gradlew.bat                 # Gradle wrapper executable (Windows)
├── settings.gradle.kts         # Gradle settings (module includes: ':shared', ':composeApp')
├── build.gradle.kts            # Root project build script (plugins, configurations)
└── README.md                   # Project overview, setup, and usage instructions
```

## Key Directory Descriptions (Package Base: `com.nguyenmoclam.sublingo`)

* **`composeApp/src/androidMain/kotlin/com/nguyenmoclam/sublingo/android/`**: Android-specific application code. This includes `MainActivity`, Jetpack Compose UIs, Android services like `FloatingBubbleService`, and integrations for ML Kit OCR (`MlKitOcrProvider`) and Android TTS (`AndroidTtsProvider`). This module consumes the `shared` module for core logic.
    * `.../android/core/`: Fundamental Android functionalities (e.g., `ForegroundAppDetector`).
    * `.../android/service/`: Android Services (e.g., `FloatingBubbleService`).
    * `.../android/ocr/`: `MlKitOcrProvider` for ML Kit specific OCR implementation.
    * `.../android/tts/`: `AndroidTtsProvider` for native Android TTS.
    * `.../android/ui/`: Jetpack Compose UI code, organized by `theme`, and major features like `bubble`, `settings`, `onboarding`.
* **`iosApp/iosApp/`**: Contains all iOS-specific Swift code, SwiftUI UIs, App Extensions, and platform integrations like Vision OCR (`VisionOcrProvider`) and iOS TTS (`IOSTtsProvider`). This module consumes the `shared` module. The Bundle ID for the app and extensions will be based on `com.nguyenmoclam.sublingo`.
    * `.../iosApp/core/`: Core iOS functionalities.
    * `.../iosApp/ocr/`: `VisionOcrProvider` for Vision Framework specific OCR implementation.
    * `.../iosApp/tts/`: `IOSTtsProvider` for `AVSpeechSynthesizer`.
    * `.../iosApp/ui/`: SwiftUI views, organized by features like `settings`, `onboarding`.
    * `.../iosApp/extensions/`: Code for `SubLingoActionExtension` and `SubLingoShareExtension`.
* **`shared/src/commonMain/kotlin/com/nguyenmoclam/sublingo/`**: Platform-agnostic Kotlin code, forming the core of the KMP module.
    * `data/`: Data handling logic, including:
        * `local/`: For the offline dictionary (SQLDelight schema `dictionary.sq`, data models for entries).
        * `remote/`: For online translation API (DTOs, Ktor client `OpenAIRemoteDataSource`).
        * `repository/`: Implementations of repository interfaces connecting domain to data sources.
    * `domain/`: Core business logic:
        * `model/`: Domain entities (e.g., `TranslationResult`, `WordDefinition`).
        * `repository/`: Interfaces defining contracts for data repositories.
        * `usecase/`: Business use cases/interactors (e.g., `GetWordDefinitionUseCase`, `TranslateSentenceUseCase`).
    * `presentation/`: (Optional) Shared ViewModels/presentation logic if applicable (e.g., `SettingsViewModel`).
    * `cache/`: `TranslationCache` for in-memory caching of translations.
    * `di/`: Dependency injection setup for the shared module (e.g., using Kodein, Koin, or manual DI via a `SharedModule` object/class).
    * `platform/`: `expect` declarations for platform-specific functionalities (e.g., `TtsPlayer`, `DictionaryFileProvider`).
    * `AppSettings.kt`: Manages persistent settings using the `multiplatform-settings` library.
    * `resources/`: Shared resources, primarily the bundled SQLite dictionary file (`sublingo_dictionary.db`).
* **`shared/src/androidMain/kotlin/com/nguyenmoclam/sublingo/`**: `actual` implementations for Android specific `expect` declarations (e.g., `AndroidTtsPlayer`, `AndroidDictionaryFileProvider`).
* **`shared/src/iosMain/kotlin/com/nguyenmoclam/sublingo/`**: `actual` implementations for iOS specific `expect` declarations (e.g., `IosTtsPlayer`, `IosDictionaryFileProvider`).
* **`shared/src/...Test/kotlin/com/nguyenmoclam/sublingo/`**: Unit tests for the shared code, separated by source set (`commonTest`, `androidUnitTest`, `iosTest`).
* **`gradle/libs.versions.toml`**: Centralized management of dependency versions.
* **`docs/`**: All project documentation, including this `project-structure.md` file.

## Notes

* The offline dictionary SQLite file (e.g., `sublingo_dictionary.db`) is planned to be located in `shared/src/commonMain/resources/`. Access to this file will be handled by platform-specific `actual` implementations of the `DictionaryFileProvider` `expect` interface. This aligns with Story 2.1.
* The package name `com.nguyenmoclam.sublingo` is used for the `composeApp` module, and `com.nguyenmoclam.sublingo.shared` for the `shared` module to maintain clear namespacing. iOS Bundle IDs will similarly be based on `com.nguyenmoclam.sublingo`.
* An initial development task will be to refactor the placeholder package names (e.g., `com.myapplication`) currently in the `https://github.com/moclam1905/SubLingo` repository to this defined `com.nguyenmoclam.sublingo.*` structure.
* This structure is designed to support the features outlined in the epics, promoting separation of concerns and clear modularity suitable for development by a team or AI agents.

## Change Log

| Change        | Date       | Version | Description                                                                                         | Author          |
| :------------ | :--------- | :------ | :-------------------------------------------------------------------------------------------------- | :-------------- |
| Initial draft | 2025-05-15 | 0.1     | Initial draft based on KMP standards and PRD needs.                                                 | Architect Agent |
