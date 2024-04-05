package com.cdd.recipeservice.ingredientmodule.ingredient.application;

import java.util.List;
import java.util.Optional;
import java.util.concurrent.ThreadLocalRandom;
import java.util.stream.IntStream;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.cdd.recipeservice.global.utils.RedisUtils;
import com.cdd.recipeservice.ingredientmodule.ingredient.domain.IngredientRepository;
import com.cdd.recipeservice.ingredientmodule.ingredient.domain.RecommendIngredient;
import com.cdd.recipeservice.ingredientmodule.ingredient.dto.response.IngredientInfo;
import com.cdd.recipeservice.ingredientmodule.ingredient.dto.response.IngredientInxInfo;
import com.cdd.recipeservice.ingredientmodule.ingredient.utils.IngredientUtils;
import com.cdd.recipeservice.ingredientmodule.weeklyprice.domain.IngredientWeeklyPrice;
import com.cdd.recipeservice.ingredientmodule.weeklyprice.domain.WeeklyPrice;
import com.fasterxml.jackson.databind.ObjectMapper;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Transactional(readOnly = true)
@Service
public class RecommendIngredientServiceImpl implements RecommendIngredientService {
	@Value("${ingredient.max-size}")
	private int maxResults;
	@Value("${ingredient.key}")
	private String recommendIngredientKey;
	private final IngredientRepository ingredientRepository;
	private final RedisTemplate<byte[], byte[]> redisTemplate;
	private final ObjectMapper objectMapper;

	@Override
	public List<RecommendIngredient> selectRecommendIngredient(
		int maxResults,
		IngredientRepository repository) {
		IngredientInxInfo ingredientInxInfo = IngredientUtils.getRangeIngredientId(repository);
		return IntStream.generate(() -> ThreadLocalRandom.current()
				.nextInt(ingredientInxInfo.getFirstId(),
					(int)ingredientInxInfo.getTotalCnt()) + 1)
			.distinct()
			.mapToObj(repository::findById)
			.limit(maxResults)
			.flatMap(Optional::stream)
			.map(RecommendIngredient::from)
			.toList();
	}

	@Scheduled(cron = "0 0 0 * * *", zone = "Asia/Seoul")
	@Transactional
	public void saveRecommendIngredientList() {
		List<RecommendIngredient> recommendIngredients = selectRecommendIngredient(maxResults, ingredientRepository);
		for (int inx = 0; inx < maxResults; inx++) {
			List<WeeklyPrice> onlineWeeklyPrice = fetchWeeklyPrice(
				"online-",
				recommendIngredients.get(inx).getIngredients().getId()
			);

			List<WeeklyPrice> offlineWeeklyPrice = fetchWeeklyPrice(
				"offline-",
				recommendIngredients.get(inx).getIngredients().getId()
			);

			RecommendIngredient recommendIngredient = recommendIngredients.get(inx)
				.update(
					onlineWeeklyPrice,
					offlineWeeklyPrice,
					recommendIngredientKey + (inx + 1));

			RedisUtils.put(
				redisTemplate,
				objectMapper,
				recommendIngredient,
				recommendIngredient.getKey());
		}
	}

	public IngredientInfo getRecommendIngredients() {
		List<RecommendIngredient> recommendIngredient =
			IntStream.range(1, maxResults + 1)
				.mapToObj(inx -> RedisUtils.get(
					redisTemplate,
					objectMapper,
					RecommendIngredient.class,
					recommendIngredientKey + inx
				))
				.toList();

		return IngredientInfo.from(recommendIngredient);
	}

	private List<WeeklyPrice> fetchWeeklyPrice(String prefix, int ingredientId) {
		return RedisUtils.get(
			redisTemplate,
			objectMapper,
			IngredientWeeklyPrice.class,
			prefix,
			ingredientId
		).getData(4);
	}
}
