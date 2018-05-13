package ua.khnu.shtefanyankovska.command.tests;

import org.apache.log4j.Logger;
import ua.khnu.shtefanyankovska.command.Command;
import ua.khnu.shtefanyankovska.db.LogicDAO;
import ua.khnu.shtefanyankovska.db.TestDAO;
import ua.khnu.shtefanyankovska.entity.Answer;
import ua.khnu.shtefanyankovska.entity.Test;
import ua.khnu.shtefanyankovska.entity.User;
import ua.khnu.shtefanyankovska.util.CheckingTest;
import ua.khnu.shtefanyankovska.util.HelperToWorkingWithTest;
import ua.khnu.shtefanyankovska.util.MyException;
import ua.khnu.shtefanyankovska.util.path.PathToGo;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.util.Enumeration;
import java.util.List;

public class EndTestCommand extends Command {

    private static final Logger LOG = Logger.getLogger(EndTestCommand.class.getName());

    @Override
    public String execute(HttpServletRequest request, HttpServletResponse response, String type) {
        HttpSession session = request.getSession(false);
        String title = request.getParameter("testName");

        TestDAO testDAO = new TestDAO();
        LogicDAO logicDAO = new LogicDAO();
        Test example = new Test();
        Test result = new Test();
        try {
            example = testDAO.findTestByTitle(title);
            result = testDAO.findTestByTitle(title);
            LOG.trace("Test structure was extracted");
            example = logicDAO.getTest(example);
            result = logicDAO.getTest(result);
            LOG.trace("Test was fully extracted");
        } catch (MyException e) {
            LOG.error("Error while getting tests");
            session.setAttribute("error", "Произошла ошибка во время обработки данных!<br>Повторите попытку позже!");
            return PathToGo.ERROR_PAGE;
        }

        CheckingTest checkingTest = new CheckingTest(example);

        try {
            LOG.info("Starting getting answers");
            for (int i = 1; i <= result.getQuestions().size(); i++) {
                List<Answer> answers = HelperToWorkingWithTest.answersForQuestion(request, i);
                result.getQuestions().get(i - 1).setAnswers(answers);
            }
            LOG.info("Finishing getting answers");
        } catch (Exception e) {
            LOG.error("Error while building test: " + e.getMessage());
            session.setAttribute("error", "Произошла ошибка во время обработки данных!<br>Повторите попытку позже!");
            return PathToGo.ERROR_PAGE;
        }

        LOG.info("Test was extracted from request");

        int score = checkingTest.checkOut(result);

        LOG.info("User scored: " + score);

        User user = (User) session.getAttribute("user");
        try {
            if (logicDAO.addResult(user.getId(), example.getId(), score)) {
                LOG.info("Score was added");

                Enumeration<String> attrs = session.getAttributeNames();
                while (attrs.hasMoreElements()) {
                    String attr = attrs.nextElement();
                    if (!attr.equals("user")) {
                        session.removeAttribute(attr);
                    }
                }
            }
        } catch (MyException e) {
            LOG.error("Error while building test: " + e.getMessage());
            session.setAttribute("error", "Произошла ошибка во время обработки данных!<br>Повторите попытку позже!");
            return PathToGo.ERROR_PAGE;
        }

        session.setAttribute("correctTest", example);
        session.setAttribute("score", score);

        return PathToGo.END_TEST;
    }
}
