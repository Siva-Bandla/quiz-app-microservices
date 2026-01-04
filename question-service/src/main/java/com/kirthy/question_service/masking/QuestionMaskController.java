package com.kirthy.question_service.masking;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("question")
public class QuestionMaskController
{
    @Autowired
    QuestionMaskService questionMaskService;

    @GetMapping("maskQuestions")
    public ResponseEntity<List<MaskQuestion>> getMaskQuestions()
    {
        return questionMaskService.getMaskQuestions();
    }

}
