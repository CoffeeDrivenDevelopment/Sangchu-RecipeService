package com.cdd.recipeservice.ingredientmodule.market.presentation;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.cdd.common.response.MessageBody;
import com.cdd.common.response.ResponseEntityFactory;
import com.cdd.recipeservice.ingredientmodule.market.application.MarketService;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@RequestMapping("/recipe-service")
@RestController
public class MarketController {

	private final MarketService marketService;

	@PostMapping("/v1/markets")
	public ResponseEntity<MessageBody<Void>> getMarketInfo() {
		marketService.getMarketInfo();
		return ResponseEntityFactory.created("마트 정보 조회 성공");
	}
}
