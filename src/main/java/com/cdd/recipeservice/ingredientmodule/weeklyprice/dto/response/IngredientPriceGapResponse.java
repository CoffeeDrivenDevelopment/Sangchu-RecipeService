package com.cdd.recipeservice.ingredientmodule.weeklyprice.dto.response;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.Builder;

@Builder
public record IngredientPriceGapResponse(
	@JsonProperty("ingredient_gap_list")
	List<IngredientPriceGap> ingredientPriceGapList
) {
	public static IngredientPriceGapResponse from(List<IngredientPriceGap> ingredientPriceGapList){
		return IngredientPriceGapResponse.builder()
			.ingredientPriceGapList(ingredientPriceGapList)
			.build();
	}
}
