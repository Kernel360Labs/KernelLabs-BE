package com.kernellabs.kernellabs.presentation.dto.request;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class SurveyRequest {
    private String q1;  // A,B,C,D
    private String q2;  // A~E
    private String q3;  // A~D
    private String q4;  // A~D
    private String q5;  // A(예), B(아니오)
}