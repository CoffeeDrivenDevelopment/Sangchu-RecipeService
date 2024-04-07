package com.cdd.recipeservice.ingredientmodule.weeklyprice.application;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.cdd.recipeservice.global.utils.RedisUtils;
import com.cdd.recipeservice.infra.member.application.MemberClient;
import com.cdd.recipeservice.infra.member.dto.MemberCoordinateResponse;
import com.cdd.recipeservice.ingredientmodule.ingredient.domain.IngredientRepository;
import com.cdd.recipeservice.ingredientmodule.ingredient.dto.response.IngredientInxInfo;
import com.cdd.recipeservice.ingredientmodule.ingredient.utils.IngredientUtils;
import com.cdd.recipeservice.ingredientmodule.market.domain.Market;
import com.cdd.recipeservice.ingredientmodule.market.domain.MarketRepository;
import com.cdd.recipeservice.ingredientmodule.market.dto.response.ClosestMarket;
import com.cdd.recipeservice.ingredientmodule.market.dto.response.MarketPricePerUserResponse;
import com.cdd.recipeservice.ingredientmodule.market.dto.response.OnlineMarket;
import com.cdd.recipeservice.ingredientmodule.market.utils.MarketIngredientLowestPriceUtils;
import com.cdd.recipeservice.ingredientmodule.targetprice.domain.TargetPriceRepository;
import com.cdd.recipeservice.ingredientmodule.targetprice.utils.TargetPriceUtils;
import com.cdd.recipeservice.ingredientmodule.weeklyprice.domain.IngredientSalesDailyPriceStat;
import com.cdd.recipeservice.ingredientmodule.weeklyprice.domain.IngredientSalesDailyPriceStatRepository;
import com.cdd.recipeservice.ingredientmodule.weeklyprice.domain.IngredientWeeklyPrice;
import com.cdd.recipeservice.ingredientmodule.weeklyprice.domain.WeeklyPrice;
import com.cdd.recipeservice.ingredientmodule.weeklyprice.dto.cond.PriceSearchCond;
import com.cdd.recipeservice.ingredientmodule.weeklyprice.dto.response.IngredientWeeklyPriceResponse;
import com.cdd.recipeservice.ingredientmodule.weeklyprice.exception.WeeklyPriceErrorCode;
import com.cdd.recipeservice.ingredientmodule.weeklyprice.exception.WeeklyPriceException;
import com.cdd.recipeservice.ingredientmodule.weeklyprice.utils.IngredientWeeklyPriceUtils;
import com.cdd.sangchupassport.Passport;
import com.fasterxml.jackson.databind.ObjectMapper;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Transactional(readOnly = true)
@Service
public class MarketIngredientWeeklyPriceService {
	private final IngredientSalesDailyPriceStatRepository ingredientSalesDailyPriceStatRepository;
	private final IngredientRepository ingredientRepository;
	private final MarketRepository marketRepository;
	private final TargetPriceRepository targetPriceRepository;
	private final RedisTemplate<byte[], byte[]> redisTemplate;
	private final ObjectMapper objectMapper;
	private final MemberClient memberClient;
	@Value("${weekly.price.weeks}")
	private int[] weeks;
	@Value("${weekly.price.unit}")
	private int unit;

	private void validateMarketType(String type) {
		if (!"online".equals(type) && !"offline".equals(type)) {
			throw new WeeklyPriceException(WeeklyPriceErrorCode.NOT_MATCH_MARKET_TYPE);
		}
	}

	public IngredientWeeklyPriceResponse getWeeklyPrice(int id, String type, int week) {
		validateMarketType(type);
		// weekly price 조회
		IngredientWeeklyPrice ingredientWeeklyPrice = RedisUtils.get(
			redisTemplate,
			objectMapper,
			IngredientWeeklyPrice.class,
			type, "-", id);
		List<WeeklyPrice> data = ingredientWeeklyPrice.getData(week);
		return IngredientWeeklyPriceResponse.of(data,
			calculatePercent(data));
	}

