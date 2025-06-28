package com.kernellabs.kernellabs.presentation.controller;

import com.kernellabs.kernellabs.application.PlaceService;
import com.kernellabs.kernellabs.global.common.ApiResponse;
import com.kernellabs.kernellabs.presentation.dto.response.PlaceDetailResponse;
import com.kernellabs.kernellabs.presentation.dto.response.PlaceListResponse;
import java.time.LocalDate;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.format.annotation.DateTimeFormat.ISO;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/places")
@RequiredArgsConstructor
public class PlaceController {

    private final PlaceService placeService;

    @GetMapping("")
    public ResponseEntity<?> getAllPlaces() {
        List<PlaceListResponse> response = placeService.getAllPlace();
        return ResponseEntity.ok(ApiResponse.success(response));
    }

    @GetMapping("/{placeId}")
    public ResponseEntity<ApiResponse<PlaceDetailResponse>> getPlace(@PathVariable Long placeId,
        @RequestParam(required = false) @DateTimeFormat(iso = ISO.DATE) LocalDate date) {
        // 날짜 파라미터가 없으면 오늘 날짜 기본값
        LocalDate targetDate = (date == null) ? LocalDate.now() : date;

        PlaceDetailResponse response = placeService.getPlaceDetailWithDate(placeId, targetDate);
        return ResponseEntity.ok(ApiResponse.success(response));
    }
}
