package com.yijiawenCoder.hbsmoj.judge.Impl;

import cn.hutool.json.JSONUtil;
import com.yijiawenCoder.hbsmoj.common.ErrorCode;
import com.yijiawenCoder.hbsmoj.exception.BusinessException;
import com.yijiawenCoder.hbsmoj.judge.JudgeService;
import com.yijiawenCoder.hbsmoj.judge.codeSandBox.CodeSandBox;
import com.yijiawenCoder.hbsmoj.judge.codeSandBox.CodeSandBoxFactory;
import com.yijiawenCoder.hbsmoj.judge.codeSandBox.CodeSandBoxProxy;
import com.yijiawenCoder.hbsmoj.judge.codeSandBox.model.ExecuteCodeRequest;
import com.yijiawenCoder.hbsmoj.judge.codeSandBox.model.ExecuteCodeResponse;
import com.yijiawenCoder.hbsmoj.judge.strategy.DefaultJudgeStrategy;
import com.yijiawenCoder.hbsmoj.judge.strategy.JudgeContext;
import com.yijiawenCoder.hbsmoj.judge.strategy.JudgeManager;
import com.yijiawenCoder.hbsmoj.judge.strategy.JudgeStrategy;
import com.yijiawenCoder.hbsmoj.model.dto.question.JudgeCase;
import com.yijiawenCoder.hbsmoj.model.dto.questionSubmit.JudgeInfo;
import com.yijiawenCoder.hbsmoj.model.entity.Question;
import com.yijiawenCoder.hbsmoj.model.entity.QuestionSubmit;
import com.yijiawenCoder.hbsmoj.model.enums.QuestionSubmitEnum;
import com.yijiawenCoder.hbsmoj.service.QuestionService;
import com.yijiawenCoder.hbsmoj.service.QuestionSubmitService;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;
import java.util.stream.Collectors;

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
    @Resource
    private JudgeManager judgeManager;
    @Value("${codesandbox.type:example}")
    private String type;


    @Override
    public QuestionSubmit doJudge(Long questionSubmitId) {
        QuestionSubmit questionSubmit = questionSubmitService.getById(questionSubmitId);
        if (questionSubmit == null) {
            throw new BusinessException(ErrorCode.PARAMS_ERROR, "题目提交不存在");
        }
        Long questionId = questionSubmit.getQuestionId();
        Question question = questionService.getById(questionId);
        if (question == null) {
            throw new BusinessException(ErrorCode.PARAMS_ERROR, "题目不存在");
        }
        //如果题目的提交状态不是等待中，就不用重复执行
        if (!questionSubmit.getStatus().equals(QuestionSubmitEnum.WAITING.getValue())) {
            throw new BusinessException(ErrorCode.PARAMS_ERROR, "不能重复提交");
        }
        QuestionSubmit questionSubmitUpdate = new QuestionSubmit();
        questionSubmitUpdate.setId(questionSubmitId);
        questionSubmitUpdate.setStatus(QuestionSubmitEnum.RUNNING.getValue());
        boolean updateById = questionSubmitService.updateById(questionSubmitUpdate);
        if (!updateById) {
            throw new BusinessException(ErrorCode.SYSTEM_ERROR, "更新失败");
        }
        //执行判题调用代码沙箱
        CodeSandBox codeSandBox = CodeSandBoxFactory.newInstance(type);
        codeSandBox = new CodeSandBoxProxy(codeSandBox);
        String code = questionSubmit.getCode();
        String language = questionSubmit.getLanguage();
        String judgeCaseStr = question.getJudgeCase();
        //获取输入用例
        List<JudgeCase> judgeCaseList = JSONUtil.toList(judgeCaseStr, JudgeCase.class);
        List<String> input = judgeCaseList.stream().map(JudgeCase::getInput).collect(Collectors.toList());
        ExecuteCodeRequest executeCodeRequest = ExecuteCodeRequest.builder()
                .Code(code)
                .Language(language)
                .inputList(input)
                .build();
        ExecuteCodeResponse executeCodeResponse = codeSandBox.executeCode(executeCodeRequest);
        List<String> outputList = executeCodeResponse.getOutList();
        //根据沙箱的执行结果输出数量是否和预期输出相等
        JudgeContext judgeContext = new JudgeContext();
        judgeContext.setJudgeInfo(executeCodeResponse.getJudgeInfo());
        judgeContext.setInputList(input);
        judgeContext.setOutputList(outputList);
        judgeContext.setQuestion(question);
        judgeContext.setJudgeCaseList(judgeCaseList);
        judgeContext.setQuestionSubmit(questionSubmit);
        //执行相对的策略

        JudgeStrategy judgeStrategy = new DefaultJudgeStrategy();
        JudgeInfo judgeInfo = judgeManager.doJudge(judgeContext);
        //修改数据库中判题结果
         questionSubmitUpdate = new QuestionSubmit();
         questionSubmitUpdate.setStatus(QuestionSubmitEnum.SUCCEED.getValue());
         questionSubmitUpdate.setJudgeInfo(JSONUtil.toJsonStr(judgeInfo));
         questionSubmitUpdate.setId(questionSubmitId);
        updateById = questionSubmitService.updateById(questionSubmitUpdate);
        if(!updateById){
            throw new BusinessException(ErrorCode.SYSTEM_ERROR,"判题失败");
        }
        QuestionSubmit questionSubmitResult = questionSubmitService.getById(questionId);
        return questionSubmitResult;
    }
}
