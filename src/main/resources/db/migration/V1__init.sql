create table comment_tbl
(
    member_id         integer,
    recipe_id         integer,
    reply_count       integer,
    comment_id        bigint not null auto_increment,
    created_at        datetime(6),
    parent_comment_id bigint,
    updated_at        datetime(6),
    comment_content   varchar(255),
    primary key (comment_id)
) engine = InnoDB;

create table cook_eat_image_tbl
(
    cook_eat_id       bigint,
    cook_eat_image_id bigint not null auto_increment,
    cook_eat_image    varchar(255),
    primary key (cook_eat_image_id)
) engine = InnoDB;

create table cook_eat_like_tbl
(
    member_id               integer,
    cook_eat_id             bigint,
    cook_eat_like_id        bigint not null auto_increment,
    cook_eat_like_viewed_at datetime(6),
    created_at              datetime(6),
    updated_at              datetime(6),
    primary key (cook_eat_like_id)
) engine = InnoDB;

create table cook_eat_tbl
(
    member_id   integer,
    cook_eat_id bigint       not null auto_increment,
    created_at  datetime(6),
    updated_at  datetime(6),
    content     varchar(255) not null,
    title       varchar(255),
    primary key (cook_eat_id)
) engine = InnoDB;

create table ingredient_sales_price_stats_tbl
(
    avg_price                       integer,
    ingredient_id                   integer not null,
    max_price                       integer,
    min_price                       integer,
    created_at                      datetime(6),
    ingredient_sales_price_stats_id bigint  not null auto_increment,
    updated_at                      datetime(6),
    primary key (ingredient_sales_price_stats_id)
) engine = InnoDB;

create table ingredient_tbl
(
    ingredient_id       integer not null auto_increment,
    ingredient_volume   integer,
    created_at          datetime(6),
    updated_at          datetime(6),
    ingredient_image    varchar(255),
    ingredient_name     varchar(255),
    ingredient_unit     varchar(255),
    classification_code enum ('PROCESSED_FOOD','SEAFOOD','RICE','DRIED_SEAFOOD','MUSHROOM','CHICKEN','VEGETABLE','FLOUR','BEANS_NUTS','FRUIT','MEAT','EGGS_DAIRY','GRAINS','PORK','BEEF','OTHER'),
    primary key (ingredient_id)
) engine = InnoDB;

create table market_ingredient_sales_price_tbl
(
    market_ingredient_sales_price    integer,
    market_ingredient_sales_volume   integer,
    created_at                       datetime(6),
    market_ingredient_id             bigint,
    market_ingredient_sales_price_id bigint       not null auto_increment,
    updated_at                       datetime(6),
    market_ingredient_sales_unit     varchar(10),
    sales_link                       varchar(255) not null,
    primary key (market_ingredient_sales_price_id)
) engine = InnoDB;

create table market_ingredient_tbl
(
    ingredient_id        integer,
    market_id            integer,
    created_at           datetime(6),
    market_ingredient_id bigint not null auto_increment,
    updated_at           datetime(6),
    primary key (market_ingredient_id)
) engine = InnoDB;

create table market_tbl
(
    lat         float(53),
    lng         float(53),
    market_id   integer not null auto_increment,
    addr        varchar(255),
    market_name varchar(255),
    url         varchar(255),
    market_type enum ('ONLINE','OFFLINE'),
    primary key (market_id)
) engine = InnoDB;

create table recipe_ingredient_tbl
(
    ingredient_id        integer,
    recipe_id            integer,
    created_at           datetime(6),
    recipe_ingredient_id bigint not null auto_increment,
    updated_at           datetime(6),
    primary key (recipe_ingredient_id)
) engine = InnoDB;

create table recipe_stats_tbl
(
    comments_count integer,
    cook_eat_count integer,
    likes_count    integer,
    recipe_id      integer not null,
    created_at     datetime(6),
    updated_at     datetime(6),
    primary key (recipe_id)
) engine = InnoDB;

