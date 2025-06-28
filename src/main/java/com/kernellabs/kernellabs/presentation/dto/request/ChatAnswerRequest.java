package com.kernellabs.kernellabs.presentation.dto.request;

import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class ChatAnswerRequest {
	@NotNull
	private String question;
}
