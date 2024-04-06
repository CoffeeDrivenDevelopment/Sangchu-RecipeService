package com.cdd.recipeservice.ingredientmodule.market.presentation;

import org.springframework.http.*;
import org.springframework.web.bind.annotation.*;

import com.cdd.common.response.*;
import com.cdd.recipeservice.ingredientmodule.market.application.*;
import com.cdd.recipeservice.ingredientmodule.market.dto.response.*;

import lombok.*;

@RequestMapping("/recipe-service")
@RequiredArgsConstructor
@RestController
public class MarketLowestPriceController {
	private final MarketLowestPriceService marketLowestPriceService;

	@GetMapping("/v1/ingredients/{id}/markets/offline/details")
	public ResponseEntity<MessageBody<MarketLowestPriceListResponse<ClosestMarket>>> getMarketLowestPrice(
		@PathVariable("id") int ingredientId,
		@RequestParam(name = "lat", required = true) double lat,
		@RequestParam(name = "lng", required = true) double lng) {
		return ResponseEntityFactory.ok(
			"오프라인 최저가 조회 성공",
			marketLowestPriceService.getOfflineMarketLowestPrice(ingredientId, lat, lng));
	}

	@GetMapping("/v1/ingredients/{id}/markets/online/details")
	public ResponseEntity<MessageBody<MarketLowestPriceListResponse<OnlineMarket>>> getOnlineMarketBestPrice(
		@PathVariable(name = "id") int ingredientId) {
		return ResponseEntityFactory.ok("온라인 최저가 조회 성공",
			marketLowestPriceService.getOnlineMarketLowestPrice(ingredientId));
	}
}
