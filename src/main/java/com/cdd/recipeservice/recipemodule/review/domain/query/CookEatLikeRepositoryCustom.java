package com.cdd.recipeservice.recipemodule.review.domain.query;

import java.util.List;

import com.cdd.recipeservice.recipemodule.review.domain.CookEatLike;

public interface CookEatLikeRepositoryCustom {
	List<CookEatLike> findCookEatsByIds(List<Long> cookEatIds, int memberId);
}
