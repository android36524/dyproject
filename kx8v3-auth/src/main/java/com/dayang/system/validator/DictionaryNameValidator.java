package com.dayang.system.validator;

import com.dayang.commons.util.CommonUtil;
import com.dayang.commons.util.DictionaryUtil;
import com.dayang.system.annotation.EffectiveDictionaryName;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;
import java.util.List;

/**
 * 类描述：静态参数名称有效性校验器
 * <pre>
 * -------------History------------------
 *   DATE                 AUTHOR         VERSION        DESCRIPTION
 *  2015年06月04日            刘建宇      V01.00.001		   新增内容
 * </pre>
 *
 * @author <a href="mailto:liujy@dayanginfo.com">刘建宇</a>
 */
public class DictionaryNameValidator implements ConstraintValidator<EffectiveDictionaryName,String> {

    private int dictType;

    @Override
    public void initialize(EffectiveDictionaryName effectiveDictionaryName) {
        this.dictType = effectiveDictionaryName.dictType();
    }

    @Override
    public boolean isValid(String dictName, ConstraintValidatorContext constraintValidatorContext) {
        if (dictName == null){
            return true;
        }
        boolean result = true;
        List<String> dictNames = DictionaryUtil.getDicNameListByDicType(dictType);
        if (!CommonUtil.strInList(dictNames,dictName)){
            result = false;
        }
        return result;
    }
}
