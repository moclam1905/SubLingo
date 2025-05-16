# Epic 6: Polish, Error Handling, and Privacy (v0.2)

**Goal:** Implement comprehensive error handling (leveraging the shared error structure from **Tech Story G.2**) across all features, ensure full adherence to the defined privacy policy including its in-app accessibility, and refine the overall user experience (UX) and user interface (UI) on both Android and iOS based on initial internal testing and feedback. This epic aims to deliver a stable, polished, and trustworthy MVP.

## Story List

### Story 6.1: System-Wide Error Handling Review and Enhancement

-   **User Story / Goal:** As a Developer, I need to review and enhance error handling across all application features (Epics 1-5) to ensure that users receive clear, informative, and actionable error messages for any foreseeable issue, utilizing the KMP shared error reporting structure.
-   **Detailed Requirements:**
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
-   **Acceptance Criteria (ACs):**
    * AC1: All known error conditions in core features have corresponding user-facing error messages derived from the shared error structure or platform-specific error handling.
    * AC2: Error messages are clear, concise, and helpful to the user.
    * AC3: The application gracefully handles and recovers from these errors without crashing.
    * AC4: A consistent error display mechanism (e.g., snackbars, dialogs, inline messages) is used across the app where appropriate.

---

### Story 6.2: Finalize and Integrate Privacy Policy Content

-   **User Story / Goal:** As a PM/Developer, I need to finalize the content of the Privacy Policy (`docs/privacy-policy.md`) and ensure it's correctly integrated and accessible within the app as per Story 5.7.
-   **Detailed Requirements:**
    * Review and finalize the text of the Privacy Policy. It must accurately reflect:
        * Data collected (selected text, text from OCR, cached translations, settings preferences).
        * How data is processed (on-device for OCR/offline dictionary, text to OpenAI/OpenRouter for online translation).
        * API key usage and storage (acknowledging current MVP build-time configuration risk and advising users about data sent to OpenAI).
        * Explicitly state that no images are stored by SubLingo post-OCR.
        * Details about the translation cache (on-device, user-clearable).
        * Use of permissions (Overlay, UsageStats if applicable, Clipboard access if relevant).
    * Ensure the finalized Privacy Policy content is correctly displayed when accessed from the Settings screen on both Android and iOS (using the mechanism from Story 5.7).
-   **Acceptance Criteria (ACs):**
    * AC1: The Privacy Policy content in `docs/privacy-policy.md` is finalized and accurately reflects all data handling practices of the MVP.
    * AC2: The finalized Privacy Policy is accessible from the Settings screen on both platforms and displays correctly.
    * AC3: The display of the Privacy Policy is clear and legible.

---

### Story 6.3: Refine Android Floating Bubble UX/UI

-   **User Story / Goal:** As an Android User, I want the floating bubble to be intuitive, non-obtrusive, and visually polished, providing clear feedback for its state.
-   **Detailed Requirements:**
    * Based on internal testing, refine the bubble's (`FloatingBubbleService` and its Compose UI):
        * **Visual Design:** Ensure it's visually appealing (Material 3), distinct but not overly distracting. Consider a subtle idle state vs. active/loading state.
        * **Interaction:** Smooth dragging, easy dismissal (if applicable, e.g., drag to a target), clear tap target.
        * **Feedback:** Visual feedback when tapped (e.g., ripple, state change), loading indicators during processing (OCR, translation), clear display of results or errors.
        * **Responsiveness:** Ensure the bubble responds quickly to interactions and KMP core responses.
    * Test the bubble's behavior over various apps (especially Reddit) and screen orientations.
-   **Acceptance Criteria (ACs):**
    * AC1: The Android floating bubble's visual appearance is polished and adheres to Material 3 guidelines.
    * AC2: Bubble interactions (drag, tap, dismiss) are smooth and responsive.
    * AC3: Clear visual feedback is provided for bubble states (idle, active/loading, showing result, error).
    * AC4: The bubble maintains its functionality and appearance consistently across different scenarios.

---

### Story 6.4: Refine iOS Extension UX/UI

-   **User Story / Goal:** As an iOS User, I want the Action and Share Extensions (`SubLingoActionExtension`, `SubLingoShareExtension`) to be fast, easy to understand, and visually consistent with a polished app experience.
-   **Detailed Requirements:**
    * Based on internal testing, refine the SwiftUI UI/UX of both Action and Share extensions:
        * **Launch Speed:** Ensure extensions launch as quickly as possible (minimize Koin init time if it impacts extension launch).
        * **Clarity:** Make sure the purpose and flow within each extension are immediately understandable.
        * **Visual Design:** Consistent and clean SwiftUI UI for displaying information, results, and errors, adhering to iOS HIG.
        * **Feedback:** Clear loading indicators and feedback messages.
    * Test extensions with various shared content types (text, different images) and from different apps (especially Reddit).
-   **Acceptance Criteria (ACs):**
    * AC1: iOS Action and Share Extensions launch quickly.
    * AC2: The UI within extensions is clear, intuitive, and visually polished.
    * AC3: Clear visual feedback is provided for loading states, results, and errors within the extensions.
    * AC4: Extensions handle various valid inputs gracefully and provide informative messages for invalid inputs.

---

### Story 6.5: Performance Review and Bottleneck Identification

