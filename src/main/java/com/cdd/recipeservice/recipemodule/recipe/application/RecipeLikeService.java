package com.cdd.recipeservice.recipemodule.recipe.application;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.cdd.recipeservice.recipemodule.recipe.domain.Recipe;
import com.cdd.recipeservice.recipemodule.recipe.domain.RecipeLike;
import com.cdd.recipeservice.recipemodule.recipe.domain.RecipeLikeRepository;
import com.cdd.recipeservice.recipemodule.recipe.domain.RecipeRepository;
import com.cdd.recipeservice.recipemodule.recipe.dto.response.RecipeLikeResponse;
import com.cdd.recipeservice.recipemodule.recipe.dto.response.RecipeSearchInfo;
import com.cdd.recipeservice.recipemodule.recipe.dto.response.RecipeSearchResponse;
import com.cdd.recipeservice.recipemodule.recipe.exception.RecipeErrorCode;
import com.cdd.recipeservice.recipemodule.recipe.exception.RecipeException;
import com.cdd.recipeservice.recipemodule.recipe.utils.RecipeLikeMapper;
import com.cdd.recipeservice.recipemodule.recipe.utils.RecipeServiceUtils;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Transactional(readOnly = true)
@Service
public class RecipeLikeService
	implements RecipeLikeLoadService, RecipeLikeUpdateService {
	private final RecipeLikeRepository recipeLikeRepository;
	private final RecipeRepository recipeRepository;

	private final RecipeLikeMapper recipeLikeMapper;

	@Transactional
	@Override
	public RecipeLikeResponse recipeLike(
		final int memberId,
		final int recipeId
	) {
		Recipe findRecipe = RecipeServiceUtils.findById(recipeRepository, recipeId);
		validateAlreadyRecipeLiked(memberId, findRecipe);

		RecipeLike recipeLike = recipeLikeMapper.makeJpaEntity(memberId, findRecipe);
		recipeLikeRepository.save(recipeLike);

		return RecipeLikeResponse.like();
	}

	private void validateAlreadyRecipeLiked(
		final int memberId,
		final Recipe recipe
	) {
		if (recipeLikeRepository.existsByMemberIdAndRecipe(memberId, recipe)) {
			throw new RecipeException(RecipeErrorCode.ALREADY_RECIPE_LIKED);
		}
	}

	@Transactional
	@Override
	public RecipeLikeResponse delete(
		final int memberId,
		final int recipeId
	) {
		Recipe findRecipe = RecipeServiceUtils.findById(recipeRepository, recipeId);

		RecipeLike findRecipeLike = recipeLikeRepository.findRecipeLikeByMemberIdAndRecipe(memberId, findRecipe)
			.orElseThrow(() -> new RecipeException(RecipeErrorCode.NO_EXISTED_RECIPE_LIKE));
		recipeLikeRepository.delete(findRecipeLike);

		return RecipeLikeResponse.notLike();
	}

	@Override
	public RecipeSearchResponse getLikeRecipeList(int memberId, Pageable pageable) {
		// TODO: 팔로워인지? 자신인지? 체크로직 필요
		Page<Recipe> recipePageList = recipeLikeRepository.findByMemberId(memberId, pageable);
		List<RecipeSearchInfo> recipeListList = recipePageList.getContent().stream()
			.map(recipe-> RecipeSearchInfo.of(recipe,true))
			.toList();
		return new RecipeSearchResponse(
			recipeListList,
			recipePageList.getTotalElements(),
			0,
			recipePageList.getPageable().getPageNumber()!= pageable.getPageNumber());

	}
}
