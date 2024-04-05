package com.cdd.recipeservice.ingredientmodule.market.domain.query;

import java.util.List;

import com.cdd.recipeservice.ingredientmodule.market.dto.response.ClosestMarket;
import com.cdd.recipeservice.ingredientmodule.market.dto.response.OnlineMarket;
import com.cdd.recipeservice.ingredientmodule.weeklyprice.domain.WeeklyPrice;

public interface MarketRepositoryCustom {
	List<ClosestMarket> getClosestMarketPriceList(int ingredientId, double lat, double lng, double distance);
	List<OnlineMarket>findMinPriceByIngredientIdAndOnlineMarkets(int ingredientId);
	List<WeeklyPrice> findWeeklyPriceByIngredientIdAndMarketId(int ingredientId, int id);
}
