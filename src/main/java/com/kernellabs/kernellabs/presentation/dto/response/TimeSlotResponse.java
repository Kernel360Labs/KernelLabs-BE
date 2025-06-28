package com.kernellabs.kernellabs.presentation.dto.response;

import com.kernellabs.kernellabs.presentation.dto.response.enums.SlotStatus;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class TimeSlotResponse {

    private String time;
    private SlotStatus status;

}
