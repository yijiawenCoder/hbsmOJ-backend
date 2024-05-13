package com.yijiawenCoder.hbsmoj.judge.strategy;

import com.yijiawenCoder.hbsmoj.model.dto.question.JudgeCase;
import com.yijiawenCoder.hbsmoj.model.dto.questionSubmit.JudgeInfo;
import com.yijiawenCoder.hbsmoj.model.entity.Question;
import com.yijiawenCoder.hbsmoj.model.entity.QuestionSubmit;
import lombok.Data;

import java.util.List;

/**
 * 定义在策略中定义的参数
 */
@Data
public class JudgeContext {
   private JudgeInfo judgeInfo;
   private List<String> inputList;
   private List<String> outputList;
   private Question question;
   private List<JudgeCase> judgeCaseList;
   private QuestionSubmit questionSubmit;


}
