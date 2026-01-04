package com.kirthy.question_service.masking;

import com.kirthy.question_service.repository.QuestionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class QuestionMaskService
{
    @Autowired
    QuestionRepository questionRepository;

    public ResponseEntity<List<MaskQuestion>> getMaskQuestions()
    {
        try {
            List<MaskQuestion> maskedQuestions = questionRepository.findAll().stream()
                                                        .map(q-> new MaskQuestion(
                                                                q.getId(),
                                                                q.getQuestionTitle(),
                                                                q.getOption1(),
                                                                q.getOption2(),
                                                                q.getOption3(),
                                                                q.getOption4(),
                                                                q.getRightAnswer()
                                                                )
                                                        ).toList();
            return new ResponseEntity<>(maskedQuestions, HttpStatus.OK);
        }catch (Exception e)
        {
            e.printStackTrace();
        }
        return new ResponseEntity<>(new ArrayList<>(), HttpStatus.BAD_REQUEST);
    }

}
