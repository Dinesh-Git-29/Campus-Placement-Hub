package com.example.demo.Controller;

import org.springframework.http.*;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;
import java.util.*;

@RestController
@RequestMapping("/chatbot")
public class ChatbotController {

    private static final String OPENAI_API_KEY = "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";  // 🔹 Replace with your API key
    private static final String API_URL = "https://api.openai.com/v1/chat/completions";

    @PostMapping("/ask")
    public ResponseEntity<String> getChatResponse(@RequestBody Map<String, String> request) {
        String userInput = request.get("message");

        String aiResponse = callOpenAI(userInput);
        return ResponseEntity.ok(aiResponse);
    }

    private String callOpenAI(String message) {
        RestTemplate restTemplate = new RestTemplate();
        HttpHeaders headers = new HttpHeaders();
        headers.set("Authorization", "Bearer " + OPENAI_API_KEY);
        headers.setContentType(MediaType.APPLICATION_JSON);

        Map<String, Object> requestBody = new HashMap<>();
        requestBody.put("model", "gpt-3.5-turbo");
        requestBody.put("messages", List.of(Map.of("role", "user", "content", message)));

        HttpEntity<Map<String, Object>> entity = new HttpEntity<>(requestBody, headers);
        ResponseEntity<Map> response = restTemplate.exchange(API_URL, HttpMethod.POST, entity, Map.class);

        List<Map<String, String>> choices = (List<Map<String, String>>) response.getBody().get("choices");
        return choices.get(0).get("message");
    }
}
