package com.cdd.recipeservice.recipemodule.comment.dto.request;

import com.fasterxml.jackson.annotation.JsonProperty;


public record RecipeCommentSaveRequest(
	@JsonProperty("recipe_id")
	int recipeId,
	@JsonProperty("parent_comment_id")
	long parentCommentId,
	@JsonProperty("content")
	String content
) {
}
