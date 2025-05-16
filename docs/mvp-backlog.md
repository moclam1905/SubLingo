
# SubLingo MVP Backlog

*Generated: 2025-05-15*

This document outlines the user stories and technical stories planned for the SubLingo Minimum Viable Product (MVP).

## I. User Stories (from Epics)

---
### Epic 1: Core App Setup & Platform Integration Framework (v0.2)
**Goal:** Establish the KMP project structure using the existing SubLingo repository, create basic UI shells for Android (Jetpack Compose) and iOS (SwiftUI), implement initial stubs for platform-specific integration points, and ensure correct project configuration including package naming. This epic lays the groundwork for all subsequent feature development.

**Stories:**

* **Story 1.1: Validate KMP Project Structure, Dependencies, and Configuration**
    * **User Story / Goal:** As a Developer, I want to validate the existing KMP project structure (`https://github.com/moclam1905/SubLingo`), configure basic dependencies, and ensure correct project-wide package naming, so that I have a stable and consistently configured foundation for shared and platform-specific code.
    * **Detailed Requirements:**
        * Clone the SubLingo repository (`https://github.com/moclam1905/SubLingo`).
        * Verify the standard KMP module structure (e.g., `shared`, `composeApp`, `iosApp`) as per the Compose Multiplatform template.
        * Ensure `shared` module can contain common Kotlin code.
        * Ensure `composeApp` (for Android) can depend on `shared` and build a basic Android application.
        * Ensure `iosApp` can depend on `shared` and build a basic iOS application.
        * Set up basic KMP dependencies for networking (Ktor) and serialization (Kotlinx.serialization) in the `shared` module, as per `docs/tech-stack.md`.
        * Confirm successful build and run of empty shell applications on both Android emulator/device and iOS simulator/device.
        * **Refactor base package names** in `composeApp`, `iosApp` (Bundle ID), and `shared` modules from placeholders (e.g., `com.myapplication`) to `com.nguyenmoclam.sublingo` (and its sub-packages like `com.nguyenmoclam.sublingo.android`, `com.nguyenmoclam.sublingo.shared`) as per `docs/project-structure.md`.
    * **Acceptance Criteria (ACs):**
        * AC1: The project `https://github.com/moclam1905/SubLingo` is successfully cloned and opened in Android Studio / IntelliJ IDEA.
        * AC2: The `shared`, `composeApp`, and `iosApp` modules are correctly configured and recognized by the IDE.
        * AC3: A simple "Hello World" type function in `shared` (within `com.nguyenmoclam.sublingo.shared` package) can be called from both `composeApp` (Android) and `iosApp`.
        * AC4: Both `composeApp` (Android) and `iosApp` can be built and launched successfully on their respective targets, displaying a blank screen or a placeholder view.
        * AC5: Ktor and Kotlinx.serialization libraries are added as dependencies to the `shared` module and are resolvable.
        * AC6: Base package names throughout the project (Kotlin/Java sources, `AndroidManifest.xml`, iOS Bundle ID) are updated to reflect `com.nguyenmoclam.sublingo` as the root.

* **Story 1.2: Implement Basic Android App Shell with Jetpack Compose**
    * **User Story / Goal:** As a Developer, I want to create a minimal Android application shell using Jetpack Compose within the `composeApp` module, so that there's an entry point and basic UI structure for the Android app.
    * **Detailed Requirements:**
        * Within `composeApp/src/androidMain/kotlin/com/nguyenmoclam/sublingo/android/`, set up a main activity (`MainActivity.kt`).
        * Implement a simple Jetpack Compose screen (e.g., displaying "SubLingo Android App - MVP").
        * Ensure the app targets Android 6.0 (API level 23) and above, as specified in `docs/tech-stack.md`.
        * Use Jetpack Compose for all UI elements.
    * **Acceptance Criteria (ACs):**
        * AC1: The `composeApp` (Android) launches and displays a screen with the text "SubLingo Android App - MVP" (or similar placeholder).
        * AC2: The screen is implemented using Jetpack Compose.
        * AC3: The app's `minSdkVersion` in `composeApp/build.gradle.kts` is set to 23.

* **Story 1.3: Implement Basic iOS App Shell with SwiftUI**
    * **User Story / Goal:** As a Developer, I want to create a minimal iOS application shell using SwiftUI within the `iosApp` module, so that there's an entry point and basic UI structure for the iOS app.
    * **Detailed Requirements:**
        * Within `iosApp/iosApp/`, set up the main app structure (`SubLingoApp.swift`).
        * Implement a simple SwiftUI view (`ContentView.swift` or similar, e.g., displaying "SubLingo iOS App - MVP").
        * Ensure the app targets iOS 16.0 and above, as specified in `docs/tech-stack.md`.
        * Use SwiftUI for all UI elements.
    * **Acceptance Criteria (ACs):**
        * AC1: The `iosApp` launches and displays a screen with the text "SubLingo iOS App - MVP" (or similar placeholder).
        * AC2: The screen is implemented using SwiftUI.
        * AC3: The app's deployment target is set to iOS 16.0 in the Xcode project settings.

