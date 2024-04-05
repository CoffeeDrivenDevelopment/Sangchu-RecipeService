package com.cdd.recipeservice.recipemodule.comment.dto.response;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.Builder;

@Builder
public record FindRecipeCommentResponse(
	@JsonProperty("comments")
	List<RecipeRootCommentResponse> comments,
	@JsonProperty("total_count")
	int totalCount,
	@JsonProperty("has_more")
	boolean hasMore,
	@JsonProperty("last")
	long lastId
) {
	public static FindRecipeCommentResponse of(
		final List<RecipeRootCommentResponse> comments,
		final boolean hasMore,
		final long lastId
	) {
		return FindRecipeCommentResponse.builder()
			.comments(comments)
			.hasMore(hasMore)
			.totalCount(comments.size())
			.lastId(lastId)
			.build();
	}
}
