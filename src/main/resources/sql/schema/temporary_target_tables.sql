CREATE TABLE IF_orders
(
    id          BIGINT,
    count       INT COMMENT '수량',
    car_id      BIGINT COMMENT '자동차 외래키',
    customer_id BIGINT COMMENT '주문 고객 외래키'
);
CREATE TABLE IF_customers
(
    id   BIGINT,
    name VARCHAR(40) COMMENT '이름'
);

CREATE TABLE IF_cars
(
    id    BIGINT,
    name  VARCHAR(40) COMMENT '이름',
    price BIGINT COMMENT '자동차 가격'
);
