package com.yijiawenCoder.hbsmoj.judge.strategy;

import com.yijiawenCoder.hbsmoj.model.dto.questionSubmit.JudgeInfo;

public interface JudgeStrategy {
    /**
     * 执行判题
     * @param judgeContext
     * @return
     */
    JudgeInfo doJudge(JudgeContext judgeContext);
}
