package com.ligq.shoe.validator;

import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.ValidationUtils;
import org.springframework.validation.Validator;

@Component
public class AddAuditMessageValidator implements Validator{

	@Override
	public boolean supports(Class<?> clazz) {
		// TODO Auto-generated method stub
		// TODO Auto-generated method stub
		return AddAuditMessageValidator.class.equals(clazz);
	}

	@Override
	public void validate(Object target, Errors errors) {
		// TODO Auto-generated method stub
		ValidationUtils.rejectIfEmpty(errors, "businessId", "audit.business.empty");
		ValidationUtils.rejectIfEmpty(errors, "businessType", "audit.businessType.empty");
		ValidationUtils.rejectIfEmpty(errors, "auditorId", "audit.auditorId.empty");
		ValidationUtils.rejectIfEmpty(errors, "auditResult", "audit.auditResult.empty");

		
	}

}
