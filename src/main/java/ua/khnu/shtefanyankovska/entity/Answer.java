package ua.khnu.shtefanyankovska.entity;

/**
 *
 * Entity Answer is a container of values associated with answer
 * <ul>
 * <li>answer
 * <li>is answer correct
 * </ul>
 *
 * @author Aleksey Shtefan
 *
 */
public class Answer extends Entity {

    private static final long serialVersionUID = 1L;
    private String ans;
    private boolean isCorrect;

    public boolean isCorrect() {
        return isCorrect;
    }

    public void setCorrect(boolean isCorrect) {
        this.isCorrect = isCorrect;
    }

    public String getAnswer() {
        return ans;
    }

    public void setAnswer(String answer) {
        this.ans = answer;
    }

    @Override
    public String toString() {
        return ans + " " + isCorrect;
    }
}
