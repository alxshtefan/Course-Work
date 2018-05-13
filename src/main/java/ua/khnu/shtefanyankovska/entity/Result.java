package ua.khnu.shtefanyankovska.entity;

/**
 *
 * Entity Result is a container of values associated with result
 * <ul>
 * <li>title of the test
 * <li>score
 * <li>date
 * </ul>
 *
 * @author Aleksey Shtefan
 *
 */
public class Result {

    private String testTitle;
    private int score;
    private String date;

    public String getTestTitle() {
        return testTitle;
    }

    public void setTestTitle(String testTitle) {
        this.testTitle = testTitle;
    }

    public int getScore() {
        return score;
    }

    public void setScore(int result) {
        this.score = result;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    @Override
    public String toString() {
        return testTitle + "\t" + score + "\t" + date + System.lineSeparator();
    }
}
