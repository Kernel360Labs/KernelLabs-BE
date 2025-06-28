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
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;
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

    public PlaceDetailResponse getPlaceDetailWithDate(Long placeId, LocalDate date) {
        // 1. 장소 정보 조회
        Place place = placeRepository.findById(placeId)
            .orElseThrow(() -> new CustomException(ErrorCode.PLACE_NOT_FOUND));

        // 2. 해당 날짜 실제 운영 시간 확인
        LocalTime openTime = place.getOpenTime();
        LocalTime closeTime = place.getCloseTime();

        // 3. 해당 날짜에 이미 예약된 시간 목록 조회
        List<Reservation> reservations = reservationRepository.findByPlaceIdAndReservationDate(placeId, date);
        Set<LocalTime> reservedSlots = getReservedSlots(reservations);

        // 4. 전체 시간 슬롯 생성 및 예약 가능 여부 판단
        List<TimeSlotResponse> timeSlots = generateTimeSlots(openTime, closeTime, reservedSlots);

        // 5. 최종 응답 DTO 생성 및 반환
        return PlaceDetailResponse.of(place, timeSlots);
    }

    private List<TimeSlotResponse> generateTimeSlots(LocalTime openTime, LocalTime closeTime, Set<LocalTime> reservedSlots) {
        List<TimeSlotResponse> slots = new ArrayList<>();
        LocalTime currentTime = openTime;
        while (!currentTime.isAfter(closeTime.minusHours(1))) {
            boolean isAvailable = !reservedSlots.contains(currentTime);
            slots.add(new TimeSlotResponse(currentTime.toString(), isAvailable));
            currentTime = currentTime.plusHours(1);
        }
        return slots;
    }

    private Set<LocalTime> getReservedSlots(List<Reservation> reservations) {
        return reservations.stream()
            .flatMap(reservation -> {
                List<LocalTime> slots = new ArrayList<>();
                LocalTime current = reservation.getStartTime();
                while (current.isBefore(reservation.getEndTime())) {
                    slots.add(current);
                    current = current.plusHours(1);
                }
                return slots.stream();
            })
            .collect(Collectors.toSet());
    }
}
