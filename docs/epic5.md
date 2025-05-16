
# Epic 5: Onboarding & Settings (v0.2)

**Goal:** Create clear user onboarding flows, especially for Android permissions and iOS extension usage, and implement a minimal settings screen (using KMP `AppSettings` for shared preferences where possible) to allow users to manage online translation features, cached data, and access the privacy policy.

## Story List

### Story 5.1: Implement Android Onboarding for "Draw Over Other Apps" Permission

-   **User Story / Goal:** As a new Android User, I want a clear, interactive guide explaining why the "Draw Over Other Apps" permission is needed and how to grant it, so I can use the floating bubble feature.
-   **Detailed Requirements:**
    * On first launch, or when the bubble feature is first accessed via `FloatingBubbleService`, if the permission is not granted, present an onboarding Jetpack Compose screen.
    * The flow should visually explain (e.g., using a GIF, short video, or sequential static images with text) the benefit of the floating bubble.
    * Clearly show the steps to navigate to system settings and grant the permission.
    * Provide a button/link to take the user directly to the relevant system settings screen for this permission.
    * The app should gracefully handle cases where the user denies or navigates away without granting permission (e.g., bubble feature remains disabled, with an option to re-initiate onboarding from settings or a UI prompt).
-   **Acceptance Criteria (ACs):**
    * AC1: An onboarding flow for the "Draw Over Other Apps" permission is presented if the permission is not granted when the bubble feature is attempted.
    * AC2: The onboarding uses visual aids and clear text.
    * AC3: A direct link/button to the system settings for this permission is provided.
    * AC4: The app detects whether the permission was granted after the user returns from settings.
    * AC5: If permission is denied, the bubble feature is appropriately disabled or limited, and the user can find a way to re-attempt granting it later (e.g., from a settings menu item).

---

### Story 5.2: Implement Android Onboarding for `UsageStatsManager` Permission (Conditional)

-   **User Story / Goal:** As a new Android User, if the app uses `UsageStatsManager` for auto-show/hide of the bubble, I want a clear guide explaining why this permission is needed and how to grant it, so I can benefit from the bubble appearing only when I'm using Reddit.
-   **Detailed Requirements:**
    * **Conditionality:** Architect and PM to confirm if "auto-show/hide" (`ForegroundAppDetector.kt`) is essential for MVP or if manual toggle of the bubble is sufficient to reduce permission friction. If included:
    * Present an onboarding Jetpack Compose screen if this permission is required by `ForegroundAppDetector` and not granted.
    * Visually explain the benefit (bubble auto-appears/hides with Reddit).
    * Clearly show steps to grant the permission in system settings for `PACKAGE_USAGE_STATS`.
    * Provide a button/link to the relevant system settings screen.
    * Handle denial gracefully (e.g., bubble requires manual toggle, or this specific auto-feature is disabled).
-   **Acceptance Criteria (ACs):**
    * AC1: (If `ForegroundAppDetector` feature is included for MVP) An onboarding flow for `UsageStatsManager` permission is presented if required and not granted.
    * AC2: The onboarding explains the benefit and shows how to grant the permission.
    * AC3: A direct link/button to system settings is provided.
    * AC4: If permission is denied, the auto-show/hide feature is disabled, and the bubble defaults to manual toggle or another defined behavior.

---

### Story 5.3: Implement iOS Onboarding for Extension Usage

-   **User Story / Goal:** As a new iOS User, I want a simple guide on how to enable and use the SubLingo Action and Share Extensions, and understand their benefits, so I can effectively translate within Reddit.
-   **Detailed Requirements:**
    * On first launch of the main `iosApp` (`SubLingoApp.swift`), present a brief SwiftUI onboarding flow.
    * Explain how to enable the `SubLingoActionExtension` and `SubLingoShareExtension` in the iOS Share Sheet settings if they are not automatically enabled by the system.
    * Visually demonstrate (e.g., GIF or static images) how to use the Action Extension (for selected text) and the Share Extension (for screenshots).
    * Briefly highlight the value proposition (e.g., "Access offline dictionary details & enhanced OpenAI translation").
-   **Acceptance Criteria (ACs):**
    * AC1: An onboarding flow is presented on the first launch of the iOS app.
    * AC2: The guide explains how to enable and use both Action and Share extensions.
    * AC3: Visual aids are used to demonstrate the usage.
    * AC4: The value of using the extensions is briefly communicated.

---

### Story 5.4: Create Basic Settings Screen UI (Android & iOS)