	public MarketPricePerUserResponse getIngredientPriceOfflineMarket(
		final Passport passport,
		final int ingredientId
	) {
		MemberCoordinateResponse memberCoordinate = memberClient.findMemberCoordinates(passport);

		List<ClosestMarket> closestMarkets = MarketIngredientLowestPriceUtils.getClosestMarketPrices(
			marketRepository,
			ingredientId,
			memberCoordinate.lat(),
			memberCoordinate.lng(),
			2
		);

		int targetPrice = TargetPriceUtils.findByMemberIdAndIngredientId(
			targetPriceRepository,
			passport.getMemberId(),
			ingredientId,
			0
		);

		List<Map<Integer, List<WeeklyPrice>>> marketPriceList = new ArrayList<>();
		for (ClosestMarket closestMarket : closestMarkets) {
			List<WeeklyPrice> weeklyPrices = marketRepository.findWeeklyPriceByIngredientIdAndMarketId(
				ingredientId,
				closestMarket.getId());
			Map<Integer, List<WeeklyPrice>> weeklyAvgPrices = IngredientWeeklyPriceUtils.generateWeeklyPrice(
				weeklyPrices,
				weeks,
				unit);
			marketPriceList.add(weeklyAvgPrices);
		}

		int todayMinimumPrice = getTodayMinimumPrice(marketPriceList);

		LocalDateTime updatedAt = RedisUtils.get(
			redisTemplate,
			objectMapper,
			IngredientWeeklyPrice.class,
			"offline", "-", ingredientId
		).getUpdateAt();

		return MarketPricePerUserResponse.of(
			updatedAt,
			targetPrice,
			todayMinimumPrice,
			closestMarkets,
			marketPriceList
		);
	}

	private double calculatePercent(List<WeeklyPrice> data) {
		int todayPrice = data.get(data.size() - 1).getPrice();
		int lastWeekPrice = data.get(data.size() - 2).getPrice();
		double percent = (todayPrice - lastWeekPrice) / (double)lastWeekPrice * 100;
		return Math.round(percent * 100.0) / 100.0;
	}

	@Transactional
	public void saveWeeklyPrice(String type) {
		IngredientInxInfo ingredientInxInfo = IngredientUtils.getRangeIngredientId(ingredientRepository);
		int id = ingredientInxInfo.getFirstId();
		int endId = id + (int)ingredientInxInfo.getTotalCnt();
		for (; id <= endId; id++) {

			PriceSearchCond cond = new PriceSearchCond(id, type, 4L);
			List<IngredientSalesDailyPriceStat> ingredientSalesPriceList = ingredientSalesDailyPriceStatRepository
				.findByIdTypeAndWeek(cond);
			Map<Integer, List<WeeklyPrice>> weeklyAvgPrices = IngredientWeeklyPriceUtils.generateWeeklyPrice(
				ingredientSalesPriceList,
				weeks,
				unit);
			IngredientWeeklyPrice ingredientWeeklyPrice = IngredientWeeklyPrice.of(type, id, weeklyAvgPrices);
			RedisUtils.put(
				redisTemplate,
				objectMapper,
				ingredientWeeklyPrice,
				ingredientWeeklyPrice.getKey());
		}
	}

	public MarketPricePerUserResponse getIngredientPriceOnlineMarket(int memberId, int ingredientId) {
		List<OnlineMarket> onlineMarkets = marketRepository.findMinPriceByIngredientIdAndOnlineMarkets(
				ingredientId, 3).stream()
			.map(marketIngredientSalesPrice -> {
				Market market = marketIngredientSalesPrice.getMarketIngredient().getMarket();
				return OnlineMarket.builder()
					.id(market.getId())
					.name(market.getName())
					.price(marketIngredientSalesPrice.getPrice())
					.link(marketIngredientSalesPrice.getSalesLink())
					.build();
			}).toList();

		int targetPrice = TargetPriceUtils.findByMemberIdAndIngredientId(
			targetPriceRepository,
			memberId,
			ingredientId,
			0
		);

		List<Map<Integer, List<WeeklyPrice>>> marketPriceList = new ArrayList<>();

		for (OnlineMarket onlineMarket : onlineMarkets) {
			List<WeeklyPrice> weeklyPrices = marketRepository.findWeeklyPriceByIngredientIdAndMarketId(
				ingredientId,
				onlineMarket.getId()
			);
			Map<Integer, List<WeeklyPrice>> weeklyAvgPrices = IngredientWeeklyPriceUtils.generateWeeklyPrice(
				weeklyPrices,
				weeks,
				unit
			);
			marketPriceList.add(weeklyAvgPrices);
		}

		int todayMinimumPrice = getTodayMinimumPrice(marketPriceList);

		LocalDateTime updatedAt = RedisUtils.get(
			redisTemplate,
			objectMapper,
			IngredientWeeklyPrice.class,
			"online", "-", ingredientId
		).getUpdateAt();

		return MarketPricePerUserResponse.of(
			updatedAt,
			targetPrice,
			todayMinimumPrice,
			onlineMarkets,
			marketPriceList);
	}

	private int getTodayMinimumPrice(List<Map<Integer, List<WeeklyPrice>>> marketPriceList) {
		List<WeeklyPrice> weeklyPrices = marketPriceList.get(0).get(weeks[0]);
		if (weeklyPrices.isEmpty()) {
			return 0;
		}
		return weeklyPrices.get(weeklyPrices.size() - 1).getPrice();
	}
}
