package ua.khnu.shtefanyankovska.command.authorization;

import org.apache.log4j.Logger;
import ua.khnu.shtefanyankovska.command.Command;
import ua.khnu.shtefanyankovska.util.path.PathToGo;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.util.Enumeration;

public class LogoutCommand extends Command {

    private static final Logger LOG = Logger.getLogger(LogoutCommand.class.getName());

    @Override
    public String execute(HttpServletRequest request, HttpServletResponse response, String type) {
        LOG.info("Logout starting");
        HttpSession session = request.getSession();
        Enumeration<String> str = session.getAttributeNames();

        while (str.hasMoreElements()) {
            session.removeAttribute(str.nextElement());
        }
        LOG.info("Session was freed");

        return PathToGo.WELCOME_PAGE;
    }

}
