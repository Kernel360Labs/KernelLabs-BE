package com.kernellabs.kernellabs.presentation.dto.response;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class TimeSlotResponse {

    private String time;
    private boolean isAvailable;

}
