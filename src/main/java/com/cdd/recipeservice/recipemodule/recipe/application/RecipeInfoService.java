package com.cdd.recipeservice.recipemodule.recipe.application;

import com.cdd.recipeservice.recipemodule.comment.dto.cond.PagingCond;
import com.cdd.recipeservice.recipemodule.recipe.dto.response.RecipeInfoResponse;
import com.cdd.recipeservice.recipemodule.recipe.dto.response.RecipeMoviesResponse;
import com.cdd.recipeservice.recipemodule.recipe.dto.response.RecipeSearchResponse;
import com.cdd.sangchupassport.Passport;

public interface RecipeInfoService {
	RecipeInfoResponse findRecipeInfo(final int recipeId);

	RecipeMoviesResponse findRecipeMovies(final String recipeName);

	RecipeSearchResponse findRecipes(Passport passport, String type, String query, PagingCond cond);
}