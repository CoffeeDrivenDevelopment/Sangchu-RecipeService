package com.cdd.recipeservice.ingredientmodule.market.presentation;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.cdd.common.response.MessageBody;
import com.cdd.common.response.ResponseEntityFactory;
import com.cdd.recipeservice.ingredientmodule.market.application.MarketLowestPriceService;
import com.cdd.recipeservice.ingredientmodule.market.dto.response.ClosestMarket;
import com.cdd.recipeservice.ingredientmodule.market.dto.response.MarketLowestPriceListResponse;
import com.cdd.recipeservice.ingredientmodule.market.dto.response.OnlineMarket;
import com.cdd.sangchupassport.Passport;
import com.cdd.sangchupassport.support.RequestPassport;

import lombok.RequiredArgsConstructor;

@RequestMapping("/recipe-service")
@RequiredArgsConstructor
@RestController
public class MarketLowestPriceController {
	private final MarketLowestPriceService marketLowestPriceService;

	@GetMapping("/v1/ingredients/{id}/markets/offline/details")
	public ResponseEntity<MessageBody<MarketLowestPriceListResponse<ClosestMarket>>> getMarketLowestPrice(
		@RequestPassport Passport passport,
		@PathVariable("id") int ingredientId
	) {
		return ResponseEntityFactory.ok(
			"오프라인 최저가 조회 성공",
			marketLowestPriceService.getOfflineMarketLowestPrice(passport, ingredientId));
	}

	@GetMapping("/v1/ingredients/{id}/markets/online/details")
	public ResponseEntity<MessageBody<MarketLowestPriceListResponse<OnlineMarket>>> getOnlineMarketBestPrice(
		@PathVariable(name = "id") int ingredientId) {
		return ResponseEntityFactory.ok("온라인 최저가 조회 성공",
			marketLowestPriceService.getOnlineMarketLowestPrice(ingredientId));
	}
}
