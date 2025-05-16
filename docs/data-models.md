
# SubLingo Data Models

This document outlines the core data structures used within the SubLingo application. These models are primarily defined in the `shared/src/commonMain/kotlin/com/nguyenmoclam/sublingo/shared/` directory, under `data/` or `domain/model/`.

## 1\. Domain Entities (KMP Shared - `domain/model/`)

These represent the core concepts in the application's business logic.

### `WordDefinition.kt`

* **Description:** Represents the result of an offline dictionary lookup for a single word.
* **Schema / Interface Definition (Kotlin Data Class):**
  ```kotlin
  package com.nguyenmoclam.sublingo.shared.domain.model

  data class WordDefinition(
      val word: String,                   // The word that was looked up
      val phoneticSymbol: String?,        // Phonetic symbol, if available (e.g., /həˈloʊ/)
      val definitions: List<DefinitionEntry> // List of definitions for the word
  )

  data class DefinitionEntry(
      val partOfSpeech: String?,          // e.g., "noun", "verb", "adjective"
      val meaning: String,                // The actual definition text
      val examples: List<String>          // List of example sentences using the word/meaning
  )
  ```
* **Validation Rules:** `word` and `meaning` should not be empty.

### `TranslationResult.kt`

* **Description:** Represents the result of a translation, either from OCR text or direct input, potentially using the online service.
* **Schema / Interface Definition (Kotlin Data Class):**
  ```kotlin
  package com.nguyenmoclam.sublingo.shared.domain.model

  data class TranslationResult(
      val originalText: String,           // The original text that was translated
      val translatedText: String,         // The translated text
      val sourceLanguage: String,         // Language code of the original text (e.g., "en")
      val targetLanguage: String,         // Language code of the translated text (e.g., "vi")
      val isOnlineTranslation: Boolean    // Flag indicating if online service was used
  )
  ```
* **Validation Rules:** `originalText` and `translatedText` should not be empty. `sourceLanguage` and `targetLanguage` should be valid ISO 639-1 codes.

### `AppSetting.kt` (Conceptual, managed by `AppSettings.kt`)

* **Description:** Represents user-configurable settings. Not a direct data class passed around but conceptualized here.
    * `isOnlineTranslationEnabled: Boolean` (Default: true)
    * `targetTranslationLanguage: String` (Default: "vi" - for Vietnamese. Future enhancement could make this configurable)

## 2\. Data Transfer Objects (DTOs) (KMP Shared - `data/remote/model/`)

These are used for communication with external APIs, specifically OpenAI/OpenRouter.

### `OpenAICompletionRequest.kt` (Example for GPT-3.5 Turbo Chat Completion)

* **Description:** Request payload for OpenAI's Chat Completions API (simplified for translation).
* **Schema / Interface Definition (Kotlin Data Class with `@Serializable`):**
  ```kotlin
  package com.nguyenmoclam.sublingo.shared.data.remote.model

  import kotlinx.serialization.SerialName
  import kotlinx.serialization.Serializable

  @Serializable
  data class OpenAICompletionRequest(
      val model: String = "gpt-3.5-turbo", // Or the specific model from OpenRouter
      val messages: List<ChatMessage>,
      @SerialName("max_tokens")
      val maxTokens: Int = 150, // Adjust as needed for translation length
      val temperature: Double = 0.7 // Adjust for creativity vs. determinism
  )

  @Serializable
  data class ChatMessage(
      val role: String, // "system", "user", "assistant"
      val content: String
  )
  ```
    * **System Message Example:** "You are a helpful assistant that translates English text to Vietnamese."
    * **User Message Example:** "Translate the following English text to Vietnamese: '{text\_to\_translate}'"

### `OpenAICompletionResponse.kt`

* **Description:** Response payload from OpenAI's Chat Completions API.
* **Schema / Interface Definition (Kotlin Data Class with `@Serializable`):**
  ```kotlin
  package com.nguyenmoclam.sublingo.shared.data.remote.model

  import kotlinx.serialization.SerialName
  import kotlinx.serialization.Serializable

  @Serializable
  data class OpenAICompletionResponse(
      val id: String,
      val choices: List<Choice>,
      val usage: UsageData? = null // Optional usage statistics
  )

  @Serializable
  data class Choice(
      val index: Int,
      val message: ChatMessageResponse,
      @SerialName("finish_reason")
      val finishReason: String
  )

  @Serializable
  data class ChatMessageResponse(
      val role: String, // "assistant"
      val content: String // This will contain the translated text
  )

  @Serializable
  data class UsageData(
      @SerialName("prompt_tokens")
      val promptTokens: Int,
      @SerialName("completion_tokens")
      val completionTokens: Int,
      @SerialName("total_tokens")
      val totalTokens: Int
  )
  ```

