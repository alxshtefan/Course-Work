package ua.khnu.shtefanyankovska.db;

import org.apache.log4j.Logger;
import ua.khnu.shtefanyankovska.entity.Answer;
import ua.khnu.shtefanyankovska.entity.Question;
import ua.khnu.shtefanyankovska.entity.Test;
import ua.khnu.shtefanyankovska.entity.User;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;

public class Utils {

    private static final Logger LOG = Logger.getLogger(Utils.class.getName());

    private Utils() {
        // nothing to do
    }

    public static User extractUser(ResultSet rs) throws SQLException {
        User user = new User();
        user.setId(rs.getInt("id"));
        user.setLogin(rs.getString("login"));
        user.setName(rs.getString("name"));
        user.setSname(rs.getString("sname"));
        user.setEmail(rs.getString("mail"));
        user.setPass(rs.getString("password"));
        user.setCanWork(rs.getBoolean("status"));
        return user;
    }

    public static Test extractTest(ResultSet rs) throws SQLException {
        Test test = new Test();
        test.setId(rs.getInt("id"));
        test.setTitle(rs.getString("title"));
        test.setDifficult(rs.getInt("difficult"));
        test.setTime(rs.getInt("time"));
        test.setSubject(rs.getString("subject"));
        test.setqNumber(rs.getInt("questCount"));
        return test;
    }

    public static Question extractQuestion(ResultSet rs) throws SQLException {
        Question question = new Question();
        question.setId(rs.getInt("id"));
        question.setQuest(rs.getString("quest"));
        return question;
    }

    public static Answer extractAnswers(ResultSet rs) throws SQLException {
        Answer answer = new Answer();
        answer.setId(rs.getInt("id"));
        answer.setAnswer(rs.getString("answer"));
        answer.setCorrect(rs.getBoolean("isCorrect"));
        return answer;
    }

    public static void close(AutoCloseable ac) {
        if (ac != null) {
            try {
                ac.close();
            } catch (Exception e) {
                LOG.error("Exception in closing " + ac.getClass().getSimpleName());
            }
        }
    }

    public static void doRollback(Connection con) {
        if (con != null) {
            try {
                con.rollback();
            } catch (SQLException e) {
                // nothing to do
            }
        }
    }

}
