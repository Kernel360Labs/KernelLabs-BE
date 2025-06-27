package com.kernellabs.kernellabs.presentation.dto.response;

import com.kernellabs.kernellabs.domain.Place;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class PlaceListResponse {

    private final Long id;
    private final String thumbnailUrl;
    private final String name;
    private final String address;

    public static PlaceListResponse from(Place place) {
        return PlaceListResponse.builder()
            .id(place.getId())
            .thumbnailUrl(place.getThumbnailUrl())
            .name(place.getName())
            .address(place.getAddress())
            .build();
    }

}
