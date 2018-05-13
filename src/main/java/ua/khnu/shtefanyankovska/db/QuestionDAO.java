package ua.khnu.shtefanyankovska.db;

import org.apache.log4j.Logger;
import ua.khnu.shtefanyankovska.entity.Question;
import ua.khnu.shtefanyankovska.util.MyException;

import javax.naming.NamingException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class QuestionDAO implements AbstractDAO<Question> {

	private static final Logger LOG = Logger.getLogger(QuestionDAO.class.getName());

	/* ====================== Necessary methods ====================== */

	public boolean create(List<Question> questions) throws MyException {
		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		try {
			con = DBManager.getConnection();
			pstmt = con.prepareStatement(Requests.GET_MAX_ID_FROM_QUESTION_TABLE);
			rs = pstmt.executeQuery();
			if (rs.next()) {
				int questionsID = rs.getInt("max(id)");
				for (int i = 0; i < questions.size(); i++) {
					questionsID += 1;
					questions.get(i).setId(questionsID);
				}
			} else {
				LOG.warn("Cannot get max(id) from table 'questions'");
				con.rollback();
				return false;
			}
			Utils.close(rs);
			Utils.close(pstmt);

			LOG.info("All id numbers were added to all questions");

			for (Question question : questions) {
				pstmt = con.prepareStatement(Requests.ADD_QUESTION);
				pstmt.setInt(1, question.getId());
				pstmt.setString(2, question.getQuest());
				if (pstmt.executeUpdate() < 0) {
					LOG.warn("Cannot add question");
					con.rollback();
					return false;
				}
			}

			LOG.trace("All questions were added");
			return true;
		} catch (SQLException | NamingException e) {
			Utils.doRollback(con);
			LOG.error("Exception in create(questions) : " + e.getMessage());
			throw new MyException(e.getMessage(), e);
		} finally {
			Utils.close(rs);
			Utils.close(pstmt);
		}
	}

	public List<Question> findEntitiesById(int testID) throws MyException {
		Connection con = null;
		List<Question> questions = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		try {
			con = DBManager.getConnection();
			pstmt = con.prepareStatement(Requests.GET_QUESTION_BY_TEST_ID);
			pstmt.setInt(1, testID);
			rs = pstmt.executeQuery();

			questions = new ArrayList<>();
			while (rs.next()) {
				questions.add(Utils.extractQuestion(rs));
			}

			return questions;
		} catch (SQLException | NamingException e) {
			LOG.error("Exception in findEntitisById(di) : " + e.getMessage());
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
			pstmt = con.prepareStatement(Requests.DELETE_QUESTION);
			pstmt.setInt(1, id);

			if (pstmt.executeUpdate() > 0) {
				LOG.debug("Question was deleted");
				return true;
			}

			LOG.warn("Cannot delete question");
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

	/* ===================== UNNECESSARY METHODS ===================== */

	@Override
	public List<Question> findAll() throws MyException {
		// nothing to do
		return new ArrayList<Question>();
	}

	@Override
	public boolean delete(Question entity) throws MyException {
		// nothing to do
		return false;
	}

	@Override
	public boolean create(Question entity) throws MyException {
		// nothing to do
		return false;
	}

	@Override
	public Question findEntityById(int id) throws MyException {
		// nothing to do
		return null;
	}
	
	@Override
	public boolean update(Question question) throws MyException {
		// nothing to do
		return false;
	}

	/* =============================================================== */

}
