package com.cdd.recipeservice.infra.youtube.dto.response;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@Getter
@NoArgsConstructor
public class YoutubeSearchResult {
	private Id id;
	private Snippet snippet;

	@AllArgsConstructor
	@Getter
	@NoArgsConstructor
	public static class Id {
		private String kind;
		private String videoId;
	}

	@AllArgsConstructor
	@Getter
	@NoArgsConstructor
	public static class Snippet {
		private String title;
		private String description;
		private Thumbnails thumbnails;
		private String channelTitle;
	}

	@AllArgsConstructor
	@Getter
	@NoArgsConstructor
	public static class Thumbnails {
		private Thumbnail high;
	}

	@AllArgsConstructor
	@Getter
	@NoArgsConstructor
	public static class Thumbnail {
		private String url;
		private int width;
		private int height;
	}
}

