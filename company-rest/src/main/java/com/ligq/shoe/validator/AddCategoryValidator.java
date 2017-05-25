package com.ligq.shoe.validator;


import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.ValidationUtils;
import org.springframework.validation.Validator;


@Component
public class AddCategoryValidator implements Validator{

	@Override
	public boolean supports(Class<?> clazz) {
		return AddCategoryValidator.class.equals(clazz);
	}

	@Override
	public void validate(Object target, Errors errors) {
		ValidationUtils.rejectIfEmpty(errors, "name", "category.name.empty");
	}

}
