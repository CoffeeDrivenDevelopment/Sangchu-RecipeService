package com.cdd.recipeservice.recipemodule.comment.dto.cond;

public record RecipeReplyCommentFindCond(
	PagingCond pagingCond,
	long commentId
) {

	public static RecipeReplyCommentFindCond of(
		final PagingCond pagingCond,
		final long commentId
	) {
		return new RecipeReplyCommentFindCond(pagingCond, commentId);
	}
}
