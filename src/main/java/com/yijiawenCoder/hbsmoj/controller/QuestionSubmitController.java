package com.yijiawenCoder.hbsmoj.controller;

import com.yijiawenCoder.hbsmoj.common.BaseResponse;
import com.yijiawenCoder.hbsmoj.common.ErrorCode;
import com.yijiawenCoder.hbsmoj.common.ResultUtils;
import com.yijiawenCoder.hbsmoj.exception.BusinessException;
import com.yijiawenCoder.hbsmoj.model.dto.questionSubmit.DoQuestionSubmitRequest;
import com.yijiawenCoder.hbsmoj.model.entity.User;
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
 *
 # @author <a href="https://github.com/yijiawenCoder">yijiawenCoder</a>
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

}
