package com.ligq.shoe.controller;

import static org.springframework.hateoas.mvc.ControllerLinkBuilder.linkTo;
import static org.springframework.hateoas.mvc.ControllerLinkBuilder.methodOn;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.transaction.Transactional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.data.rest.core.RepositoryConstraintViolationException;
import org.springframework.data.web.PageableDefault;
import org.springframework.hateoas.Link;
import org.springframework.hateoas.Resource;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.util.StringUtils;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import com.ligq.shoe.constants.JhMediaType;
import com.ligq.shoe.entity.Employee;
import com.ligq.shoe.model.RoleResponse;
import com.ligq.shoe.request.EmployeeAddRequest;
import com.ligq.shoe.response.EmployeeResponse;
import com.ligq.shoe.service.EmployeeService;
import com.ligq.shoe.utils.BeanUtils;
import com.ligq.shoe.validator.AddEmployeeValidator;

@Controller
@SuppressWarnings("all")
public class EmployeeController {
	
	private final static Logger logger = LoggerFactory.getLogger(EmployeeController.class); 
	private final static String SECURITY_TOKEN_HEADER = "X-Token";	

	@Autowired
	private AddEmployeeValidator addEmployeeValidator;
	
	@Autowired
	private EmployeeService employeeService;
	
	@RequestMapping(value="/companies/{uuid}/employees",method=RequestMethod.POST, 
			produces = JhMediaType.APPLICATION_DEFAULT_JSON_VALUE)
	public HttpEntity<?> addEmployee(
			@PathVariable String uuid,
			@RequestBody EmployeeAddRequest employeeAddRequest,
			HttpServletRequest request,
			HttpServletResponse response,
			BindingResult result){
		
		addEmployeeValidator.validate(employeeAddRequest, result);
		if(result.hasErrors()){
			logger.error("Add Employee validation failed:"+result);
			throw new RepositoryConstraintViolationException(result);
		}

		ResponseEntity<Object> responseEntity =  null;		
		try {	        
	        responseEntity=employeeService.save(uuid,employeeAddRequest,request,response);			
		} catch (Exception e) {			
			logger.error(e.getMessage(),e);
			responseEntity=new ResponseEntity<Object>(HttpStatus.BAD_REQUEST);			
		}	
		
		return responseEntity;
	}

	@RequestMapping(value="/employees/{uuid}",method=RequestMethod.PATCH, 
			produces = JhMediaType.APPLICATION_DEFAULT_JSON_VALUE)
	public HttpEntity<?> updateEmployee(
			@PathVariable String uuid,
			@RequestBody EmployeeAddRequest employeeAddRequest,
			HttpServletRequest request,
			HttpServletResponse response,
			BindingResult result){
		
		addEmployeeValidator.validate(employeeAddRequest, result);
		if(result.hasErrors()){
			logger.error("Add Employee validation failed:"+result);
			throw new RepositoryConstraintViolationException(result);
		}

		ResponseEntity<?> responseEntity =  null;		
		try {	        
	        responseEntity=employeeService
	        		.update(uuid,employeeAddRequest,request,response);			
		} catch (Exception e) {			
			logger.error(e.getMessage(),e);
			responseEntity=new ResponseEntity<Object>(HttpStatus.BAD_REQUEST);			
		}	
		
		return responseEntity;
	}
	
    @RequestMapping(value = "/employees/checkloginname", method = RequestMethod.GET,
    		produces = JhMediaType.APPLICATION_DEFAULT_JSON_VALUE)
    public HttpEntity<?> checkLoginName(
            @RequestParam(value = "loginname") String loginName) {

        Employee employee = employeeService.findByLoginName(loginName);
        if (employee == null) {
            return new ResponseEntity<HttpStatus>( HttpStatus.OK);
        } else {
            return new ResponseEntity<HttpStatus>(HttpStatus.BAD_REQUEST);
        }
    }
	
	@RequestMapping(value="/employees/{uuid}",method = RequestMethod.GET,
			produces = JhMediaType.APPLICATION_DEFAULT_JSON_VALUE)
	@Transactional
	public HttpEntity<?> findOneEmployeeById(
			 @PathVariable String uuid,
			 HttpServletRequest request,
			 HttpServletResponse response){
		String token = request.getHeader(SECURITY_TOKEN_HEADER);
		Employee employeeEntity = employeeService.findOneUserById(uuid);
		if(null == employeeEntity){
            return new ResponseEntity<HttpStatus>(HttpStatus.NOT_FOUND);
		}
		
		EmployeeResponse employeeResponse = new EmployeeResponse();
		BeanUtils.copyProperties(employeeEntity, employeeResponse);
	    Link selfLink = linkTo(methodOn(this.getClass())
	    		.findOneEmployeeById(employeeEntity.getUuid(), request, response)).withSelfRel();	    
	    employeeResponse.add(selfLink);
		List<RoleResponse> roles = employeeService
				.findRolesByUserUuid(employeeEntity.getUuid(),token);
		if(null != roles){
			employeeResponse.setRoles(roles);
		}
        return new ResponseEntity<Resource>(
        		new Resource<EmployeeResponse>(
        				employeeResponse), HttpStatus.OK);	
	}

	@RequestMapping(value="/employees/byloginid/{loginid}",method = RequestMethod.GET,
			produces = JhMediaType.APPLICATION_DEFAULT_JSON_VALUE)
	@Transactional
	public HttpEntity<?> findOneEmployeeByloginId(
			 @PathVariable String loginid,
			 HttpServletRequest request,
			 HttpServletResponse response){

		String token = request.getHeader(SECURITY_TOKEN_HEADER);
	    if(StringUtils.isEmpty(token)){
            return new ResponseEntity<HttpStatus>(
            		HttpStatus.UNAUTHORIZED);
	    }
		Employee employeeEntity = employeeService.findByLoginName(loginid);
		if(null == employeeEntity){
            return new ResponseEntity<HttpStatus>(HttpStatus.NO_CONTENT);
		}
		
		EmployeeResponse employeeResponse = new EmployeeResponse();

		BeanUtils.copyProperties(employeeEntity, employeeResponse);
	    Link selfLink = linkTo(methodOn(this.getClass())
	    		.findOneEmployeeById(employeeEntity.getUuid(), request, response)).withSelfRel();	    
	    employeeResponse.add(selfLink);

		List<RoleResponse> roles = employeeService
				.findRolesByUserUuid(employeeEntity.getUuid(),token);
		if(null != roles){
			employeeResponse.setRoles(roles);
		}
        return new ResponseEntity<Resource>(
        		new Resource<EmployeeResponse>(employeeResponse), HttpStatus.OK);		
	}
	
	@RequestMapping(value="/employees",method = RequestMethod.GET, 
			produces = JhMediaType.APPLICATION_DEFAULT_JSON_VALUE)
	@Transactional
	public HttpEntity<?> findEmployee(
			@RequestParam(value = "keyword", required = false, defaultValue = "") String keyword,
			@PageableDefault(page = 0, size = 20, sort = "createTime", 
				direction = Direction.DESC) Pageable pageRequest,
			HttpServletRequest request,
			HttpServletResponse response){

		Page<Employee> employeePage = null;
		employeePage = employeeService.findAllEmployeeByKeword(keyword,pageRequest);
		
		ResponseEntity responseEntity = null;
		try {
			responseEntity = employeeService
					.getResponseEntityConvertEmployeePage(
							"",employeePage, pageRequest, request, response);
		} catch (Exception e) {
			logger.error("User Locations Not Found:",e);
            return new ResponseEntity<HttpStatus>(HttpStatus.NOT_FOUND);			
		}
		return  responseEntity;			
	}
	
	@RequestMapping(value="/employees/{uuid}",method = RequestMethod.DELETE)
	@Transactional
	public HttpEntity<?> deleteUserById(
			 @PathVariable String uuid,
			 HttpServletRequest request,
			 HttpServletResponse response){

		
		ResponseEntity<?> responseEntity =  null;		
		try {	        
	        responseEntity=employeeService.delete(uuid,request,response);			
		} catch (Exception e) {			
			logger.error(e.getMessage(),e);
			return new ResponseEntity<Object>(HttpStatus.INTERNAL_SERVER_ERROR);			
		}	
		
		return responseEntity;
	}
}
