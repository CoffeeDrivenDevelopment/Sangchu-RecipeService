package com.cdd.recipeservice.recipemodule.recipe.dto.response;

import java.util.List;

import com.cdd.recipeservice.recipemodule.recipe.domain.Recipe;
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
	public static RecipeSearchInfo of(Recipe recipe, boolean isLiked){
		return RecipeSearchInfo.builder()
			.id(recipe.getId())
			.image(recipe.getImage())
			.name(recipe.getTitle())
			.isLiked(isLiked)
			.tags(recipe.getTag())
			.build();
	}
}
