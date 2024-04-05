package com.cdd.recipeservice.recipemodule.comment.application;

import com.cdd.recipeservice.recipemodule.comment.dto.request.RecipeCommentSaveRequest;
import com.cdd.recipeservice.recipemodule.comment.dto.request.RecipeCommentUpdateRequest;
import com.cdd.recipeservice.recipemodule.comment.dto.response.RecipeCommentRemoveResponse;
import com.cdd.recipeservice.recipemodule.comment.dto.response.RecipeCommentResponse;
import com.cdd.recipeservice.recipemodule.comment.dto.response.RecipeCommentUpdateResponse;
import com.cdd.sangchupassport.Passport;

public interface RecipeCommentModifyService {
	RecipeCommentResponse commentSave(Passport passport, RecipeCommentSaveRequest request);

	RecipeCommentUpdateResponse commentUpdate(int memberId, long commentId, RecipeCommentUpdateRequest request);

	RecipeCommentRemoveResponse commentRemove(int memberId, long commentId);
}