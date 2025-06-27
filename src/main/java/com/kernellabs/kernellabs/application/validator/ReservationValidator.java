package com.kernellabs.kernellabs.application.validator;

import com.kernellabs.kernellabs.domain.Place;
import com.kernellabs.kernellabs.domain.PlaceUnavailableDay;
import com.kernellabs.kernellabs.domain.Reservation;
import com.kernellabs.kernellabs.global.exception.CustomException;
import com.kernellabs.kernellabs.global.exception.ErrorCode;
import com.kernellabs.kernellabs.infrastructure.repository.PlaceUnavailableDayRepository;
import com.kernellabs.kernellabs.infrastructure.repository.ReservationRepository;
import com.kernellabs.kernellabs.presentation.dto.request.ReservationRequest;
import com.kernellabs.kernellabs.presentation.dto.request.ReservationUpdateRequest;
import java.time.LocalDate;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.Collections;
import java.util.List;
import java.util.Optional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class ReservationValidator {

    private final ReservationRepository reservationRepository;
    private final PlaceUnavailableDayRepository unavailableDayRepository;
    private final DateTimeFormatter TIME_FORMATTER = DateTimeFormatter.ofPattern("HH:mm");

    public void validateForCreate(Place place, ReservationRequest request) {
        // 1. 시간 슬롯 자체의 유효성 검증 (포맷, 연속성)
        validateTimeSlots(request.getTimeSlots());

        LocalTime requestStartTime = LocalTime.parse(request.getTimeSlots().get(0), TIME_FORMATTER);
        LocalTime requestEndTime = LocalTime.parse(request.getTimeSlots().get(request.getTimeSlots().size() - 1), TIME_FORMATTER).plusHours(1);

        // 2. 운영 시간 내의 요청인지 검증
        validateAgainstOperatingHours(place, request.getReservationDate(), requestStartTime, requestEndTime);

        // 3. 중복 예약이 없는지 검증
        validateNoOverlappingReservations(place.getId(), request.getReservationDate(), requestEndTime, requestStartTime);
    }

    public void validateForUpdate(Reservation reservation, ReservationUpdateRequest request) {
        LocalDate newDate = request.getNewReservationDate();
        List<String> newTimeSlots = request.getNewTimeSlots();
        validateTimeSlots(newTimeSlots);

        LocalTime newStartTime = LocalTime.parse(request.getNewTimeSlots().get(0), TIME_FORMATTER);
        LocalTime newEndTime = LocalTime.parse(request.getNewTimeSlots().get(request.getNewTimeSlots().size() - 1), TIME_FORMATTER).plusHours(1);

        validateAgainstOperatingHours(reservation.getPlace(), newDate, newStartTime, newEndTime);
        // 자기 자신 제외하고 중복 검사
        validateNoOverlappingForUpdate(reservation, newDate, newEndTime, newStartTime);    }

    // 시간 슬롯의 포맷과 연속성 검증
    private void validateTimeSlots(List<String> timeSlots) {
        if (timeSlots == null || timeSlots.isEmpty()) {
            throw new CustomException(ErrorCode.TIME_SLOTS_EMPTY);
        }
        Collections.sort(timeSlots);

        for (int i = 0; i < timeSlots.size() - 1; i++) {
            LocalTime current = LocalTime.parse(timeSlots.get(i), TIME_FORMATTER);
            LocalTime next = LocalTime.parse(timeSlots.get(i + 1), TIME_FORMATTER);
            if (!current.plusHours(1).equals(next)) {
                throw new CustomException(ErrorCode.INVALID_TIME_SLOT_SEQUENCE);
            }
        }
    }

    // 해당 날짜의 실제 운영 시간 기준으로 요청이 유효한지 검증
    private void validateAgainstOperatingHours(Place place, LocalDate date, LocalTime requestStartTime, LocalTime requestEndTime) {
        OperatingHours operatingHours = getOperatingHoursFor(place, date);

        if (requestStartTime.isBefore(operatingHours.openTime()) || requestEndTime.isAfter(operatingHours.closeTime())) {
            throw new CustomException(ErrorCode.INVALID_RESERVATION_TIME);
        }
    }

    // 중복 예약 검증
    private void validateNoOverlappingReservations(Long placeId, LocalDate date, LocalTime endTime, LocalTime startTime)  {
        if (reservationRepository.existsByPlaceIdAndReservationDateAndStartTimeBeforeAndEndTimeAfter(placeId, date, endTime, startTime)) {
            throw new CustomException(ErrorCode.RESERVATION_ALREADY_EXISTS);
        }
    }

    // 특정 날짜의 실제 운영 시간 계산
    private OperatingHours getOperatingHoursFor(Place place, LocalDate date) {
        Optional<PlaceUnavailableDay> unavailableDayOpt = unavailableDayRepository.findByPlaceIdAndUnavailableDate(place.getId(), date);

        if (unavailableDayOpt.isPresent()) {
            PlaceUnavailableDay unavailableDay = unavailableDayOpt.get();
            if (unavailableDay.getStartTimeOverride() == null) {
                throw new CustomException(ErrorCode.RESERVATION_NOT_POSSIBLE_ON_DAY);
            }
            return new OperatingHours(unavailableDay.getStartTimeOverride(), unavailableDay.getEndTimeOverride());
        } else {
            return new OperatingHours(place.getOpenTime(), place.getCloseTime());
        }
    }

    // 운영 시간을 담는 간단한 레코드
    private record OperatingHours(LocalTime openTime, LocalTime closeTime) {}

    // 자기 자신을 제외하고 중복 예약을 확인하는 메서드
    private void validateNoOverlappingForUpdate(Reservation reservation, LocalDate newDate, LocalTime newEndTime, LocalTime newStartTime) {
        if (reservationRepository.existsByPlaceIdAndReservationDateAndIdNotAndStartTimeBeforeAndEndTimeAfter(
            reservation.getPlace().getId(),
            newDate,
            reservation.getId(),
            newEndTime,
            newStartTime
        )) {
            throw new CustomException(ErrorCode.RESERVATION_ALREADY_EXISTS);
        }
    }
}
