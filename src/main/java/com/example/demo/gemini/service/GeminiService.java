package com.example.demo.gemini.service;

import com.google.genai.Client;
import com.google.genai.types.GenerateContentResponse;
import org.springframework.stereotype.Service;

@Service
public class GeminiService {

    private final Client client;

    public GeminiService() {
        // 환경변수 GOOGLE_API_KEY에서 자동으로 API 키를 가져옴
        this.client = new Client();
    }

    public String generateContent(String prompt) {
        try {
            GenerateContentResponse response = client.models.generateContent(
                    "gemini-2.0-flash",
                    prompt,
                    null);

            return response.text();

        } catch (Exception e) {
            throw new RuntimeException("Gemini API 호출 실패: " + e.getMessage(), e);
        }
    }

    public boolean hasApiKey() {
        String apiKey = System.getenv("GOOGLE_API_KEY");
        return apiKey != null && !apiKey.trim().isEmpty();
    }
}
