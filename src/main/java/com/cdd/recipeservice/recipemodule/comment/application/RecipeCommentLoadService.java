package com.cdd.recipeservice.recipemodule.comment.application;

import com.cdd.recipeservice.recipemodule.comment.dto.cond.PagingCond;
import com.cdd.recipeservice.recipemodule.comment.dto.response.FindRecipeCommentResponse;
import com.cdd.recipeservice.recipemodule.comment.dto.response.FindRecipeReplyCommentResponse;
import com.cdd.sangchupassport.Passport;

public interface RecipeCommentLoadService {
	FindRecipeCommentResponse findRecipeComment(Passport passport, PagingCond cond, int recipeId);

	FindRecipeReplyCommentResponse findRecipeReplyComment(Passport passport, PagingCond cond, long commentId);
}
