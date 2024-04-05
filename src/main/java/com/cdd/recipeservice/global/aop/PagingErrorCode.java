package com.cdd.recipeservice.global.aop;

import com.cdd.common.exception.ErrorCode;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public enum PagingErrorCode implements ErrorCode {
	TOO_MANY_REQUEST_SIZE(400, "PAGING_001", "요청하신 데이터량이 너무 큽니다."),
	;
	private final int statusCode;
	private final String errorCode;
	private final String message;
}
