package com.cdd.recipeservice.ingredientmodule.market.domain.query;

import static com.cdd.recipeservice.ingredientmodule.market.domain.QMarket.*;
import static com.cdd.recipeservice.ingredientmodule.market.domain.QMarketIngredient.*;
import static com.cdd.recipeservice.ingredientmodule.market.domain.QMarketIngredientSalesPrice.*;

import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.List;

import com.cdd.recipeservice.ingredientmodule.market.domain.MarketIngredientSalesPrice;
import com.cdd.recipeservice.ingredientmodule.market.domain.MarketType;
import com.cdd.recipeservice.ingredientmodule.market.domain.QAddress;
import com.cdd.recipeservice.ingredientmodule.market.dto.response.ClosestMarket;
import com.cdd.recipeservice.ingredientmodule.market.dto.response.QClosestMarket;
import com.cdd.recipeservice.ingredientmodule.weeklyprice.domain.QWeeklyPrice;
import com.cdd.recipeservice.ingredientmodule.weeklyprice.domain.WeeklyPrice;
import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.core.types.dsl.Expressions;
import com.querydsl.core.types.dsl.NumberExpression;
import com.querydsl.jpa.JPAExpressions;
import com.querydsl.jpa.JPQLQuery;
import com.querydsl.jpa.impl.JPAQueryFactory;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class MarketRepositoryCustomImpl implements MarketRepositoryCustom {
	private final JPAQueryFactory jpaQueryFactory;

	private static JPQLQuery<LocalDateTime> isLatestPrice() {
		return JPAExpressions.select(marketIngredientSalesPrice.createdAt.max())
			.from(marketIngredientSalesPrice)
			.where(marketIngredientSalesPrice.marketIngredient.id.eq(marketIngredient.id));
	}

	@Override
	public List<ClosestMarket> findClosestMarketPrices(int ingredientId, double lat, double lng, double distance,
		int limit) {
		return jpaQueryFactory
			.select(new QClosestMarket(
					market.id,
					market.name,
					market.address.lat,
					market.address.lng,
					getMarketDist(market.address, lat, lng).as("dist"),
					marketIngredientSalesPrice.price
				)
			)
			.from(market)
			.join(marketIngredient)
			.on(market.id.eq(marketIngredient.market.id))
			.join(marketIngredientSalesPrice)
			.on(marketIngredient.ingredient.id.eq(ingredientId)
				.and(marketIngredient.id.eq(marketIngredientSalesPrice.marketIngredient.id))
				.and(marketIngredientSalesPrice.createdAt.eq(isLatestPrice())
				)
			)
			.where(market.type.eq(MarketType.OFFLINE)
				.and(isCloseThan(lat, lng, distance))
			)
			.distinct()
			.limit(limit)
			.fetch();
	}

	private BooleanExpression isCloseThan(double lat, double lng, double distance) {
		return getMarketDist(market.address, lat, lng).loe(distance);
	}

	private NumberExpression<Double> getMarketDist(QAddress addr, double lat, double lng) {
		return Expressions.numberTemplate(Double.class,
			"ST_Distance_Sphere(point({0}, {1}), point({2}, {3}))",
			addr.lng, addr.lat, lng, lat
		);
	}

	@Override
	public List<MarketIngredientSalesPrice> findMinPriceByIngredientIdAndOnlineMarkets(
		final int ingredientId,
		final int limit
	) {
		LocalDateTime today = LocalDateTime.now(ZoneId.of("Asia/Seoul"))
			.toLocalDate().atStartOfDay();
		return jpaQueryFactory.selectFrom(marketIngredientSalesPrice)
			.join(marketIngredientSalesPrice.marketIngredient, marketIngredient).fetchJoin()
			.join(marketIngredient.market, market).fetchJoin()
			.where(market.type.eq(MarketType.ONLINE)
				.and(marketIngredient.ingredient.id.eq(ingredientId))
				.and(marketIngredientSalesPrice.createdAt.between(today.minusDays(1L).minusSeconds(1L), today)
				))
			.orderBy(marketIngredientSalesPrice.price.asc())
			.limit(limit)
			.fetch();
	}

	@Override
	public List<WeeklyPrice> findWeeklyPriceByIngredientIdAndMarketId(int ingredientId, int marketId) {
		return jpaQueryFactory.select(new QWeeklyPrice(
				marketIngredientSalesPrice.createdAt,
				marketIngredientSalesPrice.price
			))
			.from(market)
			.innerJoin(marketIngredient)
			.on(marketIngredient.ingredient.id.eq(ingredientId))
			.innerJoin(marketIngredientSalesPrice)
			.on(marketIngredient.id.eq(marketIngredientSalesPrice.marketIngredient.id))
			.orderBy(marketIngredientSalesPrice.createdAt.desc())
			.limit(28)
			.fetch();
	}
}
