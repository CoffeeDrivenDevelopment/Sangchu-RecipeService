package com.cdd.recipeservice.recipemodule.recipe.application;

import java.util.List;
import java.util.concurrent.ThreadLocalRandom;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.cdd.recipeservice.global.annotation.CheckPagingCond;
import com.cdd.recipeservice.global.utils.RedisUtils;
import com.cdd.recipeservice.infra.youtube.application.YoutubeVideoClient;
import com.cdd.recipeservice.recipemodule.comment.dto.cond.PagingCond;
import com.cdd.recipeservice.recipemodule.recipe.domain.*;
import com.cdd.recipeservice.recipemodule.recipe.dto.cond.RecipeCategoryCond;
import com.cdd.recipeservice.recipemodule.recipe.dto.cond.RecipeLikeCond;
import com.cdd.recipeservice.recipemodule.recipe.dto.cond.RecipeSearchCond;
import com.cdd.recipeservice.recipemodule.recipe.dto.response.*;
import com.cdd.recipeservice.recipemodule.recipe.utils.RecipeServiceUtils;
import com.cdd.sangchupassport.Passport;
import com.fasterxml.jackson.databind.ObjectMapper;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import reactor.core.publisher.Flux;

@RequiredArgsConstructor
@Slf4j
@Transactional(readOnly = true)
@Service
public class RecipeService implements RecipeInfoService {
	private final YoutubeVideoClient youtubeVideoClient;
	private final RecommendRecipeService recommendRecipeService;
	private final RecipeLikeRepository recipeLikeRepository;
	private final RecipeRepository recipeRepository;
	private final RedisTemplate<byte[], byte[]> redisTemplate;
	private final ObjectMapper objectMapper;

	@Value("${recipe.max-recipe-size}")
	private int maxRecipe;
	@Value("${recipe.max-youtube-size}")
	private int maxYoutube;
	@Value("${recipe.key}")
	private String key;

	@Scheduled(cron = "0 0 0 * * *", zone = "Asia/Seoul")
	@Transactional
	public void saveRecommendRecipeList() {
		List<RecipeInfo> recipes = recommendRecipeService.selectRecommendRecipe(maxRecipe, recipeRepository);
		List<RecipeRecommend> matchRecipeAndYoutube = youtubeVideoClient.matchYoutubeList(recipes, maxYoutube);
		for (int inx = 0; inx < maxRecipe; inx++) {
			RecipeRecommend recipeRecommend = matchRecipeAndYoutube.get(inx)
				.updateKey(key + (inx + 1));

			RedisUtils.put(
				redisTemplate,
				objectMapper,
				recipeRecommend,
				recipeRecommend.getKey());
		}
	}

	public RecipeRecommend getRecommendRecipe() {
		int inx = ThreadLocalRandom.current().nextInt(1, maxRecipe + 1);
		return RedisUtils.get(redisTemplate,
			objectMapper,
			RecipeRecommend.class,
			key, inx);
	}

	@Override
	public RecipeInfoResponse findRecipeInfo(final int recipeId) {
		Recipe findRecipe = RecipeServiceUtils.findById(
			recipeRepository,
			recipeId
		);
		return RecipeInfoResponse.from(findRecipe);
	}

	@Override
	public RecipeMoviesResponse findRecipeMovies(final String recipeName) {
		List<CookingMovie> cookingMovies = youtubeVideoClient.getYoutubeVideo(recipeName, 10)
			.flatMapMany(v -> Flux.fromIterable(v.getItems()))
			.map(CookingMovie::from)
			.collectList()
			.block();
		return RecipeMoviesResponse.from(cookingMovies);
	}

	@CheckPagingCond
	@Override
	public RecipeSearchResponse findRecipes(
		final Passport passport,
		final String type,
		final String query,
		final PagingCond cond
	) {
		List<Recipe> findRecipes = findRecipes(type, query, cond);
		int lastId = getLastId(findRecipes);

		return new RecipeSearchResponse(
			getRecipeSearchInfos(passport, findRecipes),
			findCount(type, query),
			lastId,
			findHasMore(query, type, lastId)
		);
	}

	private List<RecipeSearchInfo> getRecipeSearchInfos(
		final Passport passport,
		final List<Recipe> findRecipes
	) {
		List<Integer> recipeIds = findRecipes.stream().map(Recipe::getId).toList();
		List<RecipeLike> findRecipeLikes = recipeLikeRepository
			.findRecipeLikeByCond(new RecipeLikeCond(passport.getMemberId(), recipeIds));
		return getRecipeSearchInfos(findRecipes, findRecipeLikes);
	}

	private int getLastId(List<Recipe> findRecipes) {
		return findRecipes.stream()
			.mapToInt(Recipe::getId)
			.min()
			.orElse(-1);
	}

	private List<RecipeSearchInfo> getRecipeSearchInfos(
		final List<Recipe> findRecipes,
		final List<RecipeLike> findRecipeLikes
	) {
		return findRecipes.stream().map(recipe -> {
			boolean isLiked = false;

			for (RecipeLike recipeLike : findRecipeLikes) {
				if (recipeLike.getRecipe().getId().equals(recipe.getId())) {
					isLiked = true;
					break;
				}
			}

			return RecipeSearchInfo.of(recipe, isLiked);
		}).toList();
	}

	private List<Recipe> findRecipes(String type, String query, PagingCond cond) {
		if (type.equalsIgnoreCase("category")) {
			for (RecipeCategory recipeCategory : RecipeCategory.values()) {
				if (recipeCategory.name().equalsIgnoreCase(query)) {
					return recipeRepository.findByCategoryCond(new RecipeCategoryCond(recipeCategory, cond));
				}
			}
		}
		return recipeRepository.findBySearchCond(new RecipeSearchCond(query, cond));
	}

	private long findCount(String type, String query) {
		if (type.equalsIgnoreCase("category")) {
			for (RecipeCategory recipeCategory : RecipeCategory.values()) {
				if (recipeCategory.name().equalsIgnoreCase(query)) {
					return recipeRepository.countByCategoryCond(recipeCategory);
				}
			}
		}
		return recipeRepository.countBySearchCond(query);
	}

	private boolean findHasMore(
		final String query,
		final String type,
		final int lastId
	) {
		if (type.equalsIgnoreCase("category")) {
			for (RecipeCategory recipeCategory : RecipeCategory.values()) {
				if (recipeCategory.name().equalsIgnoreCase(query)) {
					return recipeRepository.hasMoreByCategoryCond(recipeCategory, lastId);
				}
			}
		}
		return recipeRepository.hasMoreBySearchCond(query, lastId);
	}
}
