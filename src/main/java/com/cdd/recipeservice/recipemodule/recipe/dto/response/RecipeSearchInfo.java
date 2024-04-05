package com.cdd.recipeservice.recipemodule.recipe.dto.response;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.Builder;

@Builder
public record RecipeSearchInfo(
	@JsonProperty("id")
	int id,
	@JsonProperty("image")
	String image,
	@JsonProperty("name")
	String name,
	@JsonProperty("is_liked")
	boolean isLiked,
	@JsonProperty("tags")
	List<String> tags
) {
}
