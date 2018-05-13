package ua.khnu.shtefanyankovska.db;

import org.apache.log4j.Logger;
import ua.khnu.shtefanyankovska.entity.User;
import ua.khnu.shtefanyankovska.util.MyException;

import javax.naming.NamingException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class UserDAO implements AbstractDAO<User> {

    private static final Logger LOG = Logger.getLogger(UserDAO.class.getName());

    /* ====================== Necessary methods ====================== */

    @Override
    public boolean create(User user) throws MyException {
        Connection con = null;
        PreparedStatement pstmt = null;
        try {
            con = DBManager.getConnection();
            pstmt = con.prepareStatement(Requests.ADD_USER);
            int k = 1;
            pstmt.setString(k++, user.getName());
            pstmt.setString(k++, user.getSname());
            pstmt.setString(k++, user.getEmail());
            pstmt.setString(k++, user.getLogin());
            pstmt.setString(k++, user.getPass());
            pstmt.setBoolean(k++, true);

            if (pstmt.executeUpdate() > 0) {
                LOG.trace("User " + user + " was added");
                return true;
            }

            return false;

        } catch (SQLException | NamingException e) {
            LOG.error("Exception in create(user) : " + e.getMessage());
            throw new MyException(e.getMessage(), e);
        } finally {
            Utils.close(pstmt);
            Utils.close(con);
        }
    }

    @Override
    public boolean update(User user) throws MyException {
        Connection con = null;
        PreparedStatement pstmt = null;
        try {
            con = DBManager.getConnection();
            if (user.isCanWork()) {
                pstmt = con.prepareStatement(Requests.UPDATE_USER_STATUS_TRUE);
            } else {
                pstmt = con.prepareStatement(Requests.UPDATE_USER_STATUS_FALSE);
            }
            pstmt.setString(1, user.getLogin());

            return pstmt.executeUpdate() > 0 ? true : false;

        } catch (SQLException | NamingException e) {
            LOG.error("Exception in update(user) : " + e.getMessage());
            throw new MyException(e.getMessage(), e);
        } finally {
            Utils.close(pstmt);
            Utils.close(con);
        }
    }

    public User findEntityByLogin(String login) throws MyException {
        Connection con = null;
        PreparedStatement pstmt = null;
        ResultSet rs = null;
        try {
            con = DBManager.getConnection();
            pstmt = con.prepareStatement(Requests.GET_USER_BY_LOGIN);
            pstmt.setString(1, login);
            rs = pstmt.executeQuery();

            User user = null;
            if (rs.next()) {
                user = Utils.extractUser(rs);
                LOG.trace("User: " + login + " was extracted");
            }

            return user;
        } catch (SQLException | NamingException e) {
            LOG.error("Exception in findEntityByLogin(login) : " + e.getMessage());
            throw new MyException(e.getMessage(), e);
        } finally {
            Utils.close(rs);
            Utils.close(pstmt);
            Utils.close(con);
        }
    }

    @Override
    public List<User> findAll() throws MyException {
        List<User> users;
        Connection con = null;
        PreparedStatement pstmt = null;
        ResultSet rs = null;
        try {
            con = DBManager.getConnection();
            pstmt = con.prepareStatement(Requests.GET_ALL_USERS);
            rs = pstmt.executeQuery();

            users = new ArrayList<>();
            while (rs.next()) {
                users.add(Utils.extractUser(rs));
                ;
            }

            return users;
        } catch (SQLException | NamingException e) {
            LOG.error("Exception in findEntityByLogin(login) : " + e.getMessage());
            throw new MyException(e.getMessage(), e);
        } finally {
            Utils.close(rs);
            Utils.close(pstmt);
            Utils.close(con);
        }
    }

    /* ================================================================= */

    /* ===================== Unnecessary methods ===================== */

    @Override
    public User findEntityById(int id) {
        // nothing to do
        return null;
    }

    @Override
    public boolean delete(int id) {
        // nothing to do
        return false;
    }

    @Override
    public boolean delete(User entity) {
        // nothing to do
        return false;
    }

    /* ================================================================= */
}
