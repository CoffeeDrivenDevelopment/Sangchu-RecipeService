package com.cdd.recipeservice.ingredientmodule.weeklyprice.utils;

import java.time.LocalDate;
import java.time.ZoneId;
import java.util.*;
import java.util.function.IntFunction;

import com.cdd.recipeservice.ingredientmodule.weeklyprice.domain.PriceCalculator;
import com.cdd.recipeservice.ingredientmodule.weeklyprice.domain.WeeklyPrice;

import io.netty.util.internal.ThreadLocalRandom;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class IngredientWeeklyPriceUtils {

	public static <T> List<T> generateIngredientSalesPriceDummyData(IntFunction<T> constructorFunction, int num) {
		List<T> list = new ArrayList<>();

		for (int i = 0; i < num; i++) {
			list.add(constructorFunction.apply(makeRandomPrice()));
		}
		return list;
	}

	public static int makeRandomPrice() {
		return ThreadLocalRandom.current().nextInt(100, 20001);
	}

	public static <T extends PriceCalculator> Map<Integer, List<WeeklyPrice>> generateWeeklyPrice(
		List<T> ingredientSalesPrices,
		int[] weeks,
		int unit) {
		return makeWeeklyAvgPrices(ingredientSalesPrices, weeks, unit);

	}

	private static <T extends PriceCalculator> Map<Integer, List<WeeklyPrice>> makeWeeklyAvgPrices(
		List<T> ingredientSalesPrices,
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
			pricesPerWeek.add(new WeeklyPrice(startDate.minusDays(i * week)));
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
