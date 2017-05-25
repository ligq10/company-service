package com.ligq.shoe.validator;


import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.ValidationUtils;
import org.springframework.validation.Validator;


@Component
public class UpdateProductPriceValidator implements Validator{

	@Override
	public boolean supports(Class<?> clazz) {
		return UpdateProductPriceValidator.class.equals(clazz);
	}

	@Override
	public void validate(Object target, Errors errors) {		
		ValidationUtils.rejectIfEmpty(errors, "price", "price.empty");
	}

}
