package com.kernellabs.kernellabs.presentation.controller;

import com.kernellabs.kernellabs.application.WorkService;
import com.kernellabs.kernellabs.global.common.ApiResponse;
import com.kernellabs.kernellabs.presentation.dto.request.SurveyRequest;
import com.kernellabs.kernellabs.presentation.dto.response.SurveyResponse;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api")
@AllArgsConstructor
public class WorkController {

    private final WorkService workService;

    @PostMapping("/recommend")
    public ResponseEntity<ApiResponse<SurveyResponse>> recommend(@RequestBody SurveyRequest req) {
        SurveyResponse surveyResponse = workService.recommend(req);
        return ResponseEntity.ok(ApiResponse.success(surveyResponse));

    }

}
