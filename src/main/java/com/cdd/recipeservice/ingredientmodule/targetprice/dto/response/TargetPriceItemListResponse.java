package com.cdd.recipeservice.ingredientmodule.targetprice.dto.response;

import java.time.LocalDateTime;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.Builder;

@Builder
public record TargetPriceItemListResponse(
	@JsonProperty("target_price_list")
	List<TargetPriceItemResponse> targetPriceItemList
) {
	public static TargetPriceItemListResponse from(List<TargetPriceItemResponse> targetPriceItemList){
		return TargetPriceItemListResponse.builder()
			.targetPriceItemList(targetPriceItemList)
			.build();
	}
}
