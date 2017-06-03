package com.ligq.shoe.validator;


import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.ValidationUtils;
import org.springframework.validation.Validator;


@Component
public class AddNewsValidator implements Validator{

	@Override
	public boolean supports(Class<?> clazz) {
		return AddNewsValidator.class.equals(clazz);
	}

	@Override
	public void validate(Object target, Errors errors) {
		ValidationUtils.rejectIfEmpty(errors, "title", "news.title.empty");
		ValidationUtils.rejectIfEmpty(errors, "content", "news.content.empty");
	}

}
