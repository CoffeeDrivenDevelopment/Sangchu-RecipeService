package com.cdd.recipeservice.ingredientmodule.targetprice.dto.response;

import java.util.*;

import com.fasterxml.jackson.annotation.*;

import lombok.*;

@AllArgsConstructor
@Builder
@Getter
@NoArgsConstructor
public class TargetPriceInfoResponse {
	@JsonProperty("targets")
	List<TargetPriceInfo> targets;
	@JsonProperty("targetPrice")
	int targetPrice;

	public static TargetPriceInfoResponse of(
		final List<TargetPriceInfo> targetPriceList,
		final int targetPrice) {
		return TargetPriceInfoResponse.builder()
			.targets(targetPriceList)
			.targetPrice(targetPrice)
			.build();
	}
}
