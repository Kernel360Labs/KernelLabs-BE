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
import java.time.format.DateTimeFormatter;
import java.util.List;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Reservation extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "place_id", nullable = false)
    private Place place;

    @Column(nullable = false)
    private String password;

    @Column(nullable = false)
    private LocalDate reservationDate;

    @Column(nullable = false)
    private LocalTime startTime;

    @Column(nullable = false)
    private LocalTime endTime;

    @Builder
    public Reservation(Place place, String password, LocalDate reservationDate, LocalTime startTime, LocalTime endTime) {
        this.place = place;
        this.password = password;
        this.reservationDate = reservationDate;
        this.startTime = startTime;
        this.endTime = endTime;
    }

    public static Reservation create(Place place, String password, LocalDate date, List<String> timeSlots) {
        DateTimeFormatter timeFormatter = DateTimeFormatter.ofPattern("HH:mm");
        LocalTime startTime = LocalTime.parse(timeSlots.get(0), timeFormatter);
        LocalTime endTime = LocalTime.parse(timeSlots.get(timeSlots.size() - 1), timeFormatter).plusHours(1);

        return Reservation.builder()
            .place(place)
            .password(password)
            .reservationDate(date)
            .startTime(startTime)
            .endTime(endTime)
            .build();
    }

    public void updateTimes(LocalDate newDate, LocalTime newStartTime, LocalTime newEndTime) {
        this.reservationDate = newDate;
        this.startTime = newStartTime;
        this.endTime = newEndTime;
    }

}
