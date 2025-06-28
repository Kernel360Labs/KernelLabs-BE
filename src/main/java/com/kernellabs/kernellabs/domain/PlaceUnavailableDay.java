package com.kernellabs.kernellabs.domain;

import com.kernellabs.kernellabs.global.common.BaseEntity;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import java.time.LocalDate;
import java.time.LocalTime;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class PlaceUnavailableDay extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "place_id", nullable = false)
    private Place place;

    @Column(nullable = false)
    private LocalDate unavailableDate;

    private String reason;

    // 해당 날짜에 특별히 적용할 운영 시간 (null이면 장소의 기본 시간을 따름)
    private LocalTime startTimeOverride;
    private LocalTime endTimeOverride;

    @Builder
    public PlaceUnavailableDay(Place place, LocalDate unavailableDate, String reason, LocalTime startTimeOverride, LocalTime endTimeOverride) {
        this.place = place;
        this.unavailableDate = unavailableDate;
        this.reason = reason;
        this.startTimeOverride = startTimeOverride;
        this.endTimeOverride = endTimeOverride;
    }
}
