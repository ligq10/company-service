package com.ligq.shoe.controller;

import javax.servlet.http.HttpServletRequest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.data.rest.core.RepositoryConstraintViolationException;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.ligq.shoe.constants.JhMediaType;
import com.ligq.shoe.request.AddCategoryRequest;
import com.ligq.shoe.request.CategoryUpdateRequest;
import com.ligq.shoe.service.CategoryService;
import com.ligq.shoe.validator.AddCategoryValidator;
import com.ligq.shoe.validator.UpdateCategoryValidator;


@Controller
@SuppressWarnings("all")
public class CategoryController {
	private final Logger logger = LoggerFactory.getLogger(getClass());

	@Autowired
	private AddCategoryValidator addcategoryvalidator;
	@Autowired
	private UpdateCategoryValidator updatecategoryvalidator;
	@Autowired
	private MessageSource messageSource;
	@Autowired
	private CategoryService categoryService;

	@RequestMapping(value = "/catalogs/{cataloguuid}/categories", 
			method = RequestMethod.GET, 
			produces = JhMediaType.APPLICATION_DEFAULT_JSON_VALUE)
	public HttpEntity<?> findCategoriesByCatalogUuid(
			@PathVariable String cataloguuid, 
			@PageableDefault(page = 0, size = 20,sort = { "sort" },
				direction = Direction.ASC) Pageable pageable,
			HttpServletRequest request) {
		
		ResponseEntity<?> responseEntity =  null;		
		try{
			responseEntity = categoryService.findCategoriesByCatalogUuid(cataloguuid,pageable,request);
		}catch(Exception e){
			logger.error(e.getMessage(),e);
			return new ResponseEntity<HttpStatus>(HttpStatus.NOT_FOUND);
		}
		return responseEntity;
	}
	
	@RequestMapping(value = "/catalogs/{cataloguuid}/categories",
			method = RequestMethod.POST,
			produces = JhMediaType.APPLICATION_DEFAULT_JSON_VALUE)
	public HttpEntity<?> addCategory(
			@PathVariable String cataloguuid,
			@RequestBody AddCategoryRequest categoryRequest,
			BindingResult result,
			HttpServletRequest request) {
		
		ResponseEntity<?> responseEntity =  null;		
		addcategoryvalidator.validate(categoryRequest, result);
		if (result.hasErrors()) {
			logger.error("Add category validator error:", result);
			throw new RepositoryConstraintViolationException(result);
		}
		
		try{
			responseEntity = categoryService
					.saveCategoryFromCatalog(cataloguuid,categoryRequest,request);
		}catch(Exception e){
			logger.error(e.getMessage(),e);
			return new ResponseEntity<HttpStatus>(HttpStatus.BAD_REQUEST);
		}
		
		return responseEntity;
	}
	
	@RequestMapping(value = "/catalogs/x/categories/{parentId}/children",
			method = RequestMethod.POST,
			produces = JhMediaType.APPLICATION_DEFAULT_JSON_VALUE)
	public HttpEntity<?> addCategoryAndChild( 
			@PathVariable String parentId,
			@RequestBody AddCategoryRequest categoryRequest,
			BindingResult result,
			HttpServletRequest request) {
		
		ResponseEntity<?> responseEntity =  null;		
		addcategoryvalidator.validate(categoryRequest, result);
		if (result.hasErrors()) {
			logger.error("Add category validator error:", result);
			throw new RepositoryConstraintViolationException(result);
		}
		
		try{
			responseEntity = categoryService
					.saveCategoryFromParentCategory(
							parentId,categoryRequest,request);
		}catch(Exception e){
			logger.error(e.getMessage());
			return new ResponseEntity<HttpStatus>(
					HttpStatus.INTERNAL_SERVER_ERROR);
		}
		
		return responseEntity;
		
	}

	@RequestMapping(value = "/catalogs/x/categories/{uuid}", 
			method = RequestMethod.GET, 
			produces = JhMediaType.APPLICATION_DEFAULT_JSON_VALUE)
	public HttpEntity<?> getCategoryByUuid(
			@PathVariable String uuid, 
			HttpServletRequest request) {
		
		ResponseEntity<?> responseEntity =  null;		
		try{
			responseEntity = categoryService.findCategoryByUuid(uuid,request);
		}catch(Exception e){
			logger.error(e.getMessage(),e);
			return new ResponseEntity<HttpStatus>(
					HttpStatus.INTERNAL_SERVER_ERROR);
		}
		return responseEntity;
		
	}

	@RequestMapping(value = "/catalogs/x/categories/{uuid}/children",
			method = RequestMethod.GET, 
			produces = JhMediaType.APPLICATION_DEFAULT_JSON_VALUE)
	public HttpEntity<?> getCategoryChildrenByUuid(
			@PathVariable String uuid, 
			@PageableDefault(page = 0, size = 20,sort = { "sort" },
				direction = Sort.Direction.ASC) Pageable pageable,
			HttpServletRequest request) {
		
		ResponseEntity<?> responseEntity =  null;		
		try{
			responseEntity = categoryService
					.findCategoriesByParentId(uuid,pageable,request);
		}catch(Exception e){
			logger.error(e.getMessage(),e);
			return new ResponseEntity<HttpStatus>(
					HttpStatus.INTERNAL_SERVER_ERROR);
		}
		return responseEntity;
		
	}
	
	@RequestMapping(value = "/catalogs/x/categories/{uuid}",method = RequestMethod.POST,
			produces = JhMediaType.APPLICATION_DEFAULT_JSON_VALUE)
	public HttpEntity<?> updateCategoryAndChildren(
			@PathVariable String uuid,
			@RequestBody CategoryUpdateRequest categoryUpdateRequest,
			BindingResult result,
			HttpServletRequest request) {
		
		updatecategoryvalidator.validate(categoryUpdateRequest, result);
		if (result.hasErrors()) {
			logger.error("Update category validator fails:", result);
			throw new RepositoryConstraintViolationException(result);
		}
		
		ResponseEntity<?> responseEntity =  null;		
		try{
			responseEntity = categoryService
					.updateCategory(uuid,categoryUpdateRequest,request);
		}catch(Exception e){
			logger.error(e.getMessage(),e);
			return new ResponseEntity<HttpStatus>(
					HttpStatus.INTERNAL_SERVER_ERROR);
		}
		return responseEntity;
		
	}

	@Transactional
	@RequestMapping(value = "/catalogs/x/categories/{uuid}",
		method = RequestMethod.DELETE, 
		produces = JhMediaType.APPLICATION_DEFAULT_JSON_VALUE)
	public HttpEntity<?> delCategory(
			@PathVariable String uuid,
			HttpServletRequest request) {
		
		ResponseEntity<?> responseEntity =  null;		
		try{
			responseEntity = categoryService.deleteCategory(uuid,request);
		}catch(Exception e){
			logger.error(e.getMessage(),e);
			return new ResponseEntity<HttpStatus>(
					HttpStatus.INTERNAL_SERVER_ERROR);
		}
		return responseEntity;
		
	}
	
}
