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
    List<Reservation> findByPlaceIdAndReservationDateAndStartTimeBeforeAndEndTimeAfter(
        Long placeId,
        LocalDate date,
        LocalTime endTime,
        LocalTime startTime
    );

    boolean existsByPlaceIdAndReservationDateAndStartTimeBeforeAndEndTimeAfter(
        Long placeId,
        LocalDate date,
        LocalTime endTime,
        LocalTime startTime
    );
}
