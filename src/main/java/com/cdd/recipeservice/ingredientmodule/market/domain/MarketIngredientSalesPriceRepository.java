package com.cdd.recipeservice.ingredientmodule.market.domain;

import org.springframework.data.jpa.repository.JpaRepository;

import com.cdd.recipeservice.ingredientmodule.market.domain.query.MarketIngredientSalesPriceRepositoryCustom;

public interface MarketIngredientSalesPriceRepository extends JpaRepository<MarketIngredientSalesPrice, Long>,
	MarketIngredientSalesPriceRepositoryCustom {
}
