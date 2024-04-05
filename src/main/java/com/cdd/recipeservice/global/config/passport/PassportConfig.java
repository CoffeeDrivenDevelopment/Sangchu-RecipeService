package com.cdd.recipeservice.global.config.passport;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.cdd.sangchupassport.exception.PassportAdvice;
import com.cdd.sangchupassport.support.PassportValidator;
import com.fasterxml.jackson.databind.ObjectMapper;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Configuration
public class PassportConfig {
	private final PassportTokenRepository passportTokenRepository;
	private final ObjectMapper mapper;

	@Bean
	public PassportValidator passportValidator() {
		return new PassportValidator(passportTokenRepository, mapper);
	}

	@Bean
	public PassportAdvice passportAdvice() {
		return new PassportAdvice();
	}
}
