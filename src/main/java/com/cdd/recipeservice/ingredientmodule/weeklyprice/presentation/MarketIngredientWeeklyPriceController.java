package com.cdd.recipeservice.ingredientmodule.weeklyprice.presentation;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.cdd.common.response.MessageBody;
import com.cdd.common.response.ResponseEntityFactory;
import com.cdd.recipeservice.ingredientmodule.market.dto.response.MarketPricePerUserResponse;
import com.cdd.recipeservice.ingredientmodule.weeklyprice.application.MarketIngredientWeeklyPriceService;
import com.cdd.recipeservice.ingredientmodule.weeklyprice.dto.response.IngredientWeeklyPriceResponse;
import com.cdd.sangchupassport.Passport;
import com.cdd.sangchupassport.support.RequestPassport;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@RequestMapping("/recipe-service")
@RestController
public class MarketIngredientWeeklyPriceController {
	private final MarketIngredientWeeklyPriceService marketIngredientWeeklyPriceService;

	@GetMapping("/v1/ingredients/{id}/prices/{type}")
	public ResponseEntity<MessageBody<IngredientWeeklyPriceResponse>> getMarketIngredientWeeklyPrice(
		@PathVariable("id") int id,
		@PathVariable("type") String type,
		@RequestParam(name = "week", required = false, defaultValue = "4") int week) {

		return ResponseEntityFactory.ok(type + " 가격 상세 조회 성공",
			marketIngredientWeeklyPriceService.getWeeklyPrice(id, type, week));
	}

	@GetMapping("/v1/ingredients/{id}/markets/prices/offline")
	public ResponseEntity<MessageBody<MarketPricePerUserResponse>> getIngredientPriceOfflineMarket(
		@RequestPassport Passport passport,
		@PathVariable("id") int ingredientId
	) {
		MarketPricePerUserResponse marketPricePerUserResponse = marketIngredientWeeklyPriceService.getIngredientPriceOfflineMarket(
			passport,
			ingredientId
		);
		return ResponseEntityFactory.ok("오프라인 가격 분석 조회 성공", marketPricePerUserResponse);
	}

	@GetMapping("/v1/ingredients/{id}/markets/prices/online")
	public ResponseEntity<MessageBody<MarketPricePerUserResponse>> getIngredientPriceOnlineMarket(
		@RequestPassport Passport passport,
		@PathVariable("id") int ingredientId) {
		MarketPricePerUserResponse marketPricePerUserResponse = marketIngredientWeeklyPriceService.getIngredientPriceOnlineMarket(
			passport.getMemberId(),
			ingredientId);
		return ResponseEntityFactory.ok("온라인 가격 분석 조회 성공", marketPricePerUserResponse);
	}

	@PostMapping("/v1/ingredients/prices/{type}")
	public ResponseEntity<MessageBody<Void>> saveWeeklyPrice(
		@PathVariable("type") String type
	) {
		marketIngredientWeeklyPriceService.saveWeeklyPrice(type);
		return ResponseEntityFactory.created(type + " 주간 가격 추이 정보 저장 성공");
	}
}
