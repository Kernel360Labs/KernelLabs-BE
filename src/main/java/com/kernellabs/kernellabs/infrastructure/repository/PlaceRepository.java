package com.kernellabs.kernellabs.infrastructure.repository;

import com.kernellabs.kernellabs.domain.Place;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PlaceRepository extends JpaRepository<Place, Long> {

}
