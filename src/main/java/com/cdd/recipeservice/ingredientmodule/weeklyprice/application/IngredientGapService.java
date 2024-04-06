package com.cdd.recipeservice.ingredientmodule.weeklyprice.application;

import java.time.LocalDate;
import java.util.List;
import java.util.stream.IntStream;

import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import com.cdd.recipeservice.global.utils.LocalDateTimeUtils;
import com.cdd.recipeservice.global.utils.RedisUtils;
import com.cdd.recipeservice.ingredientmodule.ingredient.domain.IngredientRepository;
import com.cdd.recipeservice.ingredientmodule.weeklyprice.domain.IngredientPriceGapList;
import com.cdd.recipeservice.ingredientmodule.weeklyprice.domain.IngredientSalesDailyPriceStat;
import com.cdd.recipeservice.ingredientmodule.weeklyprice.domain.IngredientSalesDailyPriceStatRepository;
import com.cdd.recipeservice.ingredientmodule.weeklyprice.dto.response.IngredientPriceGap;
import com.cdd.recipeservice.ingredientmodule.weeklyprice.dto.response.IngredientPriceGapResponse;
import com.fasterxml.jackson.databind.ObjectMapper;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@RequiredArgsConstructor
@Service
public class IngredientGapService {
	private static final String KEY = "ingredient-price-gap-";
	private final IngredientRepository ingredientRepository;
	private final IngredientSalesDailyPriceStatRepository ingredientSalesDailyPriceStatRepository;
	private final RedisTemplate<byte[], byte[]> redisTemplate;
	private final ObjectMapper objectMapper;

	public IngredientPriceGapResponse getAscGapList() {
		IngredientPriceGapList ingredientPriceGapList = RedisUtils.get(
			redisTemplate,
			objectMapper,
			IngredientPriceGapList.class,
			KEY, "asc"
		);
		return IngredientPriceGapResponse.from(ingredientPriceGapList);
	}

	public IngredientPriceGapResponse getDescGapList() {
		IngredientPriceGapList ingredientPriceGapList = RedisUtils.get(
			redisTemplate,
			objectMapper,
			IngredientPriceGapList.class,
			KEY, "desc"
		);
		return IngredientPriceGapResponse.from(ingredientPriceGapList);
	}

	@Scheduled(cron = "0 0 0 * * *")
	public void saveIngredientPriceGap() {
		LocalDate today = LocalDateTimeUtils.today("Asia/Seoul").toLocalDate();
		List<IngredientSalesDailyPriceStat> todayList =
			ingredientSalesDailyPriceStatRepository.findIngredientDailyPrice(today);
		List<IngredientSalesDailyPriceStat> yesterdayList =
			ingredientSalesDailyPriceStatRepository.findIngredientDailyPrice(
				today.minusDays(1));

		IngredientPriceGapList asc = new IngredientPriceGapList(KEY + "asc");
		IngredientPriceGapList desc = new IngredientPriceGapList(KEY + "desc");
		calculatePriceGap(todayList, yesterdayList, asc, desc);

		RedisUtils.put(
			redisTemplate,
			objectMapper,
			asc.sortAndLimit(10, 1),
			KEY + "asc");
		RedisUtils.put(
			redisTemplate,
			objectMapper,
			desc.sortAndLimit(10, -1),
			KEY + "desc");
	}

	private void calculatePriceGap(
		List<IngredientSalesDailyPriceStat> todayList,
		List<IngredientSalesDailyPriceStat> yesterdayList,
		IngredientPriceGapList asc,
		IngredientPriceGapList desc
	) {
		IntStream.range(0, Math.min(todayList.size(), yesterdayList.size()))
			.forEach(i -> {
					double percent = calculatePercent(
						todayList.get(i).getAvgPrice(),
						yesterdayList.get(i).getAvgPrice()
					);
					IngredientPriceGap ingredientPriceGap = IngredientPriceGap.of(
						todayList.get(i).getIngredient(),
						percent
					);

					if (ingredientPriceGap.getPercent() > 0) {
						asc.addGap(ingredientPriceGap);
					} else if (ingredientPriceGap.getPercent() < 0) {
						desc.addGap(ingredientPriceGap);
					}
				}
			);
	}

	private double calculatePercent(int todayAvgPrice, int yesterdayAvgPrice) {
		if (yesterdayAvgPrice != 0) {
			double result = ((double)(todayAvgPrice - yesterdayAvgPrice) / yesterdayAvgPrice) * 100.0;
			return Math.round(result * 100.0) / 100.0;
		}
		return 0.0D;
	}
}
