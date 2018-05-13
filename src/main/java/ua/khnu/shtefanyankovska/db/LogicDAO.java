package ua.khnu.shtefanyankovska.db;

import org.apache.log4j.Logger;
import ua.khnu.shtefanyankovska.entity.Answer;
import ua.khnu.shtefanyankovska.entity.Question;
import ua.khnu.shtefanyankovska.entity.Result;
import ua.khnu.shtefanyankovska.entity.Test;
import ua.khnu.shtefanyankovska.entity.User;
import ua.khnu.shtefanyankovska.util.MyException;

import javax.naming.NamingException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

public class LogicDAO {

    private static final Logger LOG = Logger.getLogger(LogicDAO.class.getName());

    /**
     * Adding test to the database.
     *
     * @param test - {@link Test} to add to the system
     * @return true if test was added, false - if not
     * @throws MyException
     */
    public boolean addTest(Test test) throws MyException {
        Connection con = null;
        try {
            con = DBManager.getConnection();
            con.setAutoCommit(false);

            TestDAO testDAO = new TestDAO();
            if (!testDAO.create(test)) {
                con.rollback();
                LOG.warn("Test wasn't added");
                return false;
            }

            QuestionDAO questionDAO = new QuestionDAO();
            if (!questionDAO.create(test.getQuestions())) {
                con.rollback();
                LOG.warn("Questions weren't added");
                return false;
            }

            AnswerDAO answerDAO = new AnswerDAO();
            for (Question q : test.getQuestions()) {
                if (!answerDAO.create(q.getAnswers())) {
                    con.rollback();
                    LOG.warn("Answers weren't added");
                    return false;
                }
            }

            if (addRelations(test)) {
                con.commit();
                return true;
            }

            con.rollback();
            return false;
        } catch (NamingException | SQLException e) {
            Utils.doRollback(con);
            LOG.error("Exception in addTest(test)");
            throw new MyException(e.getMessage(), e);
        } finally {
            Utils.close(con);
        }
    }

    /**
     * Adding relations between test and questions, questions and answers
     *
     * @param test - {@link Test} to add to the system
     * @return true if relations were added, false - if not
     * @throws MyException
     */
    private boolean addRelations(Test test) throws MyException {
        Connection con = null;
        PreparedStatement pstmt = null;
        try {
            con = DBManager.getConnection();
            con.setAutoCommit(false);

            pstmt = con.prepareStatement(Requests.ADD_RELATIONS_BETWEEN_TEST_QUESTION);
            pstmt.setInt(1, test.getId());

            List<Question> questions = test.getQuestions();
            for (Question question : questions) {
                pstmt.setInt(2, question.getId());
                pstmt.executeUpdate();
            }
            Utils.close(pstmt);

            pstmt = con.prepareStatement(Requests.ADD_RELATIONS_BETWEEN_QUESTION_ANSWER);
            for (Question question : questions) {
                pstmt.setInt(1, question.getId());
                List<Answer> answers = question.getAnswers();
                for (Answer answer : answers) {
                    pstmt.setInt(2, answer.getId());
                    if (answer.isCorrect()) {
                        pstmt.setBoolean(3, true);
                    } else {
                        pstmt.setBoolean(3, false);
                    }
                    pstmt.executeUpdate();
                }
            }

            con.commit();
            return true;
        } catch (SQLException | NamingException e) {
            Utils.doRollback(con);
            LOG.error("Exception in addRelations(test) : " + e.getMessage());
            throw new MyException(e.getMessage(), e);
        } finally {
            Utils.close(pstmt);
            Utils.close(con);
        }
    }

    /**
     * Fill test structure with questions and answers
     *
     * @param test - structure of the {@link Test}
     * @return filled test
     * @throws MyException
     */
    public Test getTest(Test test) throws MyException {
        Connection con = null;
        try {
            con = DBManager.getConnection();

            QuestionDAO questionDAO = new QuestionDAO();
            List<Question> questions = questionDAO.findEntitiesById(test.getId());

            test.setQuestions(questions);

            AnswerDAO answerDAO = new AnswerDAO();
            for (Question question : questions) {
                question.setAnswers(answerDAO.findEntitiesById(question.getId()));
            }

            return test;
        } catch (NamingException | SQLException e) {
            LOG.error("Exception in getTest(test) : " + e.getMessage());
            throw new MyException(e.getMessage(), e);
        } finally {
            Utils.close(con);
        }

    }

