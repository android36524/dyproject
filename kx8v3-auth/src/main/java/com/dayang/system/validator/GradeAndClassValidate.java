package com.dayang.system.validator;

import com.dayang.commons.util.CommonUtil;
import com.dayang.system.annotation.EffectiveGradeAndClass;
import com.dayang.system.model.StudentModel;
import org.apache.commons.beanutils.BeanUtils;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.util.List;

/**
 * 类描述：导入年级班级校验器
 * <pre>
 * -------------History------------------
 *   DATE                 AUTHOR         VERSION        DESCRIPTION
 *  2015年06月13日            刘建宇      V01.00.001		   新增内容
 * </pre>
 *
 * @author <a href="mailto:liujy@dayanginfo.com">刘建宇</a>
 */
public class GradeAndClassValidate implements ConstraintValidator<EffectiveGradeAndClass,Object> {


    @Override
    public void initialize(EffectiveGradeAndClass constraintAnnotation) {

    }

    @Override
    public boolean isValid(Object value, ConstraintValidatorContext context) {
        boolean result = true;
        Class clazz = value.getClass();
        try {
            Field gradeNameListField = clazz.getDeclaredField("gradeNames");
            gradeNameListField.setAccessible(true);
            //todo 查询年级时查出对应班级
            List<String> gradeNameList = (List<String>) gradeNameListField.get(value);
            String gradeName = BeanUtils.getProperty(value,"gradeName");
            if (!CommonUtil.strInList(gradeNameList,gradeName)){
                context.disableDefaultConstraintViolation();
                context.buildConstraintViolationWithTemplate("年级名称不合法").addConstraintViolation();
                return false;
            }
            String schId = BeanUtils.getProperty(value,"schId");
            List<String> classNames = StudentModel.dao.findClassNameListBySchId(new Long(schId),gradeName);
            String className = BeanUtils.getProperty(value,"className");
            if (!CommonUtil.strInList(classNames,className)){
                context.disableDefaultConstraintViolation();
                context.buildConstraintViolationWithTemplate("班级名称不合法").addConstraintViolation();
                return false;
            }
        } catch (NoSuchFieldException | IllegalAccessException | InvocationTargetException | NoSuchMethodException e) {
            throw new RuntimeException("校验器获取属性失败");
        }
        return result;
    }
}
