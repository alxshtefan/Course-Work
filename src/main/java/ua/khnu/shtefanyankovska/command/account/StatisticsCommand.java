package ua.khnu.shtefanyankovska.command.account;

import org.apache.log4j.Logger;
import ua.khnu.shtefanyankovska.command.Command;
import ua.khnu.shtefanyankovska.db.LogicDAO;
import ua.khnu.shtefanyankovska.entity.Result;
import ua.khnu.shtefanyankovska.entity.User;
import ua.khnu.shtefanyankovska.util.MyException;
import ua.khnu.shtefanyankovska.util.filecreator.Creator;
import ua.khnu.shtefanyankovska.util.filecreator.CreatorPDF;
import ua.khnu.shtefanyankovska.util.filecreator.CreatorTXT;
import ua.khnu.shtefanyankovska.util.filecreator.CreatorXLSX;
import ua.khnu.shtefanyankovska.util.mail.CreateMailContent;
import ua.khnu.shtefanyankovska.util.mail.Message;
import ua.khnu.shtefanyankovska.util.path.PathToGo;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.File;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

public class StatisticsCommand extends Command {
    private static final Logger LOG = Logger.getLogger(StatisticsCommand.class.getName());

    /**
     * Method sending message with statistics file to users mail. User can choose
     * type of file on account page.
     */

    @Override
    public String execute(HttpServletRequest request, HttpServletResponse response, String type) {
        HttpSession session = request.getSession(false);

        User user = (User) session.getAttribute("user");

        LogicDAO logicDAO = new LogicDAO();
        List<Result> results = null;
        try {
            results = logicDAO.getPassedTests(user);
        } catch (MyException e) {
            LOG.error("Error while getting rusults of user: " + user);
            request.setAttribute("error", "Произошла ошибка во время обработки данных!<br>Повторите попытку позже!");
            return PathToGo.ERROR_PAGE;
        }

        String fileFormat = request.getParameter("statis");

        Creator creator = null;
        if (fileFormat.equals("txt")) {
            creator = new CreatorTXT();
        } else if (fileFormat.equals("xls")) {
            creator = new CreatorXLSX();
        } else if (fileFormat.equals("pdf")) {
            creator = new CreatorPDF();
        }

        File file = new File("Statistics_" + new SimpleDateFormat("dd.MM.yyyy").format(new Date()) + "." + fileFormat);
        try {
            if (creator != null) {
                file = creator.create(results, file);
            }
        } catch (MyException e1) {
            LOG.error(e1.getMessage());
            request.setAttribute("error", "Произошла ошибка во время обработки данных!<br>Повторите попытку позже!");
            return PathToGo.ERROR_PAGE;
        }

        Message.sendFileMessage(user.getEmail(), CreateMailContent.createStatisticsContent(user.getName()), file);

        return PathToGo.INDEX_PAGE;
    }
}
