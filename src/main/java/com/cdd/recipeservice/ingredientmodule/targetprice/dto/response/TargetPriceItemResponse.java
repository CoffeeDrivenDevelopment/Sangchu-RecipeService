package com.cdd.recipeservice.ingredientmodule.targetprice.dto.response;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.Builder;

@Builder
public record TargetPriceItemResponse(
	@JsonProperty("ingredient_id")
	Integer ingredientId,

	@JsonProperty("ingredient_name")
	String ingredientName,

	@JsonProperty("target_price")
	Integer tartgetPrice,

	@JsonProperty("online_price")
	List<Integer> onlinePrice,

	@JsonProperty("offline_price")
	List<Integer> offlinePrice,

	@JsonProperty("current_online_price")
	Integer currentOnlinePrice,

	@JsonProperty("current_offline_price")
	Integer currentOfflinePrice
) {
}
