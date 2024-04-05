package com.cdd.recipeservice.recipemodule.recipe.application;

import org.springframework.data.domain.Pageable;

import com.cdd.recipeservice.recipemodule.recipe.dto.response.RecipeLikeListResponse;

public interface RecipeLikeLoadService {
	RecipeLikeListResponse getLikeRecipeList(int memberId, Pageable pageable);
}
