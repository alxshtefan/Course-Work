package ua.khnu.shtefanyankovska.command.admin;

import org.apache.log4j.Logger;
import ua.khnu.shtefanyankovska.command.Command;
import ua.khnu.shtefanyankovska.db.LogicDAO;
import ua.khnu.shtefanyankovska.entity.Question;
import ua.khnu.shtefanyankovska.entity.Test;
import ua.khnu.shtefanyankovska.util.HelperToWorkingWithTest;
import ua.khnu.shtefanyankovska.util.MyException;
import ua.khnu.shtefanyankovska.util.path.PathToGo;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.util.List;

public class DeleteTestCommand extends Command {

    private static final Logger LOG = Logger.getLogger(DeleteTestCommand.class.getName());

    @Override
    public String execute(HttpServletRequest request, HttpServletResponse response, String type) {
        HttpSession session = request.getSession(false);
        session.removeAttribute("done");
        try {
            if (session.getAttribute("test") == null) {
                LOG.info("Structure of test was extracted");
                Test test = new Test();
                test.setId(0);
                test.setTitle(request.getParameter("title"));
                test.setSubject(request.getParameter("subject"));
                test.setDifficult(Integer.parseInt(request.getParameter("difficult")));
                test.setTime(Integer.parseInt(request.getParameter("time")));
                test.setqNumber(Integer.parseInt(request.getParameter("numOfQuest")));
                session.setAttribute("test", test);
                return PathToGo.ADMIN_PAGE_FILL_TEST;
            }
        } catch (Exception e) {
            session.removeAttribute("test");
            LOG.error("Error while extracting test: " + e.getMessage());
            session.setAttribute("errorCreate",
                    "Произошла ошибка во время обработки данных!<br>Введите данные заново!");
            return PathToGo.ADMIN_MAIN_PAGE;
        }

        Test test = (Test) session.getAttribute("test");
        try {
            List<Question> questions = HelperToWorkingWithTest.questionsForTest(request, test.getqNumber());
            test.setQuestions(questions);
        } catch (Exception e) {
            session.removeAttribute("test");
            LOG.error("Error while building test: " + e.getMessage());
            session.setAttribute("error",
                    "Произошла ошибка во время обработки данных!<br>Проверьте правильность введенных данных!");
            return PathToGo.ERROR_PAGE;
        }

        LOG.info("Test was extracted from request");

        try {
            LogicDAO logicDAO = new LogicDAO();
            if (logicDAO.addTest(test)) {
                LOG.info("All values of the test were added to the table");
                LOG.info("All relations of the test were added to the table");
            }
        } catch (MyException e) {
            session.removeAttribute("test");
            LOG.error("Error while adding test: " + e.getMessage());
            session.setAttribute("error",
                    "Произошла ошибка во время обработки данных!<br>Проверьте правильность введенных данных!");
            return PathToGo.ERROR_PAGE;
        }

        session.removeAttribute("errorCreate");
        session.removeAttribute("test");
        session.setAttribute("done", true);
        return PathToGo.ADMIN_MAIN_PAGE;
    }

}
