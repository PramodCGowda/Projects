package com.sample.QuizApp.service;

import com.sample.QuizApp.dao.QuestionDao;
import com.sample.QuizApp.dao.QuizDao;
import com.sample.QuizApp.model.QuestionWrapper;
import com.sample.QuizApp.model.Questions;
import com.sample.QuizApp.model.Quiz;
import com.sample.QuizApp.model.Response;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.*;

import static org.springframework.http.HttpStatus.*;

@Service
public class QuizService {

    @Autowired
    QuizDao quizDao;

    @Autowired
    QuestionDao questionDao;

    public ResponseEntity<Map> create(String title, String category, int nQue) {
        Map<String, Object> map = new LinkedHashMap<>();
        try {
            List<Questions> questions = questionDao.findRandomQuestionByCategory(category, nQue);
            Quiz quiz = new Quiz();
            quiz.setTitle(title);
            quiz.setQuestions(questions);
            quizDao.save(quiz);
            map.put("status", 1);
            map.put("message", "Quiz " + title + "created successfully");
            return new ResponseEntity<>(map, CREATED);
        } catch (Exception e) {
            map.clear();
            map.put("status", 0);
            map.put("message", "failed to create quiz");
            return new ResponseEntity<>(map, BAD_REQUEST);
        }
    }

    public ResponseEntity<Map> getQuiz(Integer id) {
        Map<String, Object> map = new LinkedHashMap<>();
        try {
            Optional<Quiz> quiz = quizDao.findById(id);
            List<Questions> questionsFromDB = quiz.get().getQuestions();
            List<QuestionWrapper> questionForUser = new ArrayList<>();
            for (Questions q : questionsFromDB) {
                QuestionWrapper qw = new QuestionWrapper(q.getId(), q.getQuestion(), q.getOption1(), q.getOption2(), q.getOption3(), q.getOption4());
                questionForUser.add(qw);
            }
            map.put("status", 1);
            map.put("data", questionForUser);
            return new ResponseEntity<>(map, OK);
        } catch (Exception e) {
            map.clear();
            map.put("status", 0);
            map.put("message", "data not found");
            return new ResponseEntity<>(map, BAD_REQUEST);
        }
    }

    public ResponseEntity<Map> calculateResult(Integer id, List<Response> responses) {
        Map<String, Object> map = new LinkedHashMap<>();
        Quiz quiz = quizDao.findById(id).get();
        List<Questions> questions = quiz.getQuestions();
        int result = 0;
        int i = 0;
        for (Response response : responses) {
            if (response.getUserAnswer().equals(questions.get(i).getCorrectAnswer())) {
                result++;
            }
            i++;
        }
        map.put("status", 1);
        map.put("result", result);
        return new ResponseEntity<>(map, OK);
    }
}
