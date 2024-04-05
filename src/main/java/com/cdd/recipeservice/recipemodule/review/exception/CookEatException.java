package com.cdd.recipeservice.recipemodule.review.exception;

import com.cdd.common.exception.ErrorCode;
import com.cdd.common.exception.SangChuException;

public class CookEatException extends SangChuException {
	public CookEatException(ErrorCode errorCode) {
		super(errorCode);
	}
}
