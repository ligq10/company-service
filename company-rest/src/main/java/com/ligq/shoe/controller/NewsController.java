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
import com.ligq.shoe.request.AddNewsRequest;
import com.ligq.shoe.service.NewsService;
import com.ligq.shoe.validator.AddNewsValidator;

@Controller
public class NewsController {

    private static final Logger logger = LoggerFactory.getLogger(NewsController.class);

    @Autowired
    private NewsService newsService;
    @Autowired
    private AddNewsValidator addNewsValidator;
    
    @RequestMapping(value = "/news", method = RequestMethod.POST)
    public HttpEntity<?> addNews(
    		@RequestBody AddNewsRequest addNewsRequest,
    		BindingResult result,
    		HttpServletRequest request) {

    	addNewsValidator.validate(addNewsRequest, result);
		if (result.hasErrors()) {
			logger.error("Add category validator error:", result);
			throw new RepositoryConstraintViolationException(result);
		}
		ResponseEntity<?> responseEntity =  null;
        try {
        	responseEntity = newsService
        			.saveNews(addNewsRequest,request);
        } catch (Exception e) {
            logger.error(e.getMessage(),e);
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
        return responseEntity;
    	
    }

	@RequestMapping(value = "/news/{uuid}", 
			method = RequestMethod.GET, 
			produces = JhMediaType.APPLICATION_DEFAULT_JSON_VALUE)
	public HttpEntity<?> findNewsByUuid(
			@PathVariable String uuid, 
			HttpServletRequest request) {
		
		ResponseEntity<?> responseEntity =  null;		
		try{
			responseEntity = newsService.findNewsByUuid(uuid,request);
		}catch(Exception e){
			logger.error(e.getMessage(),e);
			return new ResponseEntity<HttpStatus>(
					HttpStatus.INTERNAL_SERVER_ERROR);
		}
		return responseEntity;
		
	}

	@RequestMapping(value="/news",method=RequestMethod.GET,
			produces = JhMediaType.APPLICATION_DEFAULT_JSON_VALUE)
	public HttpEntity<?> findNewsbycondition(
			@RequestParam(value = "keyword", required = false, defaultValue = "") String keyword,
    		@PageableDefault(page = 0, size = 20, sort = "createdTime",
				direction = Sort.Direction.DESC) Pageable pageable, 
			HttpServletRequest request){
		
		ResponseEntity<?> responseEntity =  null;		
		try {	
   		    responseEntity = newsService
   		    		.findNewsbycondition(
   		    				keyword,pageable,request);
		} catch (Exception e) {			
			logger.error(e.getMessage(),e);						
			responseEntity= new ResponseEntity<HttpStatus>(
					HttpStatus.INTERNAL_SERVER_ERROR);				
		}		
	   
        return responseEntity;
	}
	
	@RequestMapping(value = "/news/{uuid}", 
			method = RequestMethod.PATCH, 
			produces = JhMediaType.APPLICATION_DEFAULT_JSON_VALUE)
	public HttpEntity<?> updateNewsByUuid(
			@PathVariable String uuid, 
    		@RequestBody AddNewsRequest addNewsRequest,			
			HttpServletRequest request) {
		
		ResponseEntity<?> responseEntity =  null;		
		try{
			responseEntity = newsService.updateNewsByUuid(
					uuid,addNewsRequest,request);
		}catch(Exception e){
			logger.error(e.getMessage(),e);
			return new ResponseEntity<HttpStatus>(
					HttpStatus.INTERNAL_SERVER_ERROR);
		}
		return responseEntity;
		
	}

	@RequestMapping(value = "/news/{uuid}", 
			method = RequestMethod.DELETE, 
			produces = JhMediaType.APPLICATION_DEFAULT_JSON_VALUE)
	public HttpEntity<?> deleteNewsByUuid(
			@PathVariable String uuid, 
			HttpServletRequest request) {
		
		ResponseEntity<?> responseEntity =  null;		
		try{
			responseEntity = newsService.deleteNewsByUuid(uuid,request);
		}catch(Exception e){
			logger.error(e.getMessage(),e);
			return new ResponseEntity<HttpStatus>(
					HttpStatus.INTERNAL_SERVER_ERROR);
		}
		return responseEntity;
		
	}

}
