package com.cdd.recipeservice.ingredientmodule.targetprice.application;

import java.util.ArrayList;
import java.util.List;

import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.cdd.recipeservice.global.utils.RedisUtils;
import com.cdd.recipeservice.ingredientmodule.ingredient.domain.Ingredient;
import com.cdd.recipeservice.ingredientmodule.ingredient.domain.IngredientRepository;
import com.cdd.recipeservice.ingredientmodule.ingredient.utils.IngredientUtils;
import com.cdd.recipeservice.ingredientmodule.market.domain.MarketType;
import com.cdd.recipeservice.ingredientmodule.targetprice.domain.TargetPrice;
import com.cdd.recipeservice.ingredientmodule.targetprice.domain.TargetPriceInfoList;
import com.cdd.recipeservice.ingredientmodule.targetprice.domain.TargetPriceRepository;
import com.cdd.recipeservice.ingredientmodule.targetprice.dto.response.TargetPriceInfo;
import com.cdd.recipeservice.ingredientmodule.targetprice.dto.response.TargetPriceInfoResponse;
import com.cdd.recipeservice.ingredientmodule.targetprice.dto.response.TargetPriceItemListResponse;
import com.cdd.recipeservice.ingredientmodule.targetprice.dto.response.TargetPriceItemResponse;
import com.cdd.recipeservice.ingredientmodule.targetprice.utils.TargetPriceUtils;
import com.cdd.recipeservice.ingredientmodule.weeklyprice.domain.IngredientSalesDailyPriceStatRepository;
import com.fasterxml.jackson.databind.ObjectMapper;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Transactional(readOnly = true)
@Service
public class TargetPriceService
	implements TargetPriceSaveService, TargetPriceInfoService, TargetPriceLoadService {

	private static final String KEY = "target-price-";
	private final IngredientRepository ingredientRepository;
	private final TargetPriceRepository targetPriceRepository;
	private final IngredientSalesDailyPriceStatRepository ingredientSalesDailyPriceStatRepository;
	private final RedisTemplate<byte[], byte[]> redisTemplate;
	private final ObjectMapper objectMapper;

	@Transactional
	@Override
	public void pickTargetPrice(
		final int memberId,
		final int ingredientId,
		final int targetPrice) {
		targetPriceRepository.findByMemberIdAndIngredientId(memberId, ingredientId)
			.ifPresentOrElse(
				targetPriceEntity -> targetPriceEntity.updateTargetPrice(targetPrice),
				() -> saveTargetPrice(memberId, ingredientId, targetPrice)
			);
	}

	@Override
	@Scheduled(cron = "0 0 0 * * *", zone = "Asia/Seoul")
	@Transactional
	public void updateTargetPriceList() {
		List<Integer> ingredientIdList = ingredientRepository.findAllOnlyId();
		ingredientIdList.forEach(
			ingredientId -> {
				List<TargetPriceInfo> targetPriceList = targetPriceRepository.countPriceByIngredientId(ingredientId);

				makeTargetPriceList(targetPriceList, ingredientId);

				TargetPriceInfoList targetPriceInfoList = TargetPriceInfoList.of(
					KEY + ingredientId,
					targetPriceList);

				RedisUtils.put(
					redisTemplate,
					objectMapper,
					targetPriceInfoList,
					KEY,
					ingredientId
				);
			});
	}

	private void saveTargetPrice(
		final int memberId,
		final int ingredientId,
		final int targetPrice) {
		Ingredient findIngredient = IngredientUtils.findById(ingredientRepository, ingredientId);
		TargetPrice newTargetPrice = TargetPrice.of(memberId, findIngredient, targetPrice);
		targetPriceRepository.save(newTargetPrice);
	}

	@Override
	public TargetPriceInfoResponse getTargetPriceInfoList(
		final int memberId,
		final int ingredientId) {
		int usersTargetPrice = getUsersTargetPrice(memberId, ingredientId);
		TargetPriceInfoList targetPriceInfoList = RedisUtils.get(
			redisTemplate,
			objectMapper,
			TargetPriceInfoList.class,
			KEY, ingredientId
		);
		return TargetPriceInfoResponse.of(
			targetPriceInfoList.getTargetPriceInfos(),
			usersTargetPrice);
	}

	private void makeTargetPriceList(List<TargetPriceInfo> targetPriceList, int ingredientId) {
		if (targetPriceList.isEmpty()) {
			fillTargetPriceList(targetPriceList, ingredientId);
		} else {
			updateTargetPriceList(targetPriceList);
		}
	}

	private void fillTargetPriceList(List<TargetPriceInfo> targetPriceList, int ingredientId) {
		int todayAvgPrice = ingredientSalesDailyPriceStatRepository.findAvgPriceByIngredientId(ingredientId).orElse(0);
		int unit = todayAvgPrice / 20;
		for (int i = 0; i < 10; i++) {
			targetPriceList.add(new TargetPriceInfo(todayAvgPrice, 0L));
			todayAvgPrice -= unit;
		}
	}

	private void updateTargetPriceList(List<TargetPriceInfo> targetPriceList) {
		int size = targetPriceList.size();
		if (size >= 10)
			return;

		int firstPrice = targetPriceList.get(0).getPrice();
		int lastPrice = targetPriceList.get(size - 1).getPrice();
		int unit = (firstPrice - lastPrice) / (10 - size);
		if (unit == 0) {
			unit = firstPrice / 20;
		}

		for (int i = 1; i <= 10 - size; i++) {
			int priceToAdd = firstPrice - unit * i;
			targetPriceList.add(i, new TargetPriceInfo(priceToAdd, 0L));
		}
	}

	private int getUsersTargetPrice(
		final int memberId,
		final int ingredientId) {
		return TargetPriceUtils.findByMemberIdAndIngredientId(
			targetPriceRepository,
			memberId,
			ingredientId,
			-1);
	}

	@Override
	public TargetPriceItemListResponse getMyTargetPrice(int memberId) {
		// 리스트 불러오기
		List<TargetPrice> targetPriceList = targetPriceRepository.findByMemberId(memberId);
		// 각 리스트별 금액가불러오기
		List<TargetPriceItemResponse> targetPriceItemList = new ArrayList<>();

		for (TargetPrice targetPrice : targetPriceList) {
			List<Integer> onlinePriceList = ingredientSalesDailyPriceStatRepository.findMarketPriceList(
				targetPrice.getIngredient().getId(),
				MarketType.ONLINE);
			List<Integer> offlinePriceList = ingredientSalesDailyPriceStatRepository.findMarketPriceList(
				targetPrice.getIngredient().getId(),
				MarketType.OFFLINE);
			targetPriceItemList.add(TargetPriceItemResponse.builder()
				.ingredientId(targetPrice.getIngredient().getId())
				.ingredientName(targetPrice.getIngredient().getName())
				.tartgetPrice(targetPrice.getPrice())
				.onlinePrice(onlinePriceList)
				.offlinePrice(offlinePriceList)
				.currentOnlinePrice(onlinePriceList.get(onlinePriceList.size() - 1))
				.currentOfflinePrice(offlinePriceList.get(offlinePriceList.size() - 1))
				.build());
		}
		return TargetPriceItemListResponse.from(targetPriceItemList);
	}
}
