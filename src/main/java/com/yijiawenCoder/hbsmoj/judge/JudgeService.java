package com.yijiawenCoder.hbsmoj.judge;


import com.yijiawenCoder.hbsmoj.model.entity.QuestionSubmit;

public interface JudgeService {
    /**
     * 判题服务
     *
     * @param questionSubmitId
     * @return
     */
    QuestionSubmit doJudge(Long questionSubmitId);
}
