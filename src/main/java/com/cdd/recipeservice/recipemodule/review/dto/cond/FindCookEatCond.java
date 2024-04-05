package com.cdd.recipeservice.recipemodule.review.dto.cond;

import com.cdd.recipeservice.recipemodule.comment.dto.cond.PagingCond;

public record FindCookEatCond(
	int recipeId,
	PagingCond paging
) {
}
