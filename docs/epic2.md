# Epic 2: Offline Word Translation Feature (v0.2)

**Goal:** Implement the end-to-end offline dictionary functionality. This includes preparing and integrating a "lite" offline dictionary (SQLite), enabling word lookup with definitions and examples via a KMP `OfflineDictionaryService`, and providing Text-to-Speech (TTS) pronunciation via a KMP `TtsPlayer` abstraction on both Android and iOS platforms.

## Story List

### Story 2.1: Prepare and Bundle "Lite" Offline Dictionary Data

-   **User Story / Goal:** As a Developer, I need to prepare a "lite" English offline dictionary (from Wiktextract processed into SQLite) and bundle it within the application, so that it can be queried by the KMP shared module.
-   **Detailed Requirements:**
    * **Data Sourcing & Processing (Corresponds to Tech Story S.4):**
        * Source English Wiktextract JSONL data.
        * Develop a script to process this data, filter for a "lite" set of common English words (target >10,000 headwords, final count based on size/utility), and extract primary definitions and at least one usage example per definition where available.
        * The script will populate an SQLite database (e.g., `sublingo_dictionary.db`) using the schema defined in `docs/data-models.md` (WordEntity, DefinitionEntity, ExampleEntity tables).
    * **Bundling:**
        * The processed SQLite dictionary file (`sublingo_dictionary.db`) must be bundled with the application in a location accessible by the KMP `shared` module on both Android (e.g., `composeApp/src/main/assets/`) and iOS (e.g., `iosApp/iosApp/Resources/`). The `DictionaryFileProvider` expect/actual (from Story 1.7 & Tech Story S.2) will provide access.
    * **Size Consideration:** The final bundled dictionary should be optimized for size while maintaining utility (target < 50MB if possible).
-   **Acceptance Criteria (ACs):**
    * AC1: A script to process Wiktextract JSONL into the target SQLite schema is created and functional.
    * AC2: The generated `sublingo_dictionary.db` file contains at least 10,000 common English headwords with definitions and examples (where available) and adheres to the schema in `docs/data-models.md`.
    * AC3: The `sublingo_dictionary.db` file is included in both Android (`composeApp` assets) and iOS (`iosApp` bundle resources).
    * AC4: The KMP `shared` module, via the `DictionaryFileProvider` (expect/actual), can obtain a path or stream to access the bundled `sublingo_dictionary.db` file on both platforms.
    * AC5: The final size of `sublingo_dictionary.db` is documented and meets target constraints.

---

### Story 2.2: Implement Offline Dictionary Query Service (KMP Shared)

-   **User Story / Goal:** As a Developer, I want to implement a service (`OfflineDictionaryService`) within the KMP `shared` module that can query the bundled offline SQLite dictionary using SQLDelight, so that I can retrieve definitions and examples for a given English word.
-   **Detailed Requirements:**
    * Create a repository/service class (e.g., `OfflineDictionaryServiceImpl`) in `commonMain` within `com.nguyenmoclam.sublingo.shared.services` or `com.nguyenmoclam.sublingo.shared.data.repository`.
    * This service will receive its SQLDelight `Database` (generated from `dictionary.sq`) or specific query instances via Koin-managed dependency injection (Tech Story S.1 & S.5).
    * It will use the `DictionaryFileProvider` (from Story 1.7 & Tech Story S.2) to initialize the SQLDelight driver with the bundled database.
    * Implement a function (e.g., `findWord(word: String): WordDefinition?`) that takes an English word string as input.
    * The function should use SQLDelight-generated type-safe Kotlin APIs to query the dictionary and return a structured `WordDefinition` object (as defined in `docs/data-models.md`) or null if not found.
    * Queries should be performant.
-   **Acceptance Criteria (ACs):**
    * AC1: The `OfflineDictionaryService` can successfully initialize the SQLDelight driver with the bundled `sublingo_dictionary.db` on both Android and iOS.
    * AC2: Given a word present in the dictionary, the service returns its `WordDefinition` (including definitions, part of speech, phonetic symbol, and examples).
    * AC3: Given a word not in the dictionary, the service returns `null`.
    * AC4: Basic performance test: a lookup for a common word (excluding initial DB load) completes in under 500ms on a representative device/emulator.
    * AC5: SQLDelight setup (`dictionary.sq` and Gradle plugin) is correctly configured (Tech Story S.5).

---

### Story 2.3: Integrate Offline Word Lookup in Android UI

-   **User Story / Goal:** As a User, when I select/copy an English word in Reddit (or provide it to the app), I want to see its offline definition and examples displayed quickly within the SubLingo Android UI (e.g., floating bubble or a dedicated view).
-   **Detailed Requirements:**
    * The Android floating bubble (`FloatingBubbleService.kt`) or a designated UI component within `composeApp` should be able to receive a word (e.g., from clipboard, or later from OCR).
    * Upon receiving a word, the Android app calls the `OfflineDictionaryService` in the KMP `shared` module.
    * Display the retrieved `WordDefinition` (definitions, examples, phonetic symbol) in a clear, readable Jetpack Compose format within the bubble/UI.
    * Handle the "word not found" case (`null` from service) appropriately in the UI.
    * Implement a placeholder UI for pronunciation (button/icon).
-   **Acceptance Criteria (ACs):**
    * AC1: When a word is provided to the Android app's translation interface, it calls the KMP `OfflineDictionaryService`.
    * AC2: The `WordDefinition` for the found word is displayed in the Android UI.
    * AC3: If the word is not found, a "Word not found" (or similar) message is shown.
    * AC4: The UI includes a non-functional placeholder for TTS pronunciation.

---

### Story 2.4: Integrate Offline Word Lookup in iOS Action Extension

