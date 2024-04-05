package com.cdd.recipeservice.recipemodule.recipe.dto.response;

import org.springframework.data.domain.Page;

import com.cdd.recipeservice.recipemodule.recipe.domain.Recipe;
import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.Builder;

@Builder
public record RecipeLikeListResponse(
	@JsonProperty("recipes")
	Page<RecipeLikeDetailResponse> recipeList
) {
	public static RecipeLikeListResponse from(Page<RecipeLikeDetailResponse> recipeList){
		return RecipeLikeListResponse.builder().recipeList(recipeList).build();
	}
}
