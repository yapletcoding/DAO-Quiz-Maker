package com.yapletcoding;

import com.yapletcoding.application.QuizMaker;
import com.yapletcoding.dao.ApiQuizQuestionDao;
import com.yapletcoding.dao.FileQuizQuestionDao;
import com.yapletcoding.dao.JdbcQuizQuestionDao;
import com.yapletcoding.dao.QuizQuestionDao;
import com.yapletcoding.services.RestTriviaService;
import org.apache.commons.dbcp2.BasicDataSource;

import java.util.Scanner;

public class QuizMakerCLI {
    /*
     * TODO: Make sure to create the QuizMaker database and then
     *  run the quiz_db.sql file to build the correct tables
     */
    static final String DATABASE_URL = "jdbc:postgresql://localhost:5432/QuizMaker";
    static Scanner userInput = new Scanner(System.in);

    public static void main(String[] args) {

        QuizQuestionDao loader = null;


        String response = askInput();

        if(response.equalsIgnoreCase("d")){
            BasicDataSource dataSource = new BasicDataSource();
            dataSource.setUrl(DATABASE_URL);
            dataSource.setUsername("postgres");
            dataSource.setPassword("postgres1");
            loader = new JdbcQuizQuestionDao(dataSource);
        } else if(response.equalsIgnoreCase("a")){
            loader = new ApiQuizQuestionDao();
            RestTriviaService.apiUrlParams = true;
            RestTriviaService.NUMBER_OF_QUESTIONS = askNumberOfQuestionsInput();
            RestTriviaService.DIFFICULTY = askDifficultyInput();
        } else {
            loader = new FileQuizQuestionDao();
        }

        QuizMaker quizMaker = new QuizMaker(loader);
        quizMaker.run();
    }

    public static String askInput(){
        String ask = "Enter A to load quiz questions from API.\n";
        ask += "Enter D to load quiz questions from database.\n";
        ask += "Enter F to load from file: ";
        System.out.println(ask);
        String response = userInput.nextLine();
        return response;
    }

    public static int askNumberOfQuestionsInput(){
        System.out.println("Pick the number of questions: ");
        int quizNumber = 0;
        do {
            try {
                quizNumber = Integer.parseInt(userInput.nextLine());

                if( quizNumber < 0 || quizNumber > 25 ){
                    throw new NumberFormatException();
                }

            }catch(NumberFormatException e){
                System.out.println("Please enter a valid number from 0 to 25");
            }
        } while(quizNumber == 0);
        return quizNumber;
    }

    public static String askDifficultyInput(){
        String difficulty = "";
        do {
            System.out.println("Choose difficulty: Easy, Medium or Hard");
            difficulty = userInput.nextLine();
        } while( !difficulty.equalsIgnoreCase("easy")
                && !difficulty.equalsIgnoreCase("medium")
                && !difficulty.equalsIgnoreCase("hard"));
        return difficulty;
    }
}
