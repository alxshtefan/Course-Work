package ua.khnu.shtefanyankovska.db;

import org.apache.log4j.Logger;
import ua.khnu.shtefanyankovska.entity.Test;
import ua.khnu.shtefanyankovska.util.MyException;

import javax.naming.NamingException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class TestDAO implements AbstractDAO<Test> {

    private static final Logger LOG = Logger.getLogger(TestDAO.class);

    /* ====================== Necessary methods ====================== */

    @Override
    public List<Test> findAll() throws MyException {
        List<Test> tests = null;
        Connection con = null;
        PreparedStatement pstmt = null;
        ResultSet rs = null;
        try {
            con = DBManager.getConnection();
            pstmt = con.prepareStatement(Requests.GET_ALL_TESTS);
            rs = pstmt.executeQuery();

            tests = new ArrayList<>();
            while (rs.next()) {
                tests.add(Utils.extractTest(rs));
            }

            if (tests.get(0).getTitle().equals("stub")) {
                tests.remove(0);
            }

            return tests;
        } catch (SQLException | NamingException e) {
            LOG.error("Exception in findAll() : " + e.getMessage());
            throw new MyException(e.getMessage(), e);
        } finally {
            Utils.close(rs);
            Utils.close(pstmt);
            Utils.close(con);
        }
    }

    public List<Test> findAllBySubject(String subject) throws MyException {
        List<Test> tests = null;
        Connection con = null;
        PreparedStatement pstmt = null;
        ResultSet rs = null;
        try {
            con = DBManager.getConnection();
            pstmt = con.prepareStatement(Requests.GET_ALL_TESTS_BY_SUBJECT);
            pstmt.setString(1, subject);
            rs = pstmt.executeQuery();

            tests = new ArrayList<>();
            while (rs.next()) {
                tests.add(Utils.extractTest(rs));
            }

            if (!tests.isEmpty() && tests.get(0).getTitle().equals("stub")) {
                tests.remove(0);
            }

            return tests;
        } catch (SQLException | NamingException e) {
            LOG.error("Exception in findAll() : " + e.getMessage());
            throw new MyException(e.getMessage(), e);
        } finally {
            Utils.close(rs);
            Utils.close(pstmt);
            Utils.close(con);
        }
    }

    public Test findTestByTitle(String title) throws MyException {
        Connection con = null;
        PreparedStatement pstmt = null;
        ResultSet rs = null;
        try {
            con = DBManager.getConnection();
            pstmt = con.prepareStatement(Requests.GET_TEST_BY_TITLE);
            pstmt.setString(1, title);
            rs = pstmt.executeQuery();

            if (rs.next()) {
                return Utils.extractTest(rs);
            }
            return null;
        } catch (SQLException | NamingException e) {
            Utils.doRollback(con);
            LOG.error("Exception in findTestByTitle(title) : " + e.getMessage());
            throw new MyException(e.getMessage(), e);
        } finally {
            Utils.close(rs);
            Utils.close(pstmt);
            Utils.close(con);
        }
    }

    @Override
    public boolean create(Test test) throws MyException {
        Connection con = null;
        PreparedStatement pstmt = null;
        ResultSet rs = null;
        try {
            con = DBManager.getConnection();

            if (test.getId() == 0) {
                pstmt = con.prepareStatement(Requests.GET_MAX_ID_FROM_TEST_TABLE);
                rs = pstmt.executeQuery();
                if (rs.next()) {
                    int testID = rs.getInt("max(id)");
                    testID += 1;
                    test.setId(testID);
                } else {
                    LOG.warn("Cannot get max(id) from table 'tests'");
                    con.rollback();
                    return false;
                }
                Utils.close(rs);
                Utils.close(pstmt);
                LOG.info("ID number was added to test");
            }

            pstmt = con.prepareStatement(Requests.ADD_TEST);
            int k = 1;
            pstmt.setInt(k++, test.getId());
            pstmt.setString(k++, test.getTitle());
            pstmt.setInt(k++, test.getDifficult());
            pstmt.setInt(k++, test.getTime());
            pstmt.setInt(k++, test.getQuestions().size());
            pstmt.setString(k++, test.getSubject());

            if (pstmt.executeUpdate() > 0) {
                LOG.trace("Test " + test.getTitle() + " was added");
                return true;
            }

            LOG.warn("Cannot insert test into table");
            con.rollback();
            return false;
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

    @Override
    public boolean delete(int id) throws MyException {
        Connection con = null;
        PreparedStatement pstmt = null;
        try {
            con = DBManager.getConnection();
            pstmt = con.prepareStatement(Requests.DELETE_TEST);
            pstmt.setInt(1, id);

            if (pstmt.executeUpdate() > 0) {
                LOG.debug("Test was deleted");
                return true;
            }

            LOG.warn("Cannot delete test");
            con.rollback();
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
    public Test findEntityById(int id) throws MyException {
        // nothing to do
        return null;
    }

    @Override
    public boolean delete(Test entity) throws MyException {
        // nothing to do
        return false;
    }

    @Override
    public boolean update(Test entity) throws MyException {
        // TODO Auto-generated method stub
        return false;
    }

    /* =============================================================== */

}
