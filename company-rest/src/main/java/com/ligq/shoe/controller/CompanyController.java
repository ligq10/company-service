package com.ligq.shoe.controller;


import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.rest.core.RepositoryConstraintViolationException;
import org.springframework.data.web.PageableDefault;
import org.springframework.hateoas.Resource;
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
import com.ligq.shoe.request.CompanyAddRequest;
import com.ligq.shoe.service.CompanyService;
import com.ligq.shoe.validator.AddCompanyValidator;

@Controller
public class CompanyController{
	private final static Logger logger = LoggerFactory.getLogger(CompanyController.class); 


	@Autowired
	private AddCompanyValidator addCompanyValidator;
	@Autowired
	private CompanyService companyService;

	
	@RequestMapping(value="/companies", method = RequestMethod.GET, 
		produces = JhMediaType.APPLICATION_DEFAULT_JSON_VALUE)
	public HttpEntity<?> findCompanys(
			@RequestParam(value = "categoryId", required = false, defaultValue = "") String categoryId,
			@RequestParam(value = "subCategoryId", required = false, defaultValue = "") String subCategoryId,
			@RequestParam(value = "name", required = false, defaultValue = "") String name,
			@RequestParam(value = "serviceStatus", required = false, defaultValue = "") String serviceStatus,
			@RequestParam(value = "groupId", required = false, defaultValue = "") String groupId,
			@PageableDefault(page = 0, size = 20,sort = { "modifiedTimestamp" }, 
				direction = Sort.Direction.DESC) Pageable pageable,
			HttpServletRequest request){		
		
		ResponseEntity<?> responseEntity =  null;
		
		try {	
			responseEntity = companyService.findCompanies(categoryId, subCategoryId,
					name, serviceStatus, groupId, pageable, request);
		} catch (Exception e) {			
			logger.error(e.getMessage(),e);						
			return new ResponseEntity<HttpStatus>(HttpStatus.INTERNAL_SERVER_ERROR);				
		}		
	   
        return responseEntity;
		
	}
	
	@RequestMapping(value="/companies/{uuid}",method=RequestMethod.GET,
			produces = JhMediaType.APPLICATION_DEFAULT_JSON_VALUE)
	public HttpEntity<?> getCompanyByID(@PathVariable String uuid,HttpServletRequest request){			
		ResponseEntity<?> responseEntity =  null;

		try{
			
			responseEntity = companyService.findCompanyById(uuid,request);
			
		}catch(Exception e){
			logger.error(e.getMessage());			
			responseEntity=new ResponseEntity<Object>(HttpStatus.INTERNAL_SERVER_ERROR);			
		}		
		return responseEntity;
	}
	

	
	@RequestMapping(value="/companies/{uuid}/homepageresources",method=RequestMethod.GET,
			produces = JhMediaType.APPLICATION_DEFAULT_JSON_VALUE)
	public HttpEntity<?> getHomePageResourcesByID(
			@PathVariable String uuid,
			HttpServletRequest request){
		
		ResponseEntity<?> responseEntity =  null;
		try{		
			responseEntity = companyService
					.getHomePageResourcesByUuid(uuid,request);
		}catch(Exception e){
			logger.error(e.getMessage());			
			responseEntity = new ResponseEntity<Resource>(
					HttpStatus.INTERNAL_SERVER_ERROR);			
		}
		return responseEntity;
	}
	
	@RequestMapping(value="/companies",method=RequestMethod.POST,
			produces = JhMediaType.APPLICATION_DEFAULT_JSON_VALUE)
	public HttpEntity<?> saveCompany(
			@RequestBody CompanyAddRequest companyRequest,
			HttpServletRequest request,
			BindingResult result){
		
		addCompanyValidator.validate(companyRequest, result);
		if(result.hasErrors()){
			logger.error("Add Company validation failed:"+result);
			throw new RepositoryConstraintViolationException(result);
		}
		ResponseEntity<?> responseEntity =  null;
		try {
			responseEntity = companyService.saveCompany(companyRequest,request);
		} catch (Exception e) {
			logger.error(e.getMessage(),e);
			return new ResponseEntity<Object>(HttpStatus.INTERNAL_SERVER_ERROR);
		}	
		return responseEntity;
	}
	
