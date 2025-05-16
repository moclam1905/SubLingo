
# SubLingo Testing Strategy

## 1. Overall Philosophy & Goals

The testing strategy for SubLingo MVP prioritizes ensuring the reliability and correctness of core user-facing features. While comprehensive automation across all layers is a long-term goal, the initial MVP focus will be on:

1.  **Robustness of Core Logic:** Ensuring the KMP `shared` module (dictionary lookups, translation service interactions, caching, settings) is well-tested with unit tests.
2.  **Critical Path Validation:** Manually testing end-to-end user flows on both Android and iOS to ensure core functionality (text selection translation, OCR comment translation, offline dictionary access, online sentence translation) works as expected.
3.  **Platform-Specific Feature Integrity:** Validating the Android floating bubble and iOS App Extensions.
4.  **Progressive Automation:** Establishing a foundation for automated testing that can be expanded post-MVP.

**Goals for MVP:**

* **High unit test coverage** for the KMP `shared` module's domain and data layers.
* Successful manual E2E testing of all primary user stories defined in Epics 1-5.
* Ensure no critical crashes or data corruption issues.
* Validate OCR accuracy on a representative set of Reddit content.
* Confirm performance NFRs (offline lookup < 2s, OCR < 3s, online API call ≤ 2s) through manual or basic automated checks.

## 2. Testing Levels

### 2.1. Unit Tests

* **Scope:** Test individual functions, classes, or components in isolation within the KMP `shared` module and, where applicable, in platform-specific utility classes or ViewModels. Focus on business logic, algorithms, data transformations, and conditional paths.
* **Tools:**
    * **KMP `shared` module:**
        * KotlinTest (`io.kotest:kotest-runner-junit5`) or the built-in `kotlin.test` library. KotlinTest is preferred for its rich matchers and specification styles.
        * Mocking: MockK (`io.mockk:mockk`) for mocking dependencies within tests.
    * **Android (`composeApp`):** JUnit 4/5, Robolectric (for UI-independent Android framework code if needed), MockK/Mockito.
    * **iOS (`iosApp`):** XCTest framework, Swift's native testing capabilities.
* **Mocking/Stubbing:**
    * KMP: Use MockK to mock repository interfaces, platform service `expect` declarations, or external dependencies like Ktor's `HttpClient`.
    * Android/iOS: Mock platform-specific dependencies.
* **Location:**
    * KMP: `shared/src/commonTest/kotlin/`, `shared/src/androidTest/kotlin/`, `shared/src/iosTest/kotlin/` (mirroring the structure of the code under test).
    * Android: `composeApp/src/androidUnitTest/kotlin/`.
    * iOS: Within the `iosAppTests` or `SubLingoTests` target in Xcode.
* **Expectations (MVP):**
    * High coverage for all use cases, repositories, and critical utility functions in `shared/src/commonMain/`.
    * Focus on testing logic within `OnlineTranslationService`, `OfflineDictionaryService`, `TranslationCache`, `AppSettings`.
    * Ensure SQLDelight queries are tested (SQLDelight can generate in-memory drivers for testing).
    * Tests should be fast and run as part of local builds.

### 2.2. Integration Tests

* **Scope (MVP - Limited Automation):**
    * KMP: Verify the interaction between key components within the `shared` module (e.g., a use case correctly interacts with its repository, which in turn interacts with a data source). These might still use mocks for external boundaries like actual network calls or full file system access.
    * Platform-Specific: For MVP, most platform integration testing (e.g., bubble interacting with KMP core, extension UI with KMP core) will be primarily covered by manual E2E testing. Basic automated integration tests for ViewModel-to-Service interactions can be considered.
* **Tools:**
    * KMP: KotlinTest, MockK.
    * Android: JUnit 4/5, Espresso (for basic UI integration if time permits, otherwise manual), Robolectric.
    * iOS: XCTest.
* **Location:**
    * KMP: Potentially within the existing unit test source sets (`commonTest`, etc.) but distinguished by testing scope.
    * Android: `composeApp/src/androidTest/kotlin/` (for instrumented tests).
* **Expectations (MVP):**
    * Focus on contract validation between key KMP `shared` module components.
    * Automated platform-level integration tests are a lower priority for MVP compared to shared module unit tests and manual E2E.

### 2.3. End-to-End (E2E) / Acceptance Tests (Manual for MVP)

* **Scope:** Test complete user flows across the application on actual devices or emulators/simulators. This includes UI interactions, permission handling, OCR, offline dictionary access, online translation, and settings.
* **Tools (Manual Execution):**
    * Test case management (e.g., a simple spreadsheet, a lightweight test case management tool, or GitHub Issues).
    * Android Studio & Xcode for deploying builds.
    * Physical devices representing target OS versions and screen sizes.
