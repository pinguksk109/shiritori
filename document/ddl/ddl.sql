CREATE TABLE training.shiritori (
    id BIGINT NOT NULL AUTO_INCREMENT PRIMARY KEY,
    entry BIGINT NOT NULL,
    word varchar(100) NOT NULL
)
ENGINE=InnoDB
DEFAULT CHARSET=utf8
COLLATE=utf8_general_ci;