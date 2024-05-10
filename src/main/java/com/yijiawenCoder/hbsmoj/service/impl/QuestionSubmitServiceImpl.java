package com.yijiawenCoder.hbsmoj.service.impl;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.yijiawenCoder.hbsmoj.common.ErrorCode;
import com.yijiawenCoder.hbsmoj.constant.CommonConstant;
import com.yijiawenCoder.hbsmoj.exception.BusinessException;
import com.yijiawenCoder.hbsmoj.mapper.QuestionSubmitMapper;
import com.yijiawenCoder.hbsmoj.model.dto.questionSubmit.DoQuestionSubmitRequest;
import com.yijiawenCoder.hbsmoj.model.dto.questionSubmit.QuestionSubmitQueryRequest;
import com.yijiawenCoder.hbsmoj.model.entity.Question;
import com.yijiawenCoder.hbsmoj.model.entity.QuestionSubmit;
import com.yijiawenCoder.hbsmoj.model.entity.User;
import com.yijiawenCoder.hbsmoj.model.enums.QuestionSubmitEnum;
import com.yijiawenCoder.hbsmoj.model.enums.QuestionSubmitLanguageEnum;
import com.yijiawenCoder.hbsmoj.model.vo.QuestionSubmitVO;
import com.yijiawenCoder.hbsmoj.service.QuestionService;
import com.yijiawenCoder.hbsmoj.service.QuestionSubmitService;
import com.yijiawenCoder.hbsmoj.service.UserService;
import com.yijiawenCoder.hbsmoj.utils.SqlUtils;
import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.lang3.ObjectUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;
import javax.annotation.Resource;
import java.util.List;
import java.util.stream.Collectors;



/**
 * @author 26510
 * @description 针对表【question_submit(题目提交)】的数据库操作Service实现
 * @createDate 2024-05-08 10:33:16
 */
@Service
public class QuestionSubmitServiceImpl extends
        ServiceImpl<QuestionSubmitMapper, QuestionSubmit>
        implements QuestionSubmitService {

    @Resource
    private QuestionService questionService;
    @Resource
    private UserService userService;

    /**
     * 题目提交
     *
     * @param doQuestionSubmitRequest
     * @param loginUser
     * @return
     */
    @Override
    public long doQuestionSubmit(DoQuestionSubmitRequest doQuestionSubmitRequest, User loginUser) {
        // 校验编程语言是否合法
        String language = doQuestionSubmitRequest.getLanguage();
        if (QuestionSubmitLanguageEnum.getEnumByValue(language) == null) {
            throw new BusinessException(ErrorCode.PARAMS_ERROR, "不支持使用该编程语言");
        }
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
        questionSubmit.setLanguage(language);
        //  设置题目初始状态
        questionSubmit.setStatus(QuestionSubmitEnum.WAITING.getValue());
        questionSubmit.setJudgeInfo("{}");

        boolean res = this.save(questionSubmit);
        if (!res) {
            throw new BusinessException(ErrorCode.OPERATION_ERROR, "数据插入失败");
        }
        return questionSubmit.getId();
    }

    @Override
    public QueryWrapper<QuestionSubmit> getQueryWrapper(QuestionSubmitQueryRequest questionSubmitQueryRequest) {
        QueryWrapper<QuestionSubmit> queryWrapper = new QueryWrapper<>();
        if (questionSubmitQueryRequest == null) {
            return queryWrapper;
        }
        String language = questionSubmitQueryRequest.getLanguage();
        Integer status = questionSubmitQueryRequest.getStatus();
        Long questionId = questionSubmitQueryRequest.getQuestionId();
        Long userId = questionSubmitQueryRequest.getUserId();
        String sortField = questionSubmitQueryRequest.getSortField();
        String sortOrder = questionSubmitQueryRequest.getSortOrder();

        // 拼接查询条件
        queryWrapper.eq(StringUtils.isNotBlank(language), "language", language);
        queryWrapper.eq(ObjectUtils.isNotEmpty(userId), "userId", userId);
        queryWrapper.eq(ObjectUtils.isNotEmpty(questionId), "questionId", questionId);
        queryWrapper.eq(QuestionSubmitEnum.getEnumByValue(status) != null, "status", status);
        queryWrapper.eq("isDelete", false);
        queryWrapper.orderBy(SqlUtils.validSortField(sortField), sortOrder.equals(CommonConstant.SORT_ORDER_ASC),
                sortField);
        return queryWrapper;

    }

    @Override
    public QuestionSubmitVO getQuestionSubmitVO(QuestionSubmit questionSubmit, User loginUser) {
        QuestionSubmitVO questionSubmitVO = QuestionSubmitVO.objToVo(questionSubmit);
        // 脱敏：仅本人和管理员能看见自己（提交 userId 和登录用户 id 不同）提交的代码

        long userId = loginUser.getId();
        // 处理脱敏  仅本人，管理员能见
        if (userId != questionSubmit.getUserId() && !userService.isAdmin(loginUser)) {
            questionSubmitVO.setCode(null);
        }
        return questionSubmitVO;

    }

    @Override
    public Page<QuestionSubmitVO> getQuestionSubmitVOPage(Page<QuestionSubmit> questionSubmitPage, User loginUser) {

        List<QuestionSubmit> questionSubmitList = questionSubmitPage.getRecords();
        Page<QuestionSubmitVO> questionSubmitVOPage = new Page<>(questionSubmitPage.getCurrent(), questionSubmitPage.getSize(), questionSubmitPage.getTotal());
        if (CollectionUtils.isEmpty(questionSubmitList)) {
            return questionSubmitVOPage;
        }
        List<QuestionSubmitVO> questionSubmitVOList = questionSubmitList.stream()
                .map(questionSubmit -> getQuestionSubmitVO(questionSubmit, loginUser))
                .collect(Collectors.toList());
        questionSubmitVOPage.setRecords(questionSubmitVOList);
        return questionSubmitVOPage;

    }


}




