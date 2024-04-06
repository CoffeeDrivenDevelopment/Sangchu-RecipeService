package com.cdd.recipeservice.recipemodule.recipe.application;

import org.springframework.data.domain.Pageable;

import com.cdd.recipeservice.recipemodule.recipe.dto.response.RecipeSearchResponse;

public interface RecipeLikeLoadService {
	RecipeSearchResponse getLikeRecipeList(int memberId, Pageable pageable);
}
