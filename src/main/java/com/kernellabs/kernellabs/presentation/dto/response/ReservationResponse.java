package com.kernellabs.kernellabs.presentation.dto.response;

import com.kernellabs.kernellabs.domain.Reservation;
import java.time.LocalDate;
import java.time.LocalTime;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
@AllArgsConstructor(access = AccessLevel.PRIVATE)
public class ReservationResponse {

    private final Long reservationId;
    private final Long placeId;
    private final String placeName;
    private final LocalDate reservationDate;
    private final LocalTime startTime;
    private final LocalTime endTime;

    public static ReservationResponse from(Reservation reservation) {
        return ReservationResponse.builder()
            .reservationId(reservation.getId())
            .placeId(reservation.getPlace().getId())
            .placeName(reservation.getPlace().getName())
            .reservationDate(reservation.getReservationDate())
            .startTime(reservation.getStartTime())
            .endTime(reservation.getEndTime())
            .build();
    }
}
