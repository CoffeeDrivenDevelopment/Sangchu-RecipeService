package com.cdd.recipeservice.ingredientmodule.ingredient.presentation;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.cdd.common.response.MessageBody;
import com.cdd.common.response.ResponseEntityFactory;
import com.cdd.recipeservice.ingredientmodule.ingredient.application.IngredientService;
import com.cdd.recipeservice.ingredientmodule.ingredient.domain.IngredientType;
import com.cdd.recipeservice.ingredientmodule.ingredient.dto.response.IngredientCategoryResponse;
import com.cdd.recipeservice.ingredientmodule.ingredient.dto.response.IngredientPopularResponse;
import com.cdd.recipeservice.ingredientmodule.ingredient.dto.response.IngredientSearchResponse;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@RequestMapping("/recipe-service")
@RestController
public class IngredientController {
	private final IngredientService ingredientService;

	@GetMapping("/v1/ingredients/categories")
	public ResponseEntity<MessageBody<IngredientCategoryResponse>> getCategory() {
		return ResponseEntityFactory.ok("재료 카테고리 조회 성공", ingredientService.findAllCategory());
	}

	@GetMapping("/v1/ingredients/search")
	public ResponseEntity<MessageBody<IngredientSearchResponse>> searchIngredient(
		@RequestParam(name = "category", required = false) IngredientType category,
		@RequestParam(name = "ingredient", required = false) String ingredientName
	) {
		return ResponseEntityFactory.ok("카테고리 및 키워드 재료 검색 성공",
			ingredientService.findIngredient(category, (ingredientName == null ? "" : ingredientName)));
	}

	@GetMapping("/v1/ingredients/popular")
	public ResponseEntity<MessageBody<IngredientPopularResponse>> getPopularIngredient() {
		return ResponseEntityFactory.ok("실시간 인기재료 조회 성공", ingredientService.findPopularIngredient());
	}

	
}