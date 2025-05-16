
# Epic 1: Core App Setup & Platform Integration Framework (v0.2)

**Goal:** Establish the Kotlin Multiplatform (KMP) project structure using the existing SubLingo repository, create basic UI shells for Android (Jetpack Compose) and iOS (SwiftUI), implement initial stubs for platform-specific integration points, and ensure correct project configuration including package naming. This epic lays the groundwork for all subsequent feature development.

## Story List

### Story 1.1: Validate KMP Project Structure, Dependencies, and Configuration

-   **User Story / Goal:** As a Developer, I want to validate the existing KMP project structure (`https://github.com/moclam1905/SubLingo`), configure basic dependencies, and ensure correct project-wide package naming, so that I have a stable and consistently configured foundation for shared and platform-specific code.
-   **Detailed Requirements:**
    * Clone the SubLingo repository (`https://github.com/moclam1905/SubLingo`).
    * Verify the standard KMP module structure (e.g., `shared`, `composeApp`, `iosApp`) as per the Compose Multiplatform template.
    * Ensure `shared` module can contain common Kotlin code.
    * Ensure `composeApp` (for Android) can depend on `shared` and build a basic Android application.
    * Ensure `iosApp` can depend on `shared` and build a basic iOS application.
    * Set up basic KMP dependencies for networking (Ktor) and serialization (Kotlinx.serialization) in the `shared` module, as per `docs/tech-stack.md`.
    * Confirm successful build and run of empty shell applications on both Android emulator/device and iOS simulator/device.
    * **Refactor base package names** in `composeApp`, `iosApp` (Bundle ID), and `shared` modules from placeholders (e.g., `com.myapplication`) to `com.nguyenmoclam.sublingo` (and its sub-packages like `com.nguyenmoclam.sublingo.android`, `com.nguyenmoclam.sublingo.shared`) as per `docs/project-structure.md`.
-   **Acceptance Criteria (ACs):**
    * AC1: The project `https://github.com/moclam1905/SubLingo` is successfully cloned and opened in Android Studio / IntelliJ IDEA.
    * AC2: The `shared`, `composeApp`, and `iosApp` modules are correctly configured and recognized by the IDE.
    * AC3: A simple "Hello World" type function in `shared` (within `com.nguyenmoclam.sublingo.shared` package) can be called from both `composeApp` (Android) and `iosApp`.
    * AC4: Both `composeApp` (Android) and `iosApp` can be built and launched successfully on their respective targets, displaying a blank screen or a placeholder view.
    * AC5: Ktor and Kotlinx.serialization libraries are added as dependencies to the `shared` module and are resolvable.
    * AC6: Base package names throughout the project (Kotlin/Java sources, `AndroidManifest.xml`, iOS Bundle ID) are updated to reflect `com.nguyenmoclam.sublingo` as the root.

---

### Story 1.2: Implement Basic Android App Shell with Jetpack Compose

-   **User Story / Goal:** As a Developer, I want to create a minimal Android application shell using Jetpack Compose within the `composeApp` module, so that there's an entry point and basic UI structure for the Android app.
-   **Detailed Requirements:**
    * Within `composeApp/src/androidMain/kotlin/com/nguyenmoclam/sublingo/android/`, set up a main activity (`MainActivity.kt`).
    * Implement a simple Jetpack Compose screen (e.g., displaying "SubLingo Android App - MVP").
    * Ensure the app targets Android 6.0 (API level 23) and above, as specified in `docs/tech-stack.md`.
    * Use Jetpack Compose for all UI elements.
-   **Acceptance Criteria (ACs):**
    * AC1: The `composeApp` (Android) launches and displays a screen with the text "SubLingo Android App - MVP" (or similar placeholder).
    * AC2: The screen is implemented using Jetpack Compose.
    * AC3: The app's `minSdkVersion` in `composeApp/build.gradle.kts` is set to 23.

---

### Story 1.3: Implement Basic iOS App Shell with SwiftUI

-   **User Story / Goal:** As a Developer, I want to create a minimal iOS application shell using SwiftUI within the `iosApp` module, so that there's an entry point and basic UI structure for the iOS app.
-   **Detailed Requirements:**
    * Within `iosApp/iosApp/`, set up the main app structure (`SubLingoApp.swift`).
    * Implement a simple SwiftUI view (`ContentView.swift` or similar, e.g., displaying "SubLingo iOS App - MVP").
    * Ensure the app targets iOS 16.0 and above, as specified in `docs/tech-stack.md`.
    * Use SwiftUI for all UI elements.
-   **Acceptance Criteria (ACs):**
    * AC1: The `iosApp` launches and displays a screen with the text "SubLingo iOS App - MVP" (or similar placeholder).
    * AC2: The screen is implemented using SwiftUI.
    * AC3: The app's deployment target is set to iOS 16.0 in the Xcode project settings.

---

### Story 1.4: Implement Android Floating Bubble Service Stub

