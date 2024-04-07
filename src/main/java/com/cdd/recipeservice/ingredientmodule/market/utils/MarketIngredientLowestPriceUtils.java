package com.cdd.recipeservice.ingredientmodule.market.utils;

import java.util.Collections;
import java.util.List;

import com.cdd.recipeservice.ingredientmodule.market.domain.MarketRepository;
import com.cdd.recipeservice.ingredientmodule.market.dto.response.ClosestMarket;
import com.cdd.recipeservice.ingredientmodule.market.dto.response.OnlineMarket;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class MarketIngredientLowestPriceUtils {
	public static List<ClosestMarket> getClosestMarketPrices(
		final MarketRepository marketRepository,
		final int ingredientId,
		final double lat,
		final double lng,
		final int limit
	) {
		List<ClosestMarket> closestMarkets = marketRepository.findClosestMarketPrices(
			ingredientId,
			lat,
			lng,
			3000.0D,
			3);
		Collections.sort(closestMarkets);
		return closestMarkets.subList(0, Math.min(limit, closestMarkets.size()));
	}

	public static List<OnlineMarket> getOnlineMarketPrices(
		final MarketRepository marketRepository,
		final int ingredientId,
		final int limit
	) {
		return marketRepository.findMinPriceByIngredientIdAndOnlineMarkets(
			ingredientId,
			limit
		);
	}
}
