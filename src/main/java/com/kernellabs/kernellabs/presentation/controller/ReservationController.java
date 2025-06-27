package com.kernellabs.kernellabs.presentation.controller;

import com.kernellabs.kernellabs.application.ReservationService;
import com.kernellabs.kernellabs.global.common.ApiResponse;
import com.kernellabs.kernellabs.presentation.dto.request.ReservationRequest;
import com.kernellabs.kernellabs.presentation.dto.request.ReservationUpdateRequest;
import com.kernellabs.kernellabs.presentation.dto.response.ReservationResponse;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/reservations")
@RequiredArgsConstructor
public class ReservationController {

    private final ReservationService reservationService;

    @PostMapping("")
    public ResponseEntity<ApiResponse<ReservationResponse>> createReservation(@RequestBody @Valid ReservationRequest request) {
        ReservationResponse response = reservationService.createReservation(request);
        return ResponseEntity.ok(ApiResponse.success(response));
    }

    @PatchMapping("/{reservationId}")
    public ResponseEntity<ApiResponse<ReservationResponse>> updateReservation(@PathVariable Long reservationId,
        @Valid @RequestBody ReservationUpdateRequest request) {
        ReservationResponse response = reservationService.updateReservation(reservationId, request);
        return ResponseEntity.ok(ApiResponse.success(response));
    }
}
