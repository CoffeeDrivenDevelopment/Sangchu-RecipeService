-- 1. 외래 키 제약 조건 삭제
ALTER TABLE ingredient_sales_price_stats_tbl
DROP FOREIGN KEY FKnrn4a1msalgxvpwmnws063m9j;

-- 2. 인덱스 삭제
DROP INDEX UK_l83p1koihama5skgtkp0e5kh3 ON ingredient_sales_price_stats_tbl;

-- 3. 새로운 외래 키 제약 조건 추가
ALTER TABLE ingredient_sales_price_stats_tbl
    ADD CONSTRAINT FKnrn4a1msalgxvpwmnws063m9j
        FOREIGN KEY (`ingredient_id`)
            REFERENCES ingredient_tbl (`ingredient_id`);

-- 4. 테이블 이름 변경
ALTER TABLE ingredient_sales_price_stats_tbl
    RENAME TO ingredient_sales_daily_price_stats_tbl;
