package com.kernellabs.kernellabs.infrastructure.repository;

import com.kernellabs.kernellabs.domain.Reservation;
import jakarta.validation.constraints.FutureOrPresent;
import jakarta.validation.constraints.NotNull;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ReservationRepository extends JpaRepository<Reservation, Long> {
    boolean existsByPlaceIdAndReservationDateAndStartTimeBeforeAndEndTimeAfter(
        Long placeId,
        LocalDate date,
        LocalTime endTime,
        LocalTime startTime
    );

    // 예약 변경 시, 겹치는 예약이 있는지 확인 (자기 자신 제외)
    boolean existsByPlaceIdAndReservationDateAndIdNotAndStartTimeBeforeAndEndTimeAfter(
        Long placeId,
        LocalDate date,
        Long reservationId,
        LocalTime endTime,
        LocalTime startTime
    );

    List<Reservation> findByPlaceIdAndReservationDate(Long placeId, LocalDate date);
}
