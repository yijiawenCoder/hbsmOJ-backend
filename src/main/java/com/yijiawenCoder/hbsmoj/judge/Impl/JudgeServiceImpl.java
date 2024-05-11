package com.yijiawenCoder.hbsmoj.judge.Impl;

import com.yijiawenCoder.hbsmoj.judge.JudgeService;
import com.yijiawenCoder.hbsmoj.model.vo.QuestionSubmitVO;
import com.yijiawenCoder.hbsmoj.service.QuestionService;
import com.yijiawenCoder.hbsmoj.service.QuestionSubmitService;
import com.yijiawenCoder.hbsmoj.service.impl.QuestionSubmitServiceImpl;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

@Service
public class JudgeServiceImpl implements JudgeService {
    /***
     * 1.传入提交题目Id，获取对应的题目id，提交信息
     * 2.调用代码沙箱，获取运行结果
     * 3.根据代码沙箱的执行结果，设置题目的判题状态和信息
     * @param questionSubmitId
     * @return
     */
    @Resource
    private QuestionService questionService;
    @Resource
    private QuestionSubmitService questionSubmitService;



    @Override
    public QuestionSubmitVO doJudge(Long questionSubmitId) {
        return null;
    }
}
