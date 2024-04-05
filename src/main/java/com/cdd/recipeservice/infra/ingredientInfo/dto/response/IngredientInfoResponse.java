package com.cdd.recipeservice.infra.ingredientInfo.dto.response;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@Getter
@NoArgsConstructor
public class IngredientInfoResponse {
    @JsonProperty("Grid_20171128000000000572_1")
    private Grid grid;

    @AllArgsConstructor
    @Getter
    @NoArgsConstructor
    public static class Grid {
        @JsonProperty("totalCnt")
        private int totalCnt;
        @JsonProperty("result")
        private Result result;
        @JsonProperty("row")
        private List<Row> row;
    }

    @AllArgsConstructor
    @Getter
    @NoArgsConstructor
    public static class Result {
        @JsonProperty("code")
        private String code;
        @JsonProperty("message")
        private String message;
    }

    @AllArgsConstructor
    @Getter
    @NoArgsConstructor
    public static class Row {
        @JsonProperty("PRDLST_NM")
        private String productName;
        @JsonProperty("PURCHASE_MTH")
        private String knowHow;
    }
}
