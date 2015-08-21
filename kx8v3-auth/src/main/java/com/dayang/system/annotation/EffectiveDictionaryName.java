package com.dayang.system.annotation;

import com.dayang.system.validator.DictionaryNameValidator;

import javax.validation.Constraint;
import javax.validation.Payload;
import java.lang.annotation.*;

/**
 * 类描述：静态参数Name有效性注解
 * <pre>
 * -------------History------------------
 *   DATE                 AUTHOR         VERSION        DESCRIPTION
 *  2015年06月04日            刘建宇      V01.00.001		   新增内容
 * </pre>
 *
 * @author <a href="mailto:liujy@dayanginfo.com">刘建宇</a>
 */
@Target({ElementType.FIELD,ElementType.ANNOTATION_TYPE})
@Retention(RetentionPolicy.RUNTIME)
@Constraint(validatedBy = DictionaryNameValidator.class)
@Documented
public @interface EffectiveDictionaryName {
    
    String message() default "";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};
    int dictType();
}
