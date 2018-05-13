package ua.khnu.shtefanyankovska.entity;

import java.util.ArrayList;
import java.util.List;

/**
 *
 * Entity Question is a container of values associated with questions
 * <ul>
 * <li>question
 * <li>list of answers
 * </ul>
 *
 * @author Aleksey Shtefan
 *
 */
public class Question extends Entity {

    private static final long serialVersionUID = 1L;
    private String quest;
    private List<Answer> answers = new ArrayList<>();

    public List<Answer> getAnswers() {
        return answers;
    }

    public void setAnswers(List<Answer> answers) {
        this.answers = answers;
    }

    public String getQuest() {
        return quest;
    }

    public void setQuest(String quest) {
        this.quest = quest;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append(quest + System.lineSeparator());
        for (Answer answer : answers) {
            sb.append("\t" + answer + System.lineSeparator());
        }
        return sb.toString();
    }

}
