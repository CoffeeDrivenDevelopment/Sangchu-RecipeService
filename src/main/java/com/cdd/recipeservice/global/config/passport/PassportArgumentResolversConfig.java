package com.cdd.recipeservice.global.config.passport;

import java.util.List;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import com.cdd.sangchupassport.support.PassportArgumentResolver;
import com.cdd.sangchupassport.support.PassportValidator;
import com.fasterxml.jackson.databind.ObjectMapper;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Configuration
public class PassportArgumentResolversConfig implements WebMvcConfigurer {
	private final ObjectMapper mapper;
	private final PassportValidator validator;

	@Override
	public void addArgumentResolvers(List<HandlerMethodArgumentResolver> resolvers) {
		resolvers.add(new PassportArgumentResolver(mapper, validator));
	}
}
