package com.cdd.recipeservice.recipemodule.comment.dto.response;

import com.fasterxml.jackson.annotation.JsonProperty;

public record RecipeCommentUpdateResponse(
	@JsonProperty("content")
	String content
) {
}
