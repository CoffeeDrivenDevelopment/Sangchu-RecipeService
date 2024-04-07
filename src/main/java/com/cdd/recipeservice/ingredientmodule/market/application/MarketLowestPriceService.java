package com.cdd.recipeservice.ingredientmodule.market.application;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.cdd.recipeservice.ingredientmodule.market.domain.MarketRepository;
import com.cdd.recipeservice.ingredientmodule.market.dto.response.ClosestMarket;
import com.cdd.recipeservice.ingredientmodule.market.dto.response.MarketLowestPriceListResponse;
import com.cdd.recipeservice.ingredientmodule.market.dto.response.OnlineMarket;
import com.cdd.recipeservice.ingredientmodule.market.utils.MarketIngredientLowestPriceUtils;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Transactional(readOnly = true)
@Service
public class MarketLowestPriceService {
	private final MarketRepository marketRepository;

	/**
	 *
	 * 사용자 위치 3km이내에서 가장 가깝고 싼 시장 3개 가져오기
	 */
	public MarketLowestPriceListResponse<ClosestMarket> getOfflineMarketLowestPrice(
		final int ingredientId,
		final double lat,
		final double lng) {
		// 3km 이내 시장 3개 가져오기
		List<ClosestMarket> closestMarketPrices = MarketIngredientLowestPriceUtils.getClosestMarketPrices(
			marketRepository,
			ingredientId,
			lat,
			lng,
			3);
		return MarketLowestPriceListResponse.from(closestMarketPrices);
	}

	public MarketLowestPriceListResponse<OnlineMarket> getOnlineMarketLowestPrice(int ingredientId) {
		List<OnlineMarket> onlineMarketPrices = MarketIngredientLowestPriceUtils.getOnlineMarketPrices(
			marketRepository,
			ingredientId,
			10
		);
		return MarketLowestPriceListResponse.from(onlineMarketPrices);
	}
}
