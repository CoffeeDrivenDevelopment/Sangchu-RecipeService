package com.cdd.recipeservice.recipemodule.recipe.presentation;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.cdd.common.response.MessageBody;
import com.cdd.common.response.ResponseEntityFactory;
import com.cdd.recipeservice.recipemodule.comment.dto.cond.PagingCond;
import com.cdd.recipeservice.recipemodule.recipe.application.RecipeInfoService;
import com.cdd.recipeservice.recipemodule.recipe.application.RecipeService;
import com.cdd.recipeservice.recipemodule.recipe.domain.RecipeRecommend;
import com.cdd.recipeservice.recipemodule.recipe.dto.response.RecipeInfoResponse;
import com.cdd.recipeservice.recipemodule.recipe.dto.response.RecipeMoviesResponse;
import com.cdd.recipeservice.recipemodule.recipe.dto.response.RecipeSearchResponse;
import com.cdd.sangchupassport.Passport;
import com.cdd.sangchupassport.support.RequestPassport;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@RequestMapping("/recipe-service")
@RestController
public class RecipeController {
	private final RecipeService recipeService;
	private final RecipeInfoService recipeInfoService;

	@GetMapping("/v1/recipes")
	public ResponseEntity<MessageBody<RecipeRecommend>> getRecommendRecipes() {
		return ResponseEntityFactory.ok("추천 메뉴 조회 성공",
			recipeService.getRecommendRecipe());
	}

	@PostMapping("/v1/recipes")
	public ResponseEntity<MessageBody<Void>> saveRecommendRecipes() {
		recipeService.saveRecommendRecipeList();
		return ResponseEntityFactory.ok("추천 메뉴 저장 성공");
	}

	@GetMapping("/v1/recipes/{recipe_id}")
	public ResponseEntity<MessageBody<RecipeInfoResponse>> getRecipeInfo(@PathVariable("recipe_id") int recipeId) {
		RecipeInfoResponse response = recipeInfoService.findRecipeInfo(recipeId);
		return ResponseEntityFactory.ok("레시피 조회에 성공하였습니다", response);
	}

	@GetMapping("/v1/recipes/movies")
	public ResponseEntity<MessageBody<RecipeMoviesResponse>> getRecipeMovies(@RequestParam("name") String recipeName) {
		RecipeMoviesResponse response = recipeInfoService.findRecipeMovies(recipeName);
		return ResponseEntityFactory.ok("레시피 영상 조회에 성공하였습니다.", response);
	}

	@GetMapping("/v1/recipes/search")
	public ResponseEntity<MessageBody<RecipeSearchResponse>> getRecipes(
		@RequestPassport Passport passport,
		@RequestParam("q") String query,
		@RequestParam("last") int lastId,
		@RequestParam("size") int size,
		@RequestParam("type") String type
	) {
		RecipeSearchResponse response =
			recipeInfoService.findRecipes(passport, type, query, new PagingCond(lastId, size));
		return ResponseEntityFactory.ok("레시피 조회에 성공했습니다.", response);
	}
}
