package com.cdd.recipeservice.ingredientmodule.market.utils;

import java.util.Collections;
import java.util.List;

import com.cdd.recipeservice.ingredientmodule.market.domain.MarketRepository;
import com.cdd.recipeservice.ingredientmodule.market.dto.response.ClosestMarket;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class ClosestMarketUtils {
	public static List<ClosestMarket> getClosestMarketPriceList(
		final MarketRepository marketRepository,
		final int ingredientId,
		final double lat,
		final double lng,
		final int limit
	) {
		List<ClosestMarket> closestMarketList = marketRepository.getClosestMarketPriceList(
			ingredientId,
			lat,
			lng,
			3 * 1000.0);
		return orderBy(limit, closestMarketList);
	}

	private static List<ClosestMarket> orderBy(int limit, List<ClosestMarket> clostMarketList) {
		Collections.sort(clostMarketList);
		return clostMarketList.subList(0, Math.min(limit, clostMarketList.size()));
	}
}
