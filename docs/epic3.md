
# Epic 3: OCR-Based Comment Translation Feature (v0.2)

**Goal:** Develop the Optical Character Recognition (OCR) capabilities to extract text from Reddit comments captured via screenshots on both Android (using ML Kit) and iOS (using Vision Framework). The extracted text will then be processed by the KMP `shared` module (using `OfflineDictionaryService` or `OnlineTranslationService`) and displayed to the user.

## Story List

### Story 3.1: Android Screenshot Capture and Basic Cropping Logic

-   **User Story / Goal:** As a Developer, when the user taps the floating bubble on Android, I need to capture the current screen and apply basic cropping logic to isolate the potential comment area, so that a relevant image section is available for OCR.
-   **Detailed Requirements:**
    * On bubble tap (interaction defined in Epic 1, `FloatingBubbleService.kt`), capture a screenshot of the current screen content using appropriate Android screen capture APIs.
    * Implement a basic heuristic to crop the screenshot. This might involve:
        * Capturing a predefined region around the bubble's tap location.
        * A simple attempt to identify a rectangular area likely to contain a comment. (Advanced cropping in **Tech Story A.2**)
    * The primary goal is to reduce the image size sent to OCR and focus on the area of interest.
    * This story does *not* include the OCR processing itself, only image capture and initial cropping. The cropped image (Bitmap) will be passed to the `MlKitOcrProvider`.
-   **Acceptance Criteria (ACs):**
    * AC1: Tapping the Android floating bubble successfully captures a screenshot.
    * AC2: A cropped portion of the screenshot is available as an Android `Bitmap` object.
    * AC3: The cropping logic uses the tap location or a simple predefined region as a basis.
    * AC4: The captured/cropped image is ready to be passed to the `MlKitOcrProvider`.

---

### Story 3.2: Implement OCR Text Extraction on Android (ML Kit)

-   **User Story / Goal:** As a Developer, I need to integrate ML Kit on Android (via `MlKitOcrProvider.kt`) to perform OCR on a provided (cropped) image, so that text can be extracted from Reddit comments.
-   **Detailed Requirements:**
    * Use Google's ML Kit Text Recognition on-device API (`com.google.android.gms:play-services-mlkit-text-recognition`).
    * Create a service/provider class (e.g., `MlKitOcrProvider`) in `composeApp` that accepts an Android `Bitmap` and returns the recognized text as a string (or a structured result with confidence scores if available).
    * Handle potential ML Kit initialization and processing errors.
    * Focus on extracting English text. The result will be sent to the KMP `shared` module.
-   **Acceptance Criteria (ACs):**
    * AC1: The `MlKitOcrProvider` successfully processes a sample screenshot of a Reddit comment and extracts text.
    * AC2: The extracted text is returned as a string.
    * AC3: Errors during OCR processing (e.g., no text found, low confidence) are handled gracefully and can be reported.
    * AC4: OCR is performed on-device.

---

### Story 3.3: Display OCR-Extracted and Translated Text in Android UI

-   **User Story / Goal:** As a User, after tapping the bubble on a Reddit comment on Android, I want to see the extracted text (or its translation) displayed in the SubLingo UI.
-   **Detailed Requirements:**
    * After `MlKitOcrProvider` extracts text (Story 3.2), the `FloatingBubbleService` passes this text to the KMP `shared` module (specifically to a domain use case like `ProcessExtractedTextUseCase`).
    * The KMP use case will decide whether to use `OfflineDictionaryService` (for single words) or `OnlineTranslationService` (for sentences, if enabled).
    * For this story, if the extracted text is multiple words, initially display the raw extracted text or the offline translation of the first recognized word. Full sentence translation display will align with Epic 4.
    * Update the Android floating bubble's Jetpack Compose UI to show the OCR result/translation.
    * Provide user feedback during OCR processing (e.g., a loading indicator in the bubble).
-   **Acceptance Criteria (ACs):**
    * AC1: After bubble tap and OCR, the Android bubble UI displays the extracted text or the offline translation of the first word from the extracted text.
    * AC2: A loading indicator is shown in the bubble while OCR/translation is in progress.
    * AC3: If OCR fails to extract text, an appropriate "No text found" message is shown in the UI.

---

### Story 3.4: Implement Image Receiving in iOS Share Extension

-   **User Story / Goal:** As a Developer, I need the iOS Share Extension (`SubLingoShareExtension`) to correctly receive and handle an image (screenshot) shared by the user.
-   **Detailed Requirements:**
    * Ensure the `SubLingoShareExtension` is configured to accept image types (e.g., PNG, JPEG via its `Info.plist` and `NSExtensionActivationRule`).
    * When an image is shared to the extension, it should be accessible as a `UIImage` object within the extension's `ShareViewController.swift`.
    * Handle potential errors in receiving or loading the image. The image will be passed to the `VisionOcrProvider`.
