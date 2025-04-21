package com.example.demo.Service;

import okhttp3.*;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.io.IOException;

@Service
public class ChatbotService {
    private final OkHttpClient client = new OkHttpClient();

    @Value("${openai.api.key}") // Store API key in `application.properties`
    private String openAiApiKey;

    private static final String API_URL = "https://api.openai.com/v1/chat/completions";

    public String chatWithAI(String userMessage) throws IOException {
        JSONObject requestBody = new JSONObject();
        requestBody.put("model", "gpt-4-turbo"); // Choose GPT model
        requestBody.put("messages", new org.json.JSONArray()
                .put(new JSONObject().put("role", "system").put("content", "You are a helpful assistant."))
                .put(new JSONObject().put("role", "user").put("content", userMessage)));

        Request request = new Request.Builder()
                .url(API_URL)
                .post(RequestBody.create(requestBody.toString(), MediaType.get("application/json")))
                .addHeader("Authorization", "Bearer " + openAiApiKey)
                .addHeader("Content-Type", "application/json")
                .build();

        Response response = client.newCall(request).execute();
        return new JSONObject(response.body().string()).getJSONArray("choices").getJSONObject(0).getJSONObject("message").getString("content");
    }
}
