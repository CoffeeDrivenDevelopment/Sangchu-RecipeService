package com.cdd.recipeservice.recipemodule.comment.dto.response;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.Builder;

@Builder
public record FindRecipeReplyCommentResponse(
	@JsonProperty("comments")
	List<RecipeReplyCommentResponse> comments,
	@JsonProperty("total_count")
	int totalCount,
	@JsonProperty("has_more")
	boolean hasMore,
	@JsonProperty("last")
	long lastId
) {
	public static FindRecipeReplyCommentResponse of(
		final List<RecipeReplyCommentResponse> comments,
		final boolean hasMore,
		final long lastId
	) {
		return FindRecipeReplyCommentResponse.builder()
			.comments(comments)
			.hasMore(hasMore)
			.totalCount(comments.size())
			.lastId(lastId)
			.build();
	}
}
