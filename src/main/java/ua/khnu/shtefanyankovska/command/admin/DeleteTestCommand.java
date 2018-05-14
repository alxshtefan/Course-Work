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

public class DeleteTestCommand extends Command {

    private static final Logger LOG = Logger.getLogger(DeleteTestCommand.class.getName());

    @Override
    public String execute(HttpServletRequest request, HttpServletResponse response, String type) {
        HttpSession session = request.getSession(false);
        session.removeAttribute("done");
        String title = request.getParameter("title");

        TestDAO testDAO = new TestDAO();
        Test test = new Test();
        try {
            List<Test> tests = testDAO.findAll();
            test.setTitle(title);
            if (tests.contains(test)) {
                LOG.trace("There is test with title: " + title);
                session.removeAttribute("errorDelete");
                for (Test t : tests) {
                    if (t.equals(test)) {
                        test = t;
                        break;
                    }
                }
            } else {
                LOG.trace("There is no test with title: " + title);
                session.setAttribute("errorDelete", "В базе нет теста с названием: " + title);
                return PathToGo.ADMIN_MAIN_PAGE;
            }
        } catch (MyException e) {
            LOG.error("Error while deleting test: " + e.getMessage());
            session.setAttribute("error",
              "Произошла ошибка во время обработки данных!<br>Проверьте правильность введенных данных!");
            return PathToGo.ERROR_PAGE;
        }

        LogicDAO logicDAO = new LogicDAO();
        try {
            test = logicDAO.getTest(test);
            logicDAO.deleteTest(test);
            session.setAttribute("done", true);
            return PathToGo.ADMIN_MAIN_PAGE;
        } catch (MyException e) {
            LOG.error("Error while deleting test: " + e.getMessage());
            session.setAttribute("error",
              "Произошла ошибка во время обработки данных!<br>Проверьте правильность введенных данных!");
            return PathToGo.ERROR_PAGE;
        }
    }

}
