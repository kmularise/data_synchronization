CREATE TABLE car_order
(
    id          BIGINT PRIMARY KEY,
    count       INT COMMENT '수량',
    car_id      BIGINT COMMENT '자동차 외래키',
    customer_id BIGINT COMMENT '주문 고객 외래키'
);
CREATE TABLE customer
(
    id   BIGINT PRIMARY KEY,
    name VARCHAR(40) COMMENT '이름'
);

CREATE TABLE car
(
    id    BIGINT PRIMARY KEY,
    name  VARCHAR(40) COMMENT '이름',
    price BIGINT COMMENT '자동차 가격'
);
