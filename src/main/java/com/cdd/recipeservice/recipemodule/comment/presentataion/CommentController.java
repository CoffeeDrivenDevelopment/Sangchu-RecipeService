package com.cdd.recipeservice.recipemodule.comment.presentataion;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.cdd.common.response.MessageBody;
import com.cdd.common.response.ResponseEntityFactory;
import com.cdd.recipeservice.recipemodule.comment.application.RecipeCommentLoadService;
import com.cdd.recipeservice.recipemodule.comment.application.RecipeCommentModifyService;
import com.cdd.recipeservice.recipemodule.comment.dto.cond.PagingCond;
import com.cdd.recipeservice.recipemodule.comment.dto.request.RecipeCommentSaveRequest;
import com.cdd.recipeservice.recipemodule.comment.dto.request.RecipeCommentUpdateRequest;
import com.cdd.recipeservice.recipemodule.comment.dto.response.*;
import com.cdd.sangchupassport.Passport;
import com.cdd.sangchupassport.support.RequestPassport;

import lombok.RequiredArgsConstructor;

@RequestMapping("/recipe-service")
@RequiredArgsConstructor
@RestController
public class CommentController {
	private final RecipeCommentModifyService recipeCommentModifyService;
	private final RecipeCommentLoadService recipeCommentLoadService;

	@PostMapping("/v1/comments/recipes")
	public ResponseEntity<MessageBody<RecipeCommentResponse>> commentSave(
		@RequestPassport Passport passport,
		@RequestBody RecipeCommentSaveRequest request
	) {
		RecipeCommentResponse response = recipeCommentModifyService.commentSave(passport, request);
		return ResponseEntityFactory.created("댓글 작성에 성공하였습니다.", response);
	}

	@PutMapping("/v1/comments/{comment_id}")
	public ResponseEntity<MessageBody<RecipeCommentUpdateResponse>> commentUpdate(
		@RequestPassport Passport passport,
		@PathVariable("comment_id") long commentId,
		@RequestBody RecipeCommentUpdateRequest request
	) {
		RecipeCommentUpdateResponse response = recipeCommentModifyService
			.commentUpdate(passport.getMemberId(), commentId, request);
		return ResponseEntityFactory.ok("댓글 수정에 성공하였습니다.", response);
	}

	@DeleteMapping("/v1/comments/{comment_id}")
	public ResponseEntity<MessageBody<RecipeCommentRemoveResponse>> commentRemove(
		@RequestPassport Passport passport,
		@PathVariable("comment_id") long commentId
	) {
		RecipeCommentRemoveResponse response = recipeCommentModifyService
			.commentRemove(passport.getMemberId(), commentId);
		return ResponseEntityFactory.ok("댓글 삭제에 성공하였습니다.", response);
	}

	@GetMapping("/v1/comments/recipes/{recipe_id}")
	public ResponseEntity<MessageBody<FindRecipeCommentResponse>> findRecipeComment(
		@RequestPassport Passport passport,
		@PathVariable("recipe_id") int recipeId,
		@RequestParam("last") int lastId,
		@RequestParam("size") int size
	) {
		FindRecipeCommentResponse response = recipeCommentLoadService
			.findRecipeComment(passport, new PagingCond(lastId, size), recipeId);
		return ResponseEntityFactory.ok("댓글 조회에 성공하였습니다.", response);
	}

	@GetMapping("/v1/comments/{comment_id}/reply")
	public ResponseEntity<MessageBody<FindRecipeReplyCommentResponse>> findRecipeReplyComment(
		@RequestPassport Passport passport,
		@PathVariable("comment_id") long commentId,
		@RequestParam("last") int lastId,
		@RequestParam("size") int size
	) {
		FindRecipeReplyCommentResponse response = recipeCommentLoadService
			.findRecipeReplyComment(passport, new PagingCond(lastId, size), commentId);
		return ResponseEntityFactory.ok("댓글 조회에 성공하였습니다.", response);
	}
}
