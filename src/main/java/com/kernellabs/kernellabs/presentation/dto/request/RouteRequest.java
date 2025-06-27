package com.kernellabs.kernellabs.presentation.dto.request;

import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public class RouteRequest {
	@NotNull
	private String startDate;
	@NotNull
	private String duration;
	@NotNull
	private String time;
	@NotNull
	private String vibe;
	@NotNull
	private String interests;
	@NotNull
	private String transportation;
	private String companion;
	private String budget;
}
