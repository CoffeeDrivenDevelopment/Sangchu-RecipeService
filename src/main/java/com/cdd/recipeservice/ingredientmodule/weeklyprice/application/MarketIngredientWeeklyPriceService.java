package com.cdd.recipeservice.ingredientmodule.weeklyprice.application;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.cdd.recipeservice.global.utils.RedisUtils;
import com.cdd.recipeservice.ingredientmodule.ingredient.domain.IngredientRepository;
import com.cdd.recipeservice.ingredientmodule.ingredient.dto.response.IngredientInxInfo;
import com.cdd.recipeservice.ingredientmodule.ingredient.utils.IngredientUtils;
import com.cdd.recipeservice.ingredientmodule.market.domain.MarketRepository;
import com.cdd.recipeservice.ingredientmodule.market.domain.MarketType;
import com.cdd.recipeservice.ingredientmodule.market.dto.response.ClosestMarket;
import com.cdd.recipeservice.ingredientmodule.market.dto.response.MarketPricePerUserResponse;
import com.cdd.recipeservice.ingredientmodule.market.dto.response.OnlineMarket;
import com.cdd.recipeservice.ingredientmodule.market.utils.ClosestMarketUtils;
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
import com.cdd.recipeservice.ingredientmodule.weeklyprice.utils.IngredientSalesDailyPriceStatUtils;
import com.cdd.recipeservice.ingredientmodule.weeklyprice.utils.IngredientWeeklyPriceUtils;
import com.fasterxml.jackson.databind.ObjectMapper;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Transactional(readOnly = true)
@Service
public class MarketIngredientWeeklyPriceService {
	@Value("${weekly.price.weeks}")
	private int[] weeks;
	@Value("${weekly.price.unit}")
	private int unit;
	private final IngredientSalesDailyPriceStatRepository ingredientSalesDailyPriceStatRepository;
	private final IngredientRepository ingredientRepository;
	private final MarketRepository marketRepository;
	private final TargetPriceRepository targetPriceRepository;
	private final RedisTemplate<byte[], byte[]> redisTemplate;
	private final ObjectMapper objectMapper;

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

	public MarketPricePerUserResponse getIngredientPriceOfflineMarket(int memberId, int ingredientId, double lat,
		double lng) {
		List<ClosestMarket> closestMarkets = ClosestMarketUtils.getClosestMarketPriceList(
			marketRepository,
			ingredientId,
			lat,
			lng,
			2);
		int targetPrice = TargetPriceUtils.findByMemberIdAndIngredientId(targetPriceRepository, memberId, ingredientId,0);
		int todayMinimumPrice = IngredientSalesDailyPriceStatUtils.findMinPriceByIngredientIdAndMarket(
			ingredientSalesDailyPriceStatRepository,
			ingredientId, MarketType.OFFLINE);
		List<Map<Integer, List<WeeklyPrice>>> marketPriceList = new ArrayList<>();
		for (int i = 0; i < closestMarkets.size(); i++) {
			List<WeeklyPrice> weeklyPrices = marketRepository.findWeeklyPriceByIngredientIdAndMarketId(
				ingredientId,
				closestMarkets.get(i).getId());
			// List<ClosestMarket> closestMarketPrices = IngredientWeeklyPriceUtils.generateIngredientSalesPriceDummyData(
			// 	ClosestMarket::new, 28);
			Map<Integer, List<WeeklyPrice>> weeklyAvgPrices = IngredientWeeklyPriceUtils.generateWeeklyPrice(
				weeklyPrices,
				weeks,
				unit);
			marketPriceList.add(weeklyAvgPrices);
		}
		return MarketPricePerUserResponse.of(
			targetPrice,
			todayMinimumPrice,
			closestMarkets,
			marketPriceList);
	}

	private double calculatePercent(List<WeeklyPrice> data) {
		int todayPrice = data.get(data.size()-1).getPrice();
		int lastWeekPrice = data.get(data.size()-2).getPrice();
		double percent = (todayPrice - lastWeekPrice) / (double)lastWeekPrice * 100;
		return Math.round(percent * 100.0) / 100.0;
	}

	@Transactional
	public void saveWeeklyPrice(String type) {
		IngredientInxInfo ingredientInxInfo = IngredientUtils.getRangeIngredientId(ingredientRepository);
		int id = ingredientInxInfo.getFirstId();
		int endId = id + (int)ingredientInxInfo.getTotalCnt();
		for (; id <= endId; id++) {

			PriceSearchCond cond = new PriceSearchCond(id, type, 4);
			// List<IngredientSalesDailyPriceStat> ingredientSalesPriceList = IngredientWeeklyPriceUtils.generateIngredientSalesPriceDummyData(
			// 	IngredientSalesDailyPriceStat::new, 28);
			List<IngredientSalesDailyPriceStat> ingredientSalesPriceList = ingredientSalesDailyPriceStatRepository.findByIdTypeAndWeek(
				cond);
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
		List<OnlineMarket> onlineMarkets = marketRepository.findMinPriceByIngredientIdAndOnlineMarkets(ingredientId);
		int targetPrice = TargetPriceUtils.findByMemberIdAndIngredientId(targetPriceRepository, memberId, ingredientId,0);
		int todayMinimumPrice = IngredientSalesDailyPriceStatUtils.findMinPriceByIngredientIdAndMarket(
			ingredientSalesDailyPriceStatRepository,
			ingredientId, MarketType.ONLINE);

		List<Map<Integer, List<WeeklyPrice>>> marketPriceList = new ArrayList<>();

		for (int i = 0; i < onlineMarkets.size(); i++) {
			List<WeeklyPrice> weeklyPrices = marketRepository.findWeeklyPriceByIngredientIdAndMarketId(
				ingredientId,
				onlineMarkets.get(i).getId()
			);
			// List<ClosestMarket> closestMarketPrices = IngredientWeeklyPriceUtils.generateIngredientSalesPriceDummyData(
			// 	ClosestMarket::new, 28);
			Map<Integer, List<WeeklyPrice>> weeklyAvgPrices = IngredientWeeklyPriceUtils.generateWeeklyPrice(
				weeklyPrices,
				weeks,
				unit);
			marketPriceList.add(weeklyAvgPrices);
		}
		return MarketPricePerUserResponse.of(
			targetPrice,
			todayMinimumPrice,
			onlineMarkets,
			marketPriceList);
	}
}
