package com.ligq.shoe.controller;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
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
import com.ligq.shoe.request.AddProductPriceRequest;
import com.ligq.shoe.request.UpdateProductPriceRequest;
import com.ligq.shoe.service.ProductPriceService;
import com.ligq.shoe.validator.AddProductPriceValidator;
import com.ligq.shoe.validator.UpdateProductPriceValidator;


@Controller
@SuppressWarnings("all")
public class ProductPriceController {
	
	private final Logger logger = LoggerFactory.getLogger(getClass());   

    @Autowired
	private  AddProductPriceValidator addProductPriceValidator; 
    @Autowired
	private  UpdateProductPriceValidator updateProductPriceValidator;

    @Autowired
    private ProductPriceService productPriceService;

    @RequestMapping(value = "/companies/{uuid}/productprices",method = RequestMethod.POST,
    		 produces = JhMediaType.APPLICATION_DEFAULT_JSON_VALUE)
    public HttpEntity<?> addProductPrice(
    		@PathVariable String uuid,  		
    		@RequestBody AddProductPriceRequest addProductPriceRequest,
    		HttpServletRequest request,
    		BindingResult result) {
    	
    	addProductPriceValidator.validate(addProductPriceRequest, result);
    	if (result.hasErrors()) {
    		logger.error("Add product price validation error:", result);
			throw new RepositoryConstraintViolationException(result);
		}
		ResponseEntity<?> responseEntity =  null;
		try {	
			responseEntity = productPriceService
					.saveProductPrice(uuid, addProductPriceRequest,request);
		} catch (Exception e) {			
			logger.error(e.getMessage(),e);						
			return new ResponseEntity<HttpStatus>(HttpStatus.BAD_REQUEST);				
		}		
	   
        return responseEntity;
    	   
    }
	
	@RequestMapping(value="/companies/{uuid}/productprices",method=RequestMethod.GET,
			produces = JhMediaType.APPLICATION_DEFAULT_JSON_VALUE)
	public HttpEntity<?> findProductPricesbycondition(
			@PathVariable String uuid,
			@RequestParam(value = "productName", required = false, defaultValue = "") String productName,
			@RequestParam(value = "status", required = false, defaultValue = "") String status,
			@PageableDefault(page = 0, size = 20) Pageable pageable,
			HttpServletRequest request){
		
		ResponseEntity<?> responseEntity =  null;		
		try {	
   		    responseEntity = productPriceService
   		    		.findProductPricesbycondition(
   		    				uuid,productName,status,pageable,request);
		} catch (Exception e) {			
			logger.error(e.getMessage());						
			responseEntity= new ResponseEntity<Object>(HttpStatus.NOT_FOUND);				
		}		
	   
        return responseEntity;
	}
    
    @RequestMapping(value = "/productprices",method = RequestMethod.GET,
    		produces = JhMediaType.APPLICATION_DEFAULT_JSON_VALUE)
    public HttpEntity<?> getProductPrices(
			@PageableDefault(page = 0, size = 20) Pageable pageable,
            HttpServletRequest request) {
    		
	    ResponseEntity<?> responseEntity =  null;	
    	try{ 
    		 responseEntity = productPriceService.findAll(pageable,request);
    	}catch(Exception e){
    		logger.error(e.getMessage(),e);						
			return new ResponseEntity<HttpStatus>(HttpStatus.NOT_FOUND);				
    	}
    	return responseEntity;		
    }
    
    @RequestMapping(value = "/productprices/{uuid}", method = RequestMethod.GET,
    		produces = JhMediaType.APPLICATION_DEFAULT_JSON_VALUE)
    public HttpEntity<?> findProductPriceByUuid(
    		@PathVariable String uuid,
    		HttpServletRequest request) {
    	
	    ResponseEntity<?> responseEntity =  null;	
   	
    	try{ 
   		 responseEntity = productPriceService.findProductPriceByUuid(uuid,request);
	   	}catch(Exception e){
	   		logger.error(e.getMessage(),e);						
	   		return new ResponseEntity<HttpStatus>(HttpStatus.NOT_FOUND);				
	   	}
	   	return responseEntity;		   	
    }
    
    @RequestMapping(value = "/productprices/{uuid}",method = RequestMethod.POST,
    		produces = JhMediaType.APPLICATION_DEFAULT_JSON_VALUE)
    public HttpEntity<?> updateProductPrice(
    		@PathVariable String uuid,
    		@RequestBody UpdateProductPriceRequest updateProductPriceRequest,
    		HttpServletRequest request) {

		ResponseEntity<?> responseEntity =  null;
		try {	
			responseEntity = productPriceService
					.updateProductPriceByUuid(
							uuid, updateProductPriceRequest,request);
		} catch (Exception e) {			
			logger.error(e.getMessage(),e);						
			return new ResponseEntity<HttpStatus>(HttpStatus.BAD_REQUEST);				
		}		
	   
        return responseEntity;
    	
    }
    
    @RequestMapping(value = "/productprices/{uuid}", method = RequestMethod.DELETE,
    		produces = JhMediaType.APPLICATION_DEFAULT_JSON_VALUE)
    public HttpEntity<?> delProductPrice(
    		@PathVariable String uuid,
    		HttpServletRequest request) {
    	
    	
		ResponseEntity<?> responseEntity =  null;
		try {	
			responseEntity = productPriceService
					.deleteProductPriceByUuid(uuid,request);
		} catch (Exception e) {			
			logger.error(e.getMessage(),e);						
			return new ResponseEntity<HttpStatus>(HttpStatus.BAD_REQUEST);				
		}		
	   
        return responseEntity;	       
    }
    
	@SuppressWarnings("unchecked")
	@RequestMapping(value="/productprices/deleteproductprices",method=RequestMethod.POST,
		 produces = JhMediaType.APPLICATION_DEFAULT_JSON_VALUE)
	public HttpEntity<?> deleteProductPricesByIds(
			@RequestBody List<String> productPriceIds,
			HttpServletRequest request,
			BindingResult result){
		
		ResponseEntity<?> responseEntity =  null;
		try {	
			responseEntity = productPriceService
					.batchDeleteProductPriceByIds(productPriceIds,request);
		} catch (Exception e) {			
			logger.error(e.getMessage(),e);						
			return new ResponseEntity<HttpStatus>(HttpStatus.BAD_REQUEST);				
		}		
	   
        return responseEntity;	 	
	}
	
}
