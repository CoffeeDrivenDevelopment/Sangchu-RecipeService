package com.cdd.recipeservice.ingredientmodule.weeklyprice.presentation;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.cdd.common.response.MessageBody;
import com.cdd.common.response.ResponseEntityFactory;
import com.cdd.recipeservice.ingredientmodule.weeklyprice.application.IngredientWeeklyPriceService;
import com.cdd.recipeservice.ingredientmodule.weeklyprice.dto.response.IngredientCauseResponse;
import com.cdd.recipeservice.ingredientmodule.weeklyprice.dto.response.IngredientReasonableResponse;
import com.cdd.sangchupassport.Passport;
import com.cdd.sangchupassport.support.RequestPassport;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@RequestMapping("/recipe-service")
@RestController
public class IngredientWeeklyPriceController {
	private final IngredientWeeklyPriceService ingredientWeeklyPriceService;

	@GetMapping("/v1/ingredients/{id}/cause")
	public ResponseEntity<MessageBody<IngredientCauseResponse>> getIngredientCause(
		@PathVariable(value = "id") int id) {
		IngredientCauseResponse ingredientCause = ingredientWeeklyPriceService.getIngredientCause(id);
		return ResponseEntityFactory.ok("가격 변동 정보 조회 성공", ingredientCause);
	}

	@GetMapping("/v1/ingredients/{id}/prices/reasonable")
	public ResponseEntity<MessageBody<IngredientReasonableResponse>> getIngredientReasonable(
		@RequestPassport Passport passport,
		@PathVariable(value = "id") int id) {
		IngredientReasonableResponse ingredientReasonable = ingredientWeeklyPriceService.getIngredientReasonable(
			passport.getMemberId(),
			id);
		return ResponseEntityFactory.ok("적정 소비 금액 조회 성공", ingredientReasonable);
	}

	@PostMapping("/v1/ingredients/cause")
	public ResponseEntity<MessageBody<Void>> saveIngredientCause() {
		ingredientWeeklyPriceService.saveIngredientCause();
		return ResponseEntityFactory.ok("가격 변동 정보 저장 성공");
	}
}
