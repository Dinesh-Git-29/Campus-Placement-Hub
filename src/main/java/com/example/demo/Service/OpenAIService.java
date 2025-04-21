package com.example.demo.Service;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import org.springframework.http.*;
import java.util.Collections;
import java.util.Map;

@Service
public class OpenAIService {

    @Value("${openai.api.key}")
    private String openaiApiKey;

    private static final String OPENAI_URL = "https://api.openai.com/v1/chat/completions";

    public String getChatResponse(String userMessage) {
        RestTemplate restTemplate = new RestTemplate();
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        headers.set("Authorization", "Bearer " + openaiApiKey);

        // OpenAI API Request Body
        String requestBody = """
        {
            "model": "gpt-4",
            "messages": [
                {"role": "system", "content": "You are a helpful AI assistant."},
                {"role": "user", "content": "%s"}
            ]
        }
        """.formatted(userMessage);

        HttpEntity<String> requestEntity = new HttpEntity<>(requestBody, headers);
        ResponseEntity<Map> responseEntity = restTemplate.exchange(OPENAI_URL, HttpMethod.POST, requestEntity, Map.class);

        // Extracting response
        Map<String, Object> response = responseEntity.getBody();
        return ((Map<String, String>) ((Map<String, Object>) ((Map<String, Object>) response.get("choices")).get(0)).get("message")).get("content");
    }
}
