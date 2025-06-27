package com.kernellabs.kernellabs.presentation.controller;

import com.kernellabs.kernellabs.application.WorkService;
import com.kernellabs.kernellabs.presentation.dto.request.SurveyRequest;
import com.kernellabs.kernellabs.presentation.dto.response.SurveyResponse;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.kernellabs.kernellabs.application.VacationService;
import com.kernellabs.kernellabs.global.common.ApiResponse;
import com.kernellabs.kernellabs.presentation.dto.request.RouteRequest;
import com.kernellabs.kernellabs.presentation.dto.response.RouteResponse;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor
public class VacationController {

	private final VacationService vacationService;

	@GetMapping("/vacations")
	public ResponseEntity<ApiResponse<RouteResponse>> getVacationRoute(
		@RequestBody @Valid RouteRequest routeRequest
	) {
		RouteResponse routeResponse = vacationService.getVacationRoute(routeRequest);
		return ResponseEntity.ok(ApiResponse.success(routeResponse));
	}

    @RestController
    @RequestMapping("/api/work")
    @AllArgsConstructor
    public static class WorkController {
        private final WorkService recService;

        @PostMapping("/recommend")
        public ResponseEntity<ApiResponse<SurveyResponse>> recommend(@RequestBody SurveyRequest req) {
            SurveyResponse surveyResponse= recService.recommend(req);
            return ResponseEntity.ok(ApiResponse.success(surveyResponse));
        }
    }
}
