package com.cdd.recipeservice.infra.config;

import org.springframework.boot.context.properties.ConfigurationProperties;

import lombok.Data;

@Data
@ConfigurationProperties(prefix = "client")
public class InfraClientProperties {
	private String memberServiceUrl;
	private String storageServiceUrl;
}
