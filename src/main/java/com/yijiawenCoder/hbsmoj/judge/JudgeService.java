package com.yijiawenCoder.hbsmoj.judge;

import com.yijiawenCoder.hbsmoj.judge.codeSandBox.model.ExecuteCodeRequest;
import com.yijiawenCoder.hbsmoj.judge.codeSandBox.model.ExecuteCodeResponse;
import com.yijiawenCoder.hbsmoj.model.vo.QuestionSubmitVO;

public interface JudgeService {
    /**
     * 判题服务
     * @param questionSubmitId
     * @return
     */
    QuestionSubmitVO doJudge(Long questionSubmitId);
}
