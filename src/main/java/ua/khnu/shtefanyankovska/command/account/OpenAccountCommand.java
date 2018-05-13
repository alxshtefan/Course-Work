package ua.khnu.shtefanyankovska.command.account;

import org.apache.log4j.Logger;
import ua.khnu.shtefanyankovska.command.Command;
import ua.khnu.shtefanyankovska.db.LogicDAO;
import ua.khnu.shtefanyankovska.entity.User;
import ua.khnu.shtefanyankovska.util.MyException;
import ua.khnu.shtefanyankovska.util.path.PathToGo;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

public class OpenAccountCommand extends Command {
    private static final Logger LOG = Logger.getLogger(OpenAccountCommand.class.getName());

    /**
     * Method loading information about user and tests that he/she passed
     *
     * @return account.jsp
     */

    @Override
    public String execute(HttpServletRequest request, HttpServletResponse response, String type) {
        HttpSession session = request.getSession(false);
        User user = (User) session.getAttribute("user");
        if (user == null) {
            LOG.warn("There is no user in session");
            session.removeAttribute("user");
            return PathToGo.ERROR_500_PAGE;
        }

        LogicDAO logicDAO = new LogicDAO();
        try {
            request.setAttribute("testsResults", logicDAO.getPassedTests(user));
        } catch (MyException e) {
            LOG.error("Error while getting rusults of user: " + user);
            request.setAttribute("error", "Произошла ошибка во время обработки данных!<br>Повторите попытку позже!");
            return PathToGo.ERROR_PAGE;
        }

        return PathToGo.ACCOUNT_PAGE;
    }
}
