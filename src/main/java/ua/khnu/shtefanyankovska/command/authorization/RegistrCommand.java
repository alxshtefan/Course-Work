package ua.khnu.shtefanyankovska.command.authorization;

import org.apache.log4j.Logger;
import ua.khnu.shtefanyankovska.command.Command;
import ua.khnu.shtefanyankovska.db.UserDAO;
import ua.khnu.shtefanyankovska.entity.User;
import ua.khnu.shtefanyankovska.util.MyException;
import ua.khnu.shtefanyankovska.util.Password;
import ua.khnu.shtefanyankovska.util.mail.CreateMailContent;
import ua.khnu.shtefanyankovska.util.mail.Message;
import ua.khnu.shtefanyankovska.util.path.PathToGo;

import javax.naming.NamingException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.UnsupportedEncodingException;
import java.security.NoSuchAlgorithmException;

public class RegistrCommand extends Command {

    private static final Logger LOG = Logger.getLogger(RegistrCommand.class.getName());

    @Override
    public String execute(HttpServletRequest request, HttpServletResponse response, String type) {
        User user = new User();
        user.setName(request.getParameter("name"));
        user.setSname(request.getParameter("sname"));
        user.setLogin(request.getParameter("login"));
        String password = request.getParameter("password");
        try {
            user.setPass(Password.hash(password));
        } catch (NoSuchAlgorithmException | UnsupportedEncodingException e1) {
            LOG.error("Error while hashing password");
            return PathToGo.ERROR_500_PAGE;
        }
        user.setEmail(request.getParameter("email"));

        LOG.trace("Created user with name: " + user.getName() + " and login " + user);

//        try {
//            UserDAO userDAO = new UserDAO();
//            userDAO.create(user);
//        } catch (MyException e) {
//            LOG.warn("Error while adding user: " + user);
//            request.getSession().setAttribute("error",
//                    "Произошла ошибка во время обработки данных!<br>Проверьте правильность введенных данных!");
//            return PathToGo.ERROR_PAGE;
//        }

//        Thread t = new Thread(() -> {
//            try {
//                Message.sendTextMessage(user.getEmail(), CreateMailContent.createCongratContent(user.getName()));
//            } catch (NamingException e) {
//                // noting to do
//            }
//        });
//        t.start();

      try {
        Message.sendTextMessage(user.getEmail(), CreateMailContent.createCongratContent(user.getName()));
      } catch (NamingException e) {
        // noting to do
      }

        LOG.trace("User with name: " + user.getName() + " and login " + user + " was added");

        return PathToGo.WELCOME_PAGE;
    }
}
