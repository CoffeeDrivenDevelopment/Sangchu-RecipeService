package com.cdd.recipeservice.recipemodule.comment.dto.response;

import com.fasterxml.jackson.annotation.JsonProperty;

public record RecipeCommentRemoveResponse(
	@JsonProperty("content")
	String content
) {

	public static RecipeCommentRemoveResponse create() {
		return new RecipeCommentRemoveResponse("삭제된 댓글입니다.");
	}
}
