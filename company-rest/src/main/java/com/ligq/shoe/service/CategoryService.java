package com.ligq.shoe.service;

import static org.springframework.hateoas.mvc.ControllerLinkBuilder.linkTo;
import static org.springframework.hateoas.mvc.ControllerLinkBuilder.methodOn;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.UUID;

import javax.servlet.http.HttpServletRequest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.hateoas.Link;
import org.springframework.hateoas.PagedResources;
import org.springframework.hateoas.PagedResources.PageMetadata;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import com.ligq.shoe.controller.CategoryController;
import com.ligq.shoe.entity.Category;
import com.ligq.shoe.repository.CategoryRepository;
import com.ligq.shoe.request.AddCategoryRequest;
import com.ligq.shoe.request.CategoryUpdateRequest;
import com.ligq.shoe.response.CategoryDetailResponse;
import com.ligq.shoe.response.CategoryResponse;
import com.ligq.shoe.utils.BeanUtils;
import com.ligq.shoe.utils.DateUtils;
import com.ligq.shoe.utils.GenerateUUIDUtils;
import com.ligq.shoe.utils.LinkUtils;

@Service
public class CategoryService {

	private final Logger logger = LoggerFactory.getLogger(getClass());

	@Autowired
	private CategoryRepository categoryRepository;
	
	public ResponseEntity<?> findCategoriesByCatalogUuid(String cataloguuid,
			Pageable pageable, HttpServletRequest request) {
		Page<Category> categoryPage = categoryRepository.findByCatalogId(cataloguuid,pageable);
		if(null == categoryPage || categoryPage.getContent().isEmpty()){
	        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
		}
		List<Link> list = LinkUtils.prepareLinks(pageable.getPageNumber(),
				pageable.getPageSize(), request, categoryPage,"");
		
		List<CategoryResponse> result = new ArrayList<CategoryResponse>();
		for (Category category : categoryPage.getContent()) {
			CategoryResponse categoryResponse = new CategoryResponse();
			BeanUtils.copyPropertiesIgnoreNullValue(category, categoryResponse);
			categoryResponse.setCreatedTime(
					DateUtils.composeUTCTime(category.getCreatedTime()));
			Link link = linkTo(methodOn(CategoryController.class)
					.getCategoryByUuid(category.getUuid(), request)).withSelfRel();
			categoryResponse.add(link);
			result.add(categoryResponse);
		}
		
		PagedResources<CategoryResponse> pagedResources = new PagedResources<CategoryResponse>(
				result, new PageMetadata(categoryPage.getSize(), categoryPage.getNumber(),
						categoryPage.getTotalElements(), categoryPage.getTotalPages()),
				list);
		return new ResponseEntity(pagedResources, HttpStatus.OK);
		
	}

	public ResponseEntity<?> saveCategoryFromCatalog(String cataloguuid,
			AddCategoryRequest categoryRequest, HttpServletRequest request) {
		
		Category category = new Category();
		String uuid = null; 
		if(StringUtils.isEmpty(categoryRequest.getCode()) == false){
			uuid = GenerateUUIDUtils.getUUIDByCode(categoryRequest.getCode()).toString();
			Category categoryEntiry = categoryRepository.findOne(uuid);
			if(null != categoryEntiry){
				return new ResponseEntity<>("code already exist",HttpStatus.BAD_REQUEST);
			}
		}else{
			if(StringUtils.isEmpty(categoryRequest.getUuid()) == false){
				uuid = categoryRequest.getUuid();
			}else{
				uuid = UUID.randomUUID().toString();
			}
		}
		Date currentTime = new Date(System.currentTimeMillis());
		BeanUtils.copyPropertiesIgnoreNullValue(categoryRequest, category);
		category.setUuid(uuid);
		category.setCreatedTime(currentTime);
		category.setUpdateTime(currentTime);
		category.setCatalogId(cataloguuid);
		category.setLevel(1);
		try{			
			categoryRepository.save(category);
		}catch(Exception e){
			logger.error(e.getMessage(),e);
			return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
		}
		
		HttpHeaders headers = new HttpHeaders(); 
		headers.setLocation(linkTo(methodOn(CategoryController.class)
				.getCategoryByUuid(uuid, request)).toUri()); 
		
		return new ResponseEntity<HttpStatus>(headers, HttpStatus.CREATED);
	}

