
# SubLingo API Reference

This document provides details on external APIs consumed by the SubLingo application for its MVP. As SubLingo does not expose its own external APIs in the MVP, this document focuses on third-party services.

## 1\. External APIs Consumed

### 1.1. OpenAI API (via Direct Integration or OpenRouter)

* **Purpose:** Used for online sentence translation (FR4). The application will primarily target a model equivalent to GPT-3.5 Turbo for a balance of quality, speed, and cost.

* **Provider:** OpenAI directly, or potentially via OpenRouter if it offers better cost management or model access flexibility while still using an OpenAI-compatible API structure. The Ktor client will be configured for the appropriate endpoint.

* **Base URL(s):**

    * OpenAI (Chat Completions): `https://api.openai.com/v1/chat/completions`
    * OpenRouter (if used, example): `https://openrouter.ai/api/v1/chat/completions` (The actual OpenRouter URL might differ; this is illustrative).

* **Authentication:**

    * Method: API Key sent in the `Authorization` HTTP header as a Bearer token.
    * Header Name: `Authorization`
    * Header Value Format: `Bearer YOUR_OPENAI_API_KEY`
    * **API Key Source (MVP):** The API key is embedded in the application (HIGH RISK). It will be retrieved from a secure build-time configuration (e.g., `local.properties` on Android, `xcconfig` on iOS) as detailed in `docs/environment-vars.md` and `docs/coding-standards.md`.
        * Environment Variable Placeholder: `SUB LIGO_OPENAI_API_KEY` (actual name to be defined in `environment-vars.md`).

* **Key Endpoint Used:** Chat Completions

    * **`POST /v1/chat/completions`** (or equivalent OpenRouter path)
        * **Description:** Submits a conversation (a series of messages) to the model, and the model returns a response. For translation, this typically involves a system message setting the translation context and a user message with the text to translate.
        * **Request Body Schema:** (Content-Type: `application/json`)
            * See `OpenAICompletionRequest` and `ChatMessage` in `docs/data-models.md`.
        * **Example Request (Translate English to Vietnamese):**
          ```json
          {
            "model": "gpt-3.5-turbo",
            "messages": [
              {
                "role": "system",
                "content": "You are a helpful assistant that translates English text to Vietnamese. Provide only the translation."
              },
              {
                "role": "user",
                "content": "Hello, how are you today?"
              }
            ],
            "max_tokens": 100,
            "temperature": 0.3
          }
          ```
        * **Success Response Schema (Code: `200 OK`):**
            * See `OpenAICompletionResponse`, `Choice`, and `ChatMessageResponse` in `docs/data-models.md`.
        * **Example Success Response:**
          ```json
          {
            "id": "chatcmpl-xxxxxxxxxxxxxxxxxxxxxxxxx",
            "object": "chat.completion",
            "created": 1677652288,
            "model": "gpt-3.5-turbo-0613",
            "choices": [
              {
                "index": 0,
                "message": {
                  "role": "assistant",
                  "content": "Chào bạn, hôm nay bạn khoẻ không?"
                },
                "finish_reason": "stop"
              }
            ],
            "usage": {
              "prompt_tokens": 30,
              "completion_tokens": 10,
              "total_tokens": 40
            }
          }
          ```
        * **Common Error Responses:**
            * `401 Unauthorized`: Invalid API Key or authentication failure.
              ```json
              {
                "error": {
                  "message": "Incorrect API key provided: sk-xxxx. You can find your API key at https://platform.openai.com/account/api-keys.",
                  "type": "invalid_request_error",
                  "param": null,
                  "code": "invalid_api_key"
                }
              }
              ```
            * `429 Too Many Requests`: Rate limit exceeded or quota issues.
              ```json
              {
                "error": {
                  "message": "You exceeded your current quota, please check your plan and billing details.",
                  "type": "insufficient_quota",
                  "param": null,
                  "code": "insufficient_quota"
                }
              }
              ```
            * `500 Internal Server Error`: Issues on OpenAI's side.

* **Rate Limits:**

    * Refer to OpenAI's (or OpenRouter's) official documentation for specific rate limits (RPM - requests per minute, TPM - tokens per minute) based on account tier. These can vary.
    * The application should handle `429` errors gracefully (e.g., inform the user, potentially implement a limited client-side retry with backoff for specific scenarios if sensible, though generally, quota issues require user/account action).

* **Link to Official Docs:**

    * OpenAI API Reference: [https://platform.openai.com/docs/api-reference/chat](https://platform.openai.com/docs/api-reference/chat)
    * OpenRouter API (if used): Refer to OpenRouter's specific documentation.

## 2\. Internal APIs Provided

* N/A for MVP. SubLingo is a client-only application for the MVP and does not expose any of its own APIs.

## 3\. Cloud Provider Service SDK Usage (Non-API based)

This section details direct interactions with cloud provider services via SDKs that are not traditional REST/HTTP APIs. For SubLingo MVP, this isn't directly applicable as there's no custom backend. The Ktor client handles the HTTP communication with OpenAI.

* N/A for MVP.

## Change Log

| Change        | Date       | Version | Description                                                 | Author          |
| :------------ | :--------- | :------ | :---------------------------------------------------------- | :-------------- |
| Initial draft | 2025-05-15 | 0.1     | Drafted based on OpenAI Chat Completions API for translation. | Architect Agent |
