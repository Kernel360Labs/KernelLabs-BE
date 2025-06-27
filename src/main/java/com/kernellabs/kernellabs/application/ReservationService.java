package com.kernellabs.kernellabs.application;

import com.kernellabs.kernellabs.application.validator.ReservationValidator;
import com.kernellabs.kernellabs.domain.Place;
import com.kernellabs.kernellabs.domain.Reservation;
import com.kernellabs.kernellabs.global.exception.CustomException;
import com.kernellabs.kernellabs.global.exception.ErrorCode;
import com.kernellabs.kernellabs.infrastructure.repository.PlaceRepository;
import com.kernellabs.kernellabs.infrastructure.repository.ReservationRepository;
import com.kernellabs.kernellabs.presentation.dto.request.ReservationRequest;
import com.kernellabs.kernellabs.presentation.dto.response.ReservationResponse;
import jakarta.transaction.Transactional;
import java.time.format.DateTimeFormatter;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class ReservationService {
    private final ReservationRepository reservationRepository;
    private final PlaceRepository placeRepository;
    private final ReservationValidator reservationValidator;

    @Transactional
    public ReservationResponse createReservation(ReservationRequest request) {
        // 1. 장소 조회
        Place place = placeRepository.findById(request.getPlaceId())
            .orElseThrow(() -> new CustomException(ErrorCode.PLACE_NOT_FOUND));

        // 2. 유효성 검증
        reservationValidator.validate(place, request);

        // 3. 엔티티 생성
        Reservation reservation = Reservation.create(place, request.getPassword(),
            request.getReservationDate(), request.getTimeSlots());

        // 4. 예약 저장 및 응답 반환
       reservationRepository.save(reservation);
       return ReservationResponse.from(reservation);
    }

}
