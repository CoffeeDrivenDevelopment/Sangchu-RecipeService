package com.cdd.recipeservice.recipemodule.review.domain.query;

import java.util.List;
import java.util.Optional;

import com.cdd.recipeservice.recipemodule.review.domain.CookEat;
import com.cdd.recipeservice.recipemodule.review.dto.cond.FindCookEatCond;

public interface CookEatRepositoryCustom {
	Optional<CookEat> findByIdAsJoin(long id);
	List<CookEat> findCookEatsByCond(FindCookEatCond cond);
	boolean hasMoreCookEatById(Long lastCookEatId, int recipeId);
}
