package com.dayang.commons.util;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * 类描述：通用规则校验工具类
 * <pre>
 * -------------History------------------
 *   DATE                 AUTHOR         VERSION        DESCRIPTION
 *  2015年06月03日            刘建宇      V01.00.001		   新增内容
 * </pre>
 *
 * @author <a href="mailto:liujy@dayanginfo.com">刘建宇</a>
 */
public class ValidateUtil {


    /**
     * 验证是否为正确的手机号
     * @param mobile 手机号字符串
     * @return 是手机号返回true
     */
    public static boolean isMobile(String mobile){
        Pattern p = Pattern.compile("^((13[0-9])|(15[0-9])|(18[0-9])|(14[7]))\\d{8}$");
        Matcher m = p.matcher(mobile);
        return m.matches();
    }

    /**
     * 验证是否为xxxx-xx-xx的日期格式
     * @param data 日期字符串
     * @return 校验通过返回true
     */
    public static boolean isDate(String data){
        Pattern p = Pattern.compile("([0-9]{3}[1-9]|[0-9]{2}[1-9][0-9]{1}|[0-9]{1}[1-9][0-9]{2}|[1-9][0-9]{3})-(((0[13578]|1[02])-(0[1-9]|[12][0-9]|3[01]))|((0[469]|11)-(0[1-9]|[12][0-9]|30))|(02-(0[1-9]|[1][0-9]|2[0-8])))");
        Matcher m = p.matcher(data);
        return m.matches();
    }

    /**
     * 校验是否为身份证号码
     * @param idCard 身份证号码字符串
     * @return 校验通过返回true
     */
    public static boolean isIDCard(String idCard){
        Pattern p = Pattern.compile("^[1-9]\\d{5}[1-9]\\d{3}((0\\d)|(1[0-2]))(([0|1|2]\\d)|3[0-1])\\d{3}([0-9]|X)$");
        Matcher m = p.matcher(idCard);
        return m.matches();
    }

    /**
     * 校验是否为电话号码
     * @param tel 电话号码
     * @return 校验通过返回true
     */
    public static boolean isTel(String tel){
        Pattern p = Pattern.compile("^((\\(\\d{2,3}\\))|(\\d{3}\\-))?(\\(0\\d{2,3}\\)|0\\d{2,3}-)?[1-9]\\d{6,7}(\\-\\d{1,4})?");
        Matcher matcher = p.matcher(tel);
        return matcher.matches();
    }

    /**
     * 校验是否为email格式
     * @param email email字符串
     * @return 校验通过返回true
     */
    public static boolean isEmail(String email){
        Pattern p = Pattern.compile("^((([a-z]|\\d|[!#\\$%&'\\*\\+\\-\\/=\\?\\^_`{\\|}~]|[\\u00A0-\\uD7FF\\uF900-\\uFDCF\\uFDF0-\\uFFEF])+(\\.([a-z]|\\d|[!#\\$%&'\\*\\+\\-\\/=\\?\\^_`{\\|}~]|[\\u00A0-\\uD7FF\\uF900-\\uFDCF\\uFDF0-\\uFFEF])+)*)|((\\x22)((((\\x20|\\x09)*(\\x0d\\x0a))?(\\x20|\\x09)+)?(([\\x01-\\x08\\x0b\\x0c\\x0e-\\x1f\\x7f]|\\x21|[\\x23-\\x5b]|[\\x5d-\\x7e]|[\\u00A0-\\uD7FF\\uF900-\\uFDCF\\uFDF0-\\uFFEF])|(\\\\([\\x01-\\x09\\x0b\\x0c\\x0d-\\x7f]|[\\u00A0-\\uD7FF\\uF900-\\uFDCF\\uFDF0-\\uFFEF]))))*(((\\x20|\\x09)*(\\x0d\\x0a))?(\\x20|\\x09)+)?(\\x22)))@((([a-z]|\\d|[\\u00A0-\\uD7FF\\uF900-\\uFDCF\\uFDF0-\\uFFEF])|(([a-z]|\\d|[\\u00A0-\\uD7FF\\uF900-\\uFDCF\\uFDF0-\\uFFEF])([a-z]|\\d|-|\\.|_|~|[\\u00A0-\\uD7FF\\uF900-\\uFDCF\\uFDF0-\\uFFEF])*([a-z]|\\d|[\\u00A0-\\uD7FF\\uF900-\\uFDCF\\uFDF0-\\uFFEF])))\\.)+(([a-z]|[\\u00A0-\\uD7FF\\uF900-\\uFDCF\\uFDF0-\\uFFEF])|(([a-z]|[\\u00A0-\\uD7FF\\uF900-\\uFDCF\\uFDF0-\\uFFEF])([a-z]|\\d|-|\\.|_|~|[\\u00A0-\\uD7FF\\uF900-\\uFDCF\\uFDF0-\\uFFEF])*([a-z]|[\\u00A0-\\uD7FF\\uF900-\\uFDCF\\uFDF0-\\uFFEF])))\\.?$");
        Matcher matcher = p.matcher(email);
        return matcher.matches();
    }

    /**
     * 校验是否为QQ号码
     * @param qq qq号码字符串
     * @return 校验通过返回true
     */
    public static boolean isQQ(String qq){
        Pattern p = Pattern.compile("[1-9][0-9]{4,14}");
        Matcher matcher = p.matcher(qq);
        return matcher.matches();
    }

    /**
     * 校验是否为邮政编码格式
     * @param zipCode 邮政编码字符串
     * @return 校验通过返回true
     */
    public static boolean isZipCode(String zipCode){
        Pattern p = Pattern.compile("^[\\d]{6}$");
        Matcher matcher = p.matcher(zipCode);
        return matcher.matches();
    }
}
