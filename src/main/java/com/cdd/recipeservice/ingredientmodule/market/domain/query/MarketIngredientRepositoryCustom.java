package com.cdd.recipeservice.ingredientmodule.market.domain.query;

import java.util.List;

import com.cdd.recipeservice.ingredientmodule.market.domain.MarketIngredient;

public interface MarketIngredientRepositoryCustom {
	List<MarketIngredient> findByIngredientIdToday(int ingredientId);
}
