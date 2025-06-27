package com.kernellabs.kernellabs.domain;


import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "work")
@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Work {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;         // 장소명
    private String imgUrl;      // 이미지 주소

    @Column(columnDefinition = "TEXT")
    private String description;  // 설명
    private String type;         // 공유 오피스/카페/스터디카페 등
    private String address;      // 주소

    private Boolean workDev;     // 개발·코딩 적합 여부
    private Boolean workDoc;     // 문서작업 적합 여부
    private Boolean workMeet;    // 회의·협업 적합 여부
    private Boolean workCall;    // 통화 적합 여부

    private Double hoursNorm;    // 지원 가능 시간 정규화 (0.1~1.0)
    private Double crowdNorm;    // 사람 밀집도 (0.0~1.0)
    private Double locationNorm; // 조용⇆관광 (0.0~1.0)
    private Boolean privateAvailable; // 독립 공간 제공 여부
}
