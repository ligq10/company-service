package com.ligq.shoe.controller;

import javax.servlet.http.HttpServletRequest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.rest.core.RepositoryConstraintViolationException;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import com.ligq.shoe.constants.JhMediaType;
import com.ligq.shoe.request.AddCaseRequest;
import com.ligq.shoe.service.CaseService;
import com.ligq.shoe.validator.AddCaseValidator;

@Controller
public class CaseController {

    private static final Logger logger = LoggerFactory.getLogger(CaseController.class);

    @Autowired
    private CaseService caseService;
    @Autowired
    private AddCaseValidator addCaseValidator;
    
    @RequestMapping(value = "/cases", method = RequestMethod.POST)
    public HttpEntity<?> addCase(
    		@RequestBody AddCaseRequest addCaseRequest,
    		BindingResult result,
    		HttpServletRequest request) {

    	addCaseValidator.validate(addCaseRequest, result);
		if (result.hasErrors()) {
			logger.error("Add category validator error:", result);
			throw new RepositoryConstraintViolationException(result);
		}
		ResponseEntity<?> responseEntity =  null;
        try {
        	responseEntity = caseService
        			.saveCase(addCaseRequest,request);
        } catch (Exception e) {
            logger.error(e.getMessage(),e);
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
        return responseEntity;
    	
    }

	@RequestMapping(value = "/cases/{uuid}", 
			method = RequestMethod.GET, 
			produces = JhMediaType.APPLICATION_DEFAULT_JSON_VALUE)
	public HttpEntity<?> findCaseByUuid(
			@PathVariable String uuid, 
			HttpServletRequest request) {
		
		ResponseEntity<?> responseEntity =  null;		
		try{
			responseEntity = caseService.findCaseByUuid(uuid,request);
		}catch(Exception e){
			logger.error(e.getMessage(),e);
			return new ResponseEntity<HttpStatus>(
					HttpStatus.INTERNAL_SERVER_ERROR);
		}
		return responseEntity;
		
	}

	@RequestMapping(value="/cases",method=RequestMethod.GET,
			produces = JhMediaType.APPLICATION_DEFAULT_JSON_VALUE)
	public HttpEntity<?> findCasebycondition(
			@RequestParam(value = "keyword", required = false, defaultValue = "") String keyword,
    		@PageableDefault(page = 0, size = 20, sort = "createdTime",
				direction = Sort.Direction.DESC) Pageable pageable, 
			HttpServletRequest request){
		
		ResponseEntity<?> responseEntity =  null;		
		try {	
   		    responseEntity = caseService
   		    		.findCasebycondition(
   		    				keyword,pageable,request);
		} catch (Exception e) {			
			logger.error(e.getMessage(),e);						
			responseEntity= new ResponseEntity<HttpStatus>(
					HttpStatus.INTERNAL_SERVER_ERROR);				
		}		
	   
        return responseEntity;
	}

	@RequestMapping(value = "/cases/{uuid}", 
			method = RequestMethod.PATCH, 
			produces = JhMediaType.APPLICATION_DEFAULT_JSON_VALUE)
	public HttpEntity<?> updateCaseByUuid(
			@PathVariable String uuid, 
    		@RequestBody AddCaseRequest addCaseRequest,
			HttpServletRequest request) {
		
		ResponseEntity<?> responseEntity =  null;		
		try{
			responseEntity = caseService.updateCaseByUuid(
					uuid,addCaseRequest,request);
		}catch(Exception e){
			logger.error(e.getMessage(),e);
			return new ResponseEntity<HttpStatus>(
					HttpStatus.INTERNAL_SERVER_ERROR);
		}
		return responseEntity;
		
	}

	
	@RequestMapping(value = "/cases/{uuid}", 
			method = RequestMethod.DELETE, 
			produces = JhMediaType.APPLICATION_DEFAULT_JSON_VALUE)
	public HttpEntity<?> deleteCaseByUuid(
			@PathVariable String uuid, 
			HttpServletRequest request) {
		
		ResponseEntity<?> responseEntity =  null;		
		try{
			responseEntity = caseService.deleteCaseByUuid(uuid,request);
		}catch(Exception e){
			logger.error(e.getMessage(),e);
			return new ResponseEntity<HttpStatus>(
					HttpStatus.INTERNAL_SERVER_ERROR);
		}
		return responseEntity;
		
	}

}
