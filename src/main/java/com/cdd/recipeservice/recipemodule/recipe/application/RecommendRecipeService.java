package com.cdd.recipeservice.recipemodule.recipe.application;

import java.util.List;

import com.cdd.recipeservice.recipemodule.recipe.domain.RecipeRepository;
import com.cdd.recipeservice.recipemodule.recipe.dto.response.RecipeInfo;

public interface RecommendRecipeService {
	List<RecipeInfo> selectRecommendRecipe(int maxResults,
			RecipeRepository recipeRepository);

}
