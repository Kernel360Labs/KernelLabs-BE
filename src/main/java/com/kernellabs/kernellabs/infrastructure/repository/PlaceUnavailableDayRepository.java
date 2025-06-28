package com.kernellabs.kernellabs.infrastructure.repository;

import com.kernellabs.kernellabs.domain.PlaceUnavailableDay;
import java.time.LocalDate;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PlaceUnavailableDayRepository extends JpaRepository<PlaceUnavailableDay, Long> {
    Optional<PlaceUnavailableDay> findByPlaceIdAndUnavailableDate(Long placeId, LocalDate date);
}
