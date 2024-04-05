package com.cdd.recipeservice.global.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.cdd.common.exception.SangchuAdvice;

@Configuration
public class RestAdviceConfig {

	@Bean
	public SangchuAdvice sangchuAdvice() {
		return new SangchuAdvice();
	}
}