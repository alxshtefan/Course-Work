package ua.khnu.shtefanyankovska.util.mail;

public class CreateMailContent {

    private CreateMailContent() {
        // noting to do
    }

    public static String createCongratContent(String name) {
        return "<html><head><style type=\"text/css\">body {align-content: center;text-align: center;width: 80%;font-size: 20px;}.header {color: #008B8B;font-size: 50px;"
                + "	font-weight: bold;}</style></head><body><div class=\"header\">" + name
                + ", поздравляем!</div>Вы только что успешно зарегестировались в системе<br>"
                + "и можете начинать проходить тесты.<br> Желаем вам удачи.<p align=\"right\">Команда, Testing.</p>"
                + "<a align=\"right\" href=\"http://qrcoder.ru\" target=\"_blank\"><img src=\"http://qrcoder.ru/code/?http%3A%2F%2Flocalhost%3A8080%2FSummaryTask4%2F&4&0\" "
                + "align=\"right\" width=\"148\" height=\"148\" border=\"0\" title=\"QR код\"></a>\r\n</body></html>";
    }

    public static String createStatisticsContent(String name) {
        return "<html><head><style type=\"text/css\">body { text-align: center; align-content: center; width: 80%; font-size: 20px; }</style> </head>"
                + "<body>" + name
                + ", спасибо, что остаетесь с нами!<br>Статистика прохождения вами тестов находится в приложениях.<br>Мы верим, что вы можете лучше!<br>"
                + "Желаем удачи! <p align=\"right\">Команда, Testing.</p>"
                + "<a align=\"right\" href=\"http://qrcoder.ru\" target=\"_blank\"><img src=\"http://qrcoder.ru/code/?http%3A%2F%2Flocalhost%3A8080%2FSummaryTask4%2F&4&0\" "
                + "align=\"right\" width=\"148\" height=\"148\" border=\"0\" title=\"QR код\"></a>\r\n</body></html>";
    }

}
