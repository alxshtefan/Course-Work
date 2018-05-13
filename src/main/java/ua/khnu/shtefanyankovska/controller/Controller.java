package ua.khnu.shtefanyankovska.controller;

import org.apache.log4j.Logger;
import ua.khnu.shtefanyankovska.command.Command;
import ua.khnu.shtefanyankovska.command.CommandContainer;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.*;
import java.io.IOException;

@WebServlet("/Controller")
public class Controller extends HttpServlet{
    private static final long serialVersionUID = 1L;

    private static final Logger LOG = Logger.getLogger(Controller.class.getName());

    public void doGet(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse)
            throws IOException, ServletException {
        processRequest(httpServletRequest, httpServletResponse, "get");
    }

    @Override
    protected void doPost(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse)
            throws ServletException, IOException {
        processRequest(httpServletRequest, httpServletResponse, "post");
    }

    private void processRequest(HttpServletRequest request, HttpServletResponse response, String type)
            throws IOException, ServletException {
        String commandName = request.getParameter("command");
        LOG.debug("Looking for command name: " + commandName);

        Command command = CommandContainer.getCommand(commandName);
        LOG.debug("Found command: " + command);

        String path = command.execute(request, response, type);
        LOG.debug("Path to go: " + path);

        if (path == null) {
            LOG.warn("Send redirect to address: " + path + System.lineSeparator());
            response.sendRedirect(path);
        } else {
            if (type.equals("get")) {
                request.removeAttribute("command");
                LOG.trace("Forward to address: " + path + System.lineSeparator());
                request.getRequestDispatcher(path).forward(request, response);
            } else if (type.equals("post")) {
                LOG.trace("Send redirect to address: " + path + System.lineSeparator());
                response.sendRedirect(path);
            }
        }
    }
}
