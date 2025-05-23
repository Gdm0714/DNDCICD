package com.example.demo.gemini.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.example.demo.gemini.dto.GeminiRequest;
import com.example.demo.gemini.dto.GeminiResponse;
import com.example.demo.gemini.service.GeminiService;

import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/api/gemini")
public class GeminiController {

    @Autowired
    private GeminiService geminiService;

    @PostMapping("/chat")
    public ResponseEntity<GeminiResponse> chat(@RequestBody GeminiRequest request) {
        try {
            String response = geminiService.generateContent(request.getMessage());
            return ResponseEntity.ok(new GeminiResponse(response));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(new GeminiResponse("오류가 발생했습니다: " + e.getMessage()));
        }
    }

    @GetMapping("/test")
    public ResponseEntity<String> test() {
        return ResponseEntity.ok("Gemini API 연결 테스트 성공!");
    }

    @GetMapping("/debug")
    public ResponseEntity<Map<String, String>> debug() {
        Map<String, String> info = new HashMap<>();
        info.put("hasApiKey", geminiService.hasApiKey() ? "Yes" : "No");
        info.put("sdkVersion", "Google Generative AI Java SDK");
        return ResponseEntity.ok(info);
    }
}