-   **User Story / Goal:** As a User, I want to access a settings screen within the app, so I can manage app preferences.
-   **Detailed Requirements:**
    * Implement a navigable "Settings" screen accessible from the main UI of both the Android (`composeApp`) and iOS (`iosApp`) apps.
    * The screen should have a clean, simple layout.
    * This screen will host the setting toggles and links defined in subsequent stories.
    * Use Jetpack Compose for Android settings UI (`SettingsScreen.kt`), SwiftUI for iOS settings UI (`SettingsView.swift`).
    * A KMP `shared` ViewModel (e.g., `SettingsViewModel.kt` in `com.nguyenmoclam.sublingo.shared.presentation`) can manage the settings state using `AppSettings` (Multiplatform-Settings) and be observed by the platform UIs.
-   **Acceptance Criteria (ACs):**
    * AC1: A "Settings" screen is accessible from the main app interface on Android.
    * AC2: A "Settings" screen is accessible from the main app interface on iOS.
    * AC3: The screen layout is clean and provides placeholders for settings items, driven by a shared KMP ViewModel if implemented.

---

### Story 5.5: Implement "Toggle Online Sentence Translation" Setting

-   **User Story / Goal:** As a User, I want to be able to enable or disable online sentence translation via a setting, so I can control data usage and choose my preferred translation method.
-   **Detailed Requirements:**
    * Add a switch/toggle control on the Settings screen (Android & iOS) labeled "Enable Online Sentence Translation" (or similar).
    * The state of this toggle should be persisted using the KMP `AppSettings` (Multiplatform-Settings).
    * The KMP `OnlineTranslationService` (or the domain logic calling it) must respect this setting. If disabled, it should not attempt to make API calls and should perhaps return a specific state/error indicating the feature is off.
    * Default state: Enabled (to be confirmed, but typically good for showcasing features).
-   **Acceptance Criteria (ACs):**
    * AC1: A toggle for online sentence translation is present on the Settings screen on both platforms.
    * AC2: The setting's state is persisted via KMP `AppSettings` and respected across app sessions.
    * AC3: If the setting is OFF, the KMP `shared` module does not attempt to call the online translation API, and the UI may inform the user that the feature is disabled if an online translation would have otherwise occurred.
    * AC4: If the setting is ON, online translation functions as designed in Epic 4.

---

### Story 5.6: Implement "Clear Translation Cache" Setting

-   **User Story / Goal:** As a User, I want an option to clear cached translations, so I can free up a small amount of storage or reset my translation history.
-   **Detailed Requirements:**
    * Add a button on the Settings screen (Android & iOS) labeled "Clear Translation Cache" (or similar).
    * Tapping this button should trigger a call to the KMP `shared` module (e.g., a `ClearCacheUseCase.kt`) which will clear:
        * The offline word lookup cache (managed by `TranslationCache.kt` for `WordDefinition` objects from Story 2.7).
        * The online sentence translation cache (managed by `TranslationCache.kt` for `TranslationResult` objects from Story 4.6).
    * Provide a confirmation dialog before clearing the cache.
    * Provide feedback to the user once the cache is cleared.
-   **Acceptance Criteria (ACs):**
    * AC1: A "Clear Translation Cache" button is present on the Settings screen on both platforms.
    * AC2: Tapping the button presents a confirmation dialog.
    * AC3: Upon confirmation, the KMP `TranslationCache` for both offline and online lookups is cleared.
    * AC4: User receives feedback that the cache has been cleared.

---

### Story 5.7: Display Privacy Policy Link in Settings

-   **User Story / Goal:** As a User, I want to easily access the app's Privacy Policy, so I can understand how my data is handled.
-   **Detailed Requirements:**
    * Add a tappable item on the Settings screen (Android & iOS) labeled "Privacy Policy."
    * Tapping this item should open the app's Privacy Policy.
    * For MVP, the Privacy Policy can be a simple, locally bundled HTML or Markdown file displayed in a platform-native WebView or a dedicated rich text view. A URL to a hosted policy is also acceptable if simpler for MVP. (The content of `docs/privacy-policy.md` needs to be drafted - see Story 6.2).
-   **Acceptance Criteria (ACs):**
    * AC1: A "Privacy Policy" link/button is present on the Settings screen on both platforms.
    * AC2: Tapping the link successfully displays the Privacy Policy content to the user (either from a bundled asset or a webview).
    * AC3: The mechanism to display the Privacy Policy is functional. (Content accuracy is covered in Story 6.2).

## Change Log

| Change        | Date       | Version | Description                                                                                            | Author          |
| ------------- | ---------- | ------- | ------------------------------------------------------------------------------------------------------ | --------------- |
| Initial Draft | 2025-05-15 | 0.1     | First draft of Epic 5 stories for onboarding & settings.                                               | PM Agent        |
| Update        | 2025-05-15 | 0.2     | Clarified use of KMP AppSettings, shared ViewModel for settings, and KMP use case for clearing cache. | Architect Agent |

