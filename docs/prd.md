# **SubLingo (Instant Translate for Reddit MVP) Product Requirements Document (PRD)**

## **Intro**

SubLingo (Instant Translate for Reddit MVP) is a mobile application designed for Android and iOS to provide Reddit users, initially targeting Vietnamese users, with a seamless and quick way to translate English words, phrases, and comments within the Reddit app. The core problem it solves is the disruption and friction caused by switching between Reddit and separate translation apps, thereby enhancing user engagement, comprehension, and the overall Browse experience for non-native English speakers. This MVP focuses on delivering core translation functionalities, including offline single-word lookups and online sentence translation, with a platform-tailored UX.

## **Goals and Context**

* **Project Objectives:**  
  * Enable users to translate English text (words, phrases, comments) directly within the Reddit mobile app interface without significant disruption.  
  * Provide fast translation results (aiming for \<5 seconds for lookups).  
  * Offer offline translation capabilities for single words (definitions, examples, pronunciation).  
  * Support online translation for more complex sentences using AI services.  
  * Deliver a user-friendly and intuitive experience tailored to Android and iOS platforms.  
  * Initially target Vietnamese Reddit users, with potential for broader appeal.  
* **Measurable Outcomes:**  
  * Number of active users.  
  * Frequency of translations per user session.  
  * User retention rate.  
  * Average time to display translation.  
  * Success rate of OCR for comment translation.  
  * App store ratings and reviews.  
* **Success Criteria:**  
  * The app provides reliable single-word offline translations with definitions and examples.  
  * The app provides optional, reasonably accurate sentence translations via OpenAI/OpenRouter.  
  * The translation process for posts and comments is significantly faster and more convenient than manually switching apps.  
  * Core features are stable on targeted Android (6.0+) and iOS (16+) versions.  
  * Users can successfully navigate and use the app's primary features with minimal onboarding friction.  
* **Key Performance Indicators (KPIs):**  
  * Daily Active Users (DAU) / Monthly Active Users (MAU).  
  * Number of translations initiated (offline vs. online).  
  * Average translation speed.  
  * Task completion rate for core translation flows.  
  * Permission grant rates for critical Android permissions.  
  * Crash rate.

## **Scope and Requirements (MVP / Current Version)**

### **Functional Requirements (High-Level)**

* **FR1: In-App Text Selection Translation (Posts):**  
  * Android: Allow users to translate selected text from Reddit posts via a copy/share mechanism triggering a floating bubble/interface.  
  * iOS: Allow users to translate selected text from Reddit posts via the Share menu, invoking an Action Extension.  
* **FR2: In-App Comment Translation (OCR-based):**  
  * Android: Allow users to tap a floating bubble to capture a portion of the screen (comment area), perform OCR, and display translation.  
  * iOS: Allow users to take a screenshot of comments, share it to a Share Extension which performs OCR and displays translation. Also, support text selection via Live Text to be shared with the Action Extension.  
* **FR3: Offline Dictionary Lookup:**  
  * Provide definitions, examples, and pronunciation (via TTS) for single English words using a bundled offline dictionary.  
  * The dictionary should be a "lite" version (e.g., based on WordNet or a curated Wiktextract subset).  
* **FR4: Online Sentence Translation:**  
  * Optionally allow users to translate selected sentences or OCR-captured text using an online AI translation service (OpenAI/OpenRouter).  
  * Clearly indicate when translation is performed online.  
* **FR5: Text-to-Speech (TTS) Pronunciation:**  
  * Provide audio pronunciation for translated words (US English accent) using native OS TTS capabilities.  
* **FR6: User Onboarding:**  
  * Provide interactive guides (e.g., GIFs/videos) to explain app usage, especially for Android permissions and iOS extension workflows.  
* **FR7: Minimal Settings:**  
  * Allow users to toggle online sentence translation.  
  * Allow users to clear translation cache.  
* **FR8: Privacy & Error Handling:**  
  * Implement clear error handling for network issues, API limits, OCR failures, and permission denials.  
  * Provide a visible and clear privacy policy.

### **Non-Functional Requirements (NFRs)**

* **Performance:**  
  * Offline word lookup: \< 2 seconds.  
  * Online sentence translation (API call): Aim for ≤ 2 seconds (excluding network variability, make user aware of potential delay).  
  * OCR processing: Aim for \< 3 seconds.  
  * App launch time: Quick and responsive.  
* **Scalability:**  
  * The app should function efficiently for a single user; backend scalability is not an MVP concern due to its absence.  
