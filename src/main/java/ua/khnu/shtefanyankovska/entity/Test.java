package ua.khnu.shtefanyankovska.entity;

import java.util.ArrayList;
import java.util.List;

/**
 * Entity Test is a container of values associated with test
 * <ul>
 * <li>title
 * <li>subject
 * <li>difficult
 * <li>time
 * <li>questions
 * </ul>
 *
 * @author Aleksey Shtefan
 */
public class Test extends Entity {

    private static final long serialVersionUID = 1L;
    private String title;
    private String subject;
    private int difficult;
    private int time; // in seconds
    private List<Question> questions = new ArrayList<>();
    private int qNumber;

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getSubject() {
        return subject;
    }

    public void setSubject(String subject) {
        this.subject = subject;
    }

    public int getDifficult() {
        return difficult;
    }

    public void setDifficult(int difficult) {
        this.difficult = difficult;
    }

    public int getTime() {
        return time;
    }

    public void setTime(int time) {
        this.time = time;
    }

    public List<Question> getQuestions() {
        return questions;
    }

    public void setQuestions(List<Question> questions) {
        this.questions = questions;
    }

    public int getqNumber() {
        return qNumber;
    }

    public void setqNumber(int qNumber) {
        this.qNumber = qNumber;
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((title == null) ? 0 : title.hashCode());
        return result;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj)
            return true;
        if (obj == null)
            return false;
        if (getClass() != obj.getClass())
            return false;
        Test other = (Test) obj;
        if (title == null) {
            if (other.title != null)
                return false;
        } else if (!title.equals(other.title))
            return false;
        return true;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append(title);
        for (Question q : questions) {
            sb.append(q + System.lineSeparator());
        }
        sb.append(System.lineSeparator());
        return sb.toString();
    }
}
