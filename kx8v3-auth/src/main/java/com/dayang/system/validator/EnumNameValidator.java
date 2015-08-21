package com.dayang.system.validator;

import com.dayang.commons.util.CommonUtil;
import com.dayang.commons.util.EnumUtil;
import com.dayang.system.annotation.EffectiveEnumName;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;
import java.util.List;

/**
 * 类描述：枚举实例Name合法性校验
 * <pre>
 * -------------History------------------
 *   DATE                 AUTHOR         VERSION        DESCRIPTION
 *  2015年06月04日            刘建宇      V01.00.001		   新增内容
 * </pre>
 *
 * @author <a href="mailto:liujy@dayanginfo.com">刘建宇</a>
 */
public class EnumNameValidator implements ConstraintValidator<EffectiveEnumName,String> {

    private Class clazz;

    @Override
    public void initialize(EffectiveEnumName effectiveEnumName) {
        this.clazz = effectiveEnumName.clazz();
    }

    @Override
    public boolean isValid(String enumName, ConstraintValidatorContext constraintValidatorContext) {
        if (enumName == null){
            return true;
        }
        boolean result = true;
        List<String> enumNameList = EnumUtil.getEnumNames(clazz);
        if (!CommonUtil.strInList(enumNameList,enumName)){
            result = false;
        }
        return result;
    }
}
