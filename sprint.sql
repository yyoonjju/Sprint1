CREATE DATABASE sprint;

CREATE TABLE user (
	user_id VARCHAR(50),
	user_password VARCHAR(50),
	user_nickname VARCHAR(50),
	phone VARCHAR(20),
	address VARCHAR(100)
);

CREATE TABLE service (
	subtype VARCHAR(50),
	price BIGINT,
	term BIGINT
);

CREATE TABLE question_board (
	board_seq BIGINT AUTO_INCREMENT PRIMARY KEY,
	title VARCHAR(50),
	content VARCHAR(5000),
	answer VARCHAR(5000),
	state VARCHAR(50),
	writedate VARCHAR(50),
	user_id VARCHAR(50)
);	

CREATE TABLE SERVICE_HISTORY (
	history_seq BIGINT AUTO_INCREMENT PRIMARY KEY,
	user_id VARCHAR(50),
	subtype_code VARCHAR(50),
	subtype VARCHAR(50),
	sub_start VARCHAR(50),
	sub_end VARCHAR(50)
);

# service 테이블 데이터 삽입
INSERT INTO SERVICE(subtype, price, term) VALUES("basic",100000, 30);
INSERT INTO SERVICE(subtype, price, term) VALUES("premium",130000, 30);

DELETE FROM service_history;
DROP TABLE service_history;
DROP TABLE service;
COMMIT;
