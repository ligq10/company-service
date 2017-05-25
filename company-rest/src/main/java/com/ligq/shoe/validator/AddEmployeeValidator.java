package com.ligq.shoe.validator;

import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.ValidationUtils;
import org.springframework.validation.Validator;
@Component
public class AddEmployeeValidator implements Validator{

	@Override
	public boolean supports(Class<?> clazz) {
		return AddEmployeeValidator.class.equals(clazz);
	}

	@Override
	public void validate(Object target, Errors errors) {
		//ValidationUtils.rejectIfEmpty(errors, "name", "employee.name.empty");
		//ValidationUtils.rejectIfEmpty(errors, "identification", "employee.identification.empty");
		//ValidationUtils.rejectIfEmpty(errors, "mobileNo", "employee.mobile_no.empty");
		//ValidationUtils.rejectIfEmpty(errors, "companyId", "employee.company_id.empty");		
	}

}
