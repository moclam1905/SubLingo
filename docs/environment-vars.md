
# SubLingo Environment Variables

This document outlines the environment variables and build-time configurations used by the SubLingo application. For the MVP, SubLingo is a client-only application, so traditional server-side environment variables are not applicable. However, build-time configurations, especially for sensitive information like API keys, are crucial.

## Configuration Loading Mechanism

* **Android (`composeApp`):**
    * Sensitive keys like the OpenAI API key will be stored in a `local.properties` file at the root of the `sublingo` project. This file **must be gitignored**.
    * The key will be exposed to the application code via `BuildConfig` fields, configured in the `composeApp/build.gradle.kts` file.
    * Example `local.properties` entry:
      ```properties
      SUBLINGO_OPENAI_API_KEY="sk-yourActualOpenAiApiKey"
      ```
    * Example `composeApp/build.gradle.kts` configuration:
      ```kotlin
      // In android { ... defaultConfig { ... } } block
      // Read API key from local.properties
      val localProperties = java.util.Properties()
      val localPropertiesFile = rootProject.file("local.properties")
      if (localPropertiesFile.exists()) {
          localProperties.load(java.io.FileInputStream(localPropertiesFile))
      }
      buildConfigField("String", "OPENAI_API_KEY", "\"${localProperties.getProperty("SUBLINGO_OPENAI_API_KEY") ?: "YOUR_DEFAULT_KEY_OR_EMPTY"}\"")
      ```
* **iOS (`iosApp`):**
    * Sensitive keys will be managed using Xcode Configuration files (`.xcconfig`). A gitignored `.xcconfig` file (e.g., `Keys.xcconfig` or `Secrets.xcconfig`) will store the actual key.
    * This key will be exposed to the Swift code via the app's `Info.plist` file.
    * Example `Keys.xcconfig` (gitignored):
      ```
      SUBLINGO_OPENAI_API_KEY = sk-yourActualOpenAiApiKey
      ```
    * The main `Config.xcconfig` (or a new one included in `.gitignore`) would then `#include? "Keys.xcconfig"`.
    * In `Info.plist`, add an entry (e.g., `OpenAIAPIKey`) and set its value to `$(SUBLINGO_OPENAI_API_KEY)`.
    * Access in Swift code:
      ```swift
      func getOpenAIApiKey() -> String? {
          return Bundle.main.object(forInfoDictionaryKey: "OpenAIAPIKey") as? String
      }
      ```
* **KMP `shared` module:**
    * The API key will be passed from the native Android/iOS modules to the relevant service in the shared module (e.g., `OnlineTranslationService`) during its initialization. This can be done via an `expect`/`actual` mechanism or by passing it as a parameter when DI graph is built.

## Required Variables / Build Configurations

| Variable Name             | Description                                          | Example / Default Value (in code if placeholder) | Required for Online Translation? | Sensitive? (High Risk if Exposed) | Notes                                                                                                |
| :------------------------ | :--------------------------------------------------- | :----------------------------------------------- | :------------------------------- | :-------------------------------- | :--------------------------------------------------------------------------------------------------- |
| `SUBLINGO_OPENAI_API_KEY` | API Key for OpenAI or OpenRouter translation service | `"YOUR_API_KEY_HERE"` (placeholder in templates)   | Yes                              | Yes (HIGH RISK)                   | Actual key stored in gitignored `local.properties` (Android) or gitignored `.xcconfig` file (iOS). |
| `TARGET_LANGUAGE_CODE`    | Default target language for translations             | `"vi"` (for Vietnamese)                          | No (can be hardcoded for MVP)    | No                                | Could be made configurable in settings post-MVP.                                                     |

*(No other true environment variables are anticipated for the client-only MVP.)*

## Notes

* **Security of API Key (MVP Constraint):**
    * The `SUBLINGO_OPENAI_API_KEY` is embedded in the app via build-time configuration. This is a **SIGNIFICANT SECURITY RISK**.
    * **It is critical that `local.properties` (Android) and the `.xcconfig` file containing the actual key (iOS) are included in the project's `.gitignore` file to prevent committing the key to version control.**
    * An example file (e.g., `local.properties.example` or `Keys.xcconfig.example`) should be committed with a placeholder value, instructing developers on how to set up their local key.
    * **Risk Mitigation:**
        * On the OpenAI/OpenRouter platform, restrict the API key as much as possible (e.g., to specific models, enabled capabilities, or by platform if such restrictions are available for mobile API keys).
        * Closely monitor API usage for any signs of abuse.
        * **Recommendation:** For any post-MVP version, this key **must** be moved to a secure backend proxy.
* **Placeholder Values:** If an API key is not provided during the build, the app's online translation feature should either be disabled or should clearly indicate that the feature is not configured, rather than crashing. The default value in `BuildConfig` / `Info.plist` should reflect this.
* **Validation:** The KMP `OnlineTranslationService` should check if a valid API key has been provided before attempting to make any API calls.

## Change Log

| Change        | Date       | Version | Description                                                                                   | Author          |
| :------------ | :--------- | :------ | :-------------------------------------------------------------------------------------------- | :-------------- |
| Initial draft | 2025-05-15 | 0.1     | Initial draft focusing on API key management for MVP and build-time configuration mechanisms. | Architect Agent |