-   **User Story / Goal:** As a User, when I select an English word in Reddit and share it to the SubLingo iOS Action Extension, I want to see its offline definition and examples displayed quickly.
-   **Detailed Requirements:**
    * The iOS Action Extension (`SubLingoActionExtension`) receives the selected text (word).
    * The Action Extension calls the `OfflineDictionaryService` in the KMP `shared` module.
    * Display the retrieved `WordDefinition` (definitions, examples, phonetic symbol) in a clear, readable SwiftUI format within the Action Extension's UI.
    * Handle the "word not found" case (`null` from service) appropriately in the UI.
    * Implement a placeholder UI for pronunciation (button/icon).
-   **Acceptance Criteria (ACs):**
    * AC1: When a word is shared to the iOS Action Extension, it calls the KMP `OfflineDictionaryService`.
    * AC2: The `WordDefinition` for the found word is displayed in the Action Extension UI.
    * AC3: If the word is not found, a "Word not found" (or similar) message is shown.
    * AC4: The UI includes a non-functional placeholder for TTS pronunciation.

---

### Story 2.5: Implement Text-to-Speech (TTS) for Android via KMP Abstraction

-   **User Story / Goal:** As a User, after looking up a word on Android, I want to be able to tap a button to hear its pronunciation in a US English accent, using the KMP `TtsPlayer` abstraction.
-   **Detailed Requirements:**
    * The `composeApp` module implements the `actual TtsPlayer` for Android (e.g., `AndroidTtsPlayer.kt` in `shared/src/androidMain/.../platform/`), wrapping Android's native `TextToSpeech` engine. This `actual` implementation should be injectable via Koin.
    * Configure TTS for US English accent (`Locale.US`).
    * When the pronunciation placeholder (from Story 2.3) is tapped for a looked-up word, the Android UI invokes the `speak` method of the KMP `TtsPlayer` instance.
    * Handle TTS engine initialization and potential errors (e.g., language not available) within the `actual` implementation, propagating a status if necessary.
-   **Acceptance Criteria (ACs):**
    * AC1: Tapping the pronunciation button in the Android UI triggers the `speak` method of the KMP `TtsPlayer`.
    * AC2: The `AndroidTtsPlayer` (actual KMP implementation) uses the native Android TTS engine to pronounce the word with a US English accent.
    * AC3: The app correctly handles TTS initialization, and the `AndroidTtsPlayer` can report if TTS is unavailable.

---

### Story 2.6: Implement Text-to-Speech (TTS) for iOS via KMP Abstraction

-   **User Story / Goal:** As a User, after looking up a word in the iOS Action Extension, I want to be able to tap a button to hear its pronunciation in a US English accent, using the KMP `TtsPlayer` abstraction.
-   **Detailed Requirements:**
    * The `iosApp` module (or the `shared/src/iosMain/.../platform/`) implements the `actual TtsPlayer` for iOS (e.g., `IosTtsPlayer.kt`), wrapping iOS's native `AVSpeechSynthesizer`. This `actual` implementation should be injectable via Koin.
    * Configure TTS for US English accent (e.g., `AVSpeechSynthesisVoice(language: "en-US")`).
    * When the pronunciation placeholder (from Story 2.4) is tapped, the iOS Action Extension UI invokes the `speak` method of the KMP `TtsPlayer` instance.
    * Handle TTS engine initialization and potential errors within the `actual` implementation.
-   **Acceptance Criteria (ACs):**
    * AC1: Tapping the pronunciation button in the iOS Action Extension UI triggers the `speak` method of the KMP `TtsPlayer`.
    * AC2: The `IosTtsPlayer` (actual KMP implementation) uses `AVSpeechSynthesizer` to pronounce the word with a US English accent.
    * AC3: The app correctly handles TTS initialization.

---

### Story 2.7: Implement Basic Cache for Offline Lookups (KMP Shared)

-   **User Story / Goal:** As a Developer, I want to implement a simple in-memory cache (`TranslationCache.kt`) for the last N (e.g., 20) offline word lookups in the KMP `shared` module, so that repeated queries for the same words are faster and reduce processing.
-   **Detailed Requirements:**
    * Modify the `OfflineDictionaryService` (or its implementation/repository) to use a caching wrapper or integrate caching logic directly. The cache itself can be a separate class (`TranslationCache`) injected via Koin.
    * Implement an in-memory LRU cache (e.g., using a `LinkedHashMap`) to store `WordDefinition` results for the last 20 unique words looked up.
    * Before querying the dictionary via SQLDelight, check the cache. If the word is found in the cache, return the cached result.
    * If not in cache, query the dictionary and store the `WordDefinition` result in the cache before returning.
-   **Acceptance Criteria (ACs):**
    * AC1: The KMP `OfflineDictionaryService` (or its underlying repository) uses an in-memory cache for `WordDefinition` objects.
    * AC2: Looking up a word for the second time (if within the last 20 unique lookups and cache hasn't been cleared) retrieves the result from the cache instead of re-querying the SQLite database.
    * AC3: The cache correctly evicts the oldest entries when its capacity (20 words) is exceeded.

## Change Log

| Change        | Date       | Version | Description                                                                                                                  | Author          |
| ------------- | ---------- | ------- | ---------------------------------------------------------------------------------------------------------------------------- | --------------- |
| Initial Draft | 2025-05-15 | 0.1     | First draft of Epic 2 stories for offline dictionary.                                                                        | PM Agent        |
| Update        | 2025-05-15 | 0.2     | Clarified SQLite/SQLDelight usage in Story 2.1 & 2.2. Reframed TTS stories 2.5 & 2.6 to use KMP `TtsPlayer` abstraction. | Architect Agent |

