package ua.khnu.shtefanyankovska.command.authorization;

import org.apache.log4j.Logger;
import ua.khnu.shtefanyankovska.command.Command;
import ua.khnu.shtefanyankovska.db.UserDAO;
import ua.khnu.shtefanyankovska.entity.User;
import ua.khnu.shtefanyankovska.util.MyException;
import ua.khnu.shtefanyankovska.util.Password;
import ua.khnu.shtefanyankovska.util.path.PathToGo;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.UnsupportedEncodingException;
import java.security.NoSuchAlgorithmException;

public class LoginCommand extends Command {

    private static final Logger LOG = Logger.getLogger(LoginCommand.class.getName());

    @Override
    public String execute(HttpServletRequest request, HttpServletResponse response, String type) {
        HttpSession session = request.getSession(true);
        String login = request.getParameter("login");
        String password = request.getParameter("password");
        try {
            password = Password.hash(password);
        } catch (NoSuchAlgorithmException | UnsupportedEncodingException e1) {
            LOG.error("Error while hashing password");
            return PathToGo.ERROR_500_PAGE;
        }

        LOG.trace("Looking for user with login: " + login);

        User user = null;
        try {
            UserDAO userDAO = new UserDAO();
            user = userDAO.findEntityByLogin(login);
        } catch (MyException e) {
            LOG.error("Error while extracting user: " + login);
            session.setAttribute("error",
              "Произошла ошибка во время обработки данных!<br>Проверьте правильность введенных данных!");
            return PathToGo.ERROR_PAGE;
        }

        if (user == null) {
            LOG.warn("User: " + user + " is invalid");
            session.setAttribute("invalidLogin", true);
            return PathToGo.STAY_ON_THIS_PAGE;
        } else if (!user.isCanWork()) {
            LOG.warn("User: " + user + " is invalid");
            session.setAttribute("blocked", true);
            session.setAttribute("login", user.getLogin());
            return PathToGo.STAY_ON_THIS_PAGE;
        } else if (!user.isValid(password)) {
            LOG.warn("User: " + user + " is invalid");
            session.setAttribute("invalidPassword", true);
            session.setAttribute("login", user.getLogin());
            return PathToGo.STAY_ON_THIS_PAGE;
        } else {
            LOG.trace("User: " + user + " is valid");

            if (session.getAttribute("error") != null) {
                session.removeAttribute("error");
            }

            session.setMaxInactiveInterval(600);
            session.setAttribute("user", user);
            LOG.trace("User: " + user + " was added to session");

            if (user.getLogin().equals("admin")) {
                session.setMaxInactiveInterval(9999);
                return PathToGo.ADMIN_MAIN_PAGE;
            } else {
                return PathToGo.INDEX_PAGE;
            }
        }
    }
}
