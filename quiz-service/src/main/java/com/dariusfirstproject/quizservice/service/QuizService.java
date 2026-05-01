package com.dariusfirstproject.quizservice.service;

import com.dariusfirstproject.quizservice.DAO.QuizDAO;
import com.dariusfirstproject.quizservice.Model.QuestionWrapper;
import com.dariusfirstproject.quizservice.Model.Quiz;
import com.dariusfirstproject.quizservice.Model.Response;
import com.dariusfirstproject.quizservice.feign.QuizInterface;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class QuizService {
    @Autowired
    QuizDAO quizDAO;
    @Autowired
    QuizInterface quizInterface;

    public ResponseEntity<String> createQuiz(String category, int numQ, String title) {
        List<Integer> questions = quizInterface.getQuestionsForQuiz(category, numQ).getBody();
        Quiz quiz =  new Quiz();
        quiz.setTitle(title);
        quiz.setQuestionIds(questions);
        quizDAO.save(quiz);
        return new ResponseEntity("Quiz added successfully!", HttpStatus.CREATED);
    }
    public ResponseEntity<List<QuestionWrapper>> getQuizQuestions(int id) {
        Quiz quiz = quizDAO.findById(id).get();
        List<Integer> questionIds = quiz.getQuestionIds();
        List<QuestionWrapper> quizQuestions = quizInterface.getQuestionsFromId(questionIds).getBody();
        return new ResponseEntity(quizQuestions, HttpStatus.OK);
    }

    public ResponseEntity<Integer> calculateResponse(int id, List<Response> responses) {
        int right = quizInterface.getScore(responses).getBody();
        return new  ResponseEntity(right, HttpStatus.OK);
    }
}
