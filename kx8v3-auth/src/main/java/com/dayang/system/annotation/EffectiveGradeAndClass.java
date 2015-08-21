package com.dayang.system.annotation;

import com.dayang.system.validator.GradeAndClassValidate;

import javax.validation.Constraint;
import javax.validation.Payload;
import java.lang.annotation.*;

/**
 * 类描述：年级班级校验注解
 * <pre>
 * -------------History------------------
 *   DATE                 AUTHOR         VERSION        DESCRIPTION
 *  2015年06月13日            刘建宇      V01.00.001		   新增内容
 * </pre>
 *
 * @author <a href="mailto:liujy@dayanginfo.com">刘建宇</a>
 */
@Target({ElementType.TYPE,ElementType.ANNOTATION_TYPE})
@Retention(RetentionPolicy.RUNTIME)
@Constraint(validatedBy = GradeAndClassValidate.class)
@Documented
public @interface EffectiveGradeAndClass {
    String message() default "名称不合法";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};
}
