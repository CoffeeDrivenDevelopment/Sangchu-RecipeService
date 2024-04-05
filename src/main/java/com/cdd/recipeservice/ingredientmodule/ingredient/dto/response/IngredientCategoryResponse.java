package com.cdd.recipeservice.ingredientmodule.ingredient.dto.response;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.Builder;

@Builder
public record IngredientCategoryResponse(
	@JsonProperty("categories")
	List<IngredientCategory> categoryList
) {
}
