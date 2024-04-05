package com.cdd.recipeservice.ingredientmodule.weeklyprice.exception;

import com.cdd.common.exception.ErrorCode;
import com.cdd.common.exception.SangChuException;

public class WeeklyPriceException extends SangChuException {
	public WeeklyPriceException(ErrorCode errorCode) {
		super(errorCode);
	}
}
