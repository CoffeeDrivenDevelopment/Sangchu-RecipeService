package com.cdd.recipeservice.recipemodule.recipe.dto.response;

import java.io.Serializable;

import com.cdd.recipeservice.infra.youtube.dto.response.YoutubeSearchResult;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@Builder
@Getter
@NoArgsConstructor
public class CookingMovie implements Serializable {
	private String url;
	private String thumbnail;
	private String title;
	private String content;

	public static CookingMovie from(YoutubeSearchResult youtubeSearchResult) {
		return CookingMovie.builder()
			.url("https://www.youtube.com/watch?v=" + youtubeSearchResult.getId().getVideoId())
			.thumbnail(youtubeSearchResult.getSnippet().getThumbnails().getHigh().getUrl())
			.title(youtubeSearchResult.getSnippet().getTitle())
			.content(youtubeSearchResult.getSnippet().getDescription())
			.build();
	}
}
