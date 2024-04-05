package com.cdd.recipeservice.recipemodule.review.application;

import com.cdd.recipeservice.recipemodule.comment.dto.cond.PagingCond;
import com.cdd.recipeservice.recipemodule.review.dto.response.FindCookEatResponse;
import com.cdd.recipeservice.recipemodule.review.dto.response.FindCookEatsResponse;
import com.cdd.sangchupassport.Passport;

import com.cdd.recipeservice.recipemodule.review.dto.response.CookEatMemberResponse;

public interface CookEatLoadService {
	FindCookEatsResponse findCookEats(Passport passport, int recipeId, PagingCond cond);

	FindCookEatResponse findCookEat(Passport passport, long reviewId);
	CookEatMemberResponse loadMemberReview(Integer memberId);
}
