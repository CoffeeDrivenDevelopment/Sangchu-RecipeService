package com.cdd.recipeservice.ingredientmodule.weeklyprice.dto.response;

import com.cdd.recipeservice.ingredientmodule.ingredient.domain.Ingredient;
import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@Builder
@Getter
@NoArgsConstructor
public class IngredientReasonableResponse {
	@JsonProperty("name")
	private String name;
	@JsonProperty("price")
	private int price;
	@JsonProperty("diff")
	private int diff;
	@JsonProperty("img")
	private String img;

	public static IngredientReasonableResponse of(
		Ingredient ingredient,
		int popularTargetPrice,
		int usersTargetPrice) {
		return IngredientReasonableResponse.builder()
			.name(ingredient.getName())
			.price(popularTargetPrice)
			.diff(usersTargetPrice == -1 ? 0 : Math.abs(popularTargetPrice - usersTargetPrice))
			.img(ingredient.getImg())
			.build();
	}
}
