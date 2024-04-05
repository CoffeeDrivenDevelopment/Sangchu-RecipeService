package com.cdd.recipeservice.recipemodule.recipe.dto.response;

import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.AllArgsConstructor;

@AllArgsConstructor
public abstract class LikedResponse {
	@JsonProperty("is_liked")
	private boolean isLiked;
}
