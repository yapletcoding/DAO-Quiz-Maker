package com.yapletcoding.dao;

import com.yapletcoding.model.QuizQuestion;
import com.yapletcoding.model.TriviaApi;
import com.yapletcoding.model.TriviaApiResult;
import com.yapletcoding.services.RestTriviaService;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

/*
 * Quiz API:
 * https://opentdb.com/api_config.php
 */
public class ApiQuizQuestionDao implements QuizQuestionDao {
    RestTriviaService restTriviaService;

    @Override
    // CategoryNames
    public List<String> getQuizzes() {
        // TODO: Finish
        restTriviaService = new RestTriviaService();
        return restTriviaService.fetchCategoryNames();
    }

    @Override
    public List<QuizQuestion> getQuestionsForQuiz(String quizName) {

        // TODO: Finish
        restTriviaService = new RestTriviaService(quizName);
        TriviaApi triviaApi = restTriviaService.getTrivia();
        List<TriviaApiResult> results = triviaApi.getResults();

        List<QuizQuestion> quizQuestions = new ArrayList<>();
        for (int i = 0; i < results.size(); i++) {
            QuizQuestion q = new QuizQuestion();
            q.setQuestion(results.get(i).getQuestion());
            List<String> answers = results.get(i).getIncorrectAnswers();
            int random = new Random().nextInt(answers.size()+1);
            answers.add(random, results.get(i).getCorrectAnswer());
            q.setAnswers(answers.toArray(new String[answers.size()]));
            q.setCorrectAnswer(random + 1);             // because user type in 1,2,3,4 only.
            quizQuestions.add(q);
        }
        return quizQuestions;
    }
}
