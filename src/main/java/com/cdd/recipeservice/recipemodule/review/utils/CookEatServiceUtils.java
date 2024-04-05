package com.cdd.recipeservice.recipemodule.review.utils;

import com.cdd.common.exception.CallConstructorException;
import com.cdd.recipeservice.recipemodule.review.domain.CookEat;
import com.cdd.recipeservice.recipemodule.review.domain.CookEatRepository;
import com.cdd.recipeservice.recipemodule.review.exception.CookEatErrorCode;
import com.cdd.recipeservice.recipemodule.review.exception.CookEatException;

public class CookEatServiceUtils {
	private CookEatServiceUtils() {
		throw new CallConstructorException();
	}

	public static CookEat findById(CookEatRepository repository, Long id) {
		return repository.findByIdAsJoin(id)
			.orElseThrow(() -> new CookEatException(CookEatErrorCode.NO_EXISTED_COOK_EAT));
	}
}