* **Test Scenarios (to be derived from Epics & User Stories):**
    * **Epic 1 (Core Setup):**
        * Install and launch app on Android/iOS.
        * Verify basic UI shells appear.
        * Android: Manually toggle and drag floating bubble. Grant "Draw Over Other Apps" permission.
        * iOS: Invoke Action & Share extensions with sample text/image; verify placeholder UI.
    * **Epic 2 (Offline Dictionary):**
        * Lookup various words (present, not present, with examples, without).
        * Verify definitions, parts of speech, and examples display correctly in Android bubble / iOS extension.
        * Test TTS pronunciation for words on both platforms (US English accent).
        * Verify offline lookup cache behavior (repeated lookups).
    * **Epic 3 (OCR Translation):**
        * Android: Tap bubble on various Reddit comments (light/dark mode, different fonts, nested comments); verify screenshot capture, OCR, and display of result (first word definition or raw text for MVP).
        * iOS: Share screenshots of various Reddit comments to Share Extension; verify OCR and display. Test Live Text to Action Extension flow.
        * Test image preprocessing (grayscale) effect if implemented.
    * **Epic 4 (Online Sentence Translation):**
        * Select/OCR longer text segments; verify online translation invocation (with UI indicator).
        * Verify translated sentence display.
        * Test error handling (no network, API key error - simulated if possible, or by temporarily misconfiguring).
        * Verify online translation cache.
    * **Epic 5 (Onboarding & Settings):**
        * Go through Android "Draw Over Other Apps" & "UsageStatsManager" (if implemented) onboarding flows. Verify permission granting and app behavior on denial.
        * Go through iOS extension usage onboarding.
        * Access Settings screen. Toggle "Enable Online Sentence Translation" and verify behavior. Clear translation cache and verify. Access Privacy Policy.
    * **Epic 6 (Polish & Error Handling):**
        * Systematically test various error conditions (as listed in Story 6.1).
        * Review UI for consistency, typos, and visual polish.
* **Expectations (MVP):**
    * All core features (as per epics) must pass manual E2E testing on representative Android and iOS devices/OS versions before release consideration.
    * Focus on user experience, NFRs (performance, reliability), and visual correctness.
    * Document test results, bugs found, and fixes.

### 2.4. OCR Accuracy Testing (Specialized Manual Testing)

* **Scope:** Specifically test the accuracy of ML Kit (Android) and Vision Framework (iOS) on a diverse set of Reddit comment screenshots (as per Story 6.6).
* **Method:**
    * Prepare a test suite of at least 20-30 diverse Reddit comment screenshots (different themes, fonts, comment structures, image quality).
    * Manually trigger OCR via the app on these images.
    * Compare extracted text against actual comment text to identify accuracy, common error patterns (e.g., misrecognized characters, missed lines, text extraction from non-comment areas).
* **Documentation:** Record success/failure rates, types of errors, and effectiveness of any preprocessing steps.

## 3. Test Data Management

* **Offline Dictionary:** A bundled "lite" SQLite dictionary will be used. For testing, this dictionary should contain known words, words with multiple definitions/examples, and some edge cases.
* **OCR Test Images:** A curated set of Reddit comment screenshots (as mentioned above).
* **API Mocks (Unit/Integration Tests):** For testing `OnlineTranslationService` without actual API calls, Ktor's `MockEngine` can be used to return predefined JSON responses for specific requests.
* **User Input:** Manually entered or selected text during E2E testing.

## 4. CI/CD Integration (Post-MVP)

* While not an MVP priority for full setup, the architecture should facilitate future CI/CD:
    * Unit tests (especially for the `shared` module) should be runnable via Gradle commands and integrated into a CI pipeline (e.g., GitHub Actions) to run on every commit/PR.
    * Builds for Android (AAB/APK) and iOS (`.ipa`) should be scriptable.
    * Automated UI tests (Espresso, XCUITest, or cross-platform like Maestro/Appium) are a post-MVP goal.

## 5. Developer Local Testing

* Developers are expected to run relevant unit tests locally before pushing code.
* Manual testing of their implemented feature on an emulator/simulator or device is required before creating a pull request.
* Android Studio and Xcode provide tools for running tests, debugging, and profiling.

## Change Log

| Change        | Date       | Version | Description                                                               | Author          |
| :------------ | :--------- | :------ | :------------------------------------------------------------------------ | :-------------- |
| Initial draft | 2025-05-15 | 0.1     | Initial draft based on PRD guidelines, focusing on KMP unit tests & manual E2E. | Architect Agent |
