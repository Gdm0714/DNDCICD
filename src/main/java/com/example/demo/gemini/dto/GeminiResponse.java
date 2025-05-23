package com.example.demo.gemini.dto;

import java.time.LocalDateTime;

public class GeminiResponse {
    private String content;
    private String timestamp;

    public GeminiResponse() {
        this.timestamp = LocalDateTime.now().toString();
    }

    public GeminiResponse(String content) {
        this.content = content;
        this.timestamp = LocalDateTime.now().toString();
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(String timestamp) {
        this.timestamp = timestamp;
    }
}
