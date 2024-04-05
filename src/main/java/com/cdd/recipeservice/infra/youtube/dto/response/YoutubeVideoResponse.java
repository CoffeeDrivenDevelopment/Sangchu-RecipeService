package com.cdd.recipeservice.infra.youtube.dto.response;

import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@Getter
@NoArgsConstructor
public class YoutubeVideoResponse {
	private List<YoutubeSearchResult> items;
}
