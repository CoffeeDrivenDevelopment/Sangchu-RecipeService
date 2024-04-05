package com.cdd.recipeservice.ingredientmodule.targetprice.exception;

import com.cdd.common.exception.ErrorCode;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public enum TargetPriceErrorCode implements ErrorCode {

	NOT_PICK_TARGET_PRICE_YET(404, "TargetPrice_01", "사용자가 해당 재료의 목표가격을 설정하지 않았습니다."),
	NO_EXISTED_TARGET_PRICE(400, "TargetPrice_02", "사용자의 목표가를 찾을 수 없습니다."),
	;

	private final int statusCode;
	private final String errorCode;
	private final String message;
}
