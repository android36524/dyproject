package com.dayang.demo;

import com.dayang.commons.pojo.EdbEmplImportPojo;
import com.dayang.system.model.DeptModel;

import javax.validation.ConstraintViolation;
import javax.validation.Validation;
import javax.validation.Validator;
import javax.validation.ValidatorFactory;
import java.lang.reflect.InvocationTargetException;
import java.util.Arrays;
import java.util.List;
import java.util.Set;

public class Test {
	public static void main(String[] args) throws IllegalAccessException, NoSuchMethodException, InvocationTargetException {
		String s1 = "1";

		System.out.println("".equals(repeatStr("1,2","1,2,3",",")));
	}
	private static String repeatStr(String s1,String s2,String separator){
		String[] strArray1 = s1.split(separator);

		List<String> strArray2 = Arrays.asList(s2.split(separator));
		StringBuilder result = new StringBuilder();
		for (String str1 : strArray1){
			if (!strArray2.contains(str1)){
				result.append(str1).append(",");
			}
		}
		if (result.length() > 0){
			result.deleteCharAt(result.length() - 1);
		}
		return result.toString();
	}
}
