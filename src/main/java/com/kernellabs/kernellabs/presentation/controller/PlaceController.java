package com.kernellabs.kernellabs.presentation.controller;

import com.kernellabs.kernellabs.application.PlaceService;
import com.kernellabs.kernellabs.global.common.ApiResponse;
import com.kernellabs.kernellabs.presentation.dto.response.PlaceListResponse;
import com.kernellabs.kernellabs.presentation.dto.response.PlaceViewResponse;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
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
    public ResponseEntity<ApiResponse<PlaceViewResponse>> getPlace(@PathVariable Long placeId) {
        PlaceViewResponse response = placeService.getPlaceDetail(placeId);
        return ResponseEntity.ok(ApiResponse.success(response));
    }
}
