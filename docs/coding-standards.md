
# SubLingo Coding Standards and Patterns

This document outlines the coding standards, architectural patterns, and best practices to be followed during the development of the SubLingo application. Adherence to these standards will ensure consistency, maintainability, and quality across the codebase.

## 1\. Architectural / Design Patterns Adopted

The following high-level architectural patterns, as detailed in `docs/architecture.md`, are adopted:

* **Kotlin Multiplatform (KMP) for Shared Core:** Centralizing business logic, data handling, and API interactions in the `shared` module.
    * *Rationale/Reference:* Promotes code reuse, consistency across platforms. (See `docs/architecture.md`)
* **Native UI per Platform:** Jetpack Compose for Android, SwiftUI for iOS.
    * *Rationale/Reference:* Optimal platform look, feel, and performance. (See `docs/architecture.md`)
* **Clean Architecture Principles (within `shared` module):**
    * **Layers:** `data` (sources, repositories), `domain` (entities, use cases, repository interfaces), `presentation` (optional shared ViewModels).
    * **Dependency Rule:** Inner layers (domain) should not depend on outer layers (data, presentation, platform specifics). Dependencies flow inwards.
    * *Rationale/Reference:* Promotes separation of concerns, testability, and maintainability of the shared logic.
* **Repository Pattern:** Abstracting data sources (offline dictionary, online API) behind repository interfaces defined in the `domain` layer.
    * *Rationale/Reference:* Decouples business logic from data implementation details.
* **Dependency Injection (DI) with Koin:**
    * **Recommendation:** Utilize **Koin** (latest stable version, e.g., 3.5.x) as the primary DI framework for managing dependencies within the `shared` module and for providing these dependencies to the Android (`composeApp`) and iOS (`iosApp`) modules.
    * **Configuration:** Koin modules will be defined in `shared/src/commonMain/kotlin/com/nguyenmoclam/sublingo/shared/di/`. Initialization will occur in the `SubLingoApplication.kt` (Android) and `SubLingoApp.swift` (or `AppDelegate.swift`) for iOS.
    * *Rationale/Reference:* Koin is a lightweight, pragmatic, KMP-compatible DI framework that uses a Kotlin DSL, simplifying setup and improving modularity and testability.
* **`expect`/`actual` Mechanism:** For KMP platform-specific implementations.
    * *Rationale/Reference:* Standard KMP pattern for accessing native capabilities. (Story 1.7)
* **Asynchronous Programming with Coroutines:** For all I/O-bound and long-running operations.
    * *Rationale/Reference:* Ensures non-blocking execution and responsive UIs.

## 2\. Coding Standards

### 2.1. Primary Languages & Runtimes:

