package com.cdd.recipeservice.ingredientmodule.market.domain.query;

import static com.cdd.recipeservice.ingredientmodule.market.domain.QMarketIngredientSalesPrice.*;

import java.time.LocalDateTime;
import java.util.Optional;

import com.cdd.recipeservice.global.utils.LocalDateTimeUtils;
import com.cdd.recipeservice.ingredientmodule.market.domain.MarketIngredientSalesPrice;
import com.querydsl.jpa.impl.JPAQueryFactory;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class MarketIngredientSalesPriceRepositoryImpl implements MarketIngredientSalesPriceRepositoryCustom {
	private final JPAQueryFactory jpaQueryFactory;

	@Override
	public Optional<MarketIngredientSalesPrice> findMarketIngredientSalesPriceByToday(long marketIngredientId) {
		LocalDateTime today = LocalDateTimeUtils.today().toLocalDate().atStartOfDay();
		return Optional.ofNullable(jpaQueryFactory.selectFrom(marketIngredientSalesPrice)
			.where(marketIngredientSalesPrice.marketIngredient.id.eq(marketIngredientId)
				.and(marketIngredientSalesPrice.createdAt.between(
					today.toLocalDate().atStartOfDay(),
					today.plusDays(1L).minusSeconds(1L))
				)
			)
			.fetchFirst());
	}
}
