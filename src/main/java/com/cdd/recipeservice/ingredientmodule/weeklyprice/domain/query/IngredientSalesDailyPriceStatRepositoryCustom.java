package com.cdd.recipeservice.ingredientmodule.weeklyprice.domain.query;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

import com.cdd.recipeservice.ingredientmodule.market.domain.MarketType;
import com.cdd.recipeservice.ingredientmodule.weeklyprice.domain.IngredientSalesDailyPriceStat;
import com.cdd.recipeservice.ingredientmodule.weeklyprice.dto.cond.PriceSearchCond;

public interface IngredientSalesDailyPriceStatRepositoryCustom {
	List<IngredientSalesDailyPriceStat> findByIdTypeAndWeek(PriceSearchCond cond);

	Optional<Integer> findAvgPriceByIngredientId(int ingredientId);

	Optional<Integer> findMinPriceByIngredientIdAndMarket(int ingredientId, MarketType marketType);

	List<Integer> findPricesBetweenTodayAnd7DaysAGo(int id);

	List<Integer> findOnlinePriceList(int ingredientId);

	List<Integer> findOfflinePriceList(int ingredientId);

	List<IngredientSalesDailyPriceStat> findIngredientDailyPrice(LocalDate day);
}
