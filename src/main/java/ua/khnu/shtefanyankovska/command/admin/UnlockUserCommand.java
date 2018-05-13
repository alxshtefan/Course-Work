package ua.khnu.shtefanyankovska.command.admin;

import org.apache.log4j.Logger;
import ua.khnu.shtefanyankovska.command.Command;
import ua.khnu.shtefanyankovska.db.UserDAO;
import ua.khnu.shtefanyankovska.entity.User;
import ua.khnu.shtefanyankovska.util.MyException;
import ua.khnu.shtefanyankovska.util.path.PathToGo;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

public class UnlockUserCommand extends Command {

    private static final Logger LOG = Logger.getLogger(UnlockUserCommand.class.getName());

    @Override
    public String execute(HttpServletRequest request, HttpServletResponse response, String type) {
        HttpSession session = request.getSession(false);
        String login = request.getParameter("login");
        session.removeAttribute("done");

        UserDAO userDAO = new UserDAO();
        User user = null;
        try {
            user = userDAO.findEntityByLogin(login);
        } catch (MyException e) {
            LOG.error("Error while extracting user: " + e.getMessage());
            session.setAttribute("error",
                    "Произошла ошибка во время обработки данных!<br>Проверьте правильность введенных данных!");
            return PathToGo.ERROR_PAGE;
        }

        if (user == null) {
            LOG.warn("There is any users with login: " + login);
            session.setAttribute("errorBlock", "Пользователя с логином: " + login + " не существует!");
            return PathToGo.ADMIN_MAIN_PAGE;
        }

        if (user.isCanWork()) {
            LOG.warn("User with login: " + login + " is already unblocked!");
            session.setAttribute("errorBlock", "Пользователя с логином: " + login + " не заблокирован!");
            return PathToGo.ADMIN_MAIN_PAGE;
        }

        user.setCanWork(true);
        try {
            userDAO.update(user);
            LOG.info("User " + user + " was unlocked");
        } catch (MyException e) {
            LOG.error("Error while updating user: " + e.getMessage());
            session.setAttribute("erorr",
                    "Произошла ошибка во время обработки данных!<br>Проверьте правильность введенных данных!");
            return PathToGo.ERROR_PAGE;
        }

        session.removeAttribute("error");
        session.removeAttribute("errorBlock");
        session.setAttribute("done", true);
        return PathToGo.ADMIN_MAIN_PAGE;
    }

}
