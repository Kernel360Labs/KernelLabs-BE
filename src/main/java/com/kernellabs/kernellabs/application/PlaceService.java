package com.kernellabs.kernellabs.application;

import com.kernellabs.kernellabs.domain.Place;
import com.kernellabs.kernellabs.domain.Reservation;
import com.kernellabs.kernellabs.global.exception.CustomException;
import com.kernellabs.kernellabs.global.exception.ErrorCode;
import com.kernellabs.kernellabs.infrastructure.repository.PlaceRepository;
import com.kernellabs.kernellabs.infrastructure.repository.ReservationRepository;
import com.kernellabs.kernellabs.presentation.dto.response.PlaceDetailResponse;
import com.kernellabs.kernellabs.presentation.dto.response.PlaceListResponse;
import com.kernellabs.kernellabs.presentation.dto.response.TimeSlotResponse;
import com.kernellabs.kernellabs.presentation.dto.response.enums.SlotStatus;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.Stream;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class PlaceService {

    private final PlaceRepository placeRepository;
    private final ReservationRepository reservationRepository;

    public List<PlaceListResponse> getAllPlace() {
        return placeRepository.findAll().stream()
            .map(PlaceListResponse::from)
            .collect(Collectors.toList());
    }

    public PlaceDetailResponse getPlaceDetailWithDate(Long placeId, LocalDate date, Long editingReservationId) {
        // 1. 장소 정보 조회
        Place place = placeRepository.findById(placeId)
            .orElseThrow(() -> new CustomException(ErrorCode.PLACE_NOT_FOUND));

        // 2. 해당 날짜 실제 운영 시간 확인
        LocalTime openTime = place.getOpenTime();
        LocalTime closeTime = place.getCloseTime();

        // 3.  해당 날짜의 '모든' 예약을 일단 다 가져온다.
        List<Reservation> allReservationsOnDate = reservationRepository.findByPlaceIdAndReservationDate(placeId, date);

        // 4. '나의 예약' 시간과 '다른 사람 예약' 시간을 분리하여 Set으로 만든다.
        Set<LocalTime> myReservedSlots = getSlotsForSpecificReservation(allReservationsOnDate, editingReservationId);
        Set<LocalTime> othersReservedSlots = getSlotsForOtherReservations(allReservationsOnDate, editingReservationId);

        // 5. 3가지 상태를 포함한 전체 시간 슬롯 리스트를 생성한다.
        List<TimeSlotResponse> timeSlots = generateTimeSlotsWithStatus(openTime, closeTime, myReservedSlots, othersReservedSlots);

        // 6. 최종 응답 DTO를 만들어 반환한다.
        return PlaceDetailResponse.of(place, timeSlots);
    }

    private List<TimeSlotResponse> generateTimeSlotsWithStatus(LocalTime openTime, LocalTime closeTime, Set<LocalTime> mySlots, Set<LocalTime> otherSlots) {
        List<TimeSlotResponse> slots = new ArrayList<>();
        LocalTime currentTime = openTime;
        while (!currentTime.isAfter(closeTime.minusHours(1))) {
            SlotStatus status;
            if (mySlots.contains(currentTime)) {
                status = SlotStatus.MY_RESERVATION;
            } else if (otherSlots.contains(currentTime)) {
                status = SlotStatus.UNAVAILABLE;
            } else {
                status = SlotStatus.AVAILABLE;
            }
            slots.add(new TimeSlotResponse(currentTime.toString(), status));
            currentTime = currentTime.plusHours(1);
        }
        return slots;
    }

    private Set<LocalTime> getSlotsForSpecificReservation(List<Reservation> reservations, Long reservationId) {
        if (reservationId == null) {
            return Collections.emptySet();
        }
        return reservations.stream()
            .filter(r -> r.getId().equals(reservationId))
            .flatMap(this::expandReservationToSlots)
            .collect(Collectors.toSet());
    }
    private Set<LocalTime> getSlotsForOtherReservations(List<Reservation> reservations, Long reservationId) {
        // '나의 예약 ID'가 없는 경우(신규 예약 모드)에는 모든 예약이 '다른 사람 예약'이 된다.
        if (reservationId == null) {
            return reservations.stream()
                .flatMap(this::expandReservationToSlots)
                .collect(Collectors.toSet());
        }
        // '나의 예약 ID'가 있는 경우(수정 모드)에는 해당 예약을 제외한다.
        return reservations.stream()
            .filter(r -> !r.getId().equals(reservationId))
            .flatMap(this::expandReservationToSlots)
            .collect(Collectors.toSet());
    }

    private Stream<LocalTime> expandReservationToSlots(Reservation reservation) {
        List<LocalTime> slots = new ArrayList<>();
        LocalTime current = reservation.getStartTime();
        while (current.isBefore(reservation.getEndTime())) {
            slots.add(current);
            current = current.plusHours(1);
        }
        return slots.stream();
    }
}
