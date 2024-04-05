package com.cdd.recipeservice.ingredientmodule.weeklyprice.presentation;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.cdd.common.response.MessageBody;
import com.cdd.common.response.ResponseEntityFactory;
import com.cdd.recipeservice.ingredientmodule.market.dto.response.MarketIngredientBestPriceResponse;
import com.cdd.recipeservice.ingredientmodule.weeklyprice.application.IngredientBestPriceService;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@RequestMapping("/recipe-service")
@RestController
public class IngredientBestPriceController {
	private final IngredientBestPriceService ingredientBestPriceService;

	@GetMapping("/v1/ingredients/{id}/markets/online/details")
	public ResponseEntity<MessageBody<MarketIngredientBestPriceResponse>> getOnlineMarketBestPrice(
		@PathVariable(name = "id") int ingredientId) {
		return ResponseEntityFactory.ok("온라인 최저가 조회 성공",
			ingredientBestPriceService.getOnlineMarketBestPrice(ingredientId));
	}
}
