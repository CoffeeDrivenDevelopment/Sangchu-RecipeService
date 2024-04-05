package com.cdd.recipeservice.infra.storage.dto.request;

import org.springframework.web.multipart.MultipartFile;

import com.fasterxml.jackson.annotation.JsonProperty;

public record ImageSaveRequest(
	@JsonProperty("file_source")
	MultipartFile file
) {
}
