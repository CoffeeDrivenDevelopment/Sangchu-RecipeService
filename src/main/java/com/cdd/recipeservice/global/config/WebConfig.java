package com.cdd.recipeservice.global.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpHeaders;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import com.cdd.sangchupassport.support.SangchuHeader;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Configuration
public class WebConfig implements WebMvcConfigurer {
	@Value("${cors.allowed.origins}")
	private String[] origins;

	@Override
	public void addResourceHandlers(ResourceHandlerRegistry registry) {
		registry.addResourceHandler("/static/**")
			.addResourceLocations("classpath:/static/", "file:/app/static/");
		registry.addResourceHandler("swagger-ui.html")
			.addResourceLocations("classpath:/static/swagger-ui/", "file:/app/static/swagger-ui/");
	}

	@Override
	public void addCorsMappings(CorsRegistry registry) {
		registry.addMapping("/**")
			.allowedOrigins(origins)
			.allowedMethods("GET", "POST", "PUT", "DELETE", "HEAD", "OPTIONS")
			// .allowCredentials(true)
			.exposedHeaders(HttpHeaders.AUTHORIZATION, SangchuHeader.SANGCHU_PASSPORT.getName());
	}
}
