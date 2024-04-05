package com.cdd.recipeservice.recipemodule.comment.dto.cond;

public record RecipeCommentFindCond(
	PagingCond pagingCond,
	long recipeId,
	boolean canRootCommentSearch
) {

	public static RecipeCommentFindCond searchRootComment(
		final PagingCond pagingCond,
		final int recipeId
	) {
		return new RecipeCommentFindCond(pagingCond, recipeId, true);
	}

	public static RecipeCommentFindCond searchReplyComment(
		final PagingCond pagingCond,
		final long commentId
	) {
		return new RecipeCommentFindCond(pagingCond, commentId, false);
	}
}
