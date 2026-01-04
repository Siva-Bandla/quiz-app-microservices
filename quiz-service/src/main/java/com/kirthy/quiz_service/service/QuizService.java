package com.kirthy.quiz_service.service;


import com.kirthy.quiz_service.feign.QuizInterface;
import com.kirthy.quiz_service.model.QuestionWrapper;
import com.kirthy.quiz_service.model.Quiz;
import com.kirthy.quiz_service.model.Response;
import com.kirthy.quiz_service.repository.QuizRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class QuizService
{
    @Autowired
    QuizRepo quizRepo;

    @Autowired
    QuizInterface quizInterface;

    public ResponseEntity<String> createQuiz(String category, int numQ, String title)
    {
        List<Integer> questions = quizInterface.getQuestionsForQuiz(category, numQ).getBody();

        Quiz quiz = new Quiz();
        quiz.setTitle(title);
        quiz.setQuestions(questions);
        quizRepo.save(quiz);

        return new ResponseEntity<>("Success", HttpStatus.OK);
    }

    public ResponseEntity<List<QuestionWrapper>> getQuizQuestions(int id)
    {
        Optional<Quiz> quiz = quizRepo.findById(id);
        List<Integer> questionIds = quiz.get().getQuestions();

        return quizInterface.getQuestionFromIds(questionIds);
    }

    public ResponseEntity<Integer> calculateScore(List<Response> responses)
    {
        return quizInterface.getScore(responses);
    }
}
