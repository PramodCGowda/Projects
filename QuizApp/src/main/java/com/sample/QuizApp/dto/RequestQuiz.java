package com.sample.QuizApp.dto;

import lombok.Data;

@Data
public class RequestQuiz {
    private String title;
    private String category;
    private int numQ;
}
