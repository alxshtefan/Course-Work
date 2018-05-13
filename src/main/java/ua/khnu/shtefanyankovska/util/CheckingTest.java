package ua.khnu.shtefanyankovska.util;

import ua.khnu.shtefanyankovska.entity.Answer;
import ua.khnu.shtefanyankovska.entity.Test;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.List;

public class CheckingTest {

    private Test basic;

    /**
     * @param basic is the {@link Test}, correct entity to compare with
     */
    public CheckingTest(Test basic) {
        this.basic = basic;
    }

    /**
     * Method comparing answers on all questions from the test. It is adding score
     * just if right answer is equals to users answer.
     *
     * <p>
     * Method get answers from each question and starting compare users and correct
     * answers. At first inequality itaration starting again. If all answers
     * coincided, score is adding.
     * </p>
     *
     * @param result is the {@link Test}, that user was compliting
     * @return int (0 - 100) percent ratio of correct and uncorrect answers
     */

    public int checkOut(Test result) {
        double numOfQuestions = basic.getQuestions().size();
        double numOfCorrectAnswers = 0;

        for (int i = 0; i < numOfQuestions; i++) {
            List<Answer> rightAnswers = basic.getQuestions().get(i).getAnswers();
            List<Answer> userAnswers = result.getQuestions().get(i).getAnswers();
            boolean flag = true;
            for (int j = 0; j < 4; j++) {
                if (rightAnswers.get(j).isCorrect() != userAnswers.get(j).isCorrect()) {
                    flag = false;
                    break;
                }
            }
            if (flag) {
                numOfCorrectAnswers += 1;
            }
        }

        double score = numOfCorrectAnswers / numOfQuestions;
        score = new BigDecimal(score).setScale(2, RoundingMode.UP).doubleValue();

        return (int) (score * 100);
    }

}
