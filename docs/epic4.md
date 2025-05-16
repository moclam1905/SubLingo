# Epic 4: Online Sentence Translation Feature (v0.2)

**Goal:** Integrate with online AI translation services (OpenAI/OpenRouter via KMP `OnlineTranslationService`) to provide translation for sentences and longer text segments. This includes implementing the API communication in the KMP shared module, updating platform UIs to display these translations, managing the embedded API key securely at build time, and managing user expectations regarding online functionality.

## Story List

### Story 4.1: Implement Online Translation Service (KMP Shared)

-   **User Story / Goal:** As a Developer, I need to create a service (`OnlineTranslationService`) in the KMP `shared` module to connect to OpenAI/OpenRouter APIs for sentence translation, so that the app can translate text beyond single-word offline lookups.
-   **Detailed Requirements:**
    * Create a service class (e.g., `OnlineTranslationServiceImpl`) in `commonMain` (e.g., in `com.nguyenmoclam.sublingo.shared.services` or `com.nguyenmoclam.sublingo.shared.data.repository`). This service will be injectable via Koin.
    * Implement functions to make API calls using Ktor client to a designated AI translation provider (OpenAI GPT-3.5 Turbo or an equivalent from OpenRouter, as per PRD and `docs/api-reference.md`).
    * The service should handle API request construction (using DTOs from `docs/data-models.md`), including sending the text to be translated and appropriate system/user messages. Target language for MVP is Vietnamese (from English).
    * **API Key Management (Corresponds to Tech Story A.1 & I.1):**
        * The service will require the API key to be passed to it (e.g., during initialization via Koin).
        * The KMP `shared` module will define an `expect` mechanism or receive the key from platform-specific code that reads it from `BuildConfig` (Android) or `Info.plist` (iOS) as per `docs/environment-vars.md`.
    * Parse the API response (using DTOs from `docs/data-models.md`) to extract the translated text.
    * Implement basic error handling for API calls (network errors, API errors), returning a `Result` or sealed class type. More comprehensive error handling in Story 4.5.
-   **Acceptance Criteria (ACs):**
    * AC1: The `OnlineTranslationService` can successfully make a translation request to the chosen AI API provider using an API key provided from the platform.
    * AC2: The service correctly parses a successful API response and returns the translated text as part of a `TranslationResult` domain model.
    * AC3: Basic API errors (e.g., invalid key, network issue) are caught, and an error state/message can be propagated.
    * AC4: The service can translate a sample English sentence into Vietnamese.
    * AC5: The API key is not hardcoded in the `shared` module and is provided externally.

---

### Story 4.2: Integrate Online Sentence Translation in Android UI

-   **User Story / Goal:** As a User, when I select a sentence or use OCR on a comment containing a sentence on Android, I want the option to translate it using the online service and see the result in the SubLingo UI.
-   **Detailed Requirements:**
    * The KMP `shared` module's domain logic (e.g., a use case called by `FloatingBubbleService`) will determine when to invoke online translation (e.g., if selected/OCR'd text is more than N words, or if offline lookup yields no result for the whole phrase).
    * If online translation is invoked by KMP core:
        * The Android UI (`FloatingBubbleService` or its Compose UI) should clearly indicate that an online service is being used (e.g., small icon, text).
        * Show a loading indicator during the API call.
        * The KMP `OnlineTranslationService` is called by the shared domain logic.
        * Display the translated sentence from `TranslationResult` in the Android floating bubble or designated UI.
        * This feature's availability depends on the "Enable Online Translation" setting (Epic 5). For this story, assume it's enabled.
-   **Acceptance Criteria (ACs):**
    * AC1: The Android UI triggers KMP shared logic which then calls the KMP `OnlineTranslationService` for multi-word text segments.
    * AC2: A visual cue indicates that an online translation is being performed.
    * AC3: A loading indicator is displayed during the online translation process.
    * AC4: The translated sentence is displayed in the Android UI.
    * AC5: If the online translation fails (basic error from Story 4.1 reported by KMP core), an appropriate message is shown.

---

### Story 4.3: Integrate Online Sentence Translation in iOS Extensions

-   **User Story / Goal:** As a User, when I select a sentence or share a screenshot containing a sentence to the iOS SubLingo extensions, I want the option to translate it using the online service and see the result.
-   **Detailed Requirements:**
    * Similar logic as Android: KMP `shared` module's domain logic (called by `ActionExtension` or `ShareExtension`) determines when to invoke online translation.
    * If online translation is invoked by KMP core:
        * The iOS extension UI (SwiftUI) should clearly indicate that an online service is being used.
        * Show a loading indicator during the API call.
        * The KMP `OnlineTranslationService` is called by the shared domain logic.
        * Display the translated sentence from `TranslationResult` in the iOS Action/Share Extension UI.
        * This feature's availability depends on the "Enable Online Translation" setting (Epic 5). For this story, assume it's enabled.
