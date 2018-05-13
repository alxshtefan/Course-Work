package ua.khnu.shtefanyankovska.db;

import org.apache.log4j.Logger;
import ua.khnu.shtefanyankovska.entity.Answer;
import ua.khnu.shtefanyankovska.util.MyException;

import javax.naming.NamingException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class AnswerDAO implements AbstractDAO<Answer> {

    private static final Logger LOG = Logger.getLogger(AnswerDAO.class.getName());

    /* ====================== Necessary methods ====================== */

    public boolean create(List<Answer> answers) throws MyException {
        Connection con = null;
        PreparedStatement pstmt = null;
        ResultSet rs = null;
        try {
            con = DBManager.getConnection();
            pstmt = con.prepareStatement(Requests.GET_MAX_ID_FROM_ANSWER_TABLE);
            rs = pstmt.executeQuery();
            if (rs.next()) {
                int answersID = rs.getInt("max(id)");
                for (int i = 0; i < answers.size(); i++) {
                    answersID += 1;
                    answers.get(i).setId(answersID);
                }
            } else {
                LOG.warn("Cannot get max(id) from table 'answers'");
                con.rollback();
                return false;
            }
            Utils.close(rs);
            Utils.close(pstmt);

            LOG.info("All id numbers were added to all answers");

            for (Answer answer : answers) {
                pstmt = con.prepareStatement(Requests.ADD_ANSWER);
                pstmt.setInt(1, answer.getId());
                pstmt.setString(2, answer.getAnswer());
                if (pstmt.executeUpdate() < 0) {
                    LOG.warn("Cannot add answer");
                    con.rollback();
                    return false;
                }
            }
            return true;
        } catch (SQLException | NamingException e) {
            Utils.doRollback(con);
            LOG.error("Exception in create(test) : " + e.getMessage());
            throw new MyException(e.getMessage(), e);
        } finally {
            Utils.close(rs);
            Utils.close(pstmt);
            Utils.close(con);
        }
    }

    public List<Answer> findEntitiesById(int questionID) throws MyException {
        List<Answer> answers = null;
        Connection con = null;
        PreparedStatement pstmt = null;
        ResultSet rs = null;
        try {
            con = DBManager.getConnection();
            pstmt = con.prepareStatement(Requests.GET_ANSWER_BY_QUESTION_ID);
            pstmt.setInt(1, questionID);
            rs = pstmt.executeQuery();

            answers = new ArrayList<>();
            while (rs.next()) {
                answers.add(Utils.extractAnswers(rs));
            }

            return answers;
        } catch (SQLException | NamingException e) {
            LOG.error("Exception in findEntitiesById(di) : " + e.getMessage());
            throw new MyException(e.getMessage(), e);
        } finally {
            Utils.close(rs);
            Utils.close(pstmt);
            Utils.close(con);
        }
    }

    @Override
    public boolean delete(int id) throws MyException {
        Connection con = null;
        PreparedStatement pstmt = null;
        try {
            con = DBManager.getConnection();
            pstmt = con.prepareStatement(Requests.DELETE_ANSWER);
            pstmt.setInt(1, id);

            if (pstmt.executeUpdate() > 0) {
                LOG.debug("Question was deleted");
                return true;
            }

            LOG.warn("Cannot delete answer");
            return false;
        } catch (SQLException | NamingException e) {
            LOG.error("Exception in delete(id) : " + e.getMessage());
            throw new MyException(e.getMessage(), e);
        } finally {
            Utils.close(pstmt);
            Utils.close(con);
        }
    }

    /* =============================================================== */

    /* ===================== Unnecessary methods ===================== */

    @Override
    public List<Answer> findAll() throws MyException {
        // nothing to do
        return new ArrayList<Answer>();
    }

    @Override
    public boolean delete(Answer entity) throws MyException {
        // nothing to do
        return false;
    }

    @Override
    public boolean create(Answer entity) throws MyException {
        // nothing to do
        return false;
    }

    @Override
    public Answer findEntityById(int id) throws MyException {
        // nothing to do
        return null;
    }

    @Override
    public boolean update(Answer entity) throws MyException {
        // TODO Auto-generated method stub
        return false;
    }

    /* =============================================================== */

}
