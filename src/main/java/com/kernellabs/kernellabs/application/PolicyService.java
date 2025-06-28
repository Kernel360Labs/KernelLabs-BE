package com.kernellabs.kernellabs.application;

import org.springframework.stereotype.Service;

import com.kernellabs.kernellabs.global.util.RedisUtil;
import com.kernellabs.kernellabs.infrastructure.external.GeminiApiClient;
import com.kernellabs.kernellabs.infrastructure.external.GeminiSearchClient;
import com.kernellabs.kernellabs.presentation.dto.response.PolicyResponse;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class PolicyService {

	private final String POLICY_REDIS_KEY = "policy";
	private final RedisUtil redisUtil;
	private final GeminiSearchClient geminiSearchClient;
	private final String prompt = """
      의성군으로 이주하려는 사람들을 위한 최신 혜택 정보를 알려줘.
      주거, 교육, 귀농귀촌, 복지, 창업, 일자리 지원금, 정착금 등 모든 종류의 이주 및 정착 혜택을 포함해줘.
      각 혜택별로 지원 조건, 신청 방법, 담당 부서 또는 관련 웹사이트 링크 같은 상세 정보도 알려줘.
      정보를 대주제와 소주제로 나눠서 정리해줘.
      각 소주제 아래에 자세한 설명을 추가하고, 관련 링크가 있다면 URL 주소를 명확히 포함해줘.
      각 항목은 줄 바꿈(\n)을 사용해서 구분해줘.
      다른 지역 정보나 일반적인 내용은 제외하고, 오직 의성군 관련 혜택만 다뤄줘.
      """;

	public PolicyResponse getCurrentPolicy() {
		String result = "";
		if(redisUtil.existData(POLICY_REDIS_KEY)){
			result = redisUtil.getData(POLICY_REDIS_KEY);
		}
		else{
			result = geminiSearchClient.generateAnswer(prompt);
			redisUtil.setDataExpire(POLICY_REDIS_KEY, result, 10800);
		}
		return PolicyResponse.builder().policy(result).build();

	}
}
