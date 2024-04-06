package com.cdd.recipeservice.ingredientmodule.weeklyprice.presentation;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.cdd.common.response.MessageBody;
import com.cdd.common.response.ResponseEntityFactory;
import com.cdd.recipeservice.ingredientmodule.weeklyprice.application.IngredientGapService;
import com.cdd.recipeservice.ingredientmodule.weeklyprice.dto.response.IngredientPriceGapResponse;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@RequiredArgsConstructor
@RequestMapping("/recipe-service")
@RestController
public class IngredientGapController {
	private final IngredientGapService ingredientGapService;

	@GetMapping("/v1/ingredients/rank/asc")
	public ResponseEntity<MessageBody<IngredientPriceGapResponse>> getAscGapList() {
		IngredientPriceGapResponse response = ingredientGapService.getAscGapList();
		return ResponseEntityFactory.ok("가격 변동 상승 정보 조회 성공", response);
	}

	@GetMapping("/v1/ingredients/rank/desc")
	public ResponseEntity<MessageBody<IngredientPriceGapResponse>> getDescGapList() {
		IngredientPriceGapResponse response = ingredientGapService.getDescGapList();
		return ResponseEntityFactory.ok("가격 변동 하락 정보 조회 성공", response);
	}

	@PostMapping("/v1/ingredients/rank")
	public ResponseEntity<MessageBody<Void>> saveIngredientPriceGap() {
		ingredientGapService.saveIngredientPriceGap();
		return ResponseEntityFactory.ok("가격 변동 정보 저장 성공");
	}
}
