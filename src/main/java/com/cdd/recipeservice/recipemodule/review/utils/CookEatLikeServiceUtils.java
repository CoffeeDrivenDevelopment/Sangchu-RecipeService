package com.cdd.recipeservice.recipemodule.review.utils;

import java.util.List;

import com.cdd.common.exception.CallConstructorException;
import com.cdd.recipeservice.recipemodule.review.domain.CookEatLike;
import com.cdd.recipeservice.recipemodule.review.domain.CookEatLikeRepository;

public class CookEatLikeServiceUtils {
	private CookEatLikeServiceUtils() {
		throw new CallConstructorException();
	}

	public static List<CookEatLike> isLikedIds(
		final CookEatLikeRepository repository,
		final int memberId,
		final List<Long> cookEatIds
	) {
		return repository.findCookEatsByIds(cookEatIds, memberId);
	}
}
