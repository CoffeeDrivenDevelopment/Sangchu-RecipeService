package com.cdd.recipeservice.ingredientmodule.weeklyprice.utils;

import com.cdd.recipeservice.ingredientmodule.market.domain.MarketType;
import com.cdd.recipeservice.ingredientmodule.weeklyprice.domain.IngredientSalesDailyPriceStatRepository;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class IngredientSalesDailyPriceStatUtils {
	public static int findMinPriceByIngredientIdAndMarket(IngredientSalesDailyPriceStatRepository repository, int ingredientId, MarketType marketType) {
		return repository.findMinPriceByIngredientIdAndMarket(ingredientId, marketType)
			.orElse(0);
	}
}
