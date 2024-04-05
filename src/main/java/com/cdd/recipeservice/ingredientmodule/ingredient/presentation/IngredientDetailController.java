package com.cdd.recipeservice.ingredientmodule.ingredient.presentation;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.cdd.common.response.MessageBody;
import com.cdd.common.response.ResponseEntityFactory;
import com.cdd.recipeservice.ingredientmodule.ingredient.application.IngredientDetailService;
import com.cdd.recipeservice.ingredientmodule.ingredient.application.RecommendIngredientService;
import com.cdd.recipeservice.ingredientmodule.ingredient.dto.response.IngredientDetails;
import com.cdd.recipeservice.ingredientmodule.ingredient.dto.response.IngredientInfo;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@RequestMapping("/recipe-service")
@RestController
public class IngredientDetailController {
	private final IngredientDetailService ingredientDetailService;
	private final RecommendIngredientService recommendIngredientService;

	@GetMapping("/v1/ingredients/{id}")
	public ResponseEntity<MessageBody<IngredientDetails>> getIngredientDetails(@PathVariable(required = true) int id) {
		IngredientDetails ingredientDetails = ingredientDetailService.getIngredientDetails(id);
		return ResponseEntityFactory.ok("농수산물 상세 조회 성공",
			ingredientDetails);
	}

	@GetMapping("/v1/ingredients")
	public ResponseEntity<MessageBody<IngredientInfo>> getRecommendIngredients() {
		IngredientInfo recommendIngredients = recommendIngredientService.getRecommendIngredients();
		return ResponseEntityFactory.ok("추천 재료 조회 성공", recommendIngredients);
	}

	@PostMapping("/v1/ingredients")
	public ResponseEntity<MessageBody<Void>> updateIngredientDetails() {
		recommendIngredientService.saveRecommendIngredientList();
		return ResponseEntityFactory.created("추천 재료 저장 성공");
	}

	@PostMapping("/v1/ingredients/know-how")
	public ResponseEntity<MessageBody<Void>> updateIngredientKnowHow() {
		ingredientDetailService.updateIngredientDetails();
		return ResponseEntityFactory.created("재료 노하우 업데이트 성공");
	}
}
