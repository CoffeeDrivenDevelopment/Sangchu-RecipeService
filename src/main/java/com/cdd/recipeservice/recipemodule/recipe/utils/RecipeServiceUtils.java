package com.cdd.recipeservice.recipemodule.recipe.utils;

import com.cdd.common.exception.CallConstructorException;
import com.cdd.recipeservice.recipemodule.recipe.domain.Recipe;
import com.cdd.recipeservice.recipemodule.recipe.domain.RecipeRepository;
import com.cdd.recipeservice.recipemodule.recipe.exception.RecipeErrorCode;
import com.cdd.recipeservice.recipemodule.recipe.exception.RecipeException;

public class RecipeServiceUtils {

	private RecipeServiceUtils() {
		throw new CallConstructorException();
	}

	public static Recipe findById(final RecipeRepository repository, final int id) {
		return repository.findById(id)
			.orElseThrow(() -> new RecipeException(RecipeErrorCode.NO_EXISTED_RECIPE));
	}
}
