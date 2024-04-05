package com.cdd.recipeservice.ingredientmodule.ingredient.domain.query;

import java.util.List;

import com.cdd.recipeservice.ingredientmodule.ingredient.domain.IngredientType;
import com.cdd.recipeservice.ingredientmodule.ingredient.dto.response.IngredientCategory;
import com.cdd.recipeservice.ingredientmodule.ingredient.dto.response.IngredientPopular;
import com.cdd.recipeservice.ingredientmodule.ingredient.dto.response.IngredientSearch;
import com.cdd.recipeservice.ingredientmodule.ingredient.dto.response.IngredientInxInfo;

public interface IngredientRepositoryCustom {
	List<IngredientCategory> findAllCategory();
	IngredientInxInfo getRangeIngredientId();

	List<IngredientSearch> findIngredient(List<IngredientType> categories, String ingredientName);

	List<IngredientPopular> findPopularIngredient();
}
