package com.cdd.recipeservice.ingredientmodule.market.dto.response;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import com.cdd.recipeservice.global.utils.LocalDateTimeUtils;
import com.cdd.recipeservice.ingredientmodule.weeklyprice.domain.WeeklyPrice;
import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.Builder;
import lombok.Getter;

@Builder
@Getter
public class MarketPricePerUserResponse {
	@JsonProperty("updateAt")
	private String updateAt;
	@JsonProperty("target-price")
	private int targetPrice;
	@JsonProperty("today-minimum-price")
	private int todayMinimumPrice;
	@JsonProperty("market-type")
	private List<String> marketType;
	@JsonProperty("markets")
	private List<Map<Integer, List<WeeklyPrice>>> marketPriceList;

	public static <T extends MarketInfoMaker> MarketPricePerUserResponse of(
		LocalDateTime updatedAt,
		int targetPrice,
		int todayMinimumPrice,
		List<T> markets,
		List<Map<Integer, List<WeeklyPrice>>> weeklyPriceList) {
		MarketPricePerUserResponse marketPricePerUserResponse = MarketPricePerUserResponse.builder()
			.updateAt(LocalDateTimeUtils.timePattern(updatedAt))
			.targetPrice(targetPrice)
			.todayMinimumPrice(todayMinimumPrice)
			.marketType(new ArrayList<>())
			.marketPriceList(weeklyPriceList)
			.build();
		for (T closestMarket : markets) {
			marketPricePerUserResponse.getMarketType().add(closestMarket.getName());
		}
		return marketPricePerUserResponse;
	}

}
