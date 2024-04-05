package com.cdd.recipeservice.ingredientmodule.market.application;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.cdd.recipeservice.ingredientmodule.market.domain.MarketRepository;
import com.cdd.recipeservice.ingredientmodule.market.dto.response.ClosestMarket;
import com.cdd.recipeservice.ingredientmodule.market.dto.response.OfflineMarketLowestPriceListResponse;
import com.cdd.recipeservice.ingredientmodule.market.utils.ClosestMarketUtils;

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
	public OfflineMarketLowestPriceListResponse getOfflineMarketLowestPrice(
		final int ingredientId,
		final double lat,
		final double lng) {
		// 3km 이내 시장 3개 가져오기
		List<ClosestMarket> clostMarketList = ClosestMarketUtils.getClosestMarketPriceList(
			marketRepository,
			ingredientId,
			lat,
			lng,
			3);
		return OfflineMarketLowestPriceListResponse.from(clostMarketList);
	}
}
