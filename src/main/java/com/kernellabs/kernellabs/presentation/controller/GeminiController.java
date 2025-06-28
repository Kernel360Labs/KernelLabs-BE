package com.kernellabs.kernellabs.presentation.controller;

import com.kernellabs.kernellabs.application.GeminiService;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/genie")
@AllArgsConstructor
public class GeminiController {
    private final GeminiService geminiService;

    @PostMapping("/chat")
    public ResponseEntity<ChatResponse> chat(@Valid @RequestBody ChatRequest req) {
        String answer = geminiService.generateAnswer(req.getPrompt());
        return ResponseEntity.ok(new ChatResponse(answer));
    }

    @Data
    @NoArgsConstructor
    @AllArgsConstructor
    public static class ChatRequest {
        private String prompt;
    }

    @Data @NoArgsConstructor @AllArgsConstructor
    public static class ChatResponse {
        private String answer;
    }
}
