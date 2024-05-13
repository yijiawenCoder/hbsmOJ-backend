package com.yijiawenCoder.hbsmoj.judge.strategy;

import cn.hutool.json.JSONUtil;
import com.yijiawenCoder.hbsmoj.model.dto.question.JudgeCase;
import com.yijiawenCoder.hbsmoj.model.dto.question.JudgeConfig;
import com.yijiawenCoder.hbsmoj.model.dto.questionSubmit.JudgeInfo;
import com.yijiawenCoder.hbsmoj.model.entity.Question;
import com.yijiawenCoder.hbsmoj.model.enums.JudgeInfoMessageEnum;

import java.util.List;

/**
 * 默认判题策略
 */
public class DefaultJudgeStrategy implements  JudgeStrategy {
    /**
     * 执行判题
     * @param judgeContext
     * @return
     */

    @Override
    public JudgeInfo doJudge(JudgeContext judgeContext) {

        JudgeInfo judgeInfo = judgeContext.getJudgeInfo();
        long memory = judgeInfo.getMemory();
        long time = judgeInfo.getTime();
        List<String> inputList = judgeContext.getInputList();
        List<String> outputList = judgeContext.getOutputList();
        Question question = judgeContext.getQuestion();
        List<JudgeCase> judgeCaseList = judgeContext.getJudgeCaseList();
        JudgeInfo judgeInfoResponse = new JudgeInfo();
        JudgeInfoMessageEnum judgeInfoMessageEnum = JudgeInfoMessageEnum.ACCEPTED;
        judgeInfoResponse.setMessage(judgeInfoMessageEnum.getValue());
        judgeInfoResponse.setMemory(memory);
        judgeInfoResponse.setTime(time);

        if(outputList.size()!= inputList.size()){
            judgeInfoMessageEnum = judgeInfoMessageEnum.WRONG_ANSWER;
           judgeInfoResponse.setMessage(judgeInfoMessageEnum.getValue());
           return judgeInfoResponse;
        }
        //判断输出是否和预期相等
        for (int i = 0; i < judgeCaseList.size(); i++) {
            JudgeCase judgeCase = judgeCaseList.get(i);
            if(!judgeCase.getOutput().equals(outputList.get(i))){
                judgeInfoMessageEnum = judgeInfoMessageEnum.WRONG_ANSWER;
                judgeInfoResponse.setMessage(judgeInfoMessageEnum.getValue());
                return judgeInfoResponse;
            }
        }
        //判断题目限制
      

        String judgeConfigStr = question.getJudgeConfig();
        JudgeConfig judgeConfig = JSONUtil.toBean(judgeConfigStr, JudgeConfig.class);
        long needTimeLimit = judgeConfig.getTimeLimit();
        long needMemoryLimit = judgeConfig.getMemoryLimit();
        if(memory>needMemoryLimit){
            judgeInfoMessageEnum = judgeInfoMessageEnum.MEMORY_LIMIT_EXCEEDED;
            judgeInfoResponse.setMessage(judgeInfoMessageEnum.getValue());
            return judgeInfoResponse;
        }
        if(time>needTimeLimit){
            judgeInfoMessageEnum = judgeInfoMessageEnum.TIME_LIMIT_EXCEEDED;
            judgeInfoResponse.setMessage(judgeInfoMessageEnum.getValue());
            return judgeInfoResponse;
        }

        judgeInfoResponse.setMessage(judgeInfoMessageEnum.getValue());
        return judgeInfoResponse;
        
    }
}