	@SuppressWarnings("unchecked")
	@RequestMapping(value="/companies/deletecompanies",method=RequestMethod.POST,
		produces = JhMediaType.APPLICATION_DEFAULT_JSON_VALUE)
	public HttpEntity<?> deleteCompaniesByIds(
			@RequestBody List<String> companyIds,
			HttpServletRequest request,
			BindingResult result){

		ResponseEntity<?> responseEntity =  null;
		try {
			responseEntity = companyService
					.deleteCompaniesByIds(companyIds,request);

		} catch (Exception e) {
			logger.error(e.getMessage(),e);
			return new ResponseEntity<Object>(
					HttpStatus.INTERNAL_SERVER_ERROR);
		}
		return responseEntity;
	}
	
	@RequestMapping(value="/companies/updatecompanystatus/{uuid}",
			method=RequestMethod.POST,
			produces = JhMediaType.APPLICATION_DEFAULT_JSON_VALUE)
	public HttpEntity<?> updateCompanyStatus(
			@PathVariable String uuid,
			@RequestParam(value = "serviceStatus",
				required = false, defaultValue = "") String serviceStatus,
			HttpServletRequest request){
		
		ResponseEntity<?> responseEntity =  null;		

        try{
        	CompanyAddRequest companyRequest = new CompanyAddRequest();
        	companyRequest.setServiceStatus(serviceStatus);
        	
			responseEntity = companyService.updateCompany(uuid,companyRequest,request);		

        }catch(Exception e){
        	logger.error("更新服务状态失败："+e.getMessage());
        	return new ResponseEntity<HttpStatus>(
        			HttpStatus.INTERNAL_SERVER_ERROR);
        }
        return responseEntity;
	}
	
	
	@RequestMapping(value="/companies/{uuid}",method=RequestMethod.POST,
			produces =JhMediaType.APPLICATION_DEFAULT_JSON_VALUE)
	public HttpEntity<?> updateCompany(
			@PathVariable String uuid,
			@RequestBody CompanyAddRequest companyRequest,
			HttpServletRequest request){
		
		ResponseEntity<?> responseEntity =  null;		
		
		try{
			responseEntity = companyService
					.updateCompany(uuid,companyRequest,request);		
		}catch(Exception e){
			logger.error(e.getMessage(),e);
			return new ResponseEntity<>(
					HttpStatus.INTERNAL_SERVER_ERROR);	
		}

		return responseEntity;
	}
	
	@RequestMapping(value="/companies/{uuid}", method = RequestMethod.DELETE,
			produces = JhMediaType.APPLICATION_DEFAULT_JSON_VALUE)
	public HttpEntity<?> deleteCompanyById(@PathVariable String uuid){		
		ResponseEntity<?> responseEntity =  null;
		try{
			responseEntity = companyService.deleteCompany(uuid);
		}catch(Exception e){
			logger.error(e.getMessage());			
			responseEntity=new ResponseEntity<HttpStatus>(
					HttpStatus.INTERNAL_SERVER_ERROR);
		}		
		return responseEntity;
	}	
	
	@RequestMapping(value="/companies/batchupdateservicestatus",method=RequestMethod.POST,
			produces = JhMediaType.APPLICATION_DEFAULT_JSON_VALUE)
	public HttpEntity<?> batchUpdateCompanyServiceStatus(@RequestBody List<String> ids){
		ResponseEntity<?> responseEntity =  null;
		try{
			responseEntity = companyService.batchUpdateCompanyServiceStatus(ids);
		}catch(Exception e){
			logger.error(e.getMessage());			
			responseEntity=new ResponseEntity<HttpStatus>(
					HttpStatus.INTERNAL_SERVER_ERROR);
		}		
		return responseEntity;
	}

}
