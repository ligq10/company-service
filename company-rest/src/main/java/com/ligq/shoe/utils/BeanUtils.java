package com.ligq.shoe.utils;

import java.beans.PropertyDescriptor;
import java.lang.annotation.Annotation;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.lang.reflect.Modifier;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.BeansException;
import org.springframework.beans.FatalBeanException;
import org.springframework.util.Assert;
import org.springframework.util.StringUtils;
public abstract class BeanUtils extends org.springframework.beans.BeanUtils{
	
	public static void copyPropertiesIgnoreNullValue(Object source, Object target,String... ignoreProperties)
			throws BeansException {
		Assert.notNull(source, "Source must not be null");
		Assert.notNull(target, "Target must not be null");
		Class<?> actualEditable = target.getClass();
		PropertyDescriptor[] targetPds = getPropertyDescriptors(actualEditable);
		List<String> ignoreList = (ignoreProperties != null ? Arrays
				.asList(ignoreProperties) : null);
		for (PropertyDescriptor targetPd : targetPds) {
			if (null != targetPd.getWriteMethod()
					&& (ignoreList == null || !ignoreList.contains(targetPd
							.getName()))) {
				PropertyDescriptor sourcePd = getPropertyDescriptor(
						source.getClass(), targetPd.getName());
				if (null != sourcePd
						&& null != sourcePd.getReadMethod()
						&& !StringUtils.isEmpty(sourcePd.getPropertyType())
						&& sourcePd.getPropertyType().isAssignableFrom(
								targetPd.getPropertyType())) {
					try {
						Method readMethod = sourcePd.getReadMethod();
						if (!Modifier.isPublic(readMethod.getDeclaringClass()
								.getModifiers())) {
							readMethod.setAccessible(true);
						}
						Object value = readMethod.invoke(source);

						// 这里判断以下value是否为空 当然这里也能进行一些特殊要求的处理 例如绑定时格式转换等等
						if (null != value) {
							Method writeMethod = targetPd.getWriteMethod();
							if (!Modifier.isPublic(writeMethod
									.getDeclaringClass().getModifiers())) {
								writeMethod.setAccessible(true);
							}
							writeMethod.invoke(target, value);
						}
					} catch (Throwable ex) {
						throw new FatalBeanException(
								"Could not copy properties from source to target",
								ex);
					}
				}
			}

		}
	}
	
	public static void copyPropertiesIgnoreNullValue(Object source, Object target)
			throws BeansException {
		copyPropertiesIgnoreNullValue(source, target, (String[]) null);
	}
	
	public static Field getDeclaredField(Class type,String name){  
		  Field beanField = null;
		try {
			beanField = type.getDeclaredField(name);
		} catch (NoSuchFieldException e) {

			return null;
		} catch (SecurityException e) {

			return null;
		}

		  return beanField;  
	} 
}
