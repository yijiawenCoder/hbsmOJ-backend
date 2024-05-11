package com.yijiawenCoder.hbsmoj.judge.codeSandBox;

import com.yijiawenCoder.hbsmoj.judge.codeSandBox.model.ExecuteCodeRequest;
import com.yijiawenCoder.hbsmoj.judge.codeSandBox.model.ExecuteCodeResponse;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;

/***
 * 用户调用代理类，代理类再调用代码沙箱
 */
@Slf4j
@AllArgsConstructor
public class CodeSandBoxProxy implements CodeSandBox{
    private final CodeSandBox codeSandBox;

    /**
     * 增强代码沙箱
     * @param executeCodeRequest
     * @return
     */
    @Override
    public ExecuteCodeResponse executeCode(ExecuteCodeRequest executeCodeRequest) {
        log.info("代码沙箱请求信息"+executeCodeRequest.toString());
        ExecuteCodeResponse executeCodeResponse = codeSandBox.executeCode(executeCodeRequest);
        log.info("代码沙箱响应信息"+executeCodeResponse.toString());
        return executeCodeResponse;
    }
}
