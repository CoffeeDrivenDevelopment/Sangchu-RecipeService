package com.cdd.recipeservice.ingredientmodule.ingredient.exception;

import com.cdd.common.exception.ErrorCode;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public enum IngredientErrorCode implements ErrorCode {
	INGREDIENT_NOT_FOUND("농수산물을 찾을 수 없습니다.", "Ingredient not found", 404),
	;
	private final String message;
	private final String errorCode;
	private final int statusCode;
}