-   **Acceptance Criteria (ACs):**
    * AC1: The iOS Action/Share Extensions trigger KMP shared logic which then calls the KMP `OnlineTranslationService` for multi-word text segments.
    * AC2: A visual cue in the extension UI indicates that an online translation is being performed.
    * AC3: A loading indicator is displayed during the online translation process in the extension.
    * AC4: The translated sentence is displayed in the extension UI.
    * AC5: If the online translation fails (basic error from Story 4.1 reported by KMP core), an appropriate message is shown.

---

### Story 4.4: User Indication and Expectation Management for Online Translation

-   **User Story / Goal:** As a User, I want to be clearly informed when an online translation is occurring and understand potential implications (speed, data use, privacy), so I can make an informed choice.
-   **Detailed Requirements:**
    * **Visual Cues:** Implement consistent visual indicators (e.g., a small cloud icon or text like "Online translation") whenever an online translation is displayed on both Android (bubble) and iOS (extensions).
    * **Performance Expectation:**
        * Loading indicators (from Story 4.2, 4.3) manage this during the call. Target API latency is ≤ 2s.
    * **Privacy Note (Onboarding/Settings):** Information regarding text being sent to a third-party API should be part of the privacy policy and potentially highlighted during onboarding or in settings where online translation is toggled (Epic 5). This story focuses on the in-translation UI cues.
-   **Acceptance Criteria (ACs):**
    * AC1: A clear visual indicator (icon or text) is present alongside translations performed by the online service on both platforms.
    * AC2: The indicator clearly identifies the nature (online) of the translation.

---

### Story 4.5: Robust Error Handling for Online Translation

-   **User Story / Goal:** As a Developer, I need to implement comprehensive error handling for the KMP `OnlineTranslationService` and ensure its UI integration provides helpful feedback when online translation fails. (Corresponds to Tech Story G.2 for shared error structure).
-   **Detailed Requirements:**
    * Enhance the KMP `OnlineTranslationService` to specifically handle and wrap:
        * Network connectivity issues (e.g., Ktor `IOException`).
        * API-specific errors (e.g., quota limits `429`, invalid request `400`, server errors `5xx` from OpenAI/OpenRouter).
        * Timeout errors if the API call takes too long (Ktor client configuration).
    * Propagate these distinct error types using the shared error structure (e.g., sealed class like `DataResponse.Error` with specific error codes/messages) from the KMP service to the platform-specific UI.
    * Android and iOS UIs must display user-friendly messages for each error type (e.g., "No internet connection," "Translation service busy, try again later," "Translation failed: Invalid API Key").
-   **Acceptance Criteria (ACs):**
    * AC1: The KMP `OnlineTranslationService` correctly identifies and wraps different online translation error types into the shared error structure.
    * AC2: The Android UI displays specific, user-friendly error messages for network issues, API quota limits, and general API failures.
    * AC3: The iOS Extension UIs display specific, user-friendly error messages for network issues, API quota limits, and general API failures.
    * AC4: The app remains stable and does not crash if online translation fails.

---

### Story 4.6: Implement Basic Cache for Online Sentence Translations (KMP Shared)

-   **User Story / Goal:** As a Developer, I want to implement a simple in-memory cache (`TranslationCache.kt`) for the last N (e.g., 20) online sentence translations in the KMP `shared` module, so that repeated requests for the same sentences are faster and reduce API calls/costs.
-   **Detailed Requirements:**
    * Modify the `OnlineTranslationService` (or its implementation/repository) to use the `TranslationCache` (injected via Koin).
    * The cache will store `TranslationResult` objects.
    * The cache key should be the source sentence text (and potentially target language if it becomes configurable).
    * Before making an API call, check the cache. If the sentence is found, return the cached translation.
    * If not in cache, make the API call, and store the `TranslationResult` in the cache before returning.
-   **Acceptance Criteria (ACs):**
    * AC1: The KMP `OnlineTranslationService` (or its repository) uses the `TranslationCache` for sentence translations.
    * AC2: Translating a sentence for the second time (if within the last 20 unique sentences and cache not cleared) retrieves the result from the cache, avoiding an API call.
    * AC3: The cache correctly evicts the oldest entries when its capacity (20 sentences) is exceeded.

## Change Log

| Change        | Date       | Version | Description                                                                                                  | Author          |
| ------------- | ---------- | ------- | ------------------------------------------------------------------------------------------------------------ | --------------- |
| Initial Draft | 2025-05-15 | 0.1     | First draft of Epic 4 stories for online sentence translation.                                                 | PM Agent        |
| Update        | 2025-05-15 | 0.2     | Clarified API key handling via KMP, Koin injection, DTO usage, and use of shared error structure for Story 4.5. | Architect Agent |

