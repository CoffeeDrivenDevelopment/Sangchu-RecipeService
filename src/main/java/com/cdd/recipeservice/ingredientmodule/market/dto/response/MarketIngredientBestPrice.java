package com.cdd.recipeservice.ingredientmodule.market.dto.response;

import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.Builder;

@Builder
public class MarketIngredientBestPrice {
	@JsonProperty("market_name")
	private String marketName;
	@JsonProperty("market_link")
	private String marketLink;
	@JsonProperty("price")
	private int price;

}
