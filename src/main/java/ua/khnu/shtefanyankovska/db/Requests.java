package ua.khnu.shtefanyankovska.db;

public class Requests {

	private Requests() {
		// nothing to do
	}

	public static final String GET_USER_BY_LOGIN = "SELECT * FROM users WHERE (login = ?)";
	public static final String GET_ALL_USERS = "SELECT * FROM users";
	public static final String ADD_USER = "INSERT INTO users VALUES (DEFAULT, ?, ?, ?, ?, ?, ?)";
	public static final String UPDATE_USER_STATUS_TRUE = "UPDATE users SET status = true WHERE login = ?";
	public static final String UPDATE_USER_STATUS_FALSE = "UPDATE users SET status = false WHERE login = ?";

	public static final String GET_USERS_RESULTS = "SELECT * FROM tests t INNER JOIN results r ON t.id = r.test_id WHERE r.user_id = ?";
	public static final String GET_NUMBER_OF_PASSED_BY_TEST_ID = "SELECT count(*) AS passingNumber FROM results WHERE test_id = ?";
	public static final String ADD_RESULT = "INSERT INTO results VALUE (?, ?, ?, ?)";

	public static final String GET_ALL_TESTS = "SELECT * FROM tests";
	public static final String GET_ALL_TESTS_BY_SUBJECT = "SELECT * FROM tests WHERE subject = ?";
	public static final String GET_TEST_BY_TITLE = "SELECT * FROM tests WHERE title = ?";
	public static final String ADD_TEST = "INSERT INTO tests VALUES (?, ?, ?, ?, ?, ?)";
	public static final String DELETE_TEST = "DELETE FROM tests WHERE id = ?";

	public static final String ADD_QUESTION = "INSERT INTO questions VALUES (?, ?)";
	public static final String DELETE_QUESTION = "DELETE FROM questions WHERE id = ?";
	public static final String GET_QUESTION_BY_TEST_ID = "SELECT id, quest FROM questions q INNER JOIN tests_questions tq ON q.id=tq.question_id WHERE tq.test_id= ?";

	public static final String ADD_ANSWER = "INSERT INTO answers VALUES (?, ?)";
	public static final String DELETE_ANSWER = "DELETE FROM answers WHERE id = ?";
	public static final String GET_ANSWER_BY_QUESTION_ID = "SELECT id, answer, isCorrect FROM answers a INNER JOIN questions_answers qa ON a.id=qa.answer_id WHERE qa.question_id = ?";

	public static final String GET_MAX_ID_FROM_TEST_TABLE = "SELECT max(id) FROM tests";
	public static final String GET_MAX_ID_FROM_QUESTION_TABLE = "SELECT max(id) FROM questions";
	public static final String GET_MAX_ID_FROM_ANSWER_TABLE = "SELECT max(id) FROM answers";

	public static final String ADD_RELATIONS_BETWEEN_TEST_QUESTION = "INSERT INTO tests_questions VALUES (?, ?)";
	public static final String ADD_RELATIONS_BETWEEN_QUESTION_ANSWER = "INSERT INTO questions_answers VALUES (?, ?, ?)";

}
