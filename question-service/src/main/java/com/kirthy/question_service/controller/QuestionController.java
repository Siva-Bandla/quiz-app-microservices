package com.kirthy.question_service.controller;

import com.kirthy.question_service.model.Question;
import com.kirthy.question_service.model.QuestionWrapper;
import com.kirthy.question_service.model.Response;
import com.kirthy.question_service.service.QuestionService;
import com.netflix.discovery.converters.Auto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("question")
public class QuestionController
{
    @Autowired
    QuestionService questionService;

    @Autowired
    Environment environment;

    @GetMapping("allQuestions")
    public ResponseEntity<List<Question>> getAllQuestions()
    {
        return questionService.getAllQuestions();
    }

    @GetMapping("category/{category}")
    public ResponseEntity<List<Question>> getQuestionsByCategory(@PathVariable String category)
    {
        return questionService.getQuestionsByCategory(category);
    }

    @GetMapping("difficultyLevel/{diffLevel}")
    public List<Question> getQuestionsByDifficultyLevel(@PathVariable String diffLevel)
    {
        return questionService.getQuestionsByDifficultyLevel(diffLevel);
    }

    @PostMapping("add")
    public ResponseEntity<Question> addQuestion(@RequestBody Question question)
    {
        return questionService.addQuestion(question);
    }

    @PutMapping("update/{id}")
    public ResponseEntity<Question> updateQuestion(@PathVariable Integer id, @RequestBody Question question)
    {
        return questionService.updateQuestion(id, question);
    }

    @DeleteMapping("delete/{id}")
    public ResponseEntity<Question> deleteQuestion(@PathVariable Integer id)
    {
        return questionService.deleteQuestion(id);
    }

    @GetMapping("generate")
    public ResponseEntity<List<Integer>> getQuestionsForQuiz(@RequestParam String category, @RequestParam int numQ){
        return questionService.getQuestionsForQuiz(category, numQ);
    }

    @PostMapping("/getQuestions")
    public ResponseEntity<List<QuestionWrapper>> getQuestionFromIds(@RequestBody List<Integer> questionIds){
        return questionService.getQuestionFromIds(questionIds);
    }

    @PostMapping("/getScore")
    public ResponseEntity<Integer> getScore(@RequestBody List<Response> responses){
        System.out.println(environment.getProperty("local.server.port"));
        return questionService.getScore(responses);
    }
}
