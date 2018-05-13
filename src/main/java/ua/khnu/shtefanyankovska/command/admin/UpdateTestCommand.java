package ua.khnu.shtefanyankovska.command.admin;

import org.apache.log4j.Logger;
import ua.khnu.shtefanyankovska.command.Command;
import ua.khnu.shtefanyankovska.db.LogicDAO;
import ua.khnu.shtefanyankovska.db.TestDAO;
import ua.khnu.shtefanyankovska.entity.Question;
import ua.khnu.shtefanyankovska.entity.Test;
import ua.khnu.shtefanyankovska.util.HelperToWorkingWithTest;
import ua.khnu.shtefanyankovska.util.MyException;
import ua.khnu.shtefanyankovska.util.path.PathToGo;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.util.List;

public class UpdateTestCommand extends Command {

    private static final Logger LOG = Logger.getLogger(UpdateTestCommand.class.getName());

    @Override
    public String execute(HttpServletRequest request, HttpServletResponse response, String type) {
        HttpSession session = request.getSession(false);
        session.removeAttribute("done");
        if (session.getAttribute("test") == null) {
            String title = request.getParameter("title");
            return tryingToExtractTest(session, title);
        }

        Test test = (Test) session.getAttribute("test");

        try {
            List<Question> questions = HelperToWorkingWithTest.questionsForTest(request, test.getqNumber());
            if (questions == null || questions.isEmpty()) {
                session.removeAttribute("test");
                LOG.warn("Error while building test");
                session.setAttribute("error",
                        "Произошла ошибка во время обработки данных!<br>Проверьте правильность введенных данных!");
                return PathToGo.ERROR_PAGE;
            }
            LOG.info(test);
            LOG.info("Test was extracted from request");

            LogicDAO logicDAO = new LogicDAO();
            int id = test.getId();
            if (logicDAO.deleteTest(test)) {
                LOG.info("Test was delete successfully");
                test.setId(id);
                test.setDifficult(Integer.parseInt(request.getParameter("difficult")));
                test.setTime(Integer.parseInt(request.getParameter("time")));
                test.setQuestions(questions);
                test.setqNumber(questions.size());

                if (logicDAO.addTest(test)) {
                    LOG.info("All values of the test were added to the table");
                    LOG.info("All relations of the test were added to the table");
                }
            }
        } catch (Exception e) {
            session.removeAttribute("test");
            LOG.error("Error while building test: " + e.getMessage());
            session.setAttribute("error",
                    "Произошла ошибка во время обработки данных!<br>Проверьте правильность введенных данных!");
            return PathToGo.ERROR_PAGE;
        }

        session.removeAttribute("errorUpdate");
        session.removeAttribute("test");
        session.setAttribute("done", true);
        return PathToGo.ADMIN_MAIN_PAGE;
    }

    private String tryingToExtractTest(HttpSession session, String title) {
        TestDAO testDAO = new TestDAO();
        Test test = new Test();
        try {
            List<Test> tests = testDAO.findAll();
            test.setTitle(title);
            if (tests.contains(test)) {
                LOG.trace("There is test with title: " + title);
                session.removeAttribute("errorUpdate");
                for (Test t : tests) {
                    if (t.equals(test)) {
                        test = t;
                        break;
                    }
                }
            } else {
                LOG.trace("There is no test with title: " + title);
                session.setAttribute("errorUpdate", "В базе нет теста с названием: " + title);
                return PathToGo.ADMIN_MAIN_PAGE;
            }
        } catch (MyException e) {
            LOG.error("Error while looking for test: " + e.getMessage());
            session.setAttribute("error",
                    "Произошла ошибка во время обработки данных!<br>Проверьте правильность введенных данных!");
            return PathToGo.ERROR_PAGE;
        }

        LogicDAO logicDAO = new LogicDAO();
        try {
            test = logicDAO.getTest(test);
            session.setAttribute("test", test);
        } catch (MyException e) {
            LOG.error("Error while getting test: " + e.getMessage());
            session.setAttribute("error",
                    "Произошла ошибка во время обработки данных!<br>Проверьте правильность введенных данных!");
            return PathToGo.ERROR_PAGE;
        }

        return PathToGo.ADMIN_PAGE_UPDATE_TEST;
    }

}
