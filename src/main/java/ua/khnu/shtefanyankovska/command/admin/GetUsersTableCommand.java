package ua.khnu.shtefanyankovska.command.admin;

import org.apache.log4j.Logger;
import ua.khnu.shtefanyankovska.command.Command;
import ua.khnu.shtefanyankovska.db.UserDAO;
import ua.khnu.shtefanyankovska.entity.User;
import ua.khnu.shtefanyankovska.util.MyException;
import ua.khnu.shtefanyankovska.util.path.PathToGo;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.List;

public class GetUsersTableCommand extends Command {

    private static final Logger LOG = Logger.getLogger(GetUsersTableCommand.class.getName());

    @Override
    public String execute(HttpServletRequest request, HttpServletResponse response, String type) {

        UserDAO userDAO = new UserDAO();
        try {
            List<User> users = userDAO.findAll();
            users.remove(0);
            request.setAttribute("users", users);
        } catch (MyException e) {
            LOG.error("Error while extracting all users");
            request.getSession().setAttribute("error",
                    "Произошла ошибка во время обработки данных!<br>Проверьте правильность введенных данных!");
            return PathToGo.ERROR_PAGE;
        }

        return PathToGo.ADMIN_OPEN_TABLE_UT;
    }

}
