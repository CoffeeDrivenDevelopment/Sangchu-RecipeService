package com.cdd.recipeservice.recipemodule.recipe.presentation;

import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.cdd.common.response.MessageBody;
import com.cdd.common.response.ResponseEntityFactory;
import com.cdd.recipeservice.recipemodule.recipe.application.RecipeLikeUpdateService;
import com.cdd.recipeservice.recipemodule.recipe.application.RecipeLikeLoadService;
import com.cdd.recipeservice.recipemodule.recipe.dto.response.RecipeLikeListResponse;
import com.cdd.recipeservice.recipemodule.recipe.dto.response.RecipeLikeResponse;
import com.cdd.sangchupassport.Passport;
import com.cdd.sangchupassport.support.RequestPassport;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@RequestMapping("/recipe-service")
@RestController
public class RecipeLikeController {

	private final RecipeLikeLoadService recipeLikeLoadService;
	private final RecipeLikeUpdateService recipeLikeUpdateService;

	@PostMapping("/v1/recipes/{recipeId}/likes")
	public ResponseEntity<MessageBody<RecipeLikeResponse>> like(
		@RequestPassport Passport passport,
		@PathVariable("recipeId") int recipeId) {
		RecipeLikeResponse response = recipeLikeUpdateService.recipeLike(passport.getMemberId(), recipeId);
		return ResponseEntityFactory.created("레시피 좋아요 등록에 성공하였습니다.", response);
	}

	@DeleteMapping("v1/recipes/{recipeId}/likes")
	public ResponseEntity<MessageBody<RecipeLikeResponse>> unlink(
		@RequestPassport Passport passport,
		@PathVariable("recipeId") int recipeId
	) {
		RecipeLikeResponse response = recipeLikeUpdateService.delete(passport.getMemberId(), recipeId);
		return ResponseEntityFactory.ok("레시피 좋아요 삭제에 성공하였습니다", response);
	}
	
	
	@GetMapping("/v1/likes/{member_id}")
	public ResponseEntity<MessageBody<RecipeLikeListResponse>> getLikeList(
		@PathVariable("member_id") int memberId, Pageable pageable) {
		return ResponseEntityFactory.ok("좋아요 리스트 조회 성공",
			recipeLikeLoadService.getLikeRecipeList(memberId, pageable));
	}
}
