package com.cdd.recipeservice.ingredientmodule.market.domain;

import java.util.List;

import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.*;

import com.cdd.recipeservice.ingredientmodule.market.domain.query.*;

@Repository
public interface MarketRepository extends JpaRepository<Market, Integer>, MarketRepositoryCustom {
	@Query("SELECT m FROM Market m WHERE m.type = 'OFFLINE' and m.address.lat is null")
	List<Market> findAllOfflineMarket();
}
