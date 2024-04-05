package com.cdd.recipeservice;

import org.springframework.boot.*;
import org.springframework.boot.autoconfigure.*;
import org.springframework.boot.context.properties.*;
import org.springframework.cache.annotation.*;
import org.springframework.cloud.client.discovery.*;
import org.springframework.data.jpa.repository.config.*;
import org.springframework.scheduling.annotation.*;

@ConfigurationPropertiesScan
@EnableCaching
@EnableDiscoveryClient
@EnableScheduling
@EnableJpaAuditing
@SpringBootApplication
public class RecipeServiceApplication {

	public static void main(String[] args) {
		SpringApplication.run(RecipeServiceApplication.class, args);
	}

}