-   **User Story / Goal:** As a Developer, I want to implement a basic Android floating bubble service (`FloatingBubbleService.kt`) that can be manually toggled, so that the foundation for the in-app translation trigger is established.
-   **Detailed Requirements:**
    * Create an Android Service within `composeApp` for managing the floating bubble.
    * Implement functionality to show and hide the bubble (e.g., via a button in the main app's `MainActivity.kt` UI for now).
    * The bubble should be a simple, draggable view.
    * Request the "Draw Over Other Apps" (`SYSTEM_ALERT_WINDOW`) permission. The app should guide the user to grant this permission if not already granted (basic guidance for stub, full onboarding in Epic 5).
    * The bubble itself does not need to perform any translation actions yet.
    * The bubble should not yet attempt to detect which app is in the foreground.
-   **Acceptance Criteria (ACs):**
    * AC1: A floating bubble can be displayed on the screen over other apps.
    * AC2: The bubble can be dismissed or hidden via the test toggle.
    * AC3: The bubble is draggable.
    * AC4: The app requests the "Draw Over Other Apps" permission if not already granted, and guides the user to the settings page.
    * AC5: The app provides a mechanism (e.g., a button in its main UI) to toggle the bubble's visibility for testing.

---

### Story 1.5: Implement iOS Action Extension Stub

-   **User Story / Goal:** As a Developer, I want to create a basic iOS Action Extension stub (`SubLingoActionExtension`), so that the app can appear as an option when text is selected and shared within other apps (e.g., Reddit).
-   **Detailed Requirements:**
    * Create a new Action Extension target in the `iosApp` Xcode project.
    * Configure the extension to accept selected text input (`NSExtensionActivationRule` for `public.plain-text`).
    * When invoked, the extension should display a placeholder SwiftUI UI (e.g., "SubLingo Action Extension: [Received Text]").
    * No actual translation processing is required in this story.
-   **Acceptance Criteria (ACs):**
    * AC1: The "SubLingo" Action Extension appears in the iOS Share sheet when text is selected in a compatible app (e.g., Notes, Safari).
    * AC2: Selecting the extension launches its UI.
    * AC3: The extension's UI displays a placeholder message and the text that was shared to it.

---

### Story 1.6: Implement iOS Share Extension Stub for Images

-   **User Story / Goal:** As a Developer, I want to create a basic iOS Share Extension stub (`SubLingoShareExtension`) for images, so that the app can appear as an option when a screenshot/image is shared.
-   **Detailed Requirements:**
    * Create a new Share Extension target in the `iosApp` Xcode project.
    * Configure the extension to accept image input (`NSExtensionActivationRule` for image types like `public.image`).
    * When invoked with an image, the extension should display a placeholder SwiftUI UI (e.g., "SubLingo Share Extension: Image Received").
    * No actual OCR or translation processing is required in this story.
-   **Acceptance Criteria (ACs):**
    * AC1: The "SubLingo" Share Extension appears in the iOS Share sheet when an image (e.g., a screenshot) is shared.
    * AC2: Selecting the extension launches its UI.
    * AC3: The extension's UI displays a placeholder message indicating an image was received.

---

### Story 1.7: Define Initial KMP Communication Interfaces (Expect/Actual)

-   **User Story / Goal:** As a Developer, I want to define initial `expect`/`actual` declarations or interfaces in the KMP `shared` module for basic platform interactions, so that a clear contract for future shared logic to platform-specific feature communication is established.
-   **Detailed Requirements:**
    * Identify 1-2 basic interactions that will be needed later. Initial candidates for `expect`/`actual` declarations include a `DictionaryFileProvider` (to get the path/stream to the bundled dictionary for SQLDelight) and a `TtsPlayer` (to abstract platform TTS engines).
    * Define `expect` declarations in the `commonMain` source set of the `shared` module (e.g., in `shared/src/commonMain/kotlin/com/nguyenmoclam/sublingo/shared/platform/`) for these interactions.
    * Provide minimal `actual` implementations in `androidMain` and `iosMain` source sets (e.g., in `shared/src/androidMain/kotlin/com/nguyenmoclam/sublingo/shared/platform/` and `shared/src/iosMain/kotlin/com/nguyenmoclam/sublingo/shared/platform/`) that log a message or return a placeholder value.
    * This story is about setting up the mechanism, not full implementation of these providers/players.
-   **Acceptance Criteria (ACs):**
    * AC1: At least one `expect` class or function (e.g., `DictionaryFileProvider` or `TtsPlayer`) is defined in the `commonMain` source set of the `shared` module.
    * AC2: Corresponding `actual` implementations are provided in `androidMain` and `iosMain` source sets.
    * AC3: The project compiles successfully with these expect/actual declarations.
    * AC4: A simple test call from common code (e.g., a unit test in `commonTest`) to an `expect` function can successfully invoke the `actual` implementations, verified by logging or a simple return value.

## Change Log

| Change        | Date       | Version | Description                                                                                                | Author          |
| ------------- | ---------- | ------- | ---------------------------------------------------------------------------------------------------------- | --------------- |
| Initial Draft | 2025-05-15 | 0.1     | First draft of Epic 1 stories for core setup.                                                              | PM Agent        |
| Update        | 2025-05-15 | 0.2     | Added AC for package name refactoring in Story 1.1. Clarified `expect`/`actual` candidates in Story 1.7. | Architect Agent |

