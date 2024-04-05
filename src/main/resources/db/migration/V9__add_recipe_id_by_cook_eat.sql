alter table cook_eat_tbl
    add column recipe_id int;

alter table cook_eat_tbl
    add constraint FKnbx3eqb9c8jzhowr1d8r4854i
        foreign key (recipe_id)
            references recipe_tbl (recipe_id);
