package com.kernellabs.kernellabs.application;

import autovalue.shaded.com.google.common.collect.ImmutableList;
import com.google.genai.Client;
import com.google.genai.types.GenerateContentConfig;
import com.google.genai.types.GenerateContentResponse;
import com.google.genai.types.GoogleSearch;
import com.google.genai.types.Tool;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

@Service
public class GeminiService {

    private final Client client;
    private final Tool googleSearchTool;
    private final String modelName;

    public GeminiService(
        @Value("${gemini.api.key}") String apiKey,
        @Value("${gemini.model:gemini-2.5-flash}") String modelName
    ) {
        this.client = Client.builder()
            .apiKey(apiKey)
            .build();

        this.googleSearchTool = Tool.builder()
            .googleSearch(GoogleSearch.builder().build())
            .build();

        this.modelName = modelName;
    }

    public String generateAnswer(String prompt) {
        GenerateContentConfig config = GenerateContentConfig.builder()
            .tools(ImmutableList.of(googleSearchTool))
            .build();

        // ← 여기를 client.models()가 아니라 client.models 로 접근
        GenerateContentResponse res = client.models
            .generateContent(modelName, prompt, config);

        return res.text();
    }

}
