package com.kirthy.question_service.service;

import com.kirthy.question_service.model.Question;
import com.kirthy.question_service.model.QuestionWrapper;
import com.kirthy.question_service.model.Response;
import com.kirthy.question_service.repository.QuestionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@Service
public class QuestionService
{
    @Autowired
    QuestionRepository questionRepository;

    public ResponseEntity<List<Question>> getAllQuestions()
    {
        try {
            return new ResponseEntity<>(questionRepository.findAll(), HttpStatus.OK);
        }catch (Exception e)
        {
            e.printStackTrace();
        }
        return new ResponseEntity<>(new ArrayList<>(), HttpStatus.BAD_REQUEST);
    }

    public ResponseEntity<List<Question>> getQuestionsByCategory(String category)
    {
        try{
            return new ResponseEntity<>(questionRepository.findByCategory(category), HttpStatus.OK);
        }catch (Exception e)
        {
            e.printStackTrace();
        }
        return new ResponseEntity<>(new ArrayList<>(), HttpStatus.BAD_REQUEST);
    }

    public List<Question> getQuestionsByDifficultyLevel(String diffLevel)
    {
        return questionRepository.findByDifficultyLevel(diffLevel);
    }

    public ResponseEntity<Question> addQuestion(Question question)
    {
        try{
            return new ResponseEntity<>(questionRepository.save(question), HttpStatus.CREATED);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return new ResponseEntity<>(question, HttpStatus.BAD_REQUEST);
    }

    public ResponseEntity<Question> updateQuestion(Integer id, Question question)
    {
        try {

            return new ResponseEntity<>(questionRepository.findById(id)
                    .map(existing -> {
                        // record found → update fields
                        existing.setQuestionTitle(question.getQuestionTitle());
                        existing.setOption1(question.getOption1());
                        existing.setOption2(question.getOption2());
                        existing.setOption3(question.getOption3());
                        existing.setOption4(question.getOption4());
                        existing.setRightAnswer(question.getRightAnswer());
                        existing.setDifficultyLevel(question.getDifficultyLevel());
                        existing.setCategory(question.getCategory());
                        return questionRepository.save(existing);
                    })
                    .orElseGet(() -> {
                        // record not found → create new with given id
                        question.setId(id);
                        return questionRepository.save(question);
                    }), HttpStatus.OK);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return new ResponseEntity<>(question, HttpStatus.NOT_FOUND);
    }

    public ResponseEntity<Question> deleteQuestion(Integer id)
    {
        List<Question> dbQuestions = questionRepository.findAll();
        Question deletedQuestion = null;
        for (Question eachQuestion: dbQuestions)
        {
            if (Objects.equals(eachQuestion.getId(), id))
            {
                deletedQuestion = questionRepository.findById(id).get();
                questionRepository.delete(eachQuestion);
            }
        }
        return new ResponseEntity<>(deletedQuestion, HttpStatus.OK);
    }

    public ResponseEntity<List<Integer>> getQuestionsForQuiz(String category, int numQ) {
        List<Integer> questions = questionRepository.findRandomQuestionByCategory(category, numQ);
        return new ResponseEntity<>(questions, HttpStatus.OK);
    }

    public ResponseEntity<List<QuestionWrapper>> getQuestionFromIds(List<Integer> questionIds) {
        List<Question> questions = questionIds.stream()
                .map(id->questionRepository.findById(id).get())
                .toList();
        List<QuestionWrapper> questionWrappers = questions.stream()
                .map(q->new QuestionWrapper(
                        q.getId(),
                        q.getQuestionTitle(),
                        q.getOption1(),
                        q.getOption2(),
                        q.getOption3(),
                        q.getOption4()
                )).toList();
        return new ResponseEntity<>(questionWrappers, HttpStatus.OK);
    }

    public ResponseEntity<Integer> getScore(List<Response> responses) {
        int right = (int) responses.stream()
                .filter(response ->
                        questionRepository.findById(response.getId())
                        .map(q->q.getRightAnswer().equals(response.getResponse()))
                        .orElse(false)
                ).count();
        return new ResponseEntity<>(right, HttpStatus.OK);
    }
}
