package com.yijiawenCoder.hbsmoj.judge.codeSandBox.Impl;

import com.yijiawenCoder.hbsmoj.judge.codeSandBox.CodeSandBox;
import com.yijiawenCoder.hbsmoj.judge.codeSandBox.model.ExecuteCodeRequest;
import com.yijiawenCoder.hbsmoj.judge.codeSandBox.model.ExecuteCodeResponse;
import com.yijiawenCoder.hbsmoj.model.dto.questionSubmit.JudgeInfo;
import com.yijiawenCoder.hbsmoj.model.enums.JudgeInfoMessageEnum;
import com.yijiawenCoder.hbsmoj.model.enums.QuestionSubmitEnum;
import lombok.extern.slf4j.Slf4j;

import java.util.List;

/***
 * 示例代码沙箱（仅为跑通流程）
 */
@Slf4j
public class ExampleCodeSandBoxImpl implements CodeSandBox {
    @Override
    public ExecuteCodeResponse executeCode(ExecuteCodeRequest executeCodeRequest) {
        List<String> inputList = executeCodeRequest.getInputList();
/*        String code = executeCodeRequest.getCode();
        String language = executeCodeRequest.getLanguage();*/

        ExecuteCodeResponse executeCodeResponse = new ExecuteCodeResponse();
        executeCodeResponse.setOutList(inputList);
        executeCodeResponse.setMessage("测试执行成功");
        executeCodeResponse.setStatus(QuestionSubmitEnum.SUCCEED.getValue());
        JudgeInfo judgeInfo = new JudgeInfo();
        judgeInfo.setMessage(JudgeInfoMessageEnum.ACCEPTED.getText());
        judgeInfo.setMemory(100L);
        judgeInfo.setTime(100L);
        executeCodeResponse.setJudgeInfo(judgeInfo);
        return executeCodeResponse;
    }
}
