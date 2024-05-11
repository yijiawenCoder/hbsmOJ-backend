package com.yijiawenCoder.hbsmoj.judge.codeSandBox;


import com.yijiawenCoder.hbsmoj.judge.codeSandBox.impl.ExampleCodeSandBoxImpl;
import com.yijiawenCoder.hbsmoj.judge.codeSandBox.impl.RemoteCodeSandBoxImpl;
import com.yijiawenCoder.hbsmoj.judge.codeSandBox.impl.ThirdPartyCodeSandBoxImpl;

/**
 *  @author yijiawenCoder
 *  @date 2022/12/14 15:45
 *  @description 根据字符串参数创建指定的代码沙箱实例
 *  @version 1.0（静态工厂）
 */
public class CodeSandBoxFactory {
    /**
     * 创建代码沙箱实例
     * @param type 沙箱类型
     * @return 代码沙箱
     */
    public static CodeSandBox newInstance(String type) {
        switch (type) {
            case "example":
                return new ExampleCodeSandBoxImpl();
            case "remote":
                return  new RemoteCodeSandBoxImpl();
            case "ThirdParty":
                return new ThirdPartyCodeSandBoxImpl();
            default:
                return new ExampleCodeSandBoxImpl();
        }

    }
}
