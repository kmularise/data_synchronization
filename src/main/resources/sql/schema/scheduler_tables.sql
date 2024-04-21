CREATE TABLE SYNC_TASK (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    job_name VARCHAR(255) NOT NULL,
    period_minutes INT NOT NULL,
    table_name VARCHAR(50) NOT NULL,
    is_active TINYINT(1) NOT NULL,
    has_inserted TINYINT(1) NOT NULL,
    has_deleted TINYINT(1) NOT NULL,
    has_updated TINYINT(1) NOT NULL,
    excluded_columns JSON NOT NULL
);

CREATE TABLE SYNC_TASK_LOG (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    job_name VARCHAR(255) NOT NULL,
    start_time DATETIME NOT NULL,
    end_time DATETIME,
    status VARCHAR(20) NOT NULL
);
