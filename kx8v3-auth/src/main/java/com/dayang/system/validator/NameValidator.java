package com.dayang.system.validator;

import com.dayang.commons.util.CommonUtil;
import com.dayang.system.annotation.EffectiveName;
import org.apache.commons.beanutils.BeanUtils;
import org.apache.log4j.Logger;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Modifier;
import java.util.List;

/**
 * 类描述：校验部门名称有效性校验器类
 * <pre>
 * -------------History------------------
 *   DATE                 AUTHOR         VERSION        DESCRIPTION
 *  2015年06月03日            刘建宇      V01.00.001		   新增内容
 * </pre>
 *
 * @author <a href="mailto:liujy@dayanginfo.com">刘建宇</a>
 */
public class NameValidator implements ConstraintValidator<EffectiveName, Object> {

    private static final Logger logger = Logger.getLogger(NameValidator.class);

    private String nameField;
    private String nameListField;

    @Override
    public void initialize(EffectiveName deptName) {
        this.nameField = deptName.nameField();
        this.nameListField = deptName.nameListField();
    }

    @Override
    public boolean isValid(Object value, ConstraintValidatorContext constraintValidatorContext) {
        boolean result = true;
        Class clazz = value.getClass();
        try {
            Field listField = clazz.getDeclaredField(nameListField);
            listField.setAccessible(true);
            Object listFieldValue;
            if (Modifier.isStatic(listField.getModifiers())){
                listFieldValue = listField.get(null);
            }else {
                listFieldValue = listField.get(value);
            }
            List<String> nameList = (List<String>)listFieldValue;
            String validateFieldValue = BeanUtils.getProperty(value, nameField);
            if (!CommonUtil.strInList(nameList, validateFieldValue)){
                result = false;
            }
        } catch (NoSuchFieldException | IllegalAccessException | InvocationTargetException | NoSuchMethodException e) {
            logger.debug("获取合法名称值失败，POJO中不存在该属性");
            result = false;
        }
        return result;
    }
}