	public ResponseEntity<?> saveCategoryFromParentCategory(String parentId,
			AddCategoryRequest categoryRequest, HttpServletRequest request) {
		
		Category parentCategory = categoryRepository.findOne(parentId);
		if(null == parentCategory){
			return new ResponseEntity<>("parent category not found!",HttpStatus.BAD_REQUEST);
		}
		
		Category category = new Category();
		String uuid = null; 
		if(StringUtils.isEmpty(categoryRequest.getCode()) == false){
			uuid = GenerateUUIDUtils.getUUIDByCode(categoryRequest.getCode()).toString();
			Category categoryEntiry = categoryRepository.findOne(uuid);
			if(null != categoryEntiry){
				return new ResponseEntity<>("code already exist",HttpStatus.BAD_REQUEST);
			}
		}else{
			if(StringUtils.isEmpty(categoryRequest.getUuid()) == false){
				uuid = categoryRequest.getUuid();
			}else{
				uuid = UUID.randomUUID().toString();
			}
		}
		Date currentTime = new Date(System.currentTimeMillis());
		BeanUtils.copyPropertiesIgnoreNullValue(categoryRequest, category);
		category.setUuid(uuid);
		category.setParentId(parentId);
		category.setCreatedTime(currentTime);
		category.setUpdateTime(currentTime);
		category.setLevel(parentCategory.getLevel()+1);
		try{			
			categoryRepository.save(category);
		}catch(Exception e){
			logger.error(e.getMessage(),e);
			return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
		}
		
		HttpHeaders headers = new HttpHeaders(); 
		headers.setLocation(linkTo(methodOn(CategoryController.class)
				.getCategoryByUuid(uuid, request)).toUri()); 
		
		return new ResponseEntity<HttpStatus>(headers, HttpStatus.CREATED);
	}

	public ResponseEntity<?> findCategoryByUuid(String uuid,
			HttpServletRequest request) {
		
		Category categoryEntiry = categoryRepository.findOne(uuid);
		if(null == categoryEntiry){
			return new ResponseEntity<>(HttpStatus.NO_CONTENT);
		}
		CategoryDetailResponse categoryResponse = new CategoryDetailResponse();
		BeanUtils.copyProperties(categoryEntiry, categoryResponse);
		categoryResponse.setCreatedTime(
				DateUtils.composeUTCTime(categoryEntiry.getCreatedTime()));
		List<Link> links = null;
		try {
			links = categoryAddLink(categoryEntiry, request);
		} catch (Exception e) {
			logger.error(e.getMessage(),e);
		}
		categoryResponse.add(links);
		
		return new ResponseEntity<CategoryDetailResponse>(
				categoryResponse, HttpStatus.OK);
	}
	
	public Category findCategoryByUuid(String uuid) {
		
		return categoryRepository.findOne(uuid);

	}
	
	
	private List<Link> categoryAddLink(Category category, HttpServletRequest request)throws Exception {
		List<Link> links = new ArrayList<Link>();
		
		Link selfLink = linkTo(methodOn(CategoryController.class)
				.getCategoryByUuid(category.getUuid(), request)).withSelfRel();		
		Link childrenLink = linkTo(methodOn(CategoryController.class)
				.getCategoryChildrenByUuid(category.getUuid(),
						new PageRequest(0, 20), request)).withRel("children");
		
		if(StringUtils.isEmpty(category.getParentId()) == false){
			Link parentLink = linkTo(methodOn(CategoryController.class)
					.getCategoryByUuid(category.getParentId(), request)).withRel("parent");
			links.add(parentLink);
		}
		links.add(selfLink);
		links.add(childrenLink);		
		return links;
	}

