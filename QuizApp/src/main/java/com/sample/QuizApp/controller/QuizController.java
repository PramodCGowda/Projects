package com.sample.QuizApp.controller;

import com.sample.QuizApp.dto.RequestQuiz;
import com.sample.QuizApp.model.Response;
import com.sample.QuizApp.service.QuizService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("quiz")
public class QuizController {

    @Autowired
    QuizService quizService;
    @PostMapping("create")
    public ResponseEntity<Map> create(@RequestBody RequestQuiz requestQuiz) {
        return quizService.create(requestQuiz.getTitle(), requestQuiz.getCategory(), requestQuiz.getNumQ());
    }

    @GetMapping("getQuiz/{id}")
    public ResponseEntity<Map> getQuiz(@PathVariable Integer id) {
        return quizService.getQuiz(id);
    }

    @PostMapping("submit/{id}")
    public ResponseEntity<Map> submitQuiz(@PathVariable Integer id, @RequestBody List<Response> responses) {
        return quizService.calculateResult(id, responses);
    }
}
