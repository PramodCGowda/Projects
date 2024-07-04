package com.sample.QuizApp.controller;

import com.sample.QuizApp.model.Questions;
import com.sample.QuizApp.service.QuestionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequestMapping("question")
public class QuestionController {

    @Autowired
    QuestionService questionService;
    @GetMapping("allQuestions")
    public ResponseEntity<Map> getAllQuestions() {
        return questionService.getAllQuestions();
    }

    @GetMapping("category/{category}")
    public ResponseEntity<Map> getQuestionsByCategory(@PathVariable String category) {
        return questionService.getQuestionsByCategory(category);
    }

    @PostMapping("add")
    public ResponseEntity<Map> saveQuestion(@RequestBody Questions questions) {
        return questionService.saveQuestion(questions);
    }

    @DeleteMapping("delete/{id}")
    public ResponseEntity<Map> deleteQuestion(@PathVariable int id) {
        return questionService.deleteQuestion(id);
    }
}
