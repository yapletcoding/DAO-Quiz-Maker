package com.yapletcoding.dao;

import com.yapletcoding.model.QuizQuestion;

import java.util.List;

public interface QuizQuestionDao {

    List<String> getQuizzes();

    List<QuizQuestion> getQuestionsForQuiz(String quizName);

}
