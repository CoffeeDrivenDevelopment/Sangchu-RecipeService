package com.cdd.recipeservice.ingredientmodule.market.dto.response;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonProperty;

public record MarketIngredientBestPriceResponse(
	@JsonProperty("markets")
	List<MarketIngredientBestPrice> bestPriceList
) {
	public static MarketIngredientBestPriceResponse from(List<MarketIngredientBestPrice> bestPriceList) {
		return new MarketIngredientBestPriceResponse(bestPriceList);
	}
}
