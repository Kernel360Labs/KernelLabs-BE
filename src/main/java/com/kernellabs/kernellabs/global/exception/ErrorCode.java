package com.kernellabs.kernellabs.global.exception;

import org.springframework.http.HttpStatus;

import lombok.Getter;

@Getter
public enum ErrorCode {

	// Bad Request
	BAD_REQUEST("1001", "잘못된 요청입니다.", HttpStatus.BAD_REQUEST),
	INVALID_INPUT_VALUE("1002", "유효하지 않은 입력 값입니다.", HttpStatus.BAD_REQUEST),
	INVALID_TYPE_VALUE("1003", "요청 데이터 타입이 올바르지 않습니다.", HttpStatus.BAD_REQUEST),
	MISSING_REQUIRED_FIELD("1004", "필수 입력 항목이 누락되었습니다.", HttpStatus.BAD_REQUEST),

	// HTTP 401 Unauthorized
	UNAUTHORIZED_ACCESS("1101", "인증 정보가 유효하지 않습니다.", HttpStatus.UNAUTHORIZED),
	TOKEN_EXPIRED("1102", "인증 토큰이 만료되었습니다.", HttpStatus.UNAUTHORIZED),
	INVALID_TOKEN("1103", "유효하지 않은 인증 토큰입니다.", HttpStatus.UNAUTHORIZED),

	// HTTP 403 Forbidden
	FORBIDDEN_ACCESS("1201", "접근 권한이 없습니다.", HttpStatus.FORBIDDEN),

	// Not Found
	ELEMENT_NOT_FOUND("1301", "엔티티를 찾을 수 없습니다.", HttpStatus.NOT_FOUND),

	// Internal Server Error
	INTERNAL_SERVER_ERROR("9999", "서버 내부 오류가 발생했습니다.", HttpStatus.INTERNAL_SERVER_ERROR),
	EXTERNAL_SERVICE_ERROR("9901", "외부 서비스 연동 중 오류가 발생했습니다.", HttpStatus.INTERNAL_SERVER_ERROR),

	// custom error
    PLACE_NOT_FOUND("2001", "해당 장소를 찾을 수 없습니다.", HttpStatus.NOT_FOUND),
	TIME_SLOTS_EMPTY("2002", "예약 시간을 선택해주세요.", HttpStatus.BAD_REQUEST ),
	INVALID_TIME_SLOT_SEQUENCE("2003", "예약 시간은 연속되어야 합니다.", HttpStatus.BAD_REQUEST),
	RESERVATION_NOT_POSSIBLE_ON_DAY("2004", "선택하신 날짜는 예약이 불가능합니다.", HttpStatus.CONFLICT),
	INVALID_RESERVATION_TIME("2005", "예약 요청 시간이 운영 시간 범위를 벗어납니다.", HttpStatus.BAD_REQUEST),
	RESERVATION_ALREADY_EXISTS("2006", "해당 시간에 이미 예약이 존재합니다.", HttpStatus.CONFLICT),
	INVALID_PASSWORD("2007", "비밀번호가 일치하지 않습니다.", HttpStatus.FORBIDDEN);

	private final HttpStatus status;
	private final String code;
	private String message;

	ErrorCode(final String code, final String message, final HttpStatus status) {
		this.status = status;
		this.code = code;
		this.message = message;
	}
}
