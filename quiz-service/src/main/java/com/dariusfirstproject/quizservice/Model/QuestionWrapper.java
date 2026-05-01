package com.dariusfirstproject.quizservice.Model;

import lombok.Data;

@Data
public class QuestionWrapper {
    private int id;
    private String questionTitle;
    private String category;
    private String option1;
    private String option2;
    private String option3;
    private String option4;

    public QuestionWrapper(String option4, String option3, String option2, String option1, String category, String questionTitle, int id) {
        this.option4 = option4;
        this.option3 = option3;
        this.option2 = option2;
        this.option1 = option1;
        this.category = category;
        this.questionTitle = questionTitle;
        this.id = id;
    }
}
