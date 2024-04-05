package com.cdd.recipeservice.recipemodule.recipe.exception;

import com.cdd.common.exception.ErrorCode;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public enum RecipeErrorCode implements ErrorCode {

	NOT_MATCH_JSON(404, "RECIPE_01", "redis value 매칭이 되지 않습니다."),
	NO_EXISTED_RECIPE(400, "RECIPE_02", "해당 레시피가 존재하지 않습니다"),
	ALREADY_RECIPE_LIKED(400, "RECIPE_03", "이미 좋아요한 레시피입니다."),
	NO_EXISTED_RECIPE_LIKE(400, "RECIPE_04", "해당 레시피의 좋아요가 존재하지 않습니다"),
	NO_EXISTED_INGREDIENT(400, "INGREDIENT_01", "해당 재료가 존재하지 않습니다"),
	NO_EXISTED_MARKET(400, "MARKET_01", "해당 마켓이 존재하지 않습니다"),
	NO_EXISTED_MARKET_INGREDIENT_SALES_PRICE(400, "MARKET_INGREDIENT_SALES_PRICE_01",
		"해당 마켓 재료 가격이 존재하지 않습니다"),
	;

	private final int statusCode;
	private final String errorCode;
	private final String message;
	}
