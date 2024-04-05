package com.cdd.recipeservice.recipemodule.recipe.exception;

import com.cdd.common.exception.ErrorCode;
import com.cdd.common.exception.SangChuException;

public class RecipeException extends SangChuException {
	public RecipeException(ErrorCode errorCode) {
		super(errorCode);
	}
}
