package com.cdd.recipeservice.recipemodule.review.presentataion;

import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import com.cdd.common.response.MessageBody;
import com.cdd.common.response.ResponseEntityFactory;
import com.cdd.recipeservice.recipemodule.comment.dto.cond.PagingCond;
import com.cdd.recipeservice.recipemodule.review.application.CookEatLoadService;
import com.cdd.recipeservice.recipemodule.review.application.CookEatModifyService;
import com.cdd.recipeservice.recipemodule.review.dto.request.CookEatSaveRequest;
import com.cdd.recipeservice.recipemodule.review.dto.response.CookEatMemberResponse;
import com.cdd.recipeservice.recipemodule.review.dto.response.CookEatSaveResponse;
import com.cdd.recipeservice.recipemodule.review.dto.response.FindCookEatResponse;
import com.cdd.recipeservice.recipemodule.review.dto.response.FindCookEatsResponse;
import com.cdd.sangchupassport.Passport;
import com.cdd.sangchupassport.support.RequestPassport;

import lombok.RequiredArgsConstructor;

@RequestMapping("/recipe-service")
@RequiredArgsConstructor
@RestController
public class CookEatController {
	private final CookEatModifyService cookEatModifyService;
	private final CookEatLoadService cookEatLoadService;

	@PostMapping("/v1/recipes/{recipe_id}/reviews")
	public ResponseEntity<MessageBody<CookEatSaveResponse>> saveReview(
		@RequestPassport Passport passport,
		@PathVariable("recipe_id") int recipeId,
		@RequestPart("files") List<MultipartFile> files,
		@RequestPart("body") CookEatSaveRequest request
	) {
		CookEatSaveResponse response = cookEatModifyService.saveReview(passport, recipeId, files, request);
		return ResponseEntityFactory.created("레시피 리뷰를 작성하였습니다.", response);
	}

	@GetMapping("/v1/recipes/{recipe_id}/reviews")
	public ResponseEntity<MessageBody<FindCookEatsResponse>> findRecipes(
		@RequestPassport Passport passport,
		@PathVariable("recipe_id") int recipeId,
		@RequestParam("last") int lastId,
		@RequestParam("size") int size
	) {
		FindCookEatsResponse response = cookEatLoadService
			.findCookEats(passport, recipeId, new PagingCond(lastId, size));
		return ResponseEntityFactory.ok("리뷰 목록 조회에 성공하였습니다.", response);
	}

	@GetMapping("/v1/reviews/{review_id}")
	public ResponseEntity<MessageBody<FindCookEatResponse>> findRecipe(
		@RequestPassport Passport passport,
		@PathVariable("review_id") long reviewId
	) {
		FindCookEatResponse response = cookEatLoadService.findCookEat(passport, reviewId);
		return ResponseEntityFactory.ok("리뷰 조회에 성공하였습니다.", response);
	}

	@GetMapping("/v1/reviews/members/{member_id}")
	public ResponseEntity<MessageBody<CookEatMemberResponse>> loadMemberReview(
		@PathVariable("member_id") Integer memberId) {
		return ResponseEntityFactory.ok("멤버 리뷰 조회 성공", cookEatLoadService.loadMemberReview(memberId));
	}

	@DeleteMapping("/v1/reviews/{review_id}")
	public ResponseEntity<MessageBody<Void>> removeCookEat(
		@RequestPassport Passport passport,
		@PathVariable("review_id") long reviewId
	) {
		cookEatModifyService.removeReview(passport, reviewId);
		return ResponseEntityFactory.ok("리뷰를 삭제했습니다.");
	}
}
