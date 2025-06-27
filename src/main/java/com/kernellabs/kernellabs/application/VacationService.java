package com.kernellabs.kernellabs.application;

import org.springframework.stereotype.Service;

import com.kernellabs.kernellabs.infrastructure.external.GeminiApiClient;
import com.kernellabs.kernellabs.presentation.dto.request.RouteRequest;
import com.kernellabs.kernellabs.presentation.dto.response.RouteResponse;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class VacationService {

	private final GeminiApiClient geminiApiClient;

	public RouteResponse getVacationRoute(RouteRequest routeRequest) {
		String prompt = String.format(
			"""
			당신은 의성군 전문 여행 플래너입니다.
			사용자가 요청한 정보에 기반하여 대한민국 경상북도 의성군으로의 상세한 여행 계획을 짜주세요.

			--- 사용자 요청 정보 ---=
			시작일: %s
			기간: %s
			선호 분위기: %s
			관심 활동: %s
			동반자: %s (없을 경우 무시)
			예산: %s (없을 경우 무시)
			-------------------------

			여행 계획은 다음 지시사항을 엄격하게 지켜서 JSON 객체로만 제공해야 합니다.
			설명은 한국어로 작성하고, 마크다운(json 블록 포함)이나 다른 설명 없이 순수한 JSON 텍스트만 응답해야 합니다.
			각 장소는 'name', 'address', 'activity', 'estimated_cost', 'estimated_time' 을 반드시 포함해야 합니다.
			예상 비용은 '무료', '저렴함', '보통', '비쌈' 또는 구체적인 가격대를 한국어로 작성해 주세요.
			활동 내용과 장소 설명은 구체적이고 매력적으로 작성해 주세요.
			여행 계획은 최소 1일 이상 구성되어야 합니다.

			응답 형식 예시:
			{
			  "plan": [
				{
				  "day": 1,
				  "description": "9시 일정",
				  "places": [
					{ "name": "장소 이름", "address": "주소", "activity": "할 것", "estimated_cost": "예상 비용", "estimated_time": "예상 시간" }
				  ]
				}
			  ]
			}
			""",
			routeRequest.getStartDate(),
			routeRequest.getDuration(),
			routeRequest.getVibe(),
			routeRequest.getInterests(),
			routeRequest.getCompanion(),
			routeRequest.getBudget()
		);
		String answer = geminiApiClient.askGemini(prompt);
		return RouteResponse.builder().route(answer).build();
	}

}