-   **User Story / Goal:** As a Developer, I want to review the performance of critical operations (offline lookup, OCR, online translation) against NFRs and identify any major bottlenecks for potential optimization.
-   **Detailed Requirements:**
    * Re-measure performance for:
        * Offline word lookup speed (Target: < 2s, KMP Service: < 500ms) - Test via KMP `OfflineDictionaryService`.
        * OCR processing speed (Target: < 3s) - Test on-device `MlKitOcrProvider` (Android) and `VisionOcrProvider` (iOS) with sample images.
        * Online sentence translation API call (Target API: ≤ 2s, user-perceived will be higher) - Test KMP `OnlineTranslationService` response time, factoring in network from target region.
    * Perform these tests on target devices/emulators.
    * Identify any operations consistently failing to meet NFRs or exhibiting significant lag.
    * Document identified bottlenecks. This story is about identification; actual optimization might be a separate task or deferred if minor and MVP goals are met.
-   **Acceptance Criteria (ACs):**
    * AC1: Performance metrics for key operations (offline lookup, OCR, online translation) are measured and documented against NFRs.
    * AC2: Any operations significantly missing performance targets are identified.
    * AC3: A list of potential performance bottlenecks is created (if any).

---

### Story 6.6: Focused OCR Accuracy Testing and Minor Adjustments

-   **User Story / Goal:** As a QA/Developer, I want to test OCR accuracy on a diverse set of Reddit comment screenshots and make minor adjustments to preprocessing (Story 3.7) or cropping (Tech Story A.2) if clear benefits are identified, to maximize OCR reliability for MVP.
-   **Detailed Requirements:**
    * Collect a diverse set of at least 20-30 Reddit comment screenshots:
        * Different themes (light/dark).
        * Various system font sizes.
        * Different comment densities and layouts (e.g., nested comments, comments with icons/flairs).
    * Test OCR accuracy (ML Kit on Android, Vision Framework on iOS) on these samples with current preprocessing.
    * If specific patterns of failure are noted, investigate if minor, low-effort adjustments to image preprocessing (e.g., slight contrast change, grayscale toggle, small refinement to cropping logic) can yield noticeable improvements without adding significant complexity.
    * Document OCR performance on the test set (e.g., success rate, common error types).
-   **Acceptance Criteria (ACs):**
    * AC1: OCR accuracy is tested against a documented set of diverse Reddit comment screenshots.
    * AC2: Results (successful extractions, common failure types) are documented.
    * AC3: At least one minor preprocessing/cropping adjustment is attempted and its impact evaluated if initial results show consistent, easily addressable issues.
    * AC4: Overall OCR success rate and limitations for MVP are better understood and documented.

---

### Story 6.7: Final Onboarding Flow Review and Polish

-   **User Story / Goal:** As a PM/QA, I want to review the complete onboarding flows on both Android and iOS for clarity, accuracy, and user-friendliness, ensuring all steps are correct and easy to follow.
-   **Detailed Requirements:**
    * Go through the entire first-time user experience on both platforms, focusing on:
        * Android permission onboarding (Draw Over Other Apps - Story 5.1, UsageStatsManager if implemented - Story 5.2).
        * iOS extension enabling and usage guide (Story 5.3).
    * Check all instructional text for clarity, conciseness, and grammar.
    * Verify that all visual aids (GIFs, images) are accurate, easy to understand, and culturally appropriate for the target Vietnamese users.
    * Ensure navigation through onboarding is smooth, users can exit/skip where appropriate (and re-access info if needed, e.g., via a help section in settings).
-   **Acceptance Criteria (ACs):**
    * AC1: The Android onboarding flow for permissions is verified as clear, accurate, and guides the user effectively.
    * AC2: The iOS onboarding flow for extensions is verified as clear, accurate, and helpful.
    * AC3: All text and visual elements in the onboarding flows are polished and free of errors.
    * AC4: The onboarding process feels smooth, professional, and instills confidence.

---

### Story 6.8: General UI/UX Consistency and Polish Pass

-   **User Story / Goal:** As a Developer/QA, I want to perform a general review of the entire application UI on both platforms to identify and fix minor inconsistencies, typos, layout issues, or awkward interactions.
-   **Detailed Requirements:**
    * Review all screens, dialogs, and UI elements in the app (main app UI in `composeApp`/`iosApp`, Android bubble, iOS extensions).
    * Check for:
        * Consistent terminology and button labels (consider English and Vietnamese if any UI text is localized for MVP).
        * Consistent font usage and text alignment.
        * Consistent use of colors and iconography (if any defined).
        * Proper layout and spacing on different representative screen sizes/orientations for target devices.
        * Any remaining typos or grammatical errors in UI text.
        * Any awkward or confusing user interactions not caught in specific feature testing.
    * Fix identified minor issues. Larger redesigns are out of scope for this story.
-   **Acceptance Criteria (ACs):**
    * AC1: At least 10 minor UI/UX polish items are identified and addressed across both platforms (or a thorough review is documented).
    * AC2: The application presents a more consistent and professional look and feel.
    * AC3: Obvious typos and grammatical errors in the UI are corrected.

## Change Log

| Change        | Date       | Version | Description                                                                                 | Author          |
| ------------- | ---------- | ------- | ------------------------------------------------------------------------------------------- | --------------- |
| Initial Draft | 2025-05-15 | 0.1     | First draft of Epic 6 stories for polish & QA.                                            | PM Agent        |
| Update        | 2025-05-15 | 0.2     | Clarified interaction with shared error structure and KMP components for error handling. | Architect Agent |

