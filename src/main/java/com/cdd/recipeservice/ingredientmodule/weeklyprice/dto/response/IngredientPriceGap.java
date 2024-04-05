package com.cdd.recipeservice.ingredientmodule.weeklyprice.dto.response;

import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class IngredientPriceGap {
	@JsonProperty("ingredient_id")
	private int ingredientId;
	@JsonProperty("ingredient_name")
	private String ingredientName;
	@JsonProperty("ingredient_image")
	private String ingredientImage;

	@JsonProperty("prev_price")
	private int prevPrice;
	@JsonProperty("curr_price")
	private int currPrice;

	public IngredientPriceGap(int ingredientId, String ingredientName, String ingredientImage, int prevPrice,
		int currPrice) {
		this.ingredientId = ingredientId;
		this.ingredientName = ingredientName;
		this.ingredientImage = ingredientImage;
		this.prevPrice = prevPrice;
		this.currPrice = currPrice;
	}

	public void updateInfo(String ingredientName, String ingredientImage) {
		this.ingredientName = ingredientName;
		this.ingredientImage = ingredientImage;
	}
}
