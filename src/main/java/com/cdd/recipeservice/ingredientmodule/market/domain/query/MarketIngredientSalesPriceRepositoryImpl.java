package com.cdd.recipeservice.ingredientmodule.market.domain.query;

import static com.cdd.recipeservice.ingredientmodule.market.domain.QMarketIngredientSalesPrice.*;

import java.time.LocalDateTime;
import java.util.Optional;

import com.cdd.recipeservice.ingredientmodule.market.domain.MarketIngredientSalesPrice;
import com.querydsl.jpa.impl.JPAQueryFactory;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class MarketIngredientSalesPriceRepositoryImpl implements MarketIngredientSalesPriceRepositoryCustom {
	private final JPAQueryFactory jpaQueryFactory;

	@Override
	public Optional<MarketIngredientSalesPrice> findMarketIngredientSalesPriceByToday(long marketIngredientId){
		return Optional.ofNullable(jpaQueryFactory.selectFrom(marketIngredientSalesPrice)
			.where(marketIngredientSalesPrice.marketIngredient.id.eq(marketIngredientId)
				.and(marketIngredientSalesPrice.createdAt.year().eq(LocalDateTime.now().getYear())
					.and(marketIngredientSalesPrice.createdAt.month().eq(LocalDateTime.now().getMonthValue())
						.and(marketIngredientSalesPrice.createdAt.dayOfMonth().eq(LocalDateTime.now().getDayOfMonth())))))
			.fetchFirst());
	}
}
