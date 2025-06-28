package com.kernellabs.kernellabs.presentation.controller;

import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.kernellabs.kernellabs.application.ChatbotService;
import com.kernellabs.kernellabs.global.common.ApiResponse;
import com.kernellabs.kernellabs.presentation.dto.request.ChatAnswerRequest;
import com.kernellabs.kernellabs.presentation.dto.response.ChatAnswerResponse;
import com.kernellabs.kernellabs.presentation.dto.response.PlaceListResponse;

import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor
@RequestMapping("/chats")
public class ChatbotController {

	private final ChatbotService chatbotService;

	@PostMapping("")
	public ResponseEntity<ApiResponse<ChatAnswerResponse>> chat(
		@RequestBody ChatAnswerRequest chatAnswerRequest
	) {
		ChatAnswerResponse chatAnswerResponse = chatbotService.chat(chatAnswerRequest);
		return ResponseEntity.ok(ApiResponse.success(chatAnswerResponse ));
	}
}
