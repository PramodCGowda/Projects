package com.sample.QuizApp.service;

import com.sample.QuizApp.model.Questions;
import com.sample.QuizApp.dao.QuestionDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import static org.springframework.http.HttpStatus.*;

@Service
public class QuestionService {

    @Autowired
    QuestionDao questionDao;
    public ResponseEntity<Map> getAllQuestions() {
        Map<String, Object> map = new LinkedHashMap<>();
        try {
            List<Questions> questions = questionDao.findAll();
            map.put("status", 1);
            map.put("Data", questions);
            return new ResponseEntity<>(map, OK);
        } catch(Exception e) {
            map.clear();
            map.put("status", 0);
            map.put("message", "Data is not found");
            return new ResponseEntity<>(map, BAD_REQUEST);
        }
    }

    public ResponseEntity<Map> getQuestionsByCategory(String category) {
        Map<String, Object> map = new LinkedHashMap<>();
        try {
            List<Questions> questions = questionDao.findByCategory(category);
            map.put("status", 1);
            map.put("Data by category", questions);
            return new ResponseEntity<>(map, OK);
        } catch(Exception e) {
            map.clear();
            map.put("status", 0);
            map.put("message", "Data is not found");
            return new ResponseEntity<>(map, BAD_REQUEST);
        }
    }

    public ResponseEntity<Map> saveQuestion(Questions questions) {
        Map<String, Object> map = new LinkedHashMap<>();
        questionDao.save(questions);
        map.put("status", 1);
        map.put("message", "Record is Saved Successfully!");
        return new ResponseEntity<>(map, CREATED);
    }

    public ResponseEntity<Map> deleteQuestion(int id) {
        Map<String, Object> map = new LinkedHashMap<>();
        try {
            questionDao.deleteById(id);
            map.put("status", 1);
            map.put("message", "Record is deleted Successfully!");
            return new ResponseEntity<>(map, OK);
        } catch (Exception e) {
            map.clear();
            map.put("status", 0);
            map.put("message", "record is not found");
            return new ResponseEntity<>(map, NOT_FOUND);
        }
    }
}
