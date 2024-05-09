package com.yijiawenCoder.hbsmoj.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.yijiawenCoder.hbsmoj.common.ErrorCode;
import com.yijiawenCoder.hbsmoj.exception.BusinessException;
import com.yijiawenCoder.hbsmoj.mapper.QuestionSubmitMapper;
import com.yijiawenCoder.hbsmoj.model.dto.questionSubmit.DoQuestionSubmitRequest;
import com.yijiawenCoder.hbsmoj.model.entity.Question;
import com.yijiawenCoder.hbsmoj.model.entity.QuestionSubmit;
import com.yijiawenCoder.hbsmoj.model.entity.User;
import com.yijiawenCoder.hbsmoj.service.QuestionService;
import com.yijiawenCoder.hbsmoj.service.QuestionSubmitService;


import org.springframework.aop.framework.AopContext;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;

/**
* @author 26510
* @description 针对表【question_submit(题目提交)】的数据库操作Service实现
* @createDate 2024-05-08 10:33:16
*/
@Service
public class QuestionSubmitServiceImpl extends
        ServiceImpl<QuestionSubmitMapper, QuestionSubmit>
        implements QuestionSubmitService{

    @Resource
    private QuestionService questionService;

    /**
     * 题目提交
     *
     * @param doQuestionSubmitRequest
     * @param loginUser
     * @return
     */
    @Override
    public long doQuestionSubmit(DoQuestionSubmitRequest doQuestionSubmitRequest, User loginUser) {
        long questionId = doQuestionSubmitRequest.getQuestionId();
        // 判断实体是否存在，根据类别获取实体
        Question question = questionService.getById(questionId);
        if (question == null) {
            throw new BusinessException(ErrorCode.NOT_FOUND_ERROR);
        }
        // 是否已题目提交
        long userId = loginUser.getId();
        // 每个用户串行题目提交
        // 锁必须要包裹住事务方法
     QuestionSubmit questionSubmit = new QuestionSubmit();
     questionSubmit.setUserId(userId);
     questionSubmit.setQuestionId(questionId);
     questionSubmit.setCode(doQuestionSubmitRequest.getCode());
     questionSubmit.setLanguage(doQuestionSubmitRequest.getLanguage());
     // todo 设置题目初始状态
        questionSubmit.setStatus();
     questionSubmit.setJudgeInfo("{}");
        boolean res = this.save(questionSubmit);
        if(!res){
            throw new BusinessException(ErrorCode.OPERATION_ERROR,"数据插入失败");
        }
        return questionSubmit.getId();
    }



}




