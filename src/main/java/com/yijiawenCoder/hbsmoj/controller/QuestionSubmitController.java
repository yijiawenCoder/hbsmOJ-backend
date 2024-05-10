package com.yijiawenCoder.hbsmoj.controller;


import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.yijiawenCoder.hbsmoj.common.BaseResponse;
import com.yijiawenCoder.hbsmoj.common.ErrorCode;
import com.yijiawenCoder.hbsmoj.common.ResultUtils;

import com.yijiawenCoder.hbsmoj.exception.BusinessException;
import com.yijiawenCoder.hbsmoj.model.dto.questionSubmit.DoQuestionSubmitRequest;
import com.yijiawenCoder.hbsmoj.model.dto.questionSubmit.QuestionSubmitQueryRequest;

import com.yijiawenCoder.hbsmoj.model.entity.QuestionSubmit;
import com.yijiawenCoder.hbsmoj.model.entity.User;
import com.yijiawenCoder.hbsmoj.model.vo.QuestionSubmitVO;
import com.yijiawenCoder.hbsmoj.service.QuestionSubmitService;
import com.yijiawenCoder.hbsmoj.service.UserService;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * 题目提交接口
 * <p>
 * # @author <a href="https://github.com/yijiawenCoder">yijiawenCoder</a>
 */
@RestController
@RequestMapping("/question_submit")
@Slf4j
public class QuestionSubmitController {

    @Resource
    private QuestionSubmitService questionSubmitService;

    @Resource
    private UserService userService;

    /**
     * 提交题目
     *
     * @param doQuestionSubmitRequest
     * @param request
     * @return resultNum 本次点赞变化数
     */
    @PostMapping("/")
    public BaseResponse<Long> doQuestionSubmit(@RequestBody DoQuestionSubmitRequest doQuestionSubmitRequest, HttpServletRequest request) {
        if (doQuestionSubmitRequest == null || doQuestionSubmitRequest.getQuestionId() <= 0) {
            throw new BusinessException(ErrorCode.PARAMS_ERROR);
        }
        // 登录才能点赞
        final User loginUser = userService.getLoginUser(request);
        long questionSubmitId = questionSubmitService.doQuestionSubmit(doQuestionSubmitRequest, loginUser);

        return ResultUtils.success(questionSubmitId);
    }

    /**
     * 分页提交题目列表（仅管理员和自己能看到题目提交代码）
     *
     * @param questionSubmitQueryRequest
     * @param request
     * @return
     */
    @PostMapping("/list/page")
    public BaseResponse<Page<QuestionSubmitVO>> listQuestionSubmitByPage(@RequestBody QuestionSubmitQueryRequest questionSubmitQueryRequest,
                                                                         HttpServletRequest request) {
        long current = questionSubmitQueryRequest.getCurrent();
        long size = questionSubmitQueryRequest.getPageSize();
        // 从数据库中查询原始的题目提交分页信息Page<QuestionSubmit> questionSubmitPage = questionSubmitService.page(new Page<>(current, size),
     Page<QuestionSubmit>questionSubmitPage=questionSubmitService.page(new Page<>(current, size),
     questionSubmitService.getQueryWrapper(questionSubmitQueryRequest));
        final User loginUser = userService.getLoginUser(request);
        // 返回脱敏信息
        return ResultUtils.success(questionSubmitService.getQuestionSubmitVOPage(questionSubmitPage, loginUser));

    }
}
