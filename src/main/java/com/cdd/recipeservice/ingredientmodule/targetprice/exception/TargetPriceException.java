package com.cdd.recipeservice.ingredientmodule.targetprice.exception;

import com.cdd.common.exception.ErrorCode;
import com.cdd.common.exception.SangChuException;

public class TargetPriceException extends SangChuException {
	public TargetPriceException(ErrorCode errorCode) {
		super(errorCode);
	}
}
