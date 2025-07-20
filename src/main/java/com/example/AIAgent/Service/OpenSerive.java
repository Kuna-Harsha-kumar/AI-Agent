package com.example.AIAgent.Service;

import java.io.IOException;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Service;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

@Service
public class OpenSerive {
    



    // 🔑 Your OpenRouter API key (get from https://openrouter.ai/keys)
    private static final String API_KEY = "Bearer ";
    // 🌐 OpenRouter endpoint
    private static final String ENDPOINT = "https://openrouter.ai/api/v1/chat/completions";

    private final OkHttpClient client = new OkHttpClient();
    private final ObjectMapper mapper = new ObjectMapper();

    public String askOpenAI(String prompt) throws IOException {
        // 🧠 Create request body in OpenAI-compatible format
        Map<String, Object> requestBody = Map.of(
                "model", "mistralai/mistral-7b-instruct",  // You can change to other models on OpenRouter
                "messages", List.of(
                        Map.of("role", "user", "content", prompt)
                )
        );

        // 📝 Convert request body to JSON
        RequestBody body = RequestBody.create(
                mapper.writeValueAsString(requestBody),
                MediaType.parse("application/json")
        );

        // 📡 Build HTTP POST request with required headers
        Request request = new Request.Builder()
                .url(ENDPOINT)
                .header("Authorization", API_KEY)
                .header("Content-Type", "application/json")
                .header("HTTP-Referer", "http://localhost") // REQUIRED: change to your domain if deployed
                .post(body)
                .build();

        // 📥 Execute request and parse response
        try (Response response = client.newCall(request).execute()) {
            if (!response.isSuccessful()) {
                throw new IOException("Request failed: " + response.code() + " - " + response.message());
            }

            // ✅ Extract response text
            JsonNode json = mapper.readTree(response.body().string());
            return json.at("/choices/0/message/content").asText();
        }
    }

}