create table recipe_tbl
(
    recipe_id            integer not null auto_increment,
    recipe_serial_number integer,
    created_at           datetime(6),
    updated_at           datetime(6),
    ingredients          varchar(1000),
    cooking_difficulty   varchar(255),
    cooking_time         varchar(255),
    food_portion         varchar(255),
    recipe_image         varchar(255),
    recipe_title         varchar(255),
    food_category        enum ('DESERT','SIDEDISH','RICE','MAINDISH','NOODLEDUMPLING','BREAD','SAUCE','WESTERN','SOUP','SNACK','DRINK','STEW','STEWSOUP','SALAD','FUSION','PICKLES','OTHER'),
    food_style           enum ('SNACK','DAILY_MEAL','DRINKING_SNACK','GUEST_ENTERTAINMENT','LUNCH_BOX','NUTRITION_MEAL','LATE_NIGHT_MEAL','QUICK_MEAL','DIET','HOLIDAY','OTHER','BABY_FOOD','HANGOVER'),
    main_ingredient_type enum ('PROCESSED_FOOD','SEAFOOD','RICE','DRIED_SEAFOOD','MUSHROOM','CHICKEN','VEGETABLE','FLOUR','BEANS_NUTS','FRUIT','MEAT','EGGS_DAIRY','GRAINS','PORK','BEEF','OTHER'),
    recipe_type          enum ('FRIED','PAN_FRIED','GRILLED','BOILED','STIR_FRIED','OTHER','BRAISED','STEAMED','SEASONED','PICKLED','MIXED','BLANCHED','BOILED_SOFT','RAW'),
    primary key (recipe_id)
) engine = InnoDB;

create table recipe_like
(
    member_id             integer,
    recipe_id             integer,
    created_at            datetime(6),
    recipe_like_id        bigint not null auto_increment,
    recipe_like_viewed_at datetime(6),
    updated_at            datetime(6),
    primary key (recipe_like_id)
) engine = InnoDB;

create table target_price_tbl
(
    ingredient_id           integer not null,
    ingredient_target_price integer,
    member_id               integer,
    created_at              datetime(6),
    target_price_id         bigint  not null auto_increment,
    updated_at              datetime(6),
    primary key (target_price_id)
) engine = InnoDB;

alter table ingredient_sales_price_stats_tbl
    add constraint UK_l83p1koihama5skgtkp0e5kh3 unique (ingredient_id);

alter table ingredient_tbl
    add constraint NAME_UNIQUE unique (ingredient_name);

alter table recipe_tbl
    add constraint RECIPE_SERIAL_NUMBER_UNIQUE unique (recipe_serial_number);

alter table comment_tbl
    add constraint FKs50gt7a4tt8eune90fhgtqhbo
        foreign key (parent_comment_id)
            references comment_tbl (comment_id);

alter table comment_tbl
    add constraint FKnbx3eqb9c8jwhobr1d8r4744i
        foreign key (recipe_id)
            references recipe_tbl (recipe_id);

alter table cook_eat_image_tbl
    add constraint FKnkcmm12pa5cl4b85ore2dpgtd
        foreign key (cook_eat_id)
            references cook_eat_tbl (cook_eat_id);

alter table cook_eat_like_tbl
    add constraint FKo6g34g2wih1x41of3p03qw5qf
        foreign key (cook_eat_id)
            references cook_eat_tbl (cook_eat_id);

alter table ingredient_sales_price_stats_tbl
    add constraint FKnrn4a1msalgxvpwmnws063m9j
        foreign key (ingredient_id)
            references ingredient_tbl (ingredient_id);

alter table market_ingredient_sales_price_tbl
    add constraint FKebj6sj4hgfetsh0q61x046tfs
        foreign key (market_ingredient_id)
            references market_ingredient_tbl (market_ingredient_id);

alter table market_ingredient_tbl
    add constraint FKoycc3kmv1gw1s9u2xqlg5idrb
        foreign key (ingredient_id)
            references ingredient_tbl (ingredient_id);

alter table market_ingredient_tbl
    add constraint FKe4x3nb2535uvae29ceawc5ykh
        foreign key (market_id)
            references market_tbl (market_id);

alter table recipe_ingredient_tbl
    add constraint FK6fsb2mvv9s7h3k0w2tdtdd0cy
        foreign key (ingredient_id)
            references ingredient_tbl (ingredient_id);

alter table recipe_ingredient_tbl
    add constraint FKgqh1ueqxi1mj0x7olo4ys073i
        foreign key (recipe_id)
            references recipe_tbl (recipe_id);

alter table recipe_stats_tbl
    add constraint FKh07tpck1hw1ud6mujeganhn7l
        foreign key (recipe_id)
            references recipe_tbl (recipe_id);

alter table recipe_like
    add constraint FKs5hinr51hsf0cyxkt1jvg4dd0
        foreign key (recipe_id)
            references recipe_tbl (recipe_id);

alter table target_price_tbl
    add constraint FKeitm25xm62qsjh3bslpd4o9yl
        foreign key (ingredient_id)
            references ingredient_tbl (ingredient_id);