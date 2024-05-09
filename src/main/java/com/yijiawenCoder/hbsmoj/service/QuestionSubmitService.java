package com.yijiawenCoder.hbsmoj.service;


import com.yijiawenCoder.hbsmoj.model.dto.questionSubmit.DoQuestionSubmitRequest;
import com.yijiawenCoder.hbsmoj.model.entity.QuestionSubmit;
import com.yijiawenCoder.hbsmoj.model.entity.User;

import com.baomidou.mybatisplus.extension.service.IService;

/**
* @author 26510
* @description 针对表【question_submit(题目提交)】的数据库操作Service
* @createDate 2024-05-08 10:33:16
*/
    public interface QuestionSubmitService extends IService<QuestionSubmit> {



    /**
     * 题目提交
     *
     * @param questionSubmitRequest 题目提交信息
     * @param loginUser
     * @return
     */
    long doQuestionSubmit(DoQuestionSubmitRequest doQuestionSubmitRequest, User loginUser);



}
