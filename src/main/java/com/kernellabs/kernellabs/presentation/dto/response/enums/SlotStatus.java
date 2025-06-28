package com.kernellabs.kernellabs.presentation.dto.response.enums;

public enum SlotStatus {
    AVAILABLE,      // 예약 가능 (아무도 예약 안 함)
    UNAVAILABLE,    // 예약 불가 (다른 사람이 예약함)
    MY_RESERVATION  // 나의 예약 (선택/해제 가능)
}
