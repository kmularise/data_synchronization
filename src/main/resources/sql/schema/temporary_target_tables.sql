CREATE TABLE IF_orders
(
    id          BIGINT PRIMARY KEY,
    count       INT COMMENT '수량',
    car_id      BIGINT NOT NULL COMMENT '자동차 외래키',
    customer_id BIGINT NOT NULL COMMENT '주문 고객 외래키'
);
CREATE TABLE IF_customers
(
    id   BIGINT PRIMARY KEY,
    name VARCHAR(40) NOT NULL COMMENT '이름'
);

CREATE TABLE IF_cars
(
    id    BIGINT PRIMARY KEY,
    name  VARCHAR(40) NOT NULL COMMENT '이름',
    price BIGINT      NOT NULL COMMENT '자동차 가격'
);
