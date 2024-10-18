package com.credex.hiring.portal.dto;

import com.credex.hiring.portal.entities.QuestionPaper;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class UpdateEvaluation {

    private String rollNumber;
    private Integer questionId;
    private QuestionPaper submittedAnswer;
}
