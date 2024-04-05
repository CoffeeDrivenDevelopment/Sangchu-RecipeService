package com.cdd.recipeservice.ingredientmodule.weeklyprice.exception;

import com.cdd.common.exception.ErrorCode;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public enum WeeklyPriceErrorCode implements ErrorCode {
	NOT_MATCH_MARKET_TYPE(400, "WeeklyPrice_01", "해당 마켓 타입은 존재하지않습니다."),
	;

	private final int statusCode;
	private final String errorCode;
	private final String message;
}
