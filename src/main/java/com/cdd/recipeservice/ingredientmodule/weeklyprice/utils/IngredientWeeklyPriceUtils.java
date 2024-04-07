package com.cdd.recipeservice.ingredientmodule.weeklyprice.utils;

import java.time.LocalDate;
import java.time.ZoneId;
import java.util.*;

import com.cdd.recipeservice.ingredientmodule.weeklyprice.domain.PriceCalculator;
import com.cdd.recipeservice.ingredientmodule.weeklyprice.domain.WeeklyPrice;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class IngredientWeeklyPriceUtils {
	public static Map<Integer, List<WeeklyPrice>> makeWeeklyGraph(
		List<WeeklyPrice> ingredientSalesPrices,
		int[] weeks,
		int unit
	) {
		Map<Integer, List<WeeklyPrice>> weeklyPrices = new HashMap<>();

		for (int week : weeks) {
			List<WeeklyPrice> pricesPerWeek = new ArrayList<>();
			for (int day = 0; day < unit * week; day += week) {
				if (day >= ingredientSalesPrices.size()) {
					break;
				}
				pricesPerWeek.add(ingredientSalesPrices.get(day));
			}
			Collections.sort(pricesPerWeek, Comparator.comparing(WeeklyPrice::getDate));
			weeklyPrices.put(week, pricesPerWeek);
		}

		return weeklyPrices;
	}

	public static Map<Integer, List<WeeklyPrice>> makeWeeklyAvgPrices(
		List<WeeklyPrice> ingredientSalesPrices,
		int[] weeks,
		int unit) {
		Map<Integer, List<WeeklyPrice>> weeklyPrices = new HashMap<>();

		LocalDate today = LocalDate.now(ZoneId.of("Asia/Seoul"));
		for (int week : weeks) {
			List<WeeklyPrice> pricesPerWeek = initializeWeeklyPrices(week, today, unit);
			sumPrices(pricesPerWeek, ingredientSalesPrices, week);
			averagePrices(pricesPerWeek, week);
			Collections.sort(pricesPerWeek, Comparator.comparing(WeeklyPrice::getDate));
			weeklyPrices.put(week, pricesPerWeek);
		}

		return weeklyPrices;
	}

	private static List<WeeklyPrice> initializeWeeklyPrices(int week, LocalDate startDate, int unit) {
		List<WeeklyPrice> pricesPerWeek = new ArrayList<>();
		for (int i = 0; i < unit; i++) {
			pricesPerWeek.add(new WeeklyPrice(startDate.minusDays((long)i * week)));
		}
		return pricesPerWeek;
	}

	private static <T extends PriceCalculator> void sumPrices(List<WeeklyPrice> pricesPerWeek,
		List<T> ingredientSalesPrices,
		int week) {
		int days = 7 * week;
		for (int day = 0; day < Math.min(days, ingredientSalesPrices.size()); day++) {
			WeeklyPrice price = pricesPerWeek.get(day / week);
			price.addPrice(ingredientSalesPrices.get(day).getPrice());
		}
	}

	private static void averagePrices(List<WeeklyPrice> pricesPerWeek, int week) {
		for (WeeklyPrice price : pricesPerWeek) {
			price.avgPrice(week);
		}
	}
}