## 3\. Database Schema (Offline Dictionary - SQLDelight)

Defined in `shared/src/commonMain/kotlin/com/nguyenmoclam/sublingo/shared/data/local/dictionary.sq`. This schema is based on processing Wiktextract JSONL into SQLite as recommended in the Project Analysis and Story 2.1.

### `WordEntity` Table

* **Purpose:** Stores individual words and their primary identifiers.
* **Schema Definition (SQLDelight in `dictionary.sq`):**
  ```sql
  CREATE TABLE WordEntity (
      id INTEGER NOT NULL PRIMARY KEY AUTOINCREMENT,
      wordText TEXT NOT NULL UNIQUE,    -- The actual word, e.g., "hello"
      phoneticSymbol TEXT               -- Phonetic symbol, e.g., "/həˈloʊ/"
  );

  CREATE INDEX idx_word_text ON WordEntity(wordText);
  ```

### `DefinitionEntity` Table

* **Purpose:** Stores definitions associated with a word, including part of speech.
* **Schema Definition (SQLDelight in `dictionary.sq`):**
  ```sql
  CREATE TABLE DefinitionEntity (
      id INTEGER NOT NULL PRIMARY KEY AUTOINCREMENT,
      wordId INTEGER NOT NULL,          -- Foreign key to WordEntity.id
      partOfSpeech TEXT,                -- e.g., "noun", "verb", "adjective"
      meaning TEXT NOT NULL,            -- The definition text
      FOREIGN KEY (wordId) REFERENCES WordEntity(id) ON DELETE CASCADE
  );

  CREATE INDEX idx_definition_word_id ON DefinitionEntity(wordId);
  ```

### `ExampleEntity` Table

* **Purpose:** Stores example sentences for each definition.
* **Schema Definition (SQLDelight in `dictionary.sq`):**
  ```sql
  CREATE TABLE ExampleEntity (
      id INTEGER NOT NULL PRIMARY KEY AUTOINCREMENT,
      definitionId INTEGER NOT NULL,    -- Foreign key to DefinitionEntity.id
      exampleText TEXT NOT NULL,        -- The example sentence
      FOREIGN KEY (definitionId) REFERENCES DefinitionEntity(id) ON DELETE CASCADE
  );

  CREATE INDEX idx_example_definition_id ON ExampleEntity(definitionId);
  ```

### SQLDelight Queries (Conceptual examples in `dictionary.sq`)

* **`getDefinitionForWord.sq`:**
  ```sql
  import com.nguyenmoclam.sublingo.shared.domain.model.WordDefinition;
  import com.nguyenmoclam.sublingo.shared.domain.model.DefinitionEntry;

  selectWordDefinition:
  SELECT WE.wordText, WE.phoneticSymbol, DE.partOfSpeech, DE.meaning, GROUP_CONCAT(EE.exampleText, '|||') AS examples
  FROM WordEntity WE
  JOIN DefinitionEntity DE ON WE.id = DE.wordId
  LEFT JOIN ExampleEntity EE ON DE.id = EE.definitionId
  WHERE WE.wordText = :word
  GROUP BY WE.id, DE.id;
  -- This query needs careful mapping to WordDefinition and DefinitionEntry in Kotlin using SQLDelight's features.
  -- SQLDelight will generate type-safe Kotlin code to execute this and map results.
  -- A more structured approach might involve multiple queries or a custom mapper.
  ```
  *Note: The `GROUP_CONCAT` with a custom separator `|||` is a common way to fetch multiple examples per definition in a single query, which then needs to be split in Kotlin. SQLDelight's custom mappers would be essential here to construct the nested `WordDefinition` object.*

## 4\. Cached Data Structures (KMP Shared - `cache/`)

The in-memory cache (`TranslationCache.kt`) will store instances of:

* `WordDefinition` (for offline lookups)
* `TranslationResult` (for online sentence translations)

The cache keys will be:

* The word string for `WordDefinition`.
* The original sentence string for `TranslationResult`.

The cache implementation will likely use a `LinkedHashMap` to maintain LRU (Least Recently Used) eviction policy for the specified capacity (e.g., 20 entries for words, 20 for sentences, as per Story 2.7 and 4.6).

## Change Log

| Change        | Date       | Version | Description                                                                | Author          |
| :------------ | :--------- | :------ | :------------------------------------------------------------------------- | :-------------- |
| Initial draft | 2025-05-15 | 0.1     | Initial draft defining domain entities, DTOs, and DB schema for dictionary. | Architect Agent |