	@SuppressWarnings("rawtypes")
	public ResponseEntity<?> findCategoriesByParentId(String parentId,
			Pageable pageable, HttpServletRequest request) {
		Page<Category> categoryPage = categoryRepository.findByParentId(parentId,pageable);
		if(null == categoryPage || categoryPage.getContent().isEmpty()){
	        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
		}
		List<Link> list = LinkUtils.prepareLinks(pageable.getPageNumber(),
				pageable.getPageSize(), request, categoryPage,"");
		
		List<CategoryResponse> result = new ArrayList<CategoryResponse>();
		for (Category category : categoryPage.getContent()) {
			CategoryResponse categoryResponse = new CategoryResponse();
			BeanUtils.copyPropertiesIgnoreNullValue(category, categoryResponse);
			categoryResponse.setCreatedTime(
					DateUtils.composeUTCTime(category.getCreatedTime()));
			Link link = linkTo(methodOn(CategoryController.class)
					.getCategoryByUuid(category.getUuid(), request)).withSelfRel();
			categoryResponse.add(link);
			result.add(categoryResponse);
		}
		
		PagedResources<CategoryResponse> pagedResources = new PagedResources<CategoryResponse>(
				result, new PageMetadata(categoryPage.getSize(), categoryPage.getNumber(),
						categoryPage.getTotalElements(), categoryPage.getTotalPages()),
				list);
		return new ResponseEntity<PagedResources>(pagedResources, HttpStatus.OK);
	}

	public ResponseEntity<?> updateCategory(String uuid,
			CategoryUpdateRequest categoryUpdateRequest,
			HttpServletRequest request) {
		
		Category category = categoryRepository.findOne(uuid);
		if(null == category){
	        return new ResponseEntity<>("category not found!",HttpStatus.BAD_REQUEST);
		}
		Date currentTime = new Date(System.currentTimeMillis());
		BeanUtils.copyPropertiesIgnoreNullValue(categoryUpdateRequest, category);
		category.setUpdateTime(currentTime);
		try {
			categoryRepository.save(category);
		} catch (Exception e) {
			logger.error(e.getMessage(),e);
	        return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
		}
		return new ResponseEntity<HttpStatus>(HttpStatus.OK);
	}

	public ResponseEntity<?> deleteCategory(String uuid,
			HttpServletRequest request) {
		Category category = categoryRepository.findOne(uuid);
		if(null == category){
	        return new ResponseEntity<>("category not found!",HttpStatus.BAD_REQUEST);
		}
		Pageable pageable = new PageRequest(0, 20);
		this.deleteChildrenCategory(category.getUuid(), pageable);
		categoryRepository.delete(category);

		return new ResponseEntity<HttpStatus>(HttpStatus.OK);
	}

	public void deleteCategoryByCatalogUuid(String catalogUuid,Pageable pageable){
		
		Page<Category> categoryPage = categoryRepository
				.findByCatalogId(catalogUuid, pageable);
		if(null == categoryPage || categoryPage.getContent().isEmpty()){
			return;
		}
		for(Category category : categoryPage.getContent()){
			Pageable childrenPageable = new PageRequest(0, 20);
			this.deleteChildrenCategory(category.getUuid(), childrenPageable);
			categoryRepository.delete(category);
		}
		if(pageable.getPageNumber() <= categoryPage.getTotalPages()){
			this.deleteCategoryByCatalogUuid(catalogUuid,
					new PageRequest(pageable.getPageNumber(), pageable.getPageSize()));
		}
	}
	
	private void deleteChildrenCategory(String parentId,Pageable pageable){
		Page<Category> categoryPage = categoryRepository.findByParentId(parentId, pageable);
		if(null == categoryPage || categoryPage.getContent().isEmpty()){
			return;
		}
		for(Category category : categoryPage.getContent()){
			Pageable childrenPageable = new PageRequest(0, 20);
			this.deleteChildrenCategory(category.getUuid(), childrenPageable);
			categoryRepository.delete(category);
		}
		
		if(pageable.getPageNumber() <= categoryPage.getTotalPages()){
			this.deleteChildrenCategory(parentId,
					new PageRequest(pageable.getPageNumber(), pageable.getPageSize()));
		}
	}
}
