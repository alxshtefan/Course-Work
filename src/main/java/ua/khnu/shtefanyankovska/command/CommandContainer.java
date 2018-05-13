package ua.khnu.shtefanyankovska.command;

import org.apache.log4j.Logger;
import ua.khnu.shtefanyankovska.command.account.OpenAccountCommand;
import ua.khnu.shtefanyankovska.command.account.StatisticsCommand;
import ua.khnu.shtefanyankovska.command.admin.AddTestCommand;
import ua.khnu.shtefanyankovska.command.admin.BlockUserCommand;
import ua.khnu.shtefanyankovska.command.admin.DeleteTestCommand;
import ua.khnu.shtefanyankovska.command.admin.GetTestsTableCommand;
import ua.khnu.shtefanyankovska.command.admin.GetUsersTableCommand;
import ua.khnu.shtefanyankovska.command.admin.UnlockUserCommand;
import ua.khnu.shtefanyankovska.command.admin.UpdateTestCommand;
import ua.khnu.shtefanyankovska.command.authorization.LoginCommand;
import ua.khnu.shtefanyankovska.command.authorization.LogoutCommand;
import ua.khnu.shtefanyankovska.command.authorization.RegistrCommand;
import ua.khnu.shtefanyankovska.command.tests.EndTestCommand;
import ua.khnu.shtefanyankovska.command.tests.OpenTestsPageCommand;
import ua.khnu.shtefanyankovska.command.tests.StartTestCommand;

import java.util.HashMap;
import java.util.Map;

public class CommandContainer {
    private static final Logger LOG = Logger.getLogger(CommandContainer.class.getName());
    private static Map<String, Command> commands = new HashMap<>();

    static {
        // ===================================================== LOGIN COMMANDS
        commands.put("login", new LoginCommand());
        commands.put("logout", new LogoutCommand());
        commands.put("registration", new RegistrCommand());

        // ===================================================== ADMIN COMMANDS
        commands.put("addTest", new AddTestCommand());
        commands.put("updateTest", new UpdateTestCommand());
        commands.put("deleteTest", new DeleteTestCommand());

        commands.put("blockUser", new BlockUserCommand());
        commands.put("unLockUser", new UnlockUserCommand());

        commands.put("openUsers", new GetUsersTableCommand());
        commands.put("openTests", new GetTestsTableCommand());

        // ===================================================== ACCOUNT COMMANDS
        commands.put("openAccountPage", new OpenAccountCommand());
        commands.put("getUsersStatistics", new StatisticsCommand());

        // ===================================================== TESTS COMMANDS
        commands.put("openTestsPage", new OpenTestsPageCommand());
        commands.put("startTest", new StartTestCommand());
        commands.put("endTest", new EndTestCommand());

        LOG.info("CommandContainer was filled");
    }

    private CommandContainer() {
        // nothing to do
    }

    public static Command getCommand(String commandName) {
        if (commandName == null || !commands.containsKey(commandName)) {
            LOG.warn("Command with name: " + commandName + " wasn't found");
            return commands.get("noCommand");
        }

        return commands.get(commandName);
    }
}
