package com.cdd.recipeservice.ingredientmodule.ingredient.dto.response;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.Builder;

@Builder
public record IngredientPopularResponse(
	@JsonProperty("populars")
	List<IngredientPopular> popularList
) {
}
