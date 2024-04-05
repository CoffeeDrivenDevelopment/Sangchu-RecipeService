package com.cdd.recipeservice.recipemodule.review.exception;

import com.cdd.common.exception.ErrorCode;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public enum CookEatErrorCode implements ErrorCode {
	TOO_LONG_TITLE(400, "COOKEAT_001", "리뷰의 제목이 30글자를 초과합니다."),
	TOO_LONG_CONTENT(400, "COOKEAT_002", "리뷰의 내용이 255글자를 초과합니다."),
	EMPTY_IAMGE(400, "COOKEAT_003", "리뷰의 사진은 반드시 1장이상 있어야 합니다."),
	TOO_MANY_IMAGES(400, "COOKEAT_004", "리뷰의 사진은 5장을 초과할 수 없습니다."),
	NO_EXISTED_COOK_EAT(400, "COOKEAT_005", "존재하지 않는 리뷰입니다."),
	NOT_OWNER(403, "COOKEAT_006", "권한이 존재하지 않습니다.");

	private final int statusCode;
	private final String errorCode;
	private final String message;
}
