package ua.khnu.shtefanyankovska.command.tests;

import org.apache.log4j.Logger;
import ua.khnu.shtefanyankovska.command.Command;
import ua.khnu.shtefanyankovska.db.LogicDAO;
import ua.khnu.shtefanyankovska.db.TestDAO;
import ua.khnu.shtefanyankovska.entity.Test;
import ua.khnu.shtefanyankovska.util.MyException;
import ua.khnu.shtefanyankovska.util.path.PathToGo;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.util.List;

public class StartTestCommand extends Command {

  private static final Logger LOG = Logger.getLogger(StartTestCommand.class.getName());

  @Override
  public String execute(HttpServletRequest request, HttpServletResponse response, String type) {
    HttpSession session = request.getSession(false);
    int id = Integer.parseInt(request.getParameter("title").toCharArray()[request.getParameter("title").length()-1]+"");

    TestDAO testDAO = new TestDAO();
    Test test = null;
    try {
      List<Test> tests = testDAO.findAll();
      for (Test t : tests) {
        if (id == t.getId()) {
          LOG.trace("There is test with id: " + id);
          test = t;
          test.setId(id);
          break;
        }
      }
      if (test == null) {
        LOG.trace("There is no test with id: " + id);
        return PathToGo.TESTS_PAGE;
      }
    } catch (MyException e) {
      LOG.error("Error while getting test: " + e.getMessage());
      session.setAttribute("error",
        "Произошла ошибка во время обработки данных!<br>Похоже, что выбранный вами тест более не существует!");
      return PathToGo.ERROR_PAGE;
    }

    LogicDAO logicDAO = new LogicDAO();
    try {
      test = logicDAO.getTest(test);
      request.setAttribute("test", test);
    } catch (MyException e) {
      LOG.error("Error while getting test: " + e.getMessage());
      session.setAttribute("error",
        "Произошла ошибка во время обработки данных!<br>Похоже, что выбранный вами тест более не существует!");
      return PathToGo.ERROR_PAGE;
    }

    return PathToGo.TEST;
  }
}