* **Story 1.4: Implement Android Floating Bubble Service Stub**
    * **User Story / Goal:** As a Developer, I want to implement a basic Android floating bubble service (`FloatingBubbleService.kt`) that can be manually toggled, so that the foundation for the in-app translation trigger is established.
    * **Detailed Requirements:**
        * Create an Android Service within `composeApp` for managing the floating bubble.
        * Implement functionality to show and hide the bubble (e.g., via a button in the main app's `MainActivity.kt` UI for now).
        * The bubble should be a simple, draggable view.
        * Request the "Draw Over Other Apps" (`SYSTEM_ALERT_WINDOW`) permission. The app should guide the user to grant this permission if not already granted (basic guidance for stub, full onboarding in Epic 5).
        * The bubble itself does not need to perform any translation actions yet.
        * The bubble should not yet attempt to detect which app is in the foreground.
    * **Acceptance Criteria (ACs):**
        * AC1: A floating bubble can be displayed on the screen over other apps.
        * AC2: The bubble can be dismissed or hidden via the test toggle.
        * AC3: The bubble is draggable.
        * AC4: The app requests the "Draw Over Other Apps" permission if not already granted, and guides the user to the settings page.
        * AC5: The app provides a mechanism (e.g., a button in its main UI) to toggle the bubble's visibility for testing.

* **Story 1.5: Implement iOS Action Extension Stub**
    * **User Story / Goal:** As a Developer, I want to create a basic iOS Action Extension stub (`SubLingoActionExtension`), so that the app can appear as an option when text is selected and shared within other apps (e.g., Reddit).
    * **Detailed Requirements:**
        * Create a new Action Extension target in the `iosApp` Xcode project.
        * Configure the extension to accept selected text input (`NSExtensionActivationRule` for `public.plain-text`).
        * When invoked, the extension should display a placeholder SwiftUI UI (e.g., "SubLingo Action Extension: [Received Text]").
        * No actual translation processing is required in this story.
    * **Acceptance Criteria (ACs):**
        * AC1: The "SubLingo" Action Extension appears in the iOS Share sheet when text is selected in a compatible app (e.g., Notes, Safari).
        * AC2: Selecting the extension launches its UI.
        * AC3: The extension's UI displays a placeholder message and the text that was shared to it.

* **Story 1.6: Implement iOS Share Extension Stub for Images**
    * **User Story / Goal:** As a Developer, I want to create a basic iOS Share Extension stub (`SubLingoShareExtension`) for images, so that the app can appear as an option when a screenshot/image is shared.
    * **Detailed Requirements:**
        * Create a new Share Extension target in the `iosApp` Xcode project.
        * Configure the extension to accept image input (`NSExtensionActivationRule` for image types like `public.image`).
        * When invoked with an image, the extension should display a placeholder SwiftUI UI (e.g., "SubLingo Share Extension: Image Received").
        * No actual OCR or translation processing is required in this story.
    * **Acceptance Criteria (ACs):**
        * AC1: The "SubLingo" Share Extension appears in the iOS Share sheet when an image (e.g., a screenshot) is shared.
        * AC2: Selecting the extension launches its UI.
        * AC3: The extension's UI displays a placeholder message indicating an image was received.

* **Story 1.7: Define Initial KMP Communication Interfaces (Expect/Actual)**
    * **User Story / Goal:** As a Developer, I want to define initial `expect`/`actual` declarations or interfaces in the KMP `shared` module for basic platform interactions, so that a clear contract for future shared logic to platform-specific feature communication is established.
    * **Detailed Requirements:**
        * Identify 1-2 basic interactions that will be needed later. Initial candidates for `expect`/`actual` declarations include a `DictionaryFileProvider` (to get the path/stream to the bundled dictionary for SQLDelight) and a `TtsPlayer` (to abstract platform TTS engines).
        * Define `expect` declarations in the `commonMain` source set of the `shared` module (e.g., in `shared/src/commonMain/kotlin/com/nguyenmoclam/sublingo/shared/platform/`) for these interactions.
        * Provide minimal `actual` implementations in `androidMain` and `iosMain` source sets (e.g., in `shared/src/androidMain/kotlin/com/nguyenmoclam/sublingo/shared/platform/` and `shared/src/iosMain/kotlin/com/nguyenmoclam/sublingo/shared/platform/`) that log a message or return a placeholder value.
        * This story is about setting up the mechanism, not full implementation of these providers/players.
    * **Acceptance Criteria (ACs):**
        * AC1: At least one `expect` class or function (e.g., `DictionaryFileProvider` or `TtsPlayer`) is defined in the `commonMain` source set of the `shared` module.
        * AC2: Corresponding `actual` implementations are provided in `androidMain` and `iosMain` source sets.
        * AC3: The project compiles successfully with these expect/actual declarations.
        * AC4: A simple test call from common code (e.g., a unit test in `commonTest`) to an `expect` function can successfully invoke the `actual` implementations, verified by logging or a simple return value.

---
### Epic 2: Offline Word Translation Feature (v0.2)
**Goal:** Implement the end-to-end offline dictionary functionality. This includes preparing and integrating a "lite" offline dictionary (SQLite), enabling word lookup with definitions and examples via a KMP `OfflineDictionaryService`, and providing Text-to-Speech (TTS) pronunciation via a KMP `TtsPlayer` abstraction on both Android and iOS platforms.

**Stories:**

* **Story 2.1: Prepare and Bundle "Lite" Offline Dictionary Data**
    * **User Story / Goal:** As a Developer, I need to prepare a "lite" English offline dictionary (from Wiktextract processed into SQLite) and bundle it within the application, so that it can be queried by the KMP shared module.
    * **Detailed Requirements:**
        * **Data Sourcing & Processing (Corresponds to Tech Story S.4):**
            * Source English Wiktextract JSONL data.
            * Develop a script to process this data, filter for a "lite" set of common English words (target >10,000 headwords, final count based on size/utility), and extract primary definitions and at least one usage example per definition where available.
            * The script will populate an SQLite database (e.g., `sublingo_dictionary.db`) using the schema defined in `docs/data-models.md` (WordEntity, DefinitionEntity, ExampleEntity tables).
        * **Bundling:**
            * The processed SQLite dictionary file (`sublingo_dictionary.db`) must be bundled with the application in a location accessible by the KMP `shared` module on both Android (e.g., `composeApp/src/main/assets/`) and iOS (e.g., `iosApp/iosApp/Resources/`). The `DictionaryFileProvider` expect/actual (from Story 1.7 & Tech Story S.2) will provide access.
        * **Size Consideration:** The final bundled dictionary should be optimized for size while maintaining utility (target < 50MB if possible).
    * **Acceptance Criteria (ACs):**
        * AC1: A script to process Wiktextract JSONL into the target SQLite schema is created and functional.
        * AC2: The generated `sublingo_dictionary.db` file contains at least 10,000 common English headwords with definitions and examples (where available) and adheres to the schema in `docs/data-models.md`.
        * AC3: The `sublingo_dictionary.db` file is included in both Android (`composeApp` assets) and iOS (`iosApp` bundle resources).
        * AC4: The KMP `shared` module, via the `DictionaryFileProvider` (expect/actual), can obtain a path or stream to access the bundled `sublingo_dictionary.db` file on both platforms.
        * AC5: The final size of `sublingo_dictionary.db` is documented and meets target constraints.

* **Story 2.2: Implement Offline Dictionary Query Service (KMP Shared)**
    * **User Story / Goal:** As a Developer, I want to implement a service (`OfflineDictionaryService`) within the KMP `shared` module that can query the bundled offline SQLite dictionary using SQLDelight, so that I can retrieve definitions and examples for a given English word.
    * **Detailed Requirements:**
        * Create a repository/service class (e.g., `OfflineDictionaryServiceImpl`) in `commonMain` within `com.nguyenmoclam.sublingo.shared.services` or `com.nguyenmoclam.sublingo.shared.data.repository`.
        * This service will receive its SQLDelight `Database` (generated from `dictionary.sq`) or specific query instances via Koin-managed dependency injection (Tech Story S.1 & S.5).
        * It will use the `DictionaryFileProvider` (from Story 1.7 & Tech Story S.2) to initialize the SQLDelight driver with the bundled database.
        * Implement a function (e.g., `findWord(word: String): WordDefinition?`) that takes an English word string as input.
        * The function should use SQLDelight-generated type-safe Kotlin APIs to query the dictionary and return a structured `WordDefinition` object (as defined in `docs/data-models.md`) or null if not found.
        * Queries should be performant.
    * **Acceptance Criteria (ACs):**
        * AC1: The `OfflineDictionaryService` can successfully initialize the SQLDelight driver with the bundled `sublingo_dictionary.db` on both Android and iOS.
        * AC2: Given a word present in the dictionary, the service returns its `WordDefinition` (including definitions, part of speech, phonetic symbol, and examples).
        * AC3: Given a word not in the dictionary, the service returns `null`.
        * AC4: Basic performance test: a lookup for a common word (excluding initial DB load) completes in under 500ms on a representative device/emulator.
        * AC5: SQLDelight setup (`dictionary.sq` and Gradle plugin) is correctly configured (Tech Story S.5).

* **Story 2.3: Integrate Offline Word Lookup in Android UI**
    * **User Story / Goal:** As a User, when I select/copy an English word in Reddit (or provide it to the app), I want to see its offline definition and examples displayed quickly within the SubLingo Android UI (e.g., floating bubble or a dedicated view).
    * **Detailed Requirements:**
        * The Android floating bubble (`FloatingBubbleService.kt`) or a designated UI component within `composeApp` should be able to receive a word (e.g., from clipboard, or later from OCR).
        * Upon receiving a word, the Android app calls the `OfflineDictionaryService` in the KMP `shared` module.
        * Display the retrieved `WordDefinition` (definitions, examples, phonetic symbol) in a clear, readable Jetpack Compose format within the bubble/UI.
        * Handle the "word not found" case (`null` from service) appropriately in the UI.
        * Implement a placeholder UI for pronunciation (button/icon).
    * **Acceptance Criteria (ACs):**
        * AC1: When a word is provided to the Android app's translation interface, it calls the KMP `OfflineDictionaryService`.
        * AC2: The `WordDefinition` for the found word is displayed in the Android UI.
        * AC3: If the word is not found, a "Word not found" (or similar) message is shown.
        * AC4: The UI includes a non-functional placeholder for TTS pronunciation.

* **Story 2.4: Integrate Offline Word Lookup in iOS Action Extension**
    * **User Story / Goal:** As a User, when I select an English word in Reddit and share it to the SubLingo iOS Action Extension, I want to see its offline definition and examples displayed quickly.
    * **Detailed Requirements:**
        * The iOS Action Extension (`SubLingoActionExtension`) receives the selected text (word).
        * The Action Extension calls the `OfflineDictionaryService` in the KMP `shared` module.
        * Display the retrieved `WordDefinition` (definitions, examples, phonetic symbol) in a clear, readable SwiftUI format within the Action Extension's UI.
        * Handle the "word not found" case (`null` from service) appropriately in the UI.
        * Implement a placeholder UI for pronunciation (button/icon).
    * **Acceptance Criteria (ACs):**
        * AC1: When a word is shared to the iOS Action Extension, it calls the KMP `OfflineDictionaryService`.
        * AC2: The `WordDefinition` for the found word is displayed in the Action Extension UI.
        * AC3: If the word is not found, a "Word not found" (or similar) message is shown.
        * AC4: The UI includes a non-functional placeholder for TTS pronunciation.

* **Story 2.5: Implement Text-to-Speech (TTS) for Android via KMP Abstraction**
    * **User Story / Goal:** As a User, after looking up a word on Android, I want to be able to tap a button to hear its pronunciation in a US English accent, using the KMP `TtsPlayer` abstraction.
    * **Detailed Requirements:**
        * The `composeApp` module implements the `actual TtsPlayer` for Android (e.g., `AndroidTtsPlayer.kt` in `shared/src/androidMain/.../platform/`), wrapping Android's native `TextToSpeech` engine. This `actual` implementation should be injectable via Koin.
        * Configure TTS for US English accent (`Locale.US`).
        * When the pronunciation placeholder (from Story 2.3) is tapped for a looked-up word, the Android UI invokes the `speak` method of the KMP `TtsPlayer` instance.
        * Handle TTS engine initialization and potential errors (e.g., language not available) within the `actual` implementation, propagating a status if necessary.
    * **Acceptance Criteria (ACs):**
        * AC1: Tapping the pronunciation button in the Android UI triggers the `speak` method of the KMP `TtsPlayer`.
        * AC2: The `AndroidTtsPlayer` (actual KMP implementation) uses the native Android TTS engine to pronounce the word with a US English accent.
        * AC3: The app correctly handles TTS initialization, and the `AndroidTtsPlayer` can report if TTS is unavailable.

* **Story 2.6: Implement Text-to-Speech (TTS) for iOS via KMP Abstraction**
    * **User Story / Goal:** As a User, after looking up a word in the iOS Action Extension, I want to be able to tap a button to hear its pronunciation in a US English accent, using the KMP `TtsPlayer` abstraction.
    * **Detailed Requirements:**
        * The `iosApp` module (or the `shared/src/iosMain/.../platform/`) implements the `actual TtsPlayer` for iOS (e.g., `IosTtsPlayer.kt`), wrapping iOS's native `AVSpeechSynthesizer`. This `actual` implementation should be injectable via Koin.
        * Configure TTS for US English accent (e.g., `AVSpeechSynthesisVoice(language: "en-US")`).
        * When the pronunciation placeholder (from Story 2.4) is tapped, the iOS Action Extension UI invokes the `speak` method of the KMP `TtsPlayer` instance.
        * Handle TTS engine initialization and potential errors within the `actual` implementation.
    * **Acceptance Criteria (ACs):**
        * AC1: Tapping the pronunciation button in the iOS Action Extension UI triggers the `speak` method of the KMP `TtsPlayer`.
        * AC2: The `IosTtsPlayer` (actual KMP implementation) uses `AVSpeechSynthesizer` to pronounce the word with a US English accent.
        * AC3: The app correctly handles TTS initialization.

* **Story 2.7: Implement Basic Cache for Offline Lookups (KMP Shared)**
    * **User Story / Goal:** As a Developer, I want to implement a simple in-memory cache (`TranslationCache.kt`) for the last N (e.g., 20) offline word lookups in the KMP `shared` module, so that repeated queries for the same words are faster and reduce processing.
    * **Detailed Requirements:**
        * Modify the `OfflineDictionaryService` (or its implementation/repository) to use a caching wrapper or integrate caching logic directly. The cache itself can be a separate class (`TranslationCache`) injected via Koin.
        * Implement an in-memory LRU cache (e.g., using a `LinkedHashMap`) to store `WordDefinition` results for the last 20 unique words looked up.
        * Before querying the dictionary via SQLDelight, check the cache. If the word is found in the cache, return the cached result.
        * If not in cache, query the dictionary and store the `WordDefinition` result in the cache before returning.
    * **Acceptance Criteria (ACs):**
        * AC1: The KMP `OfflineDictionaryService` (or its underlying repository) uses an in-memory cache for `WordDefinition` objects.
        * AC2: Looking up a word for the second time (if within the last 20 unique lookups and cache hasn't been cleared) retrieves the result from the cache instead of re-querying the SQLite database.
        * AC3: The cache correctly evicts the oldest entries when its capacity (20 words) is exceeded.

---
### Epic 3: OCR-Based Comment Translation Feature (v0.2)
**Goal:** Develop the Optical Character Recognition (OCR) capabilities to extract text from Reddit comments captured via screenshots on both Android (using ML Kit) and iOS (using Vision Framework). The extracted text will then be processed by the KMP `shared` module (using `OfflineDictionaryService` or `OnlineTranslationService`) and displayed to the user.

**Stories:**

* **Story 3.1: Android Screenshot Capture and Basic Cropping Logic**
    * **User Story / Goal:** As a Developer, when the user taps the floating bubble on Android, I need to capture the current screen and apply basic cropping logic to isolate the potential comment area, so that a relevant image section is available for OCR.
    * **Detailed Requirements:**
        * On bubble tap (interaction defined in Epic 1, `FloatingBubbleService.kt`), capture a screenshot of the current screen content using appropriate Android screen capture APIs.
        * Implement a basic heuristic to crop the screenshot. This might involve:
            * Capturing a predefined region around the bubble's tap location.
            * A simple attempt to identify a rectangular area likely to contain a comment. (Advanced cropping in **Tech Story A.2**)
        * The primary goal is to reduce the image size sent to OCR and focus on the area of interest.
        * This story does *not* include the OCR processing itself, only image capture and initial cropping. The cropped image (Bitmap) will be passed to the `MlKitOcrProvider`.
    * **Acceptance Criteria (ACs):**
        * AC1: Tapping the Android floating bubble successfully captures a screenshot.
        * AC2: A cropped portion of the screenshot is available as an Android `Bitmap` object.
        * AC3: The cropping logic uses the tap location or a simple predefined region as a basis.
        * AC4: The captured/cropped image is ready to be passed to the `MlKitOcrProvider`.

* **Story 3.2: Implement OCR Text Extraction on Android (ML Kit)**
    * **User Story / Goal:** As a Developer, I need to integrate ML Kit on Android (via `MlKitOcrProvider.kt`) to perform OCR on a provided (cropped) image, so that text can be extracted from Reddit comments.
    * **Detailed Requirements:**
        * Use Google's ML Kit Text Recognition on-device API (`com.google.android.gms:play-services-mlkit-text-recognition`).
        * Create a service/provider class (e.g., `MlKitOcrProvider`) in `composeApp` that accepts an Android `Bitmap` and returns the recognized text as a string (or a structured result with confidence scores if available).
        * Handle potential ML Kit initialization and processing errors.
        * Focus on extracting English text. The result will be sent to the KMP `shared` module.
    * **Acceptance Criteria (ACs):**
        * AC1: The `MlKitOcrProvider` successfully processes a sample screenshot of a Reddit comment and extracts text.
        * AC2: The extracted text is returned as a string.
        * AC3: Errors during OCR processing (e.g., no text found, low confidence) are handled gracefully and can be reported.
        * AC4: OCR is performed on-device.

* **Story 3.3: Display OCR-Extracted and Translated Text in Android UI**
    * **User Story / Goal:** As a User, after tapping the bubble on a Reddit comment on Android, I want to see the extracted text (or its translation) displayed in the SubLingo UI.
    * **Detailed Requirements:**
        * After `MlKitOcrProvider` extracts text (Story 3.2), the `FloatingBubbleService` passes this text to the KMP `shared` module (specifically to a domain use case like `ProcessExtractedTextUseCase`).
        * The KMP use case will decide whether to use `OfflineDictionaryService` (for single words) or `OnlineTranslationService` (for sentences, if enabled).
        * For this story, if the extracted text is multiple words, initially display the raw extracted text or the offline translation of the first recognized word. Full sentence translation display will align with Epic 4.
        * Update the Android floating bubble's Jetpack Compose UI to show the OCR result/translation.
        * Provide user feedback during OCR processing (e.g., a loading indicator in the bubble).
    * **Acceptance Criteria (ACs):**
        * AC1: After bubble tap and OCR, the Android bubble UI displays the extracted text or the offline translation of the first word from the extracted text.
        * AC2: A loading indicator is shown in the bubble while OCR/translation is in progress.
        * AC3: If OCR fails to extract text, an appropriate "No text found" message is shown in the UI.

* **Story 3.4: Implement Image Receiving in iOS Share Extension**
    * **User Story / Goal:** As a Developer, I need the iOS Share Extension (`SubLingoShareExtension`) to correctly receive and handle an image (screenshot) shared by the user.
    * **Detailed Requirements:**
        * Ensure the `SubLingoShareExtension` is configured to accept image types (e.g., PNG, JPEG via its `Info.plist` and `NSExtensionActivationRule`).
        * When an image is shared to the extension, it should be accessible as a `UIImage` object within the extension's `ShareViewController.swift`.
        * Handle potential errors in receiving or loading the image. The image will be passed to the `VisionOcrProvider`.
    * **Acceptance Criteria (ACs):**
        * AC1: The `SubLingoShareExtension` can be invoked by sharing a screenshot.
        * AC2: The shared image is successfully loaded and available as a `UIImage` within the extension.
        * AC3: If no image or an invalid item is shared, the extension handles it gracefully (e.g., shows an error or dismisses).

* **Story 3.5: Implement OCR Text Extraction on iOS (Vision Framework)**
    * **User Story / Goal:** As a Developer, I need to integrate the Vision Framework (via `VisionOcrProvider.swift`) within the iOS Share Extension to perform OCR on a received image, so that text can be extracted from Reddit comments.
    * **Detailed Requirements:**
        * Use Apple's Vision Framework (`VNRecognizeTextRequest`) for on-device text recognition.
        * Create a service/provider class (e.g., `VisionOcrProvider`) in `iosApp` that accepts a `UIImage` and returns the recognized text as a string (or a structured result).
        * Prioritize accuracy in the `VNRecognizeTextRequest`.
        * Handle potential Vision framework initialization and processing errors.
        * Focus on extracting English text. The result will be sent to the KMP `shared` module.
    * **Acceptance Criteria (ACs):**
        * AC1: The `VisionOcrProvider` successfully processes a sample screenshot of a Reddit comment and extracts text within the Share Extension.
        * AC2: The extracted text is returned as a string.
        * AC3: Errors during OCR processing (e.g., no text found, low confidence) are handled gracefully and can be reported.
        * AC4: OCR is performed on-device.

* **Story 3.6: Display OCR-Extracted and Translated Text in iOS Share Extension UI**
    * **User Story / Goal:** As a User, after sharing a screenshot of a Reddit comment to the SubLingo iOS Share Extension, I want to see the extracted text (or its translation).
    * **Detailed Requirements:**
        * After `VisionOcrProvider` extracts text (Story 3.5), the `ShareViewController.swift` passes this text to the KMP `shared` module (specifically to a domain use case like `ProcessExtractedTextUseCase`).
        * The KMP use case will decide whether to use `OfflineDictionaryService` (for single words) or `OnlineTranslationService` (for sentences, if enabled).
        * For this story, if the extracted text is multiple words, initially display the raw extracted text or the offline translation of the first recognized word. Full sentence translation display will align with Epic 4.
        * Update the Share Extension's SwiftUI UI to show the OCR result/translation.
        * Provide user feedback during OCR processing (e.g., a loading indicator in the extension UI).
    * **Acceptance Criteria (ACs):**
        * AC1: After sharing an image and OCR, the iOS Share Extension UI displays the extracted text or the offline translation of the first word.
        * AC2: A loading indicator is shown while OCR/translation is in progress within the extension.
        * AC3: If OCR fails to extract text, an appropriate "No text found" message is shown in the extension UI.

* **Story 3.7: Basic Image Preprocessing for OCR Improvement (Platform Specific)**
    * **User Story / Goal:** As a Developer, I want to implement basic image preprocessing routines (e.g., grayscale conversion) in the platform-specific OCR providers (`MlKitOcrProvider`, `VisionOcrProvider`) before sending an image to OCR, so that OCR accuracy can be potentially improved on varied Reddit comment styles.
    * **Detailed Requirements:**
        * **Android (`MlKitOcrProvider`):** Implement a function to convert the input `Bitmap` to grayscale before passing it to ML Kit.
        * **iOS (`VisionOcrProvider`):** Implement a function to convert the input `UIImage` to grayscale (e.g., using Core Image filters) before passing it to the Vision Framework.
        * Other basic preprocessing like adjusting contrast or binarization could be considered if straightforward and demonstrably beneficial, but grayscale is the minimum.
        * The goal is to standardize the image somewhat to aid the OCR engines.
    * **Acceptance Criteria (ACs):**
        * AC1: Images are converted to grayscale within `MlKitOcrProvider` (Android) and `VisionOcrProvider` (iOS) before being sent to the respective OCR engines.
        * AC2: The preprocessing step does not significantly degrade overall OCR processing time.
        * AC3: Qualitative testing on a few sample Reddit comment screenshots shows that grayscale conversion does not harm (and ideally helps or has no negative effect on) OCR results compared to no preprocessing.

---
### Epic 4: Online Sentence Translation Feature (v0.2)
**Goal:** Integrate with online AI translation services (OpenAI/OpenRouter via KMP `OnlineTranslationService`) to provide translation for sentences and longer text segments. This includes implementing the API communication in the KMP shared module, updating platform UIs to display these translations, managing the embedded API key securely at build time, and managing user expectations regarding online functionality.

**Stories:**

* **Story 4.1: Implement Online Translation Service (KMP Shared)**
    * **User Story / Goal:** As a Developer, I need to create a service (`OnlineTranslationService`) in the KMP `shared` module to connect to OpenAI/OpenRouter APIs for sentence translation, so that the app can translate text beyond single-word offline lookups.
    * **Detailed Requirements:**
        * Create a service class (e.g., `OnlineTranslationServiceImpl`) in `commonMain` (e.g., in `com.nguyenmoclam.sublingo.shared.services` or `com.nguyenmoclam.sublingo.shared.data.repository`). This service will be injectable via Koin.
        * Implement functions to make API calls using Ktor client to a designated AI translation provider (OpenAI GPT-3.5 Turbo or an equivalent from OpenRouter, as per PRD and `docs/api-reference.md`).
        * The service should handle API request construction (using DTOs from `docs/data-models.md`), including sending the text to be translated and appropriate system/user messages. Target language for MVP is Vietnamese (from English).
        * **API Key Management (Corresponds to Tech Story A.1 & I.1):**
            * The service will require the API key to be passed to it (e.g., during initialization via Koin).
            * The KMP `shared` module will define an `expect` mechanism or receive the key from platform-specific code that reads it from `BuildConfig` (Android) or `Info.plist` (iOS) as per `docs/environment-vars.md`.
        * Parse the API response (using DTOs from `docs/data-models.md`) to extract the translated text.
        * Implement basic error handling for API calls (network errors, API errors), returning a `Result` or sealed class type. More comprehensive error handling in Story 4.5.
    * **Acceptance Criteria (ACs):**
        * AC1: The `OnlineTranslationService` can successfully make a translation request to the chosen AI API provider using an API key provided from the platform.
        * AC2: The service correctly parses a successful API response and returns the translated text as part of a `TranslationResult` domain model.
        * AC3: Basic API errors (e.g., invalid key, network issue) are caught, and an error state/message can be propagated.
        * AC4: The service can translate a sample English sentence into Vietnamese.
        * AC5: The API key is not hardcoded in the `shared` module and is provided externally.

* **Story 4.2: Integrate Online Sentence Translation in Android UI**
    * **User Story / Goal:** As a User, when I select a sentence or use OCR on a comment containing a sentence on Android, I want the option to translate it using the online service and see the result in the SubLingo UI.
    * **Detailed Requirements:**
        * The KMP `shared` module's domain logic (e.g., a use case called by `FloatingBubbleService`) will determine when to invoke online translation (e.g., if selected/OCR'd text is more than N words, or if offline lookup yields no result for the whole phrase).
        * If online translation is invoked by KMP core:
            * The Android UI (`FloatingBubbleService` or its Compose UI) should clearly indicate that an online service is being used (e.g., small icon, text).
            * Show a loading indicator during the API call.
            * The KMP `OnlineTranslationService` is called by the shared domain logic.
            * Display the translated sentence from `TranslationResult` in the Android floating bubble or designated UI.
            * This feature's availability depends on the "Enable Online Translation" setting (Epic 5). For this story, assume it's enabled.
    * **Acceptance Criteria (ACs):**
        * AC1: The Android UI triggers KMP shared logic which then calls the KMP `OnlineTranslationService` for multi-word text segments.
        * AC2: A visual cue indicates that an online translation is being performed.
        * AC3: A loading indicator is displayed during the online translation process.
        * AC4: The translated sentence is displayed in the Android UI.
        * AC5: If the online translation fails (basic error from Story 4.1 reported by KMP core), an appropriate message is shown.

* **Story 4.3: Integrate Online Sentence Translation in iOS Extensions**
    * **User Story / Goal:** As a User, when I select a sentence or share a screenshot containing a sentence to the iOS SubLingo extensions, I want the option to translate it using the online service and see the result.
    * **Detailed Requirements:**
        * Similar logic as Android: KMP `shared` module's domain logic (called by `ActionExtension` or `ShareExtension`) determines when to invoke online translation.
        * If online translation is invoked by KMP core:
            * The iOS extension UI (SwiftUI) should clearly indicate that an online service is being used.
            * Show a loading indicator during the API call.
            * The KMP `OnlineTranslationService` is called by the shared domain logic.
            * Display the translated sentence from `TranslationResult` in the iOS Action/Share Extension UI.
            * This feature's availability depends on the "Enable Online Translation" setting (Epic 5). For this story, assume it's enabled.
    * **Acceptance Criteria (ACs):**
        * AC1: The iOS Action/Share Extensions trigger KMP shared logic which then calls the KMP `OnlineTranslationService` for multi-word text segments.
        * AC2: A visual cue in the extension UI indicates that an online translation is being performed.
        * AC3: A loading indicator is displayed during the online translation process in the extension.
        * AC4: The translated sentence is displayed in the extension UI.
        * AC5: If the online translation fails (basic error from Story 4.1 reported by KMP core), an appropriate message is shown.

* **Story 4.4: User Indication and Expectation Management for Online Translation**
    * **User Story / Goal:** As a User, I want to be clearly informed when an online translation is occurring and understand potential implications (speed, data use, privacy), so I can make an informed choice.
    * **Detailed Requirements:**
        * **Visual Cues:** Implement consistent visual indicators (e.g., a small cloud icon or text like "Online translation") whenever an online translation is displayed on both Android (bubble) and iOS (extensions).
        * **Performance Expectation:**
            * Loading indicators (from Story 4.2, 4.3) manage this during the call. Target API latency is ≤ 2s.
        * **Privacy Note (Onboarding/Settings):** Information regarding text being sent to a third-party API should be part of the privacy policy and potentially highlighted during onboarding or in settings where online translation is toggled (Epic 5). This story focuses on the in-translation UI cues.
    * **Acceptance Criteria (ACs):**
        * AC1: A clear visual indicator (icon or text) is present alongside translations performed by the online service on both platforms.
        * AC2: The indicator clearly identifies the nature (online) of the translation.

* **Story 4.5: Robust Error Handling for Online Translation**
    * **User Story / Goal:** As a Developer, I need to implement comprehensive error handling for the KMP `OnlineTranslationService` and ensure its UI integration provides helpful feedback when online translation fails. (Corresponds to Tech Story G.2 for shared error structure).
    * **Detailed Requirements:**
        * Enhance the KMP `OnlineTranslationService` to specifically handle and wrap:
            * Network connectivity issues (e.g., Ktor `IOException`).
            * API-specific errors (e.g., quota limits `429`, invalid request `400`, server errors `5xx` from OpenAI/OpenRouter).
            * Timeout errors if the API call takes too long (Ktor client configuration).
        * Propagate these distinct error types using the shared error structure (e.g., sealed class like `DataResponse.Error` with specific error codes/messages) from the KMP service to the platform-specific UI.
        * Android and iOS UIs must display user-friendly messages for each error type (e.g., "No internet connection," "Translation service busy, try again later," "Translation failed: Invalid API Key").
    * **Acceptance Criteria (ACs):**
        * AC1: The KMP `OnlineTranslationService` correctly identifies and wraps different online translation error types into the shared error structure.
        * AC2: The Android UI displays specific, user-friendly error messages for network issues, API quota limits, and general API failures.
        * AC3: The iOS Extension UIs display specific, user-friendly error messages for network issues, API quota limits, and general API failures.
        * AC4: The app remains stable and does not crash if online translation fails.

* **Story 4.6: Implement Basic Cache for Online Sentence Translations (KMP Shared)**
    * **User Story / Goal:** As a Developer, I want to implement a simple in-memory cache (`TranslationCache.kt`) for the last N (e.g., 20) online sentence translations in the KMP `shared` module, so that repeated requests for the same sentences are faster and reduce API calls/costs.
    * **Detailed Requirements:**
        * Modify the `OnlineTranslationService` (or its implementation/repository) to use the `TranslationCache` (injected via Koin).
        * The cache will store `TranslationResult` objects.
        * The cache key should be the source sentence text (and potentially target language if it becomes configurable).
        * Before making an API call, check the cache. If the sentence is found, return the cached translation.
        * If not in cache, make the API call, and store the `TranslationResult` in the cache before returning.
    * **Acceptance Criteria (ACs):**
        * AC1: The KMP `OnlineTranslationService` (or its repository) uses the `TranslationCache` for sentence translations.
        * AC2: Translating a sentence for the second time (if within the last 20 unique sentences and cache not cleared) retrieves the result from the cache, avoiding an API call.
        * AC3: The cache correctly evicts the oldest entries when its capacity (20 sentences) is exceeded.

---
### Epic 5: Onboarding & Settings (v0.2)
**Goal:** Create clear user onboarding flows, especially for Android permissions and iOS extension usage, and implement a minimal settings screen (using KMP `AppSettings` for shared preferences where possible) to allow users to manage online translation features, cached data, and access the privacy policy.

**Stories:**

* **Story 5.1: Implement Android Onboarding for "Draw Over Other Apps" Permission**
    * **User Story / Goal:** As a new Android User, I want a clear, interactive guide explaining why the "Draw Over Other Apps" permission is needed and how to grant it, so I can use the floating bubble feature.
    * **Detailed Requirements:**
        * On first launch, or when the bubble feature is first accessed via `FloatingBubbleService`, if the permission is not granted, present an onboarding Jetpack Compose screen.
        * The flow should visually explain (e.g., using a GIF, short video, or sequential static images with text) the benefit of the floating bubble.
        * Clearly show the steps to navigate to system settings and grant the permission.
        * Provide a button/link to take the user directly to the relevant system settings screen for this permission.
        * The app should gracefully handle cases where the user denies or navigates away without granting permission (e.g., bubble feature remains disabled, with an option to re-initiate onboarding from settings or a UI prompt).
    * **Acceptance Criteria (ACs):**
        * AC1: An onboarding flow for the "Draw Over Other Apps" permission is presented if the permission is not granted when the bubble feature is attempted.
        * AC2: The onboarding uses visual aids and clear text.
        * AC3: A direct link/button to the system settings for this permission is provided.
        * AC4: The app detects whether the permission was granted after the user returns from settings.
        * AC5: If permission is denied, the bubble feature is appropriately disabled or limited, and the user can find a way to re-attempt granting it later (e.g., from a settings menu item).

* **Story 5.2: Implement Android Onboarding for `UsageStatsManager` Permission (Conditional)**
    * **User Story / Goal:** As a new Android User, if the app uses `UsageStatsManager` for auto-show/hide of the bubble, I want a clear guide explaining why this permission is needed and how to grant it, so I can benefit from the bubble appearing only when I'm using Reddit.
    * **Detailed Requirements:**
        * **Conditionality:** Architect and PM to confirm if "auto-show/hide" (`ForegroundAppDetector.kt`) is essential for MVP or if manual toggle of the bubble is sufficient to reduce permission friction. If included:
        * Present an onboarding Jetpack Compose screen if this permission is required by `ForegroundAppDetector` and not granted.
        * Visually explain the benefit (bubble auto-appears/hides with Reddit).
        * Clearly show steps to grant the permission in system settings for `PACKAGE_USAGE_STATS`.
        * Provide a button/link to the relevant system settings screen.
        * Handle denial gracefully (e.g., bubble requires manual toggle, or this specific auto-feature is disabled).
    * **Acceptance Criteria (ACs):**
        * AC1: (If `ForegroundAppDetector` feature is included for MVP) An onboarding flow for `UsageStatsManager` permission is presented if required and not granted.
        * AC2: The onboarding explains the benefit and shows how to grant the permission.
        * AC3: A direct link/button to system settings is provided.
        * AC4: If permission is denied, the auto-show/hide feature is disabled, and the bubble defaults to manual toggle or another defined behavior.

* **Story 5.3: Implement iOS Onboarding for Extension Usage**
    * **User Story / Goal:** As a new iOS User, I want a simple guide on how to enable and use the SubLingo Action and Share Extensions, and understand their benefits, so I can effectively translate within Reddit.
    * **Detailed Requirements:**
        * On first launch of the main `iosApp` (`SubLingoApp.swift`), present a brief SwiftUI onboarding flow.
        * Explain how to enable the `SubLingoActionExtension` and `SubLingoShareExtension` in the iOS Share Sheet settings if they are not automatically enabled by the system.
        * Visually demonstrate (e.g., GIF or static images) how to use the Action Extension (for selected text) and the Share Extension (for screenshots).
        * Briefly highlight the value proposition (e.g., "Access offline dictionary details & enhanced OpenAI translation").
    * **Acceptance Criteria (ACs):**
        * AC1: An onboarding flow is presented on the first launch of the iOS app.
        * AC2: The guide explains how to enable and use both Action and Share extensions.
        * AC3: Visual aids are used to demonstrate the usage.
        * AC4: The value of using the extensions is briefly communicated.

* **Story 5.4: Create Basic Settings Screen UI (Android & iOS)**
    * **User Story / Goal:** As a User, I want to access a settings screen within the app, so I can manage app preferences.
    * **Detailed Requirements:**
        * Implement a navigable "Settings" screen accessible from the main UI of both the Android (`composeApp`) and iOS (`iosApp`) apps.
        * The screen should have a clean, simple layout.
        * This screen will host the setting toggles and links defined in subsequent stories.
        * Use Jetpack Compose for Android settings UI (`SettingsScreen.kt`), SwiftUI for iOS settings UI (`SettingsView.swift`).
        * A KMP `shared` ViewModel (e.g., `SettingsViewModel.kt` in `com.nguyenmoclam.sublingo.shared.presentation`) can manage the settings state using `AppSettings` (Multiplatform-Settings) and be observed by the platform UIs.
    * **Acceptance Criteria (ACs):**
        * AC1: A "Settings" screen is accessible from the main app interface on Android.
        * AC2: A "Settings" screen is accessible from the main app interface on iOS.
        * AC3: The screen layout is clean and provides placeholders for settings items, driven by a shared KMP ViewModel if implemented.

* **Story 5.5: Implement "Toggle Online Sentence Translation" Setting**
    * **User Story / Goal:** As a User, I want to be able to enable or disable online sentence translation via a setting, so I can control data usage and choose my preferred translation method.
    * **Detailed Requirements:**
        * Add a switch/toggle control on the Settings screen (Android & iOS) labeled "Enable Online Sentence Translation" (or similar).
        * The state of this toggle should be persisted using the KMP `AppSettings` (Multiplatform-Settings).
        * The KMP `OnlineTranslationService` (or the domain logic calling it) must respect this setting. If disabled, it should not attempt to make API calls and should perhaps return a specific state/error indicating the feature is off.
        * Default state: Enabled (to be confirmed, but typically good for showcasing features).
    * **Acceptance Criteria (ACs):**
        * AC1: A toggle for online sentence translation is present on the Settings screen on both platforms.
        * AC2: The setting's state is persisted via KMP `AppSettings` and respected across app sessions.
        * AC3: If the setting is OFF, the KMP `shared` module does not attempt to call the online translation API, and the UI may inform the user that the feature is disabled if an online translation would have otherwise occurred.
        * AC4: If the setting is ON, online translation functions as designed in Epic 4.

* **Story 5.6: Implement "Clear Translation Cache" Setting**
    * **User Story / Goal:** As a User, I want an option to clear cached translations, so I can free up a small amount of storage or reset my translation history.
    * **Detailed Requirements:**
        * Add a button on the Settings screen (Android & iOS) labeled "Clear Translation Cache" (or similar).
        * Tapping this button should trigger a call to the KMP `shared` module (e.g., a `ClearCacheUseCase.kt`) which will clear:
            * The offline word lookup cache (managed by `TranslationCache.kt` for `WordDefinition` objects from Story 2.7).
            * The online sentence translation cache (managed by `TranslationCache.kt` for `TranslationResult` objects from Story 4.6).
        * Provide a confirmation dialog before clearing the cache.
        * Provide feedback to the user once the cache is cleared.
    * **Acceptance Criteria (ACs):**
        * AC1: A "Clear Translation Cache" button is present on the Settings screen on both platforms.
        * AC2: Tapping the button presents a confirmation dialog.
        * AC3: Upon confirmation, the KMP `TranslationCache` for both offline and online lookups is cleared.
        * AC4: User receives feedback that the cache has been cleared.

* **Story 5.7: Display Privacy Policy Link in Settings**
    * **User Story / Goal:** As a User, I want to easily access the app's Privacy Policy, so I can understand how my data is handled.
    * **Detailed Requirements:**
        * Add a tappable item on the Settings screen (Android & iOS) labeled "Privacy Policy."
        * Tapping this item should open the app's Privacy Policy.
        * For MVP, the Privacy Policy can be a simple, locally bundled HTML or Markdown file displayed in a platform-native WebView or a dedicated rich text view. A URL to a hosted policy is also acceptable if simpler for MVP. (The content of `docs/privacy-policy.md` needs to be drafted - see Story 6.2).
    * **Acceptance Criteria (ACs):**
        * AC1: A "Privacy Policy" link/button is present on the Settings screen on both platforms.
        * AC2: Tapping the link successfully displays the Privacy Policy content to the user (either from a bundled asset or a webview).
        * AC3: The mechanism to display the Privacy Policy is functional. (Content accuracy is covered in Story 6.2).

---
### Epic 6: Polish, Error Handling, and Privacy (v0.2)
**Goal:** Implement comprehensive error handling (leveraging the shared error structure from **Tech Story G.2**) across all features, ensure full adherence to the defined privacy policy including its in-app accessibility, and refine the overall user experience (UX) and user interface (UI) on both Android and iOS based on initial internal testing and feedback. This epic aims to deliver a stable, polished, and trustworthy MVP.

**Stories:**

* **Story 6.1: System-Wide Error Handling Review and Enhancement**
    * **User Story / Goal:** As a Developer, I need to review and enhance error handling across all application features (Epics 1-5) to ensure that users receive clear, informative, and actionable error messages for any foreseeable issue, utilizing the KMP shared error reporting structure.
    * **Detailed Requirements:**
        * Systematically review all user flows and ensure KMP services/use cases return defined error types (from Tech Story G.2).
        * Platform UIs (Android bubble/main app, iOS extensions/main app) must map these shared error types to user-friendly messages for:
            * Offline dictionary lookups (word not found, dictionary load error - if `OfflineDictionaryService` reports it).
            * OCR processing (no text detected, low confidence - as reported by platform OCR providers to KMP, then to UI).
            * Online sentence translation (network issues, API errors, timeouts, quota limits - building on Story 4.5).
            * Permission denials and their consequences (platform-specific handling).
            * TTS initialization/playback errors (as reported by KMP `TtsPlayer` actuals).
        * Ensure all error messages are user-friendly (avoid technical jargon).
        * Where possible, suggest recovery steps or direct users to relevant settings (e.g., "Check internet connection," "Enable online translation in Settings").
        * Verify that the app remains stable and does not crash when errors occur.
    * **Acceptance Criteria (ACs):**
        * AC1: All known error conditions in core features have corresponding user-facing error messages derived from the shared error structure or platform-specific error handling.
        * AC2: Error messages are clear, concise, and helpful to the user.
        * AC3: The application gracefully handles and recovers from these errors without crashing.
        * AC4: A consistent error display mechanism (e.g., snackbars, dialogs, inline messages) is used across the app where appropriate.

* **Story 6.2: Finalize and Integrate Privacy Policy Content**
    * **User Story / Goal:** As a PM/Developer, I need to finalize the content of the Privacy Policy (`docs/privacy-policy.md`) and ensure it's correctly integrated and accessible within the app as per Story 5.7.
    * **Detailed Requirements:**
        * Review and finalize the text of the Privacy Policy. It must accurately reflect:
            * Data collected (selected text, text from OCR, cached translations, settings preferences).
            * How data is processed (on-device for OCR/offline dictionary, text to OpenAI/OpenRouter for online translation).
            * API key usage and storage (acknowledging current MVP build-time configuration risk and advising users about data sent to OpenAI).
            * Explicitly state that no images are stored by SubLingo post-OCR.
            * Details about the translation cache (on-device, user-clearable).
            * Use of permissions (Overlay, UsageStats if applicable, Clipboard access if relevant).
        * Ensure the finalized Privacy Policy content is correctly displayed when accessed from the Settings screen on both Android and iOS (using the mechanism from Story 5.7).
    * **Acceptance Criteria (ACs):**
        * AC1: The Privacy Policy content in `docs/privacy-policy.md` is finalized and accurately reflects all data handling practices of the MVP.
        * AC2: The finalized Privacy Policy is accessible from the Settings screen on both platforms and displays correctly.
        * AC3: The display of the Privacy Policy is clear and legible.

* **Story 6.3: Refine Android Floating Bubble UX/UI**
    * **User Story / Goal:** As an Android User, I want the floating bubble to be intuitive, non-obtrusive, and visually polished, providing clear feedback for its state.
    * **Detailed Requirements:**
        * Based on internal testing, refine the bubble's (`FloatingBubbleService` and its Compose UI):
            * **Visual Design:** Ensure it's visually appealing (Material 3), distinct but not overly distracting. Consider a subtle idle state vs. active/loading state.
            * **Interaction:** Smooth dragging, easy dismissal (if applicable, e.g., drag to a target), clear tap target.
            * **Feedback:** Visual feedback when tapped (e.g., ripple, state change), loading indicators during processing (OCR, translation), clear display of results or errors.
            * **Responsiveness:** Ensure the bubble responds quickly to interactions and KMP core responses.
        * Test the bubble's behavior over various apps (especially Reddit) and screen orientations.
    * **Acceptance Criteria (ACs):**
        * AC1: The Android floating bubble's visual appearance is polished and adheres to Material 3 guidelines.
        * AC2: Bubble interactions (drag, tap, dismiss) are smooth and responsive.
        * AC3: Clear visual feedback is provided for bubble states (idle, active/loading, showing result, error).
        * AC4: The bubble maintains its functionality and appearance consistently across different scenarios.

* **Story 6.4: Refine iOS Extension UX/UI**
    * **User Story / Goal:** As an iOS User, I want the Action and Share Extensions (`SubLingoActionExtension`, `SubLingoShareExtension`) to be fast, easy to understand, and visually consistent with a polished app experience.
    * **Detailed Requirements:**
        * Based on internal testing, refine the SwiftUI UI/UX of both Action and Share extensions:
            * **Launch Speed:** Ensure extensions launch as quickly as possible (minimize Koin init time if it impacts extension launch).
            * **Clarity:** Make sure the purpose and flow within each extension are immediately understandable.
            * **Visual Design:** Consistent and clean SwiftUI UI for displaying information, results, and errors, adhering to iOS HIG.
            * **Feedback:** Clear loading indicators and feedback messages.
        * Test extensions with various shared content types (text, different images) and from different apps (especially Reddit).
    * **Acceptance Criteria (ACs):**
        * AC1: iOS Action and Share Extensions launch quickly.
        * AC2: The UI within extensions is clear, intuitive, and visually polished.
        * AC3: Clear visual feedback is provided for loading states, results, and errors within the extensions.
        * AC4: Extensions handle various valid inputs gracefully and provide informative messages for invalid inputs.

* **Story 6.5: Performance Review and Bottleneck Identification**
    * **User Story / Goal:** As a Developer, I want to review the performance of critical operations (offline lookup, OCR, online translation) against NFRs and identify any major bottlenecks for potential optimization.
    * **Detailed Requirements:**
        * Re-measure performance for:
            * Offline word lookup speed (Target: < 2s, KMP Service: < 500ms) - Test via KMP `OfflineDictionaryService`.
            * OCR processing speed (Target: < 3s) - Test on-device `MlKitOcrProvider` (Android) and `VisionOcrProvider` (iOS) with sample images.
            * Online sentence translation API call (Target API: ≤ 2s, user-perceived will be higher) - Test KMP `OnlineTranslationService` response time, factoring in network from target region.
        * Perform these tests on target devices/emulators.
        * Identify any operations consistently failing to meet NFRs or exhibiting significant lag.
        * Document identified bottlenecks. This story is about identification; actual optimization might be a separate task or deferred if minor and MVP goals are met.
    * **Acceptance Criteria (ACs):**
        * AC1: Performance metrics for key operations (offline lookup, OCR, online translation) are measured and documented against NFRs.
        * AC2: Any operations significantly missing performance targets are identified.
        * AC3: A list of potential performance bottlenecks is created (if any).

* **Story 6.6: Focused OCR Accuracy Testing and Minor Adjustments**
    * **User Story / Goal:** As a QA/Developer, I want to test OCR accuracy on a diverse set of Reddit comment screenshots and make minor adjustments to preprocessing (Story 3.7) or cropping (Tech Story A.2) if clear benefits are identified, to maximize OCR reliability for MVP.
    * **Detailed Requirements:**
        * Collect a diverse set of at least 20-30 Reddit comment screenshots:
            * Different themes (light/dark).
            * Various system font sizes.
            * Different comment densities and layouts (e.g., nested comments, comments with icons/flairs).
        * Test OCR accuracy (ML Kit on Android, Vision Framework on iOS) on these samples with current preprocessing.
        * If specific patterns of failure are noted, investigate if minor, low-effort adjustments to image preprocessing (e.g., slight contrast change, grayscale toggle, small refinement to cropping logic) can yield noticeable improvements without adding significant complexity.
        * Document OCR performance on the test set (e.g., success rate, common error types).
    * **Acceptance Criteria (ACs):**
        * AC1: OCR accuracy is tested against a documented set of diverse Reddit comment screenshots.
        * AC2: Results (successful extractions, common failure types) are documented.
        * AC3: At least one minor preprocessing/cropping adjustment is attempted and its impact evaluated if initial results show consistent, easily addressable issues.
        * AC4: Overall OCR success rate and limitations for MVP are better understood and documented.

* **Story 6.7: Final Onboarding Flow Review and Polish**
    * **User Story / Goal:** As a PM/QA, I want to review the complete onboarding flows on both Android and iOS for clarity, accuracy, and user-friendliness, ensuring all steps are correct and easy to follow.
    * **Detailed Requirements:**
        * Go through the entire first-time user experience on both platforms, focusing on:
            * Android permission onboarding (Draw Over Other Apps - Story 5.1, UsageStatsManager if implemented - Story 5.2).
            * iOS extension enabling and usage guide (Story 5.3).
        * Check all instructional text for clarity, conciseness, and grammar.
        * Verify that all visual aids (GIFs, images) are accurate, easy to understand, and culturally appropriate for the target Vietnamese users.
        * Ensure navigation through onboarding is smooth, users can exit/skip where appropriate (and re-access info if needed, e.g., via a help section in settings).
    * **Acceptance Criteria (ACs):**
        * AC1: The Android onboarding flow for permissions is verified as clear, accurate, and guides the user effectively.
        * AC2: The iOS onboarding flow for extensions is verified as clear, accurate, and helpful.
        * AC3: All text and visual elements in the onboarding flows are polished and free of errors.
        * AC4: The onboarding process feels smooth, professional, and instills confidence.

* **Story 6.8: General UI/UX Consistency and Polish Pass**
    * **User Story / Goal:** As a Developer/QA, I want to perform a general review of the entire application UI on both platforms to identify and fix minor inconsistencies, typos, layout issues, or awkward interactions.
    * **Detailed Requirements:**
        * Review all screens, dialogs, and UI elements in the app (main app UI in `composeApp`/`iosApp`, Android bubble, iOS extensions).
        * Check for:
            * Consistent terminology and button labels (consider English and Vietnamese if any UI text is localized for MVP).
            * Consistent font usage and text alignment.
            * Consistent use of colors and iconography (if any defined).
            * Proper layout and spacing on different representative screen sizes/orientations for target devices.
            * Any remaining typos or grammatical errors in UI text.
            * Any awkward or confusing user interactions not caught in specific feature testing.
        * Fix identified minor issues. Larger redesigns are out of scope for this story.
    * **Acceptance Criteria (ACs):**
        * AC1: At least 10 minor UI/UX polish items are identified and addressed across both platforms (or a thorough review is documented).
        * AC2: The application presents a more consistent and professional look and feel.
        * AC3: Obvious typos and grammatical errors in the UI are corrected.

---
## II. Technical Stories (Foundational/Enabling Tasks)

These stories represent necessary technical groundwork for the MVP.

### Top Priority Technical Stories (Sequence Early)

* **Tech Story G.1: Initial Project Package Name Refactoring**
    * **Description:** Refactor all placeholder package names (e.g., `com.myapplication`) in the cloned `moclam1905/SubLingo` repository to the standard `com.nguyenmoclam.sublingo.*` across all relevant files in `composeApp`, `iosApp`, and `shared` modules, including `AndroidManifest.xml`, Gradle files, and Kotlin/Swift source files.
    * **Rationale:** Prerequisite for clean project setup and consistency. Overlaps with Story 1.1 AC6.
    * **Related Epics:** All.

* **Tech Story S.1: Implement Koin Dependency Injection Setup for `shared` module**
    * **Description:** Configure Koin modules in `shared/src/commonMain/kotlin/com/nguyenmoclam/sublingo/shared/di/` to define how services like `OfflineDictionaryService`, `OnlineTranslationService`, `TranslationCache`, `AppSettings`, and repository implementations are created and injected. Provide a way for platform apps to initialize Koin for the shared module.
    * **Rationale:** Essential for modularity, testability, and managing dependencies for shared services.
    * **Related Epics:** Underpins Epics 2, 4, 5.

* **Tech Story S.5: Setup SQLDelight Gradle Configuration and Generate Initial DB Adapters**
    * **Description:** Configure the SQLDelight plugin in `shared/build.gradle.kts`, create the initial `dictionary.sq` file with the schema (as per `docs/data-models.md`), and run the Gradle task to generate the Kotlin database adapters.
    * **Rationale:** Foundational step for implementing `OfflineDictionaryService`.
    * **Related Epics:** Epic 2.

* **Tech Story S.4: Develop Data Processing Script for Wiktextract to SQLite**
    * **Description:** Create and run a script (e.g., Python) to parse the Wiktextract JSONL data, filter it for a "lite" English dictionary, and populate the defined SQLite schema (`dictionary.sq`) into the `sublingo_dictionary.db` file.
    * **Rationale:** Creates the actual `sublingo_dictionary.db` asset needed for Story 2.1.
    * **Related Epics:** Epic 2.

* **Tech Story S.2: Define and Implement `DictionaryFileProvider` expect/actual**
    * **Description:** Create the `expect class/interface DictionaryFileProvider` in `commonMain` (e.g., in `shared/src/commonMain/kotlin/com/nguyenmoclam/sublingo/shared/platform/`) for accessing the bundled `sublingo_dictionary.db` file. Implement the `actual` providers in `androidMain` (reading from assets) and `iosMain` (reading from the app bundle).
    * **Rationale:** Needed by `OfflineDictionaryService` (SQLDelight) to get the path/stream to the database. Links with Story 1.7.
    * **Related Epics:** Epic 2.

* **Tech Story A.1: Implement API Key BuildConfig Injection (Android)**
    * **Description:** Set up the `local.properties` and `build.gradle.kts` in `composeApp` to securely inject the `SUBLINGO_OPENAI_API_KEY` into `BuildConfig.OPENAI_API_KEY` as defined in `docs/environment-vars.md`.
    * **Rationale:** Necessary for `OnlineTranslationService` to access the key without committing it to VCS on Android.
    * **Related Epics:** Epic 4.

* **Tech Story I.1: Implement API Key Injection via xcconfig & Info.plist (iOS)**
    * **Description:** Set up the `Keys.xcconfig` (gitignored), main `.xcconfig`, and `Info.plist` in `iosApp` to securely provide the `SUBLINGO_OPENAI_API_KEY` to the Swift code, as defined in `docs/environment-vars.md`.
    * **Rationale:** Necessary for `OnlineTranslationService` to access the key on iOS without committing it to VCS.
    * **Related Epics:** Epic 4.

* **Tech Story G.3: Setup Basic KMP Logging Abstraction**
    * **Description:** Implement a KMP logging abstraction using a library like Napier/Kermit or an `expect`/`actual` pattern for `Log.d`, `Log.e` etc., as decided in `docs/coding-standards.md`.
    * **Rationale:** Allows consistent logging from shared code and platform-specific adaptations; invaluable for debugging.
    * **Related Epics:** All.

* **Tech Story G.2: Define and Implement Shared Error Reporting Structure**
    * **Description:** In the KMP `shared` module, define common error types/sealed classes (e.g., `NetworkError`, `DictionaryError`, `OcrError`, `ApiKeyError`) that can be used by services and use cases. Ensure platform UIs can map these to user-friendly messages.
    * **Rationale:** Enables systematic error handling (Story 4.5, Story 6.1).
    * **Related Epics:** Primarily Epics 4, 6, but benefits all.

### Medium Priority Technical Stories

* **Tech Story S.3: Implement `TtsPlayer` expect/actual for Basic Pronunciation Control**
    * **Description:** Create `expect interface TtsPlayer` in `commonMain` with functions like `speak(text: String, language: String)` and `stop()`. Implement `actual` players in `androidMain` (wrapping `AndroidTtsProvider`) and `iosMain` (wrapping `IOSTtsProvider`).
    * **Rationale:** Provides a KMP abstraction for TTS, making it callable from shared logic if needed and standardizing platform interaction. Links with Story 1.7, enables cleaner Stories 2.5 & 2.6.
    * **Related Epics:** Epic 2.

* **Tech Story I.2: Establish Communication Channel between App Extensions and KMP Shared Module (iOS)**
    * **Description:** Define and implement the mechanism by which the `SubLingoActionExtension` and `SubLingoShareExtension` will initialize Koin (if needed within extension scope) and access KMP `shared` module services/use cases.
    * **Rationale:** Crucial for iOS extension functionality.
    * **Related Epics:** Epics 1, 2, 3, 4.

* **Tech Story A.3: Implement Clipboard Listener with Foreground App Awareness (Android)**
    * **Description:** In `FloatingBubbleService` or a related utility, implement the Android `ClipboardManager.OnPrimaryClipChangedListener`. Ensure this listener is only active and processing clipboard content when SubLingo's bubble is active and Reddit (or a configurable set of apps) is in the foreground (using `ForegroundAppDetector`). Handle Android 10+ restrictions.
    * **Rationale:** Key for one of the Android interaction models for text selection translation.
    * **Related Epics:** Epic 1, and enables flows for Epics 2, 4.

### Lower Priority Technical Stories (Iterative Improvement)

* **Tech Story A.2: Develop Robust Screenshot Cropping Heuristics for Floating Bubble (Android)**
    * **Description:** Beyond basic capture (Story 3.1), implement more refined logic within `FloatingBubbleService` to intelligently crop the screenshot based on tap location, screen density, and potentially trying to identify comment-like UI elements to improve OCR input quality.
    * **Rationale:** Enhancement to improve OCR accuracy; can be refined based on testing (Story 6.6).
    * **Related Epics:** Epic 3.

