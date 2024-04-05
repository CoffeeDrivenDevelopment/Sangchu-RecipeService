package com.cdd.recipeservice.infra.youtube.application;

import java.util.List;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;

import com.cdd.recipeservice.infra.youtube.dto.response.YoutubeVideoResponse;
import com.cdd.recipeservice.recipemodule.recipe.domain.RecipeRecommend;
import com.cdd.recipeservice.recipemodule.recipe.dto.response.CookingMovie;
import com.cdd.recipeservice.recipemodule.recipe.dto.response.RecipeInfo;

import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Service
public class YoutubeVideoClient {
	@Value("${openapi.youtube.api-key}")
	private String YOUTUBE_API_KEY;
	@Value("${openapi.youtube.url}")
	private String YOUTUBE_URL;

	public Mono<YoutubeVideoResponse> getYoutubeVideo(String keyword, int maxYoutubeSize) {
		return WebClient.create(YOUTUBE_URL)
			.get()
			.uri(uriBuilder -> uriBuilder
				.queryParam("part", "snippet")
				.queryParam("q", keyword + "레시피")
				.queryParam("maxResults", maxYoutubeSize)
				.queryParam("key", YOUTUBE_API_KEY)
				.build())
			.retrieve()
			.bodyToMono(YoutubeVideoResponse.class);
	}

	public <T extends RecipeInfo> List<RecipeRecommend> matchYoutubeList(List<T> recipes, int maxYoutubeSize) {
		return Flux.fromIterable(recipes)
			.flatMap(recipe -> getYoutubeVideo(recipe.getName(), maxYoutubeSize)
				.flatMapMany(youtubeVideo -> Flux.fromIterable(youtubeVideo.getItems()))
				.map(CookingMovie::from)
				.collectList()
				.map(cookingMovies -> RecipeRecommend.of(recipe, cookingMovies))
			)
			.collectList()
			.block();
	}
}
