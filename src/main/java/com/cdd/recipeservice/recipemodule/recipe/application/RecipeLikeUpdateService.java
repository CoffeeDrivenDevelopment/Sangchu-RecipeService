package com.cdd.recipeservice.recipemodule.recipe.application;

import com.cdd.recipeservice.recipemodule.recipe.dto.response.RecipeLikeResponse;

public interface RecipeLikeUpdateService {
	RecipeLikeResponse recipeLike(int memberId, int recipeId);
	RecipeLikeResponse delete(int memberId, int recipeId);
}
