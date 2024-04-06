package com.cdd.recipeservice.ingredientmodule.weeklyprice.dto.response;

import java.util.List;

import com.cdd.recipeservice.ingredientmodule.weeklyprice.domain.IngredientPriceGapList;
import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.Builder;

@Builder
public record IngredientPriceGapResponse(
	@JsonProperty("ingredient_gap_list")
	List<IngredientPriceGap> ingredientPriceGapList
) {
	public static IngredientPriceGapResponse from(IngredientPriceGapList ingredientPriceGapList){
		return IngredientPriceGapResponse.builder()
			.ingredientPriceGapList(ingredientPriceGapList.getIngredientPriceGaps())
			.build();
	}
}
