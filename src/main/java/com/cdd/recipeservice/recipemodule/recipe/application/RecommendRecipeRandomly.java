package com.cdd.recipeservice.recipemodule.recipe.application;

import java.util.List;
import java.util.Optional;
import java.util.concurrent.ThreadLocalRandom;
import java.util.stream.IntStream;

import org.springframework.stereotype.Service;

import com.cdd.recipeservice.recipemodule.recipe.domain.RecipeRepository;
import com.cdd.recipeservice.recipemodule.recipe.dto.response.RecipeInfo;

@Service
public class RecommendRecipeRandomly implements RecommendRecipeService {

	@Override
	public List<RecipeInfo> selectRecommendRecipe(int maxResults,
		RecipeRepository recipeRepository) {
		int totalCount = (int)recipeRepository.count();
		return IntStream.generate(() -> ThreadLocalRandom.current().nextInt(1, 40001)) // 무작위 ID 생성
			.distinct()
			.mapToObj(recipeRepository::findById)
			.limit(maxResults)
			.flatMap(Optional::stream)
			.map(RecipeInfo::of)
			.toList();
	}
}