-   **Acceptance Criteria (ACs):**
    * AC1: The `SubLingoShareExtension` can be invoked by sharing a screenshot.
    * AC2: The shared image is successfully loaded and available as a `UIImage` within the extension.
    * AC3: If no image or an invalid item is shared, the extension handles it gracefully (e.g., shows an error or dismisses).

---

### Story 3.5: Implement OCR Text Extraction on iOS (Vision Framework)

-   **User Story / Goal:** As a Developer, I need to integrate the Vision Framework (via `VisionOcrProvider.swift`) within the iOS Share Extension to perform OCR on a received image, so that text can be extracted from Reddit comments.
-   **Detailed Requirements:**
    * Use Apple's Vision Framework (`VNRecognizeTextRequest`) for on-device text recognition.
    * Create a service/provider class (e.g., `VisionOcrProvider`) in `iosApp` that accepts a `UIImage` and returns the recognized text as a string (or a structured result).
    * Prioritize accuracy in the `VNRecognizeTextRequest`.
    * Handle potential Vision framework initialization and processing errors.
    * Focus on extracting English text. The result will be sent to the KMP `shared` module.
-   **Acceptance Criteria (ACs):**
    * AC1: The `VisionOcrProvider` successfully processes a sample screenshot of a Reddit comment and extracts text within the Share Extension.
    * AC2: The extracted text is returned as a string.
    * AC3: Errors during OCR processing (e.g., no text found, low confidence) are handled gracefully and can be reported.
    * AC4: OCR is performed on-device.

---

### Story 3.6: Display OCR-Extracted and Translated Text in iOS Share Extension UI

-   **User Story / Goal:** As a User, after sharing a screenshot of a Reddit comment to the SubLingo iOS Share Extension, I want to see the extracted text (or its translation).
-   **Detailed Requirements:**
    * After `VisionOcrProvider` extracts text (Story 3.5), the `ShareViewController.swift` passes this text to the KMP `shared` module (specifically to a domain use case like `ProcessExtractedTextUseCase`).
    * The KMP use case will decide whether to use `OfflineDictionaryService` (for single words) or `OnlineTranslationService` (for sentences, if enabled).
    * For this story, if the extracted text is multiple words, initially display the raw extracted text or the offline translation of the first recognized word. Full sentence translation display will align with Epic 4.
    * Update the Share Extension's SwiftUI UI to show the OCR result/translation.
    * Provide user feedback during OCR processing (e.g., a loading indicator in the extension UI).
-   **Acceptance Criteria (ACs):**
    * AC1: After sharing an image and OCR, the iOS Share Extension UI displays the extracted text or the offline translation of the first word.
    * AC2: A loading indicator is shown while OCR/translation is in progress within the extension.
    * AC3: If OCR fails to extract text, an appropriate "No text found" message is shown in the extension UI.

---

### Story 3.7: Basic Image Preprocessing for OCR Improvement (Platform Specific)

-   **User Story / Goal:** As a Developer, I want to implement basic image preprocessing routines (e.g., grayscale conversion) in the platform-specific OCR providers (`MlKitOcrProvider`, `VisionOcrProvider`) before sending an image to OCR, so that OCR accuracy can be potentially improved on varied Reddit comment styles.
-   **Detailed Requirements:**
    * **Android (`MlKitOcrProvider`):** Implement a function to convert the input `Bitmap` to grayscale before passing it to ML Kit.
    * **iOS (`VisionOcrProvider`):** Implement a function to convert the input `UIImage` to grayscale (e.g., using Core Image filters) before passing it to the Vision Framework.
    * Other basic preprocessing like adjusting contrast or binarization could be considered if straightforward and demonstrably beneficial, but grayscale is the minimum.
    * The goal is to standardize the image somewhat to aid the OCR engines.
-   **Acceptance Criteria (ACs):**
    * AC1: Images are converted to grayscale within `MlKitOcrProvider` (Android) and `VisionOcrProvider` (iOS) before being sent to the respective OCR engines.
    * AC2: The preprocessing step does not significantly degrade overall OCR processing time.
    * AC3: Qualitative testing on a few sample Reddit comment screenshots shows that grayscale conversion does not harm (and ideally helps or has no negative effect on) OCR results compared to no preprocessing.

## Change Log

| Change        | Date       | Version | Description                                                                  | Author          |
| ------------- | ---------- | ------- | ---------------------------------------------------------------------------- | --------------- |
| Initial Draft | 2025-05-15 | 0.1     | First draft of Epic 3 stories for OCR features.                              | PM Agent        |
| Update        | 2025-05-15 | 0.2     | Clarified interaction with KMP shared module and platform-specific providers. | Architect Agent |

