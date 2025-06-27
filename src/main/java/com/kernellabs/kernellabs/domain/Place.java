package com.kernellabs.kernellabs.domain;

import com.kernellabs.kernellabs.global.common.BaseEntity;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Lob;
import java.time.LocalTime;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Place extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, unique = true)
    private String name;

    @Column(nullable = false)
    private String address;

    @Column(nullable = false)
    private LocalTime openTime;

    @Column(nullable = false)
    private LocalTime closeTime;

    @Column
    private String thumbnailUrl;

    @Lob
    private String description;

    @Column
    private Integer unitPrice;

    @Builder
    public Place(String name, String address, LocalTime openTime, LocalTime closeTime, String thumbnailUrl, String description, Integer unitPrice) {
        this.name = name;
        this.address = address;
        this.openTime = openTime;
        this.closeTime = closeTime;
        this.thumbnailUrl = thumbnailUrl;
        this.description = description;
        this.unitPrice = unitPrice;
    }

}
