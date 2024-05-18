CREATE TABLE IF_car_order
(
    id          BIGINT,
    count       INT COMMENT '수량',
    car_id      BIGINT COMMENT '자동차 외래키',
    customer_id BIGINT COMMENT '주문 고객 외래키'
);
CREATE TABLE IF_customer
(
    id   BIGINT,
    name VARCHAR(40) COMMENT '이름'
);

CREATE TABLE IF_car
(
    id    BIGINT,
    name  VARCHAR(40) COMMENT '이름',
    price BIGINT COMMENT '자동차 가격'
);
