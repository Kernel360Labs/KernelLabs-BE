package com.kernellabs.kernellabs.presentation.dto.response;

import com.kernellabs.kernellabs.domain.Work;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class SurveyResponse {
    private String name;         // 장소명
    private String description;  // 설명
    private String type;         // 공유 오피스/카페/스터디카페 등
    private String address;      // 주소
    private String imgUrl;

    public static SurveyResponse from(Work work) {
        return new SurveyResponse(
            work.getName(),
            work.getDescription(),
            work.getType(),
            work.getAddress(),
            work.getImgUrl()
        );
    }
}
