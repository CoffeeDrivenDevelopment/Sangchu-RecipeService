package com.cdd.recipeservice.ingredientmodule.ingredient.utils;

import com.cdd.common.exception.CallConstructorException;
import com.cdd.common.exception.SangChuException;
import com.cdd.recipeservice.ingredientmodule.ingredient.domain.Ingredient;
import com.cdd.recipeservice.ingredientmodule.ingredient.domain.IngredientRepository;
import com.cdd.recipeservice.ingredientmodule.ingredient.dto.response.IngredientInxInfo;
import com.cdd.recipeservice.ingredientmodule.ingredient.exception.IngredientErrorCode;

public class IngredientUtils {

	private IngredientUtils() {
		throw new CallConstructorException();
	}

	public static Ingredient findById(IngredientRepository ingredientRepository, int id) {
		return ingredientRepository.findById(id)
			.orElseThrow(() ->
				new SangChuException(IngredientErrorCode.INGREDIENT_NOT_FOUND));
	}

	public static IngredientInxInfo getRangeIngredientId(IngredientRepository ingredientRepository) {
		return ingredientRepository.getRangeIngredientId();

	}

}
