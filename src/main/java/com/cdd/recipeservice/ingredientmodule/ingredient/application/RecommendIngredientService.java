package com.cdd.recipeservice.ingredientmodule.ingredient.application;

import java.util.List;

import com.cdd.recipeservice.ingredientmodule.ingredient.domain.IngredientRepository;
import com.cdd.recipeservice.ingredientmodule.ingredient.domain.RecommendIngredient;
import com.cdd.recipeservice.ingredientmodule.ingredient.dto.response.IngredientInfo;

public interface RecommendIngredientService {
	List<RecommendIngredient> selectRecommendIngredient(int maxResults,
		IngredientRepository repository);

	void saveRecommendIngredientList();

	IngredientInfo getRecommendIngredients();
}
