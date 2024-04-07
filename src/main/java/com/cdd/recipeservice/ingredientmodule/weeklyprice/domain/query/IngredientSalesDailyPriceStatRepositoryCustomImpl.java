package com.cdd.recipeservice.ingredientmodule.weeklyprice.domain.query;

import static com.cdd.recipeservice.ingredientmodule.weeklyprice.domain.QIngredientSalesDailyPriceStat.*;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

import com.cdd.recipeservice.global.utils.LocalDateTimeUtils;
import com.cdd.recipeservice.ingredientmodule.market.domain.MarketType;
import com.cdd.recipeservice.ingredientmodule.weeklyprice.domain.IngredientSalesDailyPriceStat;
import com.cdd.recipeservice.ingredientmodule.weeklyprice.domain.QWeeklyPrice;
import com.cdd.recipeservice.ingredientmodule.weeklyprice.domain.WeeklyPrice;
import com.cdd.recipeservice.ingredientmodule.weeklyprice.dto.cond.PriceSearchCond;
import com.querydsl.jpa.impl.JPAQueryFactory;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@RequiredArgsConstructor
public class IngredientSalesDailyPriceStatRepositoryCustomImpl
	implements IngredientSalesDailyPriceStatRepositoryCustom {
	private final JPAQueryFactory jpaQueryFactory;

	@Override
	public List<WeeklyPrice> findByIdTypeAndWeek(PriceSearchCond cond) {
		LocalDateTime today = LocalDateTimeUtils.today().toLocalDate().atStartOfDay();
		return jpaQueryFactory.select(new QWeeklyPrice(
					ingredientSalesDailyPriceStat.createdAt,
					ingredientSalesDailyPriceStat.avgPrice
				)
			)
			.from(ingredientSalesDailyPriceStat)
			.where(ingredientSalesDailyPriceStat.ingredient.id.eq(cond.getId())
				.and(ingredientSalesDailyPriceStat.marketType.eq(cond.getType()))
				.and(ingredientSalesDailyPriceStat.createdAt.between(cond.getWeek(),
					today.plusDays(1L).minusSeconds(1L)))
			)
			.orderBy(ingredientSalesDailyPriceStat.createdAt.desc())
			.fetch();
	}

	@Override
	public Optional<Integer> findAvgPriceByIngredientId(int ingredientId) {
		return Optional.ofNullable(jpaQueryFactory.select(ingredientSalesDailyPriceStat.avgPrice)
			.from(ingredientSalesDailyPriceStat)
			.where(ingredientSalesDailyPriceStat.ingredient.id.eq(ingredientId))
			.orderBy(ingredientSalesDailyPriceStat.createdAt.asc())
			.fetchFirst()
		);
	}

	@Override
	public Optional<Integer> findMinPriceByIngredientIdAndMarket(int ingredientId, MarketType marketType) {
		LocalDateTime today = LocalDateTimeUtils.today().toLocalDate().atStartOfDay();
		return Optional.ofNullable(jpaQueryFactory
			.select(ingredientSalesDailyPriceStat.minPrice)
			.from(ingredientSalesDailyPriceStat)
			.where(ingredientSalesDailyPriceStat.marketType.eq(marketType)
				.and(ingredientSalesDailyPriceStat.ingredient.id.eq(ingredientId))
				.and(ingredientSalesDailyPriceStat.createdAt.between(today, today.plusDays(1L).minusSeconds(1L))
				)
			)
			.fetchFirst()
		);
	}

	@Override
	public List<Integer> findPricesBetweenTodayAnd7DaysAGo(int id) {
		LocalDateTime today = LocalDateTimeUtils.today().toLocalDate().atStartOfDay();
		LocalDateTime sevenDaysAgo = today.minusDays(7);
		return jpaQueryFactory.select(ingredientSalesDailyPriceStat.avgPrice)
			.from(ingredientSalesDailyPriceStat)
			.where(ingredientSalesDailyPriceStat.ingredient.id.eq(id)
				.and(ingredientSalesDailyPriceStat.marketType.eq(MarketType.OFFLINE))
				.and(ingredientSalesDailyPriceStat.createdAt.between(sevenDaysAgo, today.plusDays(1L).minusSeconds(1L)))
			)
			.orderBy(ingredientSalesDailyPriceStat.createdAt.desc())
			.fetch();
	}

	@Override
	public List<Integer> findMarketPriceList(int ingredientId, MarketType marketType) {
		return jpaQueryFactory.select(ingredientSalesDailyPriceStat.minPrice)
			.from(ingredientSalesDailyPriceStat)
			.where(ingredientSalesDailyPriceStat.marketType.eq(marketType)
				.and(ingredientSalesDailyPriceStat.ingredient.id.eq(ingredientId)))
			.limit(30)
			.fetch();
	}

	@Override
	public List<IngredientSalesDailyPriceStat> findIngredientDailyPrice(LocalDate day) {
		return jpaQueryFactory.selectFrom(ingredientSalesDailyPriceStat)
			.where(ingredientSalesDailyPriceStat.createdAt.between(
						day.atStartOfDay(),
						day.plusDays(1L).atStartOfDay().minusSeconds(1L)
					)
					.and(ingredientSalesDailyPriceStat.marketType
						.eq(MarketType.ONLINE)
					)
			)
			.fetch();
	}
}
