
-- 기존 컬럼 이름 변경
ALTER TABLE ingredient_sales_daily_price_stats_tbl
    CHANGE COLUMN ingredient_sales_price_stats_id ingredient_sales_daily_price_stats_id BIGINT NOT NULL AUTO_INCREMENT;

