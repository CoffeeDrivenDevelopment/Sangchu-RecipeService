package com.cdd.recipeservice.ingredientmodule.market.application;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.cdd.recipeservice.infra.member.application.MemberClient;
import com.cdd.recipeservice.infra.member.dto.MemberCoordinateResponse;
import com.cdd.recipeservice.ingredientmodule.market.domain.Market;
import com.cdd.recipeservice.ingredientmodule.market.domain.MarketRepository;
import com.cdd.recipeservice.ingredientmodule.market.dto.response.ClosestMarket;
import com.cdd.recipeservice.ingredientmodule.market.dto.response.MarketLowestPriceListResponse;
import com.cdd.recipeservice.ingredientmodule.market.dto.response.OnlineMarket;
import com.cdd.recipeservice.ingredientmodule.market.utils.MarketIngredientLowestPriceUtils;
import com.cdd.sangchupassport.Passport;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Transactional(readOnly = true)
@Service
public class MarketLowestPriceService {
	private final MarketRepository marketRepository;
	private final MemberClient memberClient;

	/**
	 *
	 * 사용자 위치 3km이내에서 가장 가깝고 싼 시장 3개 가져오기
	 */
	public MarketLowestPriceListResponse<ClosestMarket> getOfflineMarketLowestPrice(
		final Passport passport,
		final int ingredientId
	) {
		MemberCoordinateResponse memberCoordinate = memberClient.findMemberCoordinates(passport);
		List<ClosestMarket> closestMarketPrices = MarketIngredientLowestPriceUtils.getClosestMarketPrices(
			marketRepository,
			ingredientId,
			memberCoordinate.lat(),
			memberCoordinate.lng(),
			3);

		return MarketLowestPriceListResponse.from(closestMarketPrices);
	}

	public MarketLowestPriceListResponse<OnlineMarket> getOnlineMarketLowestPrice(int ingredientId) {
		List<OnlineMarket> list = MarketIngredientLowestPriceUtils.getOnlineMarketPrices(
				marketRepository,
				ingredientId,
				10
			).stream()
			.map(marketIngredientSalesPrice -> {
				Market market = marketIngredientSalesPrice.getMarketIngredient().getMarket();
				return OnlineMarket.builder()
					.id(market.getId())
					.name(market.getName())
					.price(marketIngredientSalesPrice.getPrice())
					.link(marketIngredientSalesPrice.getSalesLink())
					.build();
			}).toList();
		return MarketLowestPriceListResponse.from(list);
	}
}
