CREATE DATABASE finalTask;

CREATE TABLE users (
	id INT PRIMARY KEY AUTO_INCREMENT,
    name VARCHAR(20) NOT NULL,
    sname VARCHAR(20) NOT NULL,
    mail VARCHAR(40) NOT NULL,
    login VARCHAR(20) NOT NULL UNIQUE,
    password VARCHAR(200) NOT NULL,
    status boolean
);

INSERT INTO users VALUES (DEFAULT, 'admin', 'admin', 'admin', 'admin', 'admin', 1);

CREATE TABLE tests (
	id INT PRIMARY KEY AUTO_INCREMENT,
	title VARCHAR(30) NOT NULL UNIQUE,
	difficult INT NOT NULL,
	time INT NOT NULL,
	questCount INT NOT NULL,
	subject VARCHAR(20)
);

CREATE TABLE questions (
	id INT PRIMARY KEY AUTO_INCREMENT,
	quest VARCHAR(150) NOT NULL
);

CREATE TABLE answers (
	id INT PRIMARY KEY AUTO_INCREMENT,
	answer VARCHAR(75) NOT NULL
);

CREATE TABLE results (
	user_id INT,
    FOREIGN KEY (user_id) REFERENCES users(id)
	ON DELETE CASCADE,
	test_id INT,
    FOREIGN KEY (test_id) REFERENCES tests(id)
	ON DELETE CASCADE,
	result DOUBLE NOT NULL,
	date VARCHAR(11) NOT NULL
);

CREATE TABLE tests_questions (
	test_id INT,
    FOREIGN KEY (test_id) REFERENCES tests(id)
	ON DELETE CASCADE,
	question_id INT,
    FOREIGN KEY (question_id) REFERENCES questions(id)
	ON DELETE CASCADE,
	PRIMARY KEY (test_id, question_id)
);

CREATE TABLE questions_answers (
	question_id INT,
    FOREIGN KEY (question_id) REFERENCES questions(id)
	ON DELETE CASCADE,
	answer_id INT,
    FOREIGN KEY (answer_id) REFERENCES answers(id)
	ON DELETE CASCADE,
	PRIMARY KEY (question_id, answer_id),
	isCorrect boolean
);