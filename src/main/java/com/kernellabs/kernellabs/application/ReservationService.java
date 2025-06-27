package com.kernellabs.kernellabs.application;

import com.kernellabs.kernellabs.application.validator.ReservationValidator;
import com.kernellabs.kernellabs.domain.Place;
import com.kernellabs.kernellabs.domain.Reservation;
import com.kernellabs.kernellabs.global.exception.CustomException;
import com.kernellabs.kernellabs.global.exception.ErrorCode;
import com.kernellabs.kernellabs.infrastructure.repository.PlaceRepository;
import com.kernellabs.kernellabs.infrastructure.repository.ReservationRepository;
import com.kernellabs.kernellabs.presentation.dto.request.ReservationDeleteRequest;
import com.kernellabs.kernellabs.presentation.dto.request.ReservationRequest;
import com.kernellabs.kernellabs.presentation.dto.request.ReservationUpdateRequest;
import com.kernellabs.kernellabs.presentation.dto.response.ReservationResponse;
import jakarta.transaction.Transactional;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
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
        reservationValidator.validateForCreate(place, request);

        // 3. 엔티티 생성
        Reservation reservation = Reservation.create(place, request.getPassword(),
            request.getReservationDate(), request.getTimeSlots());

        // 4. 예약 저장 및 응답 반환
       reservationRepository.save(reservation);
       return ReservationResponse.from(reservation);
    }

    @Transactional
    public ReservationResponse updateReservation(Long reservationId, ReservationUpdateRequest request) {
        // 1. 예약 조회 및 비밀번호 확인
        Reservation reservation = findReservationById(reservationId);
        validatePassword(request.getPassword(), reservation.getPassword());

        // 2. 변경 요청 유효성 검사
        reservationValidator.validateForUpdate(reservation, request);

        // 3. 엔티티 상태 변경
        reservation.updateTimes(request.getNewReservationDate(), parseStartTime(request.getNewTimeSlots()), parseEndTime(request.getNewTimeSlots()));
        return ReservationResponse.from(reservation);
    }

    private LocalTime parseStartTime(List<String> timeSlots) {
        return LocalTime.parse(timeSlots.get(0), DateTimeFormatter.ofPattern("HH:mm"));
    }

    private LocalTime parseEndTime(List<String> timeSlots) {
        return LocalTime.parse(timeSlots.get(timeSlots.size() - 1), DateTimeFormatter.ofPattern("HH:mm")).plusHours(1);
    }

    private void validatePassword(String rawPassword, String storedPassword) {
        if (!rawPassword.equals(storedPassword)) {
            throw new CustomException(ErrorCode.INVALID_PASSWORD);
        }
    }

    private Reservation findReservationById(Long reservationId) {
        return reservationRepository.findById(reservationId)
            .orElseThrow(() -> new CustomException(ErrorCode.PLACE_NOT_FOUND));
    }

}
