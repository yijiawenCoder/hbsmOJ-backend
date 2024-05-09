    package com.yijiawenCoder.hbsmoj.model.dto.questionSubmit;

import com.baomidou.mybatisplus.annotation.TableField;
import lombok.Data;

import java.io.Serializable;

    /**
 * 帖子点赞请求
 *
# @author <a href="https://github.com/yijiawenCoder">yijiawenCoder</a>
 */
@Data
public class DoQuestionSubmitRequest implements Serializable {

        /**
         * 编程语言
         */
        private String language;

        /**
         * 用户代码
         */
        private String code;

        /**
         * 题目Id
         */
  private long  QuestionId;








        @TableField(exist = false)
        private static final long serialVersionUID = 1L;
}