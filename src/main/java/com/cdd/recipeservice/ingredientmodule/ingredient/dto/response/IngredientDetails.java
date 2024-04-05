package com.cdd.recipeservice.ingredientmodule.ingredient.dto.response;

import com.cdd.recipeservice.ingredientmodule.ingredient.domain.Ingredient;
import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.Builder;

@Builder
public record IngredientDetails(
	@JsonProperty("name")
	String name,
	@JsonProperty("know-how")
	String knowHow,
	@JsonProperty("img")
	String img
) {
	public static IngredientDetails from(Ingredient ingredient) {
		return IngredientDetails.builder()
			.name(ingredient.getName())
			.knowHow(ingredient.getKnowHow())
			.img(ingredient.getImg())
			.build();
	}
}
