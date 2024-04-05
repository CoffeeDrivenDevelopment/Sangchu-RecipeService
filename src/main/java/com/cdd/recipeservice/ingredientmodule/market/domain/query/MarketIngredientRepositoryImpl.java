package com.cdd.recipeservice.ingredientmodule.market.domain.query;

import static com.cdd.recipeservice.ingredientmodule.market.domain.QMarketIngredient.*;

import java.util.List;

import com.cdd.recipeservice.ingredientmodule.market.domain.MarketIngredient;
import com.querydsl.jpa.impl.JPAQueryFactory;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class MarketIngredientRepositoryImpl implements MarketIngredientRepositoryCustom {
	private final JPAQueryFactory jpaQueryFactory;
	@Override
	public List<MarketIngredient> findByIngredientIdToday(int ingredientId) {
		return jpaQueryFactory.selectFrom(marketIngredient)
			.where(marketIngredient.ingredient.id.eq(ingredientId))
			.fetch();
	}
}
