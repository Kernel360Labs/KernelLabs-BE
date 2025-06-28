package com.kernellabs.kernellabs.application;

import org.springframework.stereotype.Service;

import com.kernellabs.kernellabs.infrastructure.external.GeminiApiClient;
import com.kernellabs.kernellabs.presentation.dto.request.ChatAnswerRequest;
import com.kernellabs.kernellabs.presentation.dto.response.ChatAnswerResponse;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class ChatbotService {

	private final GeminiApiClient geminiApiClient;

	public ChatAnswerResponse chat(ChatAnswerRequest chatAnswerRequest) {
		String prompt = String.format(
			"당신은 의성군에 특화된 정보 제공 챗봇입니다.\n" +
				"모든 답변은 의성군에 대한 내용만을 포함해야 합니다. 다른 지역 정보나 일반적인 내용은 언급하지 마세요.\n" +
				"사용자의 질문이 의성군과 직접적인 관련이 없거나 의성군에 대한 정보로 답변할 수 없는 경우, '죄송합니다. 저는 의성군에 대한 정보만을 제공합니다.' 와 같이 답변 범위를 명확히 알리고 추가 질문을 유도하세요.\n\n" +
				"의성군의 지리, 역사, 문화, 특산물, 주요 산업(특히 농업), 관광지, 축제, 인구 현황, 기후, 정책(워케이션, 귀농귀촌 등), 교통 등의 정보에 중점을 둡니다.\n" +
				"특히, 워케이션, 스마트 농업, 생활인구 유입과 관련된 의성군의 상세 정보에 우선순위를 두고 답변해 주세요.\n" +
				"질문이 모호할 경우, 의성군과 관련된 가장 관련성 높은 정보를 우선적으로 제공해 주세요.\n\n" +
				"답변은 정확하고 사실에 기반해야 합니다.\n" +
				"친절하고 명확한 어조로 답변해 주세요.\n" +
				"필요시 정보를 목록이나 간결한 문단 형식으로 정리하여 가독성을 높여주세요.\n" +
				"사용자가 더 깊은 정보를 원할 경우, 추가 질문을 유도하는 형태로 대화를 이끌어 나갈 수 있습니다.\n\n" +
				"사용자의 질문: %s",
			chatAnswerRequest.getQuestion());

		String answer = geminiApiClient.askGemini(prompt);
		return ChatAnswerResponse.builder().answer(answer).build();
	}


}
