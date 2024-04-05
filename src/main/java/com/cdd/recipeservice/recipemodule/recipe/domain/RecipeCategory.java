package com.cdd.recipeservice.recipemodule.recipe.domain;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum RecipeCategory {
    DESERT("디저트"),
    SIDEDISH("밑반찬"),
    RICE("밥/죽/떡"),
    MAINDISH("메인반찬"),
    NOODLEDUMPLING("면/만두"),
    BREAD("빵"),
    SAUCE("양념/소스/잼"),
    WESTERN("양식"),
    SOUP("스프"),
    SNACK("과자"),
    DRINK("차/음료/술"),
    STEW("찌개"),
    STEWSOUP("국/탕"),
    SALAD("샐러드"),
    FUSION("퓨전"),
    PICKLES("김치/젓갈/장류"),
    OTHER("기타");

    private final String desc;
}
