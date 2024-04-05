package com.cdd.recipeservice.ingredientmodule.targetprice.dto.request;

import com.fasterxml.jackson.annotation.JsonProperty;

public record TargetPriceRequest(
	@JsonProperty("target-price")
	int TargetPrice
) {
}
