package com.kernellabs.kernellabs.presentation.dto.response;

import com.kernellabs.kernellabs.domain.Place;
import java.time.LocalTime;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class PlaceViewResponse {

    private final Long id;
    private final String thumbnailUrl;
    private final String name;
    private final String address;
    private final LocalTime openTime;
    private final LocalTime closeTime;
    private final Integer unitPrice;
    private final String description;

    public static PlaceViewResponse from(Place place) {
        return PlaceViewResponse.builder()
            .id(place.getId())
            .thumbnailUrl(place.getThumbnailUrl())
            .name(place.getName())
            .address(place.getAddress())
            .openTime(place.getOpenTime())
            .closeTime(place.getCloseTime())
            .unitPrice(place.getUnitPrice())
            .description(place.getDescription())
            .build();
    }

}
