package com.kernellabs.kernellabs.presentation.dto.request;

import jakarta.validation.constraints.FutureOrPresent;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import java.time.LocalDate;
import java.util.List;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class ReservationUpdateRequest {

    @NotBlank(message = "비밀번호는 필수입니다.")
    @Pattern(regexp = "\\d{4}", message = "비밀번호는 4자리 숫자여야 합니다.")
    private String password;

    @NotNull(message = "변경할 날짜를 입력해주세요.")
    @FutureOrPresent(message = "과거 날짜로는 변경할 수 없습니다.")
    private LocalDate newReservationDate;

    @NotEmpty(message = "변경할 예약 시간은 최소 1개 이상 선택해야 합니다.")
    @Size(max = 3, message = "예약은 최대 3시간까지 가능합니다.")
    private List<String> newTimeSlots;

}
