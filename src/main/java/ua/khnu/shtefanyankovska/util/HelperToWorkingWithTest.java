package ua.khnu.shtefanyankovska.util;

import ua.khnu.shtefanyankovska.entity.Answer;
import ua.khnu.shtefanyankovska.entity.Question;

import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class HelperToWorkingWithTest {

    /**
     * Method extracting questions and answers to them, returning list of those
     * questions
     *
     * @param request        - {@link HttpServletRequest} from which parameters of questions
     *                       and answers will be getting
     * @param numberOfQuests - number of questions in the current test
     * @return list of questions to the current test
     */
    public static List<Question> questionsForTest(HttpServletRequest request, int numberOfQuests) {
        List<Question> questions = new ArrayList<>();
        for (int i = 1; i <= numberOfQuests; i++) {
            String q = request.getParameter("question" + i);
            Question question = new Question();
            question.setQuest(q);
            question.setAnswers(answersForQuestion(request, i));
            questions.add(question);
        }

        return questions;
    }

    /**
     * Method extracting from request answers for current question
     *
     * @param request - {@link HttpServletRequest} from which parameters of answers will
     *                be getting
     * @param num     - number of the questions to add answers to
     * @return list of answers to the current question
     */
    public static List<Answer> answersForQuestion(HttpServletRequest request, int num) {
        List<Answer> answers = new ArrayList<>();
        String[] arrayRightAnswers = request.getParameterValues("ranswer" + num);
        if (arrayRightAnswers == null) {
            arrayRightAnswers = new String[1];
        }
        List<String> rightAnswers = Arrays.asList(arrayRightAnswers);

        for (int i = 1; i < 5; i++) {
            String a = request.getParameter("answer" + num + "text" + i);
            Answer answer = new Answer();
            answer.setAnswer(a);
            if (rightAnswers.contains(String.valueOf(i))) {
                answer.setCorrect(true);
            } else {
                answer.setCorrect(false);
            }
            answers.add(answer);
        }
        return answers;
    }

}
