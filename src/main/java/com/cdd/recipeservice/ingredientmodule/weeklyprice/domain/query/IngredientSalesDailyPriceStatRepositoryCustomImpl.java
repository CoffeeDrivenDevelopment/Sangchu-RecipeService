package com.cdd.recipeservice.ingredientmodule.weeklyprice.domain.query;

import static com.cdd.recipeservice.ingredientmodule.weeklyprice.domain.QIngredientSalesDailyPriceStat.*;

import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.Comparator;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

import com.cdd.recipeservice.ingredientmodule.market.domain.MarketType;
import com.cdd.recipeservice.ingredientmodule.weeklyprice.domain.IngredientSalesDailyPriceStat;
import com.cdd.recipeservice.ingredientmodule.weeklyprice.dto.cond.PriceSearchCond;
import com.cdd.recipeservice.ingredientmodule.weeklyprice.dto.response.IngredientPriceGap;
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
	public List<IngredientPriceGap> findIngredientDailyPrice(boolean asc) {
		List<IngredientSalesDailyPriceStat> todaylist = jpaQueryFactory.selectFrom(ingredientSalesDailyPriceStat)
			.where(ingredientSalesDailyPriceStat.createdAt.year()
				.eq(LocalDateTime.now(ZoneId.of("Asia/Seoul")).getYear())
				.and(ingredientSalesDailyPriceStat.createdAt.month()
					.eq(LocalDateTime.now(ZoneId.of("Asia/Seoul")).getMonth().getValue()))
				.and(ingredientSalesDailyPriceStat.createdAt.dayOfMonth()
					.eq(LocalDateTime.now(ZoneId.of("Asia/Seoul")).getDayOfMonth())
					.and(ingredientSalesDailyPriceStat.marketType.eq(MarketType.ONLINE)))
			)
			.orderBy(ingredientSalesDailyPriceStat.ingredient.id.asc())
			.fetch();

		List<IngredientSalesDailyPriceStat> yesterdaylist = jpaQueryFactory.selectFrom(ingredientSalesDailyPriceStat)
			.where(ingredientSalesDailyPriceStat.createdAt.year()
				.eq(LocalDateTime.now(ZoneId.of("Asia/Seoul")).minusDays(1).getYear())
				.and(ingredientSalesDailyPriceStat.createdAt.month()
					.eq(LocalDateTime.now(ZoneId.of("Asia/Seoul")).minusDays(1).getMonth().getValue()))
				.and(ingredientSalesDailyPriceStat.createdAt.dayOfMonth()
					.eq(LocalDateTime.now(ZoneId.of("Asia/Seoul")).minusDays(1).getDayOfMonth())
					.and(ingredientSalesDailyPriceStat.marketType.eq(MarketType.ONLINE))
				))
			.orderBy(ingredientSalesDailyPriceStat.ingredient.id.asc())
			.fetch();


		log.info("#####RPS ASC " + todaylist.size());

		log.info("#####RPS ASC " + todaylist.size());

		log.info("#####RPS ASC " + yesterdaylist.size());

		log.info(LocalDateTime.now().toString());
		return IntStream.range(0, Math.max(4, Math.min(todaylist.size(), yesterdaylist.size())))
			.mapToObj(i -> IngredientPriceGap.builder()
				.currPrice(todaylist.get(i)
					.getMinPrice())
				.prevPrice(yesterdaylist.get(i)
					.getMinPrice())
				.ingredientId(todaylist.get(i)
					.getIngredient()
					.getId()
				).build())
			.sorted(asc
				? Comparator.comparingInt(o -> (o.getPrevPrice() - o.getCurrPrice()))
				: Comparator.comparingInt(o -> (o.getCurrPrice() - o.getPrevPrice()))
			)
			.collect(Collectors.toList());
	}
}
