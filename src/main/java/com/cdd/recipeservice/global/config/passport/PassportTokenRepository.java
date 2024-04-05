package com.cdd.recipeservice.global.config.passport;

import org.springframework.data.repository.CrudRepository;

import com.cdd.sangchupassport.token.PassportToken;

public interface PassportTokenRepository extends CrudRepository<PassportToken, String> {
}
