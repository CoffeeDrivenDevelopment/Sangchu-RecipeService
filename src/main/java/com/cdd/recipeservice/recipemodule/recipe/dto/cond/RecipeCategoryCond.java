package com.cdd.recipeservice.recipemodule.recipe.dto.cond;

import com.cdd.recipeservice.recipemodule.comment.dto.cond.PagingCond;
import com.cdd.recipeservice.recipemodule.recipe.domain.RecipeCategory;

public record RecipeCategoryCond(
	RecipeCategory category,
	PagingCond pagingCond
) {
}
