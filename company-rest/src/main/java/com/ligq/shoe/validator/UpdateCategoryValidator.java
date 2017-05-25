package com.ligq.shoe.validator;


import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;


@Component
public class UpdateCategoryValidator implements Validator{

	@Override
	public boolean supports(Class<?> clazz) {
		return UpdateCategoryValidator.class.equals(clazz);
	}

	@Override
	public void validate(Object target, Errors errors) {
	}

}
