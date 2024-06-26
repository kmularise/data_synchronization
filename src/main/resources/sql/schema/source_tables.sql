CREATE TABLE car_order
(
    id          BIGINT AUTO_INCREMENT PRIMARY KEY,
    count       INT COMMENT '수량',
    car_id      BIGINT NOT NULL COMMENT '자동차 외래키',
    customer_id BIGINT NOT NULL COMMENT '주문 고객 외래키'
);
CREATE TABLE customer
(
    id   BIGINT AUTO_INCREMENT PRIMARY KEY,
    name VARCHAR(40) NOT NULL COMMENT '이름'
);

CREATE TABLE car
(
    id    BIGINT AUTO_INCREMENT PRIMARY KEY,
    name  VARCHAR(40) NOT NULL COMMENT '이름',
    price BIGINT      NOT NULL COMMENT '자동차 가격'
);
