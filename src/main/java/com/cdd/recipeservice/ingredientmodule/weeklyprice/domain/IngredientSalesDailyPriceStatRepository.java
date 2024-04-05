package com.cdd.recipeservice.ingredientmodule.weeklyprice.domain;

import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.*;

import com.cdd.recipeservice.ingredientmodule.weeklyprice.domain.query.*;

@Repository
public interface IngredientSalesDailyPriceStatRepository extends JpaRepository<IngredientSalesDailyPriceStat, Long>,
	IngredientSalesDailyPriceStatRepositoryCustom {
}
