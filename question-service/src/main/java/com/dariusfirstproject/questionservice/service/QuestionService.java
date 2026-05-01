package com.dariusfirstproject.questionservice.service;


import com.dariusfirstproject.questionservice.DAO.QuestionDAO;
import com.dariusfirstproject.questionservice.Model.Question;
import com.dariusfirstproject.questionservice.Model.QuestionWrapper;
import com.dariusfirstproject.questionservice.Model.Response;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class QuestionService {
    @Autowired
    QuestionDAO questionDAO;
    public ResponseEntity<List<Question>> getAllQuestions() {
        try {
            return new ResponseEntity<>(questionDAO.findAll(), HttpStatus.OK);
        }catch(Exception e){
            e.printStackTrace();
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    public ResponseEntity<List<Question>> getQuestionsByCategory(String category) {
        try {
            return new ResponseEntity<>(questionDAO.findByCategory(category), HttpStatus.OK);
        }catch(Exception e){
            e.printStackTrace();
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }    }

    public ResponseEntity<String> addQuestion(Question question) {
        questionDAO.save(question);
        return new ResponseEntity("Question added successfully", HttpStatus.CREATED);
    }

    public ResponseEntity<List<Integer>> getQuestionsForQuiz(String categoryName, int numQuestions) {
        List<Integer> questionIds = questionDAO.findRandomQuestionsByCategory(categoryName, numQuestions);
        return new ResponseEntity<>(questionIds,HttpStatus.OK);
    }

    public ResponseEntity<List<QuestionWrapper>> getQuestionsFromId(List<Integer> questionIds) {
        List<QuestionWrapper> questionWrappers = new ArrayList<>();
        List<Question> questions = new ArrayList<>();
        for(Integer id: questionIds){
            questions.add(questionDAO.findById(id).get());
        }
        for(Question question: questions){
            QuestionWrapper questionWrapper = new QuestionWrapper(question.getOption4(),question.getOption3(),question.getOption2(),question.getOption1(),question.getCategory(),question.getQuestionTitle(),question.getId());
            questionWrappers.add(questionWrapper);
        }
        return new ResponseEntity<>(questionWrappers, HttpStatus.OK);
    }

    public ResponseEntity<Integer> getScore(List<Response> responses) {
        int right=0;
        for(Response r: responses){
            Question question = questionDAO.findById(r.getId()).get();
            if (r.getResponse().equals(question.getRightAnswer())){
                right++;
            }
        }
        return new  ResponseEntity(right, HttpStatus.OK);
    }
}

