package com.cdd.recipeservice.ingredientmodule.weeklyprice.dto.response;

import com.cdd.recipeservice.ingredientmodule.ingredient.domain.Ingredient;
import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@Getter
@Builder
@NoArgsConstructor
public class IngredientPriceGap {
	@JsonProperty("ingredient_id")
	private int ingredientId;
	@JsonProperty("ingredient_name")
	private String ingredientName;
	@JsonProperty("ingredient_image")
	private String ingredientImage;
	@JsonProperty("percent")
	private double percent;

	public static IngredientPriceGap of(
		Ingredient ingredient,
		double percent) {
		return IngredientPriceGap.builder()
			.ingredientId(ingredient.getId())
			.ingredientName(ingredient.getName())
			.ingredientImage(ingredient.getImg())
			.percent(percent)
			.build();
	}
}