* **Kotlin:** Latest stable version (e.g., 1.9.23+ for KMP `shared` module and Android).
    * Adhere to the official [Kotlin Coding Conventions](https://kotlinlang.org/docs/coding-conventions.html).
* **Swift:** Latest stable version (e.g., 5.9+ for iOS app).
    * Adhere to the official [Swift API Design Guidelines](https://www.swift.org/documentation/api-design-guidelines/).
* **Java (Minimal):** If absolutely necessary for specific Android interop, use latest LTS (e.g., Java 17). Prefer Kotlin.

### 2.2. Style Guide & Linters:

* **Kotlin:**
    * **Linter:** Ktlint.
    * **Formatter:** Use IntelliJ IDEA / Android Studio default formatter configured for Kotlin conventions, or Ktlint formatting.
    * *Configuration:* Project-level `.editorconfig` should be used to enforce basic styles. Ktlint can be integrated into the Gradle build via `build.gradle.kts` in the relevant modules.
* **Swift:**
    * **Linter:** SwiftLint.
    * **Formatter:** Xcode default formatter or SwiftFormat.
    * *Configuration:* SwiftLint configuration file (`.swiftlint.yml`) in the `iosApp/iosApp/` directory or `iosApp/` root.
* **General:** Use Android Studio / IntelliJ IDEA and Xcode's default formatters and import optimizers. Enable "Optimize Imports on the fly" in IDE settings.

### 2.3. Naming Conventions:

* **Packages (Kotlin):** `com.nguyenmoclam.sublingo.shared.feature` or `com.nguyenmoclam.sublingo.android.feature` (lowercase, dot-separated).
* **Files:**
    * Kotlin: `PascalCase.kt` (e.g., `OfflineDictionaryService.kt`, `SettingsViewModel.kt`).
    * Swift: `PascalCase.swift` (e.g., `VisionOcrProvider.swift`, `SettingsView.swift`).
    * Jetpack Compose Composables: `PascalCase` (e.g., `BubbleContent`, `OnboardingScreen`). Functions that emit UI.
    * SQLDelight files: `lowercase_underscore.sq` (e.g., `dictionary.sq`).
* **Classes & Interfaces (Kotlin, Swift), Structs (Swift), Enums, Annotations:** `PascalCase`.
* **Functions & Methods (Kotlin, Swift):** `camelCase`.
* **Variables (Kotlin, Swift):** `camelCase`.
    * Local variables, parameters.
* **Constants (Kotlin):** `UPPER_SNAKE_CASE` (for `const val` or `@JvmField val` in objects).
* **Constants (Swift):** `camelCase` for static properties that are value-like (e.g., `static let defaultTimeout = 5.0`). `PascalCase` for enum cases.
* **Boolean Variables/Functions:** Prefer affirmative names prefixed with `is`, `has`, `can` (e.g., `isOnlineTranslationEnabled`, `hasValidCache`).

### 2.4. File Structure:

* Adhere to the layout defined in `docs/project-structure.md`.
* Group files by feature or layer within the defined module structure. Each class, interface, or enum should ideally be in its own file, unless they are very small and closely related (e.g., sealed interface variants).

### 2.5. Asynchronous Operations:

* **Kotlin (KMP, Android):** Use Kotlin Coroutines (`suspend` functions, `Flow`, `Job`, `CoroutineScope`).
    * Prefer structured concurrency. CoroutineScopes should be managed according to component lifecycles (e.g., `viewModelScope` in Android ViewModels).
    * Use appropriate dispatchers (e.g., `Dispatchers.IO` for disk/network I/O, `Dispatchers.Default` for CPU-intensive tasks, `Dispatchers.Main` or platform-specific main dispatchers for UI updates).
* **Swift (iOS):** Use Swift Concurrency (`async`/`await`, `Task`).
    * Manage `Task` lifecycles appropriately, canceling them when their work is no longer needed.

### 2.6. Type Safety & Nullability:

* **Kotlin:** Leverage Kotlin's null safety features strictly. Avoid `!!` operator; use safe calls (`?.`), Elvis operator (`?:`), or scope functions (`let`, `run`, `apply`, `also`) for handling nullables.
* **Swift:** Leverage Swift's optional system strictly. Use optional chaining (`?.`), nil-coalescing operator (`??`), and `guard let` or `if let` for unwrapping optionals. Avoid force unwrapping (`!`) unless the value is guaranteed to exist by logic.
* Define explicit types for variables, function parameters, and return types.
* Use data classes (Kotlin) and structs (Swift) for immutable data transfer objects (DTOs) and value objects where appropriate.

### 2.7. Comments & Documentation:

* **KDoc (Kotlin) / Swift Documentation Comments (///):** Write clear and concise documentation for all public/internal classes, functions, properties, and `expect` declarations, especially in the `shared` module's API surface and any complex logic. Explain parameters, return values, and any non-obvious behavior.
* **TODO comments:** Format as `// TODO (username or JIRA/issue ID): Description of what needs to be done and why.`
* Keep comments up-to-date with code changes. Remove commented-out code before committing/merging, unless it's for a very specific, temporary debugging reason with an explanatory comment and a follow-up task to remove it.

### 2.8. Dependency Management:

* **Gradle (KMP, Android):** Use `gradle/libs.versions.toml` (version catalog) for managing dependency versions consistently.
* **iOS (Swift Package Manager):** Prefer Swift Package Manager for native iOS dependencies. If CocoaPods are used for specific libraries, manage `Podfile` carefully.
* Minimize dependencies. Evaluate the necessity, benefits, and potential maintenance overhead before adding a new library. Review transitive dependencies.

### 2.9. Logging:

* **KMP `shared` module:**
    * **Recommendation:** Use a lightweight KMP-compatible logging library such as **Napier** or **Kermit**. These provide a simple facade over platform-specific logging mechanisms. (If neither is chosen, define an `expect` interface for logging).
    * Log key events, errors, important state changes, and method entry/exit for critical paths during debugging.
* **Android (`composeApp`):** The chosen KMP logger's Android artifact will typically use Android's standard `Log` class (`android.util.Log`). Timber can be used directly in `composeApp` if more advanced Android-specific logging features are needed.
* **iOS (`iosApp`):** The chosen KMP logger's iOS artifact will typically use `OSLog` (`os.Logger`).
* **Log Levels:** Use standard levels (VERBOSE/DEBUG, INFO, WARNING, ERROR) appropriately. Default production log level should be INFO or WARNING.
* Avoid logging sensitive information (API keys, PII, full request/response bodies if they contain sensitive data).

## 3\. Error Handling Strategy

* **General Approach (KMP `shared` module):**
    * Use Kotlin's `Result` type (`kotlin.Result`) or custom sealed classes (e.g., `sealed class DataResponse<out T> { data class Success<T>(val data: T) : DataResponse<T>() data class Error(val exception: Exception) : DataResponse<Nothing>() }`) to represent success/failure states for operations that can fail (e.g., use cases, repository methods). This makes error handling explicit and type-safe.
    * Exceptions should be used for truly exceptional/unrecoverable conditions. Define custom, meaningful exceptions inheriting from `Exception` or `RuntimeException` where appropriate (e.g., `OfflineDictionaryError`, `OnlineTranslationError`).
* **Network Calls (Ktor in `shared` module):**
    * Catch specific Ktor exceptions (e.g., `HttpRequestTimeoutException`, `ClientRequestException`, `ServerResponseException`).
    * Map HTTP status codes (4xx, 5xx) to specific error types within the `Result` or sealed class.
    * Implement retries with exponential backoff for transient server errors (e.g., 503) if appropriate for the OpenAI/OpenRouter API.
* **OCR Processing:**
    * Return a distinct state or error type if no text is found or if the OCR confidence is below an acceptable threshold.
* **Offline Dictionary:**
    * Handle cases where a word is not found gracefully (e.g., return null or an empty result, not an exception).
    * Handle potential `SQLException`s if the dictionary database has issues, mapping them to a defined error state.
* **Platform-Specific Error Handling:**
    * On Android and iOS, observe the `Result` or sealed class from shared module ViewModels or use cases.
    * Translate these shared error types into user-friendly messages, dialogs, or specific UI states (e.g., show a snackbar, display an error illustration).
    * Handle platform-specific errors (e.g., permission denials, TTS engine initialization failures) gracefully within the platform code.
* **User-Facing Errors:** Display clear, concise, and potentially actionable error messages to the user. Avoid exposing raw technical stack traces or jargon.

## 4\. Security Best Practices

* **API Key Management (MVP Constraint):**
    * The OpenAI/OpenRouter API key will be embedded in the client-side code (as per user decision for MVP). This is a **HIGH RISK**.
    * **Mitigation (Client-side):** The key should **NOT** be directly in source code committed to the repository.
        * **Android:** Store in `local.properties` (which is gitignored by default) and access via `BuildConfig.OPENAI_API_KEY`.
        * **iOS:** Store in an `xcconfig` file (gitignored) and expose to Swift code via `Info.plist`.
        * The `docs/environment-vars.md` will detail this setup.
    * **Mitigation (API Provider Side):** If OpenAI/OpenRouter allows, restrict the API key usage by application ID (Android package name, iOS Bundle ID) or by enabling only necessary API capabilities (e.g., only specific models). Monitor API usage closely for anomalies.
    * **Post-MVP:** This is a critical item to address. Prioritize moving the API key to a secure backend proxy.
* **Input Validation:** Validate all inputs:
    * Text from user selection or clipboard before processing or sending to APIs.
    * Text extracted from OCR.
* **Permissions:**
    * Request permissions just-in-time (when the feature needing it is first accessed).
    * Clearly explain *why* each permission is needed using onboarding screens or dialogs, especially for sensitive Android permissions (`SYSTEM_ALERT_WINDOW`, `PACKAGE_USAGE_STATS`).
* **Data Handling:**
    * Do not store screenshots locally after OCR processing is complete. Process them in memory.
    * Plaintext is sent to OpenAI for translation. This must be clearly stated in the Privacy Policy (`docs/privacy-policy.md`).
    * The translation cache is on-device, stores limited entries (e.g., 20 words, 20 sentences), and must be clearable by the user via settings.
* **Dependencies:** Regularly check dependencies for known vulnerabilities (e.g., using GitHub Dependabot if enabled, or manual checks against vulnerability databases).
* **Network Security (Android):** Define a Network Security Configuration file (`composeApp/src/main/res/xml/network_security_config.xml`) to enforce HTTPS for all domains, especially if targeting older Android versions where defaults might be less strict.
  ```xml
  <network-security-config>
      <base-config cleartextTrafficPermitted="false">
          <trust-anchors>
              <certificates src="system" />
          </trust-anchors>
      </base-config>
  </network-security-config>
  ```
  Ensure `android:networkSecurityConfig="@xml/network_security_config"` is set in `AndroidManifest.xml`.
* **WebViews (If Used):** If any part of the app uses WebViews (e.g., for displaying privacy policy from a URL), ensure they are configured securely (e.g., disable JavaScript if not needed for that specific content, restrict navigation).

## 5\. Immutability

* Prefer immutable data structures:
    * **Kotlin:** Use `val` over `var` wherever possible. Use immutable collections (`List`, `Set`, `Map`) from `kotlin.collections` by default; use `MutableList`, etc., only when mutability is essential and localized. Utilize `data class` for state-carrying objects.
    * **Swift:** Use `let` over `var` wherever possible. Use immutable value types (structs, enums) for models and state.
* This approach simplifies state management, improves predictability, and reduces the likelihood of bugs related to unintended side effects, especially in concurrent environments.

## 6\. Platform-Specific Guidelines

* **Android (`composeApp`):**
    * Follow Material 3 Design guidelines for UI components and theming.
    * Use Jetpack ViewModel for managing UI-related state and business logic connections, respecting Android lifecycles.
    * Handle configuration changes (e.g., screen rotation) gracefully, preserving state.
    * Optimize for performance and battery life, especially for background operations or services like the `FloatingBubbleService`.
* **iOS (`iosApp`):**
    * Follow Apple's Human Interface Guidelines (HIG).
    * Utilize SwiftUI's state management tools (`@State`, `@StateObject`, `@ObservedObject`, `@EnvironmentObject`) appropriately.
    * Ensure App Extensions (`SubLingoActionExtension`, `SubLingoShareExtension`) are lightweight, launch quickly, and manage their limited resources efficiently.
    * Handle app lifecycle events and view lifecycles correctly.

## Change Log

| Change        | Date       | Version | Description                                                                         | Author          |
| :------------ | :--------- | :------ | :---------------------------------------------------------------------------------- | :-------------- |
| Initial draft | 2025-05-15 | 0.1     | Initial draft based on KMP and platform best practices.                               | Architect Agent |
