package com.cdd.recipeservice.recipemodule.recipe.dto.cond;

import com.cdd.recipeservice.recipemodule.comment.dto.cond.PagingCond;

public record RecipeSearchCond(
	String query,
	PagingCond pagingCond
) {
}
