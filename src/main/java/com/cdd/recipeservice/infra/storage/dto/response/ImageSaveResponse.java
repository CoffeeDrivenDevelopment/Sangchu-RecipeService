package com.cdd.recipeservice.infra.storage.dto.response;

import com.fasterxml.jackson.annotation.JsonProperty;

public record ImageSaveResponse(
	@JsonProperty("file_source")
	String image
) {
}
