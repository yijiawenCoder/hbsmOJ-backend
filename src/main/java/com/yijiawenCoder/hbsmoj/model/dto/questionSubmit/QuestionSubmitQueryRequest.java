package com.yijiawenCoder.hbsmoj.model.dto.questionSubmit;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.yijiawenCoder.hbsmoj.common.PageRequest;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.io.Serializable;
import java.util.List;

/**
 * 查询请求
 *
# @author <a href="https://github.com/yijiawenCoder">yijiawenCoder</a>
 */
@EqualsAndHashCode(callSuper = true)
@Data
public class QuestionSubmitQueryRequest extends PageRequest implements Serializable {
    /**
     * 编程语言
     */
    private String language;



    /**
     * 提交状态
     */
    private Integer status;

    /**
     * 题目Id
     */
        private Long questionId;


    /**
     * 用户 id
     */
    private Long userId;


    @TableField(exist = false)
    private static final long serialVersionUID = 1L;
}