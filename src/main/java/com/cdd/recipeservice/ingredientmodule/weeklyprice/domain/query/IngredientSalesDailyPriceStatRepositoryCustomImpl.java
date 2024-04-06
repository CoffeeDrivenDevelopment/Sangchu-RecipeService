package com.cdd.recipeservice.ingredientmodule.weeklyprice.domain.query;

import static com.cdd.recipeservice.ingredientmodule.weeklyprice.domain.QIngredientSalesDailyPriceStat.*;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.List;
import java.util.Optional;

import com.cdd.recipeservice.ingredientmodule.market.domain.MarketType;
import com.cdd.recipeservice.ingredientmodule.weeklyprice.domain.IngredientSalesDailyPriceStat;
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
	public List<IngredientSalesDailyPriceStat> findByIdTypeAndWeek(PriceSearchCond cond) {
		return jpaQueryFactory.selectFrom(ingredientSalesDailyPriceStat)
			.where(ingredientSalesDailyPriceStat.ingredient.id.eq(cond.getId())
				.and(ingredientSalesDailyPriceStat.marketType.eq(cond.getType()))
				.and(ingredientSalesDailyPriceStat.createdAt.after(cond.getWeek()))
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
		return Optional.ofNullable(jpaQueryFactory
			.select(ingredientSalesDailyPriceStat.minPrice)
			.from(ingredientSalesDailyPriceStat)
			.where(ingredientSalesDailyPriceStat.marketType.eq(marketType)
				.and(ingredientSalesDailyPriceStat.ingredient.id.eq(ingredientId))
			)
			.orderBy(ingredientSalesDailyPriceStat.createdAt.desc())
			.fetchFirst()
		);
	}

	@Override
	public List<Integer> findPricesBetweenTodayAnd7DaysAGo(int id) {
		LocalDateTime today = LocalDateTime.now(ZoneId.of("Asia/Seoul"));
		LocalDateTime sevenDaysAgo = today.minusDays(7);
		return jpaQueryFactory.select(ingredientSalesDailyPriceStat.avgPrice)
			.from(ingredientSalesDailyPriceStat)
			.where(ingredientSalesDailyPriceStat.ingredient.id.eq(id)
				.and(ingredientSalesDailyPriceStat.marketType.eq(MarketType.OFFLINE))
				.and(ingredientSalesDailyPriceStat.createdAt.between(sevenDaysAgo, today))
			)
			.orderBy(ingredientSalesDailyPriceStat.createdAt.desc())
			.fetch();
	}

	@Override
	public List<Integer> findOnlinePriceList(int ingredientId) {
		return jpaQueryFactory.select(ingredientSalesDailyPriceStat.minPrice)
			.from(ingredientSalesDailyPriceStat)
			.where(ingredientSalesDailyPriceStat.marketType.eq(MarketType.ONLINE)
				.and(ingredientSalesDailyPriceStat.ingredient.id.eq(ingredientId)))
			.limit(30)
			.fetch();
	}

	@Override
	public List<Integer> findOfflinePriceList(int ingredientId) {
		return jpaQueryFactory.select(ingredientSalesDailyPriceStat.minPrice)
			.from(ingredientSalesDailyPriceStat)
			.where(ingredientSalesDailyPriceStat.marketType.eq(MarketType.OFFLINE)
				.and(ingredientSalesDailyPriceStat.ingredient.id.eq(ingredientId)))
			.limit(30)
			.fetch();
	}

	@Override
	public List<IngredientSalesDailyPriceStat> findIngredientDailyPrice(LocalDate day) {
		return jpaQueryFactory.selectFrom(ingredientSalesDailyPriceStat)
			.where(ingredientSalesDailyPriceStat.createdAt.between(
						day.atStartOfDay(),
						day.plusDays(1).atStartOfDay()
					)
					.and(ingredientSalesDailyPriceStat.marketType
						.eq(MarketType.ONLINE)
					)
			)
			.fetch();
	}
}
