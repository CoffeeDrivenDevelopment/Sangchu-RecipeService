package com.cdd.recipeservice.recipemodule.recipe.dto.response;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.Builder;

@Builder
public record RecipeSearchResponse(
	@JsonProperty("recipes")
	List<RecipeSearchInfo> recipes,
	@JsonProperty("total_count")
	long totalCount,
	@JsonProperty("last")
	int lastId,
	@JsonProperty("has_more")
	boolean hasMore
) {
}