    /**
     * Deleting test, questions, answers that related to it
     *
     * @param test - @{link Test} to delete
     * @return true if operation success, false - if not
     * @throws MyException
     */
    public boolean deleteTest(Test test) throws MyException {
        Connection con = null;
        try {
            con = DBManager.getConnection();
            con.setAutoCommit(false);

            TestDAO testDAO = new TestDAO();
            testDAO.delete(test.getId());

            QuestionDAO questionDAO = new QuestionDAO();
            AnswerDAO answerDAO = new AnswerDAO();
            for (Question question : test.getQuestions()) {
                if (!questionDAO.delete(question.getId())) {
                    LOG.warn("Cannot delete question");
                    con.rollback();
                    return false;
                }
                for (Answer answer : question.getAnswers()) {
                    if (!answerDAO.delete(answer.getId())) {
                        LOG.warn("Cannot delete answer");
                        con.rollback();
                        return false;
                    }
                }
            }
            con.commit();
            return true;
        } catch (NamingException | SQLException e) {
            Utils.doRollback(con);
            LOG.error("Exception in deleteTest(test)");
            throw new MyException(e.getMessage(), e);
        } finally {
            Utils.close(con);
        }
    }

    /**
     * Extracting users results
     *
     * @param user - {@link User} whose results looking for
     * @return list of {@link Result} of the current user
     * @throws MyException
     */
    public List<Result> getPassedTests(User user) throws MyException {
        List<Result> results;
        Connection con = null;
        PreparedStatement pstmt = null;
        ResultSet rs = null;
        try {
            con = DBManager.getConnection();
            pstmt = con.prepareStatement(Requests.GET_USERS_RESULTS);
            pstmt.setInt(1, user.getId());
            rs = pstmt.executeQuery();

            results = new ArrayList<>();
            while (rs.next()) {
                Result result = new Result();
                result.setTestTitle(rs.getString("title"));
                result.setScore(rs.getInt("result"));
                Date testDate = new SimpleDateFormat("dd.MM.yyyy").parse(rs.getString("date"));
                Calendar c = Calendar.getInstance();
                c.add(Calendar.DATE, -3);
                if (!testDate.after(c.getTime())) {
                    continue;
                }
                result.setDate(rs.getString("date"));

                results.add(result);
            }

            return results;
        } catch (NamingException | SQLException e) {
            LOG.error("Exception in getPassedTests(user) " + e);
            throw new MyException(e.getMessage(), e);
        } catch (ParseException e) {
            LOG.error("Exception in getPassedTests(user) " + e);
            throw new MyException(e.getMessage(), e);
        } finally {
            Utils.close(rs);
            Utils.close(pstmt);
            Utils.close(con);
        }
    }

    /**
     * Adding users result for th test
     *
     * @param userID - id of the {@link User}
     * @param testID - id of the {@link Test}
     * @param score  - score current user for current test
     * @return true if operation success, false - if not
     * @throws MyException
     */
    public boolean addResult(int userID, int testID, int score) throws MyException {
        Connection con = null;
        PreparedStatement pstmt = null;
        try {
            con = DBManager.getConnection();
            pstmt = con.prepareStatement(Requests.ADD_RESULT);
            pstmt.setInt(1, userID);
            pstmt.setInt(2, testID);
            pstmt.setInt(3, score);
            pstmt.setString(4, new SimpleDateFormat("dd.MM.yyyy").format(new Date()));
            if (pstmt.executeUpdate() > 0) {
                return true;
            }
        } catch (NamingException | SQLException e) {
            LOG.error("Exception in addResult(...) " + e);
            throw new MyException(e.getMessage(), e);
        } finally {
            Utils.close(pstmt);
            Utils.close(con);
        }
        return false;
    }

    public int findNumberResults(int id) throws MyException {
        Connection con = null;
        PreparedStatement pstmt = null;
        ResultSet rs = null;
        try {
            con = DBManager.getConnection();
            pstmt = con.prepareStatement(Requests.GET_NUMBER_OF_PASSED_BY_TEST_ID);
            pstmt.setInt(1, id);
            rs = pstmt.executeQuery();
            if (rs.next()) {
                return rs.getInt("passingNumber");
            }
            return -1;
        } catch (NamingException | SQLException e) {
            LOG.error("Exception in findNumberResults(id) " + e);
            throw new MyException(e.getMessage(), e);
        } finally {
            Utils.close(rs);
            Utils.close(pstmt);
            Utils.close(con);
        }
    }

}
