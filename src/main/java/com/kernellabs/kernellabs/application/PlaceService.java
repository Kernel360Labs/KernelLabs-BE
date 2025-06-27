package com.kernellabs.kernellabs.application;

import com.kernellabs.kernellabs.domain.Place;
import com.kernellabs.kernellabs.global.exception.CustomException;
import com.kernellabs.kernellabs.global.exception.ErrorCode;
import com.kernellabs.kernellabs.infrastructure.repository.PlaceRepository;
import com.kernellabs.kernellabs.presentation.dto.response.PlaceListResponse;
import com.kernellabs.kernellabs.presentation.dto.response.PlaceViewResponse;
import java.util.List;
import java.util.stream.Collectors;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class PlaceService {

    private final PlaceRepository placeRepository;

    public List<PlaceListResponse> getAllPlace() {
        return placeRepository.findAll().stream()
            .map(PlaceListResponse::from)
            .collect(Collectors.toList());
    }

    public PlaceViewResponse getPlaceDetail(Long placeId) {
        Place place = placeRepository.findById(placeId)
            .orElseThrow(() -> new CustomException(ErrorCode.PLACE_NOT_FOUND));

        return PlaceViewResponse.from(place);
    }
}
