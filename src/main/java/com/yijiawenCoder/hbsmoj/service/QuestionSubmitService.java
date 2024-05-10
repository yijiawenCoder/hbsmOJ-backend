package com.yijiawenCoder.hbsmoj.service;


import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.yijiawenCoder.hbsmoj.model.dto.question.QuestionQueryRequest;
import com.yijiawenCoder.hbsmoj.model.dto.questionSubmit.DoQuestionSubmitRequest;
import com.yijiawenCoder.hbsmoj.model.dto.questionSubmit.QuestionSubmitQueryRequest;
import com.yijiawenCoder.hbsmoj.model.entity.Question;
import com.yijiawenCoder.hbsmoj.model.entity.QuestionSubmit;
import com.yijiawenCoder.hbsmoj.model.entity.User;

import com.baomidou.mybatisplus.extension.service.IService;
import com.yijiawenCoder.hbsmoj.model.vo.QuestionSubmitVO;
import com.yijiawenCoder.hbsmoj.model.vo.QuestionVO;

import javax.servlet.http.HttpServletRequest;

/**
* @author 26510
* @description 针对表【question_submit(题目提交)】的数据库操作Service
* @createDate 2024-05-08 10:33:16
*/
    public interface QuestionSubmitService extends IService<QuestionSubmit> {



    /**
     * 题目提交
     *
     * @param doQuestionSubmitRequest 题目提交信息
     * @param loginUser
     * @return
     */
    long doQuestionSubmit(DoQuestionSubmitRequest doQuestionSubmitRequest, User loginUser);


    /**
     * 获取查询条件
     *
     * @param questionSubmitQueryRequest
     * @return
     */
    QueryWrapper<QuestionSubmit> getQueryWrapper(QuestionSubmitQueryRequest questionSubmitQueryRequest);



    /**
     * 获取题目封装
     *
     * @param questionSubmit
     * @param loginUser
     * @return
     */
    QuestionSubmitVO getQuestionSubmitVO(QuestionSubmit questionSubmit,  User loginUser);

    /**
     * 分页获取题目封装
     *
     * @param questionSubmitPage
     * @param loginUser
     * @return
     */
    Page<QuestionSubmitVO> getQuestionSubmitVOPage(Page<QuestionSubmit> questionSubmitPage, User loginUser);

}
