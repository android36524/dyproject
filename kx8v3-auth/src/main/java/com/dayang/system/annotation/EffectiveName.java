package com.dayang.system.annotation;



import com.dayang.system.validator.NameValidator;

import javax.validation.Constraint;
import javax.validation.Payload;
import java.lang.annotation.*;

/**
 * 类描述：部门名称有效性注解
 * <pre>
 * -------------History------------------
 *   DATE                 AUTHOR         VERSION        DESCRIPTION
 *  2015年06月04日            刘建宇      V01.00.001		   新增内容
 * </pre>
 *
 * @author <a href="mailto:liujy@dayanginfo.com">刘建宇</a>
 */
@Target({ElementType.TYPE,ElementType.ANNOTATION_TYPE})
@Retention(RetentionPolicy.RUNTIME)
@Constraint(validatedBy = NameValidator.class)
@Documented
public @interface EffectiveName {
    String message() default "名称不合法";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};

    String nameField();
    String nameListField();

}
