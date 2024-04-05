package com.cdd.recipeservice.ingredientmodule.market.domain;

import org.springframework.data.jpa.repository.JpaRepository;

import com.cdd.recipeservice.ingredientmodule.market.domain.query.MarketIngredientRepositoryCustom;

public interface MarketIngredientRepository extends JpaRepository<MarketIngredient, Long>,
	MarketIngredientRepositoryCustom {
}
