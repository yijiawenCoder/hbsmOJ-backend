package com.yijiawenCoder.hbsmoj.judge.codeSandBox;

import com.yijiawenCoder.hbsmoj.judge.codeSandBox.model.ExecuteCodeRequest;
import com.yijiawenCoder.hbsmoj.judge.codeSandBox.model.ExecuteCodeResponse;

/**
 * 代码沙箱接口定义
 * @author yijiawenCoder
 */
public interface CodeSandBox {
    /**
     *  执行代码
     * @param executeCodeRequest
     * @return
     */
    ExecuteCodeResponse executeCode(ExecuteCodeRequest executeCodeRequest);
}
