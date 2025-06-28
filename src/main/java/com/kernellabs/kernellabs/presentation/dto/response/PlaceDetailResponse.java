package com.kernellabs.kernellabs.presentation.dto.response;

import com.kernellabs.kernellabs.domain.Place;
import java.time.LocalTime;
import java.util.List;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class PlaceDetailResponse {
    private Long id;
    private String name;
    private String address;
    private String thumbnailUrl;
    private String description;
    private LocalTime openTime;
    private LocalTime closeTime;
    private Integer unitPrice;
    private List<TimeSlotResponse> timeSlots; // 시간표 정보

    public static PlaceDetailResponse of(Place place, List<TimeSlotResponse> timeSlots) {
        return PlaceDetailResponse.builder()
            .id(place.getId())
            .name(place.getName())
            .address(place.getAddress())
            .thumbnailUrl(place.getThumbnailUrl())
            .description(place.getDescription())
            .openTime(place.getOpenTime())
            .closeTime(place.getCloseTime())
            .unitPrice(place.getUnitPrice())
            .timeSlots(timeSlots)
            .build();
    }
}
