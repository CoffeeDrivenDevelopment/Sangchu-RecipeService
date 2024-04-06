package com.cdd.recipeservice.recipemodule.comment.dto.cond;

public record RecipeRootCommentFindCond(
	PagingCond pagingCond,
	int recipeId
) {

	public static RecipeRootCommentFindCond of(
		final PagingCond pagingCond,
		final int recipeId
	) {
		return new RecipeRootCommentFindCond(pagingCond, recipeId);
	}
}
