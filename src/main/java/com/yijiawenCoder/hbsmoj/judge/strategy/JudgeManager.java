package com.yijiawenCoder.hbsmoj.judge.strategy;

import com.yijiawenCoder.hbsmoj.model.dto.questionSubmit.JudgeInfo;
import com.yijiawenCoder.hbsmoj.model.entity.Question;
import com.yijiawenCoder.hbsmoj.model.entity.QuestionSubmit;
import org.springframework.stereotype.Service;

/**
 * 判题管理（简化调用） 对选策略进行封装
 */
@Service
public class JudgeManager  {
    /**
     * 执行判题
     */
    public JudgeInfo doJudge(JudgeContext judgeContext) {
        QuestionSubmit questionSubmit = new QuestionSubmit();
        String language = questionSubmit.getLanguage();
        JudgeStrategy judgeStrategy = new DefaultJudgeStrategy();
        //使用java策略
        if ("java".equals(language)) {
            judgeStrategy = new JavaLanguageJudgeStrategy();
        }
         return  judgeStrategy.doJudge(judgeContext);
    }
}
