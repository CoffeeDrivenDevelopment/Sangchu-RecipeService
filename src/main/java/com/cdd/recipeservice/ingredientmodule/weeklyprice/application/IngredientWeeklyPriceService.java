package com.cdd.recipeservice.ingredientmodule.weeklyprice.application;

import java.util.List;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.cdd.recipeservice.global.utils.RedisUtils;
import com.cdd.recipeservice.ingredientmodule.ingredient.domain.Ingredient;
import com.cdd.recipeservice.ingredientmodule.ingredient.domain.IngredientRepository;
import com.cdd.recipeservice.ingredientmodule.ingredient.dto.response.IngredientInxInfo;
import com.cdd.recipeservice.ingredientmodule.ingredient.utils.IngredientUtils;
import com.cdd.recipeservice.ingredientmodule.targetprice.domain.TargetPriceInfoList;
import com.cdd.recipeservice.ingredientmodule.targetprice.domain.TargetPriceRepository;
import com.cdd.recipeservice.ingredientmodule.targetprice.dto.response.TargetPriceInfo;
import com.cdd.recipeservice.ingredientmodule.targetprice.utils.TargetPriceUtils;
import com.cdd.recipeservice.ingredientmodule.weeklyprice.domain.IngredientSalesDailyPriceStatRepository;
import com.cdd.recipeservice.ingredientmodule.weeklyprice.dto.response.IngredientCauseResponse;
import com.cdd.recipeservice.ingredientmodule.weeklyprice.dto.response.IngredientReasonableResponse;
import com.fasterxml.jackson.databind.ObjectMapper;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Transactional(readOnly = true)
@Service
public class IngredientWeeklyPriceService {
	@Value("${ingredient.weekly-price.key}")
	private String key;
	private static final String TARGET_PRICE_KEY = "target-price-";
	private final IngredientSalesDailyPriceStatRepository ingredientSalesDailyPriceStatRepository;
	private final IngredientRepository ingredientRepository;
	private final TargetPriceRepository targetPriceRepository;
	private final RedisTemplate<byte[], byte[]> redisTemplate;
	private final ObjectMapper objectMapper;

	public IngredientCauseResponse getIngredientCause(int ingredientId) {
		return RedisUtils.get(
			redisTemplate,
			objectMapper,
			IngredientCauseResponse.class,
			key, ingredientId
		);

	}

	public IngredientReasonableResponse getIngredientReasonable(int memberId, int ingredientId) {
		int targetPrice = TargetPriceUtils.findByMemberIdAndIngredientId(targetPriceRepository, memberId,
			ingredientId,
			0);
		TargetPriceInfoList targetPriceInfoList = RedisUtils.get(
			redisTemplate,
			objectMapper,
			TargetPriceInfoList.class,
			TARGET_PRICE_KEY, ingredientId
		);
		TargetPriceInfo popularTargetPrice = targetPriceInfoList.loadMaximum();
		Ingredient ingredient = IngredientUtils.findById(ingredientRepository, ingredientId);
		return IngredientReasonableResponse.of(ingredient, popularTargetPrice.getPrice(), targetPrice);
	}

	@Scheduled(cron = "0 30 0 * * *", zone = "Asia/Seoul")
	@Transactional
	public void saveIngredientCause() {
		IngredientInxInfo ingredientInxInfo = IngredientUtils.getRangeIngredientId(ingredientRepository);
		int id = ingredientInxInfo.getFirstId();
		int endId = id + (int)ingredientInxInfo.getTotalCnt();
		for (; id < endId; id++) {
			List<Integer> pricesBetweenTodayAnd7DaysAGo = ingredientSalesDailyPriceStatRepository
				.findPricesBetweenTodayAnd7DaysAGo(id);

			String img = IngredientUtils.findById(ingredientRepository, id).getImg();
			IngredientCauseResponse ingredientCauseResponse = predictPriceTransition(img,
				pricesBetweenTodayAnd7DaysAGo);
			RedisUtils.put(
				redisTemplate,
				objectMapper,
				ingredientCauseResponse,
				key, id
			);
		}
	}

	private IngredientCauseResponse predictPriceTransition(
		String img,
		List<Integer> pricesBetweenTodayAnd7DaysAGo) {
		if (pricesBetweenTodayAnd7DaysAGo.size() < 2) {
			return IngredientCauseResponse.of(img, "유지", 1);
		}
		int dayAsc = 0;
		int dayDesc = 0;
		int dayStable = 0;
		int beforePrice = pricesBetweenTodayAnd7DaysAGo.get(0);
		for (int i = 1; i < pricesBetweenTodayAnd7DaysAGo.size(); i++) {
			int price = pricesBetweenTodayAnd7DaysAGo.get(i);
			if (price > beforePrice) {
				dayAsc++;
				dayDesc = 0;
				dayStable = 0;
			} else if (price < beforePrice) {
				dayDesc++;
				dayAsc = 0;
				dayStable = 0;
			} else {
				dayStable++;
				dayAsc = 0;
				dayDesc = 0;
			}
			beforePrice = price;
		}
		String type = getTransitionType(dayAsc, dayDesc);
		return IngredientCauseResponse.of(img, type, Math.max(dayAsc, Math.max(dayDesc, dayStable)));
	}

	private String getTransitionType(int dayAsc, int dayDesc) {
		if (dayAsc != 0) {
			return "상승";
		} else if (dayDesc != 0) {
			return "하락";
		} else {
			return "유지";
		}
	}
}
