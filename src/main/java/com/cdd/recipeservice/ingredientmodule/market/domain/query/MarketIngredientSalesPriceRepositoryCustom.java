package com.cdd.recipeservice.ingredientmodule.market.domain.query;

import java.util.Optional;

import com.cdd.recipeservice.ingredientmodule.market.domain.MarketIngredientSalesPrice;

public interface MarketIngredientSalesPriceRepositoryCustom {
	Optional<MarketIngredientSalesPrice> findMarketIngredientSalesPriceByToday(long marketIngredientId);
}
