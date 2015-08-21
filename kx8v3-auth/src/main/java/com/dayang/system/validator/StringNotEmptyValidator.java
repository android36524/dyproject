package com.dayang.system.validator;

import com.dayang.commons.util.CommonUtil;
import com.dayang.system.annotation.StringNotEmpty;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

/**
 * 类描述：字符串空校验
 * <pre>
 * -------------History------------------
 *   DATE                 AUTHOR         VERSION        DESCRIPTION
 *  2015年06月04日            刘建宇      V01.00.001		   新增内容
 * </pre>
 *
 * @author <a href="mailto:liujy@dayanginfo.com">刘建宇</a>
 */
public class StringNotEmptyValidator implements ConstraintValidator<StringNotEmpty,String> {

    @Override
    public void initialize(StringNotEmpty stringNotEmpty) {

    }

    @Override
    public boolean isValid(String str, ConstraintValidatorContext constraintValidatorContext) {
        if (str == null || !CommonUtil.isNotEmptyString(str.trim())){
            return false;
        }
        return true;
    }
}
