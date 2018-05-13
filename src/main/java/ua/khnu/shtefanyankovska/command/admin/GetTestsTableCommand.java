package ua.khnu.shtefanyankovska.command.admin;

import org.apache.log4j.Logger;
import ua.khnu.shtefanyankovska.command.Command;
import ua.khnu.shtefanyankovska.db.LogicDAO;
import ua.khnu.shtefanyankovska.db.TestDAO;
import ua.khnu.shtefanyankovska.entity.Test;
import ua.khnu.shtefanyankovska.util.MyException;
import ua.khnu.shtefanyankovska.util.path.PathToGo;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.ArrayList;
import java.util.List;

public class GetTestsTableCommand extends Command {

    private static final Logger LOG = Logger.getLogger(GetTestsTableCommand.class.getName());

    @Override
    public String execute(HttpServletRequest request, HttpServletResponse response, String type) {

        TestDAO testDAO = new TestDAO();
        try {
            List<Test> tests = testDAO.findAll();
            List<Integer> passingNumber = new ArrayList<>();

            LogicDAO logicDAO = new LogicDAO();
            for (Test test : tests) {
                int r = logicDAO.findNumberResults(test.getId());
                if (r == -1) {
                    LOG.error("Error while extracting all tests");
                    request.getSession().setAttribute("error",
                            "Произошла ошибка во время обработки данных!<br>Повторите запрос позже!");
                    return PathToGo.ERROR_PAGE;
                }
                passingNumber.add(logicDAO.findNumberResults(test.getId()));
            }

            request.setAttribute("tests", tests);
            request.setAttribute("passingNumber", passingNumber);
        } catch (MyException e) {
            LOG.error("Error while extracting all tests");
            request.getSession().setAttribute("error",
                    "Произошла ошибка во время обработки данных!<br>Проверьте правильность введенных данных!");
            return PathToGo.ERROR_PAGE;
        }

        return PathToGo.ADMIN_OPEN_TABLE_UT;
    }
}
