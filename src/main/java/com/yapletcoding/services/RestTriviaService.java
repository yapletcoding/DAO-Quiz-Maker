package com.yapletcoding.services;

import com.yapletcoding.model.TriviaApi;
import com.yapletcoding.services.model.TriviaCategories;
import com.yapletcoding.services.model.TriviaCategory;
import org.springframework.web.client.ResourceAccessException;
import org.springframework.web.client.RestClientResponseException;
import org.springframework.web.client.RestTemplate;

import java.util.ArrayList;
import java.util.List;

/*
 * Category #s here: https://opentdb.com/api_config.php
 */
public class RestTriviaService {
    public static boolean apiUrlParams = false;
    public static String DIFFICULTY = "medium";
    public static int NUMBER_OF_QUESTIONS = 10;
    private static final String API_URL = "https://opentdb.com/api.php";
    private static final String CATEGORY_URL = "https://opentdb.com/api_category.php";
    private final int GENERAL_KNOWLEDGE_QUIZ_ID = 9;

    private Integer numberOfQuestions, category;
    private String difficulty, type;
    private RestTemplate restTemplate = new RestTemplate();
    private TriviaCategories categories = fetchCategories();

    public RestTriviaService(){}

    public RestTriviaService(String category){
        this.difficulty = "medium";
        this.type = "multiple";
        this.numberOfQuestions = NUMBER_OF_QUESTIONS;
        this.category = findCategoryId(category);
    }

    public TriviaCategories fetchCategories(){
        return restTemplate.getForObject(CATEGORY_URL, TriviaCategories.class);
    }

    public List<String> fetchCategoryNames() {
        List<String> categoryNames = new ArrayList<>();
        for(TriviaCategory each: categories.getTriviaCategories()){
            categoryNames.add(each.getName());
        }
        return  categoryNames;
    }

    public int findCategoryId(String category){
        for(TriviaCategory each: categories.getTriviaCategories()){
            if(each.getName().equals(category)){
                return each.getId();
            }
        }
        return GENERAL_KNOWLEDGE_QUIZ_ID;
    }

    public TriviaApi getTrivia() throws RestClientResponseException {
        /*
         * Example:
         * "https://opentdb.com/api.php?amount=10&category=9&difficulty=medium&type=multiple"
         */

        // TODO: Finish
        TriviaApi triviaApi = null;
        String URL= API_URL+ "?amount="+ NUMBER_OF_QUESTIONS +"&category="+ this.category
                +"&difficulty="+ DIFFICULTY +"&type=multiple";
        try {
            triviaApi = restTemplate.getForObject(URL, TriviaApi.class);
        }  catch(RestClientResponseException e){
            System.out.println(e.getRawStatusCode() + ": " + e.getStatusText());
            e.printStackTrace();
        } catch(ResourceAccessException e){
            System.out.println("ERROR: unable to connect to API.");
            e.printStackTrace();
        }
        return triviaApi;
    }
}
