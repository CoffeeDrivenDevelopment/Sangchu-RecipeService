package com.cdd.recipeservice.infra.storage.application;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.client.MultipartBodyBuilder;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.reactive.function.BodyInserters;
import org.springframework.web.reactive.function.client.WebClient;

import com.cdd.recipeservice.infra.config.InfraClientProperties;
import com.cdd.recipeservice.infra.storage.dto.response.ImageSaveResponse;

import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Component
public class StorageClient {
	private final String url;

	@Autowired
	public StorageClient(InfraClientProperties properties) {
		url = properties.getStorageServiceUrl();
	}

	public List<ImageSaveResponse> saveImages(final List<MultipartFile> files) {
		return Flux.fromIterable(files)
			.flatMap(this::saveImageRequest)
			.collectList()
			.block();
	}

	private Mono<ImageSaveResponse> saveImageRequest(final MultipartFile file) {
		MultipartBodyBuilder builder = new MultipartBodyBuilder();
		builder.part("file_source", file.getResource());
		return WebClient.create(url)
			.post()
			.uri("/v1/file")
			.contentType(MediaType.MULTIPART_FORM_DATA)
			.body(BodyInserters.fromMultipartData(builder.build()))
			.retrieve()
			.bodyToMono(ImageSaveResponse.class);
	}
}