* **Reliability/Availability:**  
  * Offline features should be 100% available without an internet connection.  
  * Graceful degradation of online features during network outages.  
  * Stable operation on supported OS versions, minimizing crashes.  
* **Security:**  
  * API Keys: Currently embedded in the app (recognised as a risk for the architect to address). No user authentication or personal data storage beyond cached translations.  
  * Plaintext is sent to online translation APIs; no images are stored by the app post-OCR.  
  * Adherence to platform guidelines for permissions and data handling.  
* **Maintainability:**  
  * Code developed in Kotlin Multiplatform for shared logic, with native UIs (Jetpack Compose for Android, SwiftUI for iOS).  
  * Clear separation between UI, business logic, and services.  
* **Usability/Accessibility:**  
  * Intuitive UI for initiating translations.  
  * Clear visual feedback during translation processes.  
  * Onboarding to guide users through platform-specific interaction models and permissions.  
  * Basic accessibility considerations (e.g., readable font sizes, sufficient contrast where possible within Reddit's UI constraints for OCR).  
* **Other Constraints:**  
  * Utilize the existing SubLingo GitHub project.  
  * No backend infrastructure for MVP.  
  * Offline dictionary size to be managed to keep app size reasonable.

### **User Experience (UX) Requirements (High-Level)**

* UX Goal 1: Provide an "instant" and seamless translation experience with minimal steps, keeping users within the Reddit app context.  
* UX Goal 2: Ensure platform-specific interactions feel natural and intuitive (Android floating bubble, iOS Share/Action Extensions).  
* UX Goal 3: Clearly differentiate between offline and online translation features and their implications.  
* (No separate UI/UX spec document is requested to be created initially, but this can be developed if wireframes/mockups become available. For now, UX is guided by the platform-native approaches described).

### **Integration Requirements (High-Level)**

* Integration Point 1: Reddit App (for text selection, comment area interaction \- no direct API integration, but interaction with its UI).  
* Integration Point 2: OpenAI / OpenRouter APIs (for online sentence translation).  
* Integration Point 3: Native OS services (Clipboard, Share, TTS, OCR via ML Kit/Vision Framework).

### **Testing Requirements (High-Level)**

* Primary focus on completing core app features first.  
* Testing will initially be manual, focusing on core translation workflows, OCR accuracy on diverse Reddit content, and offline dictionary functionality.  
* Unit tests for KMP shared logic are desirable.  
* (See docs/testing-strategy.md for details \- to be created by QA/Dev)

## **Epic Overview (MVP / Current Version)**

* **Epic 1: Core App Setup & Platform Integration Framework** \- Goal: Establish the KMP project structure, basic UI shells for Android and iOS, and initial plumbing for platform-specific features like the Android bubble service and iOS extension points.  
* **Epic 2: Offline Word Translation Feature** \- Goal: Implement the offline dictionary functionality, including dictionary parsing, storage, lookup, and TTS pronunciation for single words.  
* **Epic 3: OCR-Based Comment Translation Feature** \- Goal: Develop the OCR capabilities for translating Reddit comments using screenshots for both Android (ML Kit) and iOS (Vision Framework), including UI interaction.  
* **Epic 4: Online Sentence Translation Feature** \- Goal: Integrate with OpenAI/OpenRouter for translating sentences, including managing API calls and displaying results.  
* **Epic 5: Onboarding & Settings** \- Goal: Create user onboarding flows for permissions and feature discovery, and implement a minimal settings screen.  
* **Epic 6: Polish, Error Handling, and Privacy** \- Goal: Implement comprehensive error handling, ensure privacy policy adherence, and refine the overall UX based on initial testing.

## **Key Reference Documents**

* docs/project-brief.md 
* docs/project-analysis.md
* docs/prd.md (This document)
* docs/architecture.md (to be created by Architect)  
* docs/epic1.md, docs/epic2.md, ... (to be created by PM)  
* docs/tech-stack.md (to be created by Architect)  
* docs/api-reference.md (for OpenAI/OpenRouter, to be documented by Dev)  
* docs/testing-strategy.md (to be created by QA/Dev)  
* docs/ui-ux-spec.md (Optional, if detailed UI designs are created later)

## **Post-MVP / Future Enhancements**

* Expanded language support (more target languages for translation).  
* User-configurable dictionary sources.  
* Enhanced learning features (e.g., saving translations, flashcards).  
* Improved OCR accuracy with advanced image processing or user-selectable regions.  
* Better handling of Reddit-specific formatting (spoiler tags, markdown).  
* Backend solution for secure API key management and potentially user accounts/settings sync.  
* Accessibility Service for direct text reading on Android (if policy risks can be mitigated).

## **Change Log**

| Change | Date | Version | Description | Author |
| :---- | :---- | :---- | :---- | :---- |
| Initial Draft | 2025-05-15 | 0.1 | First draft based on brief, analysis, and user updates. | PM Agent |

## **Initial Architect Prompt**

The SubLingo (Instant Translate for Reddit MVP) aims to provide quick, in-app translations. The following technical guidance should inform architectural decisions:

### **Technical Infrastructure**

* **Starter Project/Template:** The project has been initialized and is available at: https://github.com/moclam1905/SubLingo. This existing KMP structure should be used as the foundation.  
* **Hosting/Cloud Provider:** No backend services requiring hosting are planned for the MVP. All processing is on-device or via third-party APIs (OpenAI/OpenRouter).  
* **Frontend Platform:**  
  * Android: Jetpack Compose is the required UI framework.  
  * iOS: SwiftUI is the required UI framework.  
  * Shared Logic: Kotlin Multiplatform (KMP) will be used for business logic, translation processing, dictionary management, and API calls.  
* **Backend Platform:** No custom backend will be developed for the MVP.  
* **Database Requirements:**  
  * Offline Dictionary: An embedded solution for storing and querying a "lite" English dictionary (e.g., WordNet StarDict or a pre-processed Wiktextract dataset converted to SQLite). This data will be bundled with the app. The analysis document suggests Wiktextract JSONL processed into SQLite as a favorable approach.  
  * Translation Cache: A small, on-device cache for recent translations (e.g., last 20\) to avoid redundant lookups/API calls.

### **Technical Constraints**

* **API Key Management:** Currently, API keys for OpenAI/OpenRouter are embedded directly in the app code due to the absence of a backend. This is a significant security risk. The Architect should:  
  * Assess the feasibility and effort of implementing a minimal, secure server-side proxy or function for API key management even for the MVP.  
  * If a backend solution is deemed out of scope for MVP, clearly document the risks and advise on mitigation strategies (e.g., strict API key restrictions, monitoring usage). This is a critical point.  
* **Offline First (for words):** Core single-word lookup functionality must operate entirely offline.  
* **Platform-Specific Implementations:**  
  * Android: Requires a floating bubble interface (overlay permission) and potentially UsageStatsManager for foreground app detection (high-friction permission). Clipboard and Share intent for text input. On-device OCR via ML Kit.  
  * iOS: Requires Action and Share Extensions. On-device OCR via Vision Framework. Interaction with Live Text output.  
* **API Usage:** Only APIs from OpenRouter or OpenAI are to be used for online translation.

### **Deployment Considerations**

* Deployment will be through standard app store channels (Google Play Store, Apple App Store).  
* CI/CD pipeline setup is desirable but may be deferred post-MVP if focus is solely on core features first.

### **Local Development & Testing Requirements**

* **Testing Priority:** The user's current focus is on completing core app features first, implying that comprehensive automated testing infrastructure might be secondary for the initial MVP deliverables. However, the architecture should support testability.  
* **Local Development & Testing Utilities:** The user is unsure which tools are most suitable. The Architect should recommend and design for:  
  * The ability for developers to run and debug the KMP, Android, and iOS modules effectively in their local environments.  
  * Consideration for unit testing shared KMP logic.  
  * Ease of testing platform-specific UI and integration points (bubble, extensions).  
  * Tools/mocks for simulating Reddit app interactions or API responses might be beneficial if feasible.

### **Other Technical Considerations**

* **OCR Accuracy:** This is a critical challenge. The architecture should allow for image preprocessing steps before sending images to ML Kit/Vision Framework to improve accuracy on varied Reddit content.  
* **Performance:** Adherence to defined performance NFRs is key. Optimize OCR, dictionary lookups, and API interactions.  
* **Error Handling:** Robust error handling for API calls, OCR failures, permission issues, etc., is critical for UX.  
* **Privacy:** Ensure compliance with the stated privacy policy (no image storage, plaintext to APIs, cache details).  
* **App Size:** The offline dictionary and bundled assets should be optimized to keep the app size reasonable.

The Architect should design a solution that leverages KMP effectively for shared logic while enabling high-quality, platform-native user experiences on Android (Jetpack Compose) and iOS (SwiftUI). The solution must address the API key security concern with a clear recommendation.

