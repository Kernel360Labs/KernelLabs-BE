package com.kernellabs.kernellabs.presentation.controller;

import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.kernellabs.kernellabs.application.PolicyService;
import com.kernellabs.kernellabs.global.common.ApiResponse;
import com.kernellabs.kernellabs.presentation.dto.response.PolicyResponse;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/policies")
@RequiredArgsConstructor
public class PolicyController {
	private final PolicyService policyService;

	@GetMapping("")
	public ResponseEntity<ApiResponse<PolicyResponse>> getCurrentPolicy() {
		PolicyResponse policyResponse = policyService.getCurrentPolicy();
		return ResponseEntity.ok(ApiResponse.success(policyResponse));
	}
}
