package com.ligq.shoe.controller;

import javax.servlet.http.HttpServletRequest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.ligq.shoe.constants.JhMediaType;
import com.ligq.shoe.request.CataLogAddRequest;
import com.ligq.shoe.request.CataLogUpdateRequest;
import com.ligq.shoe.service.CataLogService;


@Controller
@RequestMapping(value = "/catalogs")
@SuppressWarnings("all")
public class CatalogController {
	private final Logger logger = LoggerFactory.getLogger(getClass());
	
	@Autowired
	private CataLogService cataLogService;
	
	@RequestMapping(method = RequestMethod.GET, 
			produces = JhMediaType.APPLICATION_DEFAULT_JSON_VALUE)
	public HttpEntity<?> findCatalogs(
			@PageableDefault(page = 0, size = 20,sort = { "sort" },
			direction = Sort.Direction.ASC) Pageable pageable,
			HttpServletRequest request) {
		
		ResponseEntity<?> responseEntity =  null;
		try{
			responseEntity = cataLogService.findCatalogs(pageable, request);
		}catch(Exception e){
			logger.error(e.getMessage(),e);
			return new ResponseEntity<HttpStatus>(
					HttpStatus.INTERNAL_SERVER_ERROR);
		}
		return responseEntity;
	}
	
	
	@RequestMapping(method = RequestMethod.POST,
			produces = JhMediaType.APPLICATION_DEFAULT_JSON_VALUE)
	public HttpEntity<?> addCatalog(
			@RequestBody CataLogAddRequest cataLogAddRequest,
			HttpServletRequest request) {
		
		ResponseEntity<?> responseEntity =  null;		
		try {
			responseEntity = cataLogService
					.saveCataLog(cataLogAddRequest,request);

		} catch (Exception e) {
			logger.error(e.getMessage());
			return new ResponseEntity<HttpStatus>(HttpStatus.INTERNAL_SERVER_ERROR);
		}
		return responseEntity;
	}
	
	
	@RequestMapping(value="/{uuid}",method = RequestMethod.GET,
			produces = JhMediaType.APPLICATION_DEFAULT_JSON_VALUE)
	public HttpEntity<?> findCatalogById(
			@PathVariable String uuid,
			HttpServletRequest request) {
		
		ResponseEntity<?> responseEntity =  null;		
		
		try{
			responseEntity = cataLogService
					.findCataLogById(uuid,request);

		}catch(Exception e){
			logger.error(e.getMessage());
			return new ResponseEntity<HttpStatus>(
					HttpStatus.INTERNAL_SERVER_ERROR);
		}
		return responseEntity;
	}
	
	@RequestMapping(value="/{uuid}",method = RequestMethod.POST, 
			produces = JhMediaType.APPLICATION_DEFAULT_JSON_VALUE)
	public HttpEntity<?> updateCatalog(
			@PathVariable String uuid,
			@RequestBody CataLogUpdateRequest cataLogUpdateRequest,
			HttpServletRequest request) {
		
		ResponseEntity<?> responseEntity =  null;		
		try{
			responseEntity = cataLogService
					.updateCataLog(uuid, cataLogUpdateRequest,request);
		}catch(Exception e){
			logger.error(e.getMessage());
			return new ResponseEntity<HttpStatus>(
					HttpStatus.INTERNAL_SERVER_ERROR);
		}
		return responseEntity;
	}
	
	@RequestMapping(value="/{uuid}",method = RequestMethod.DELETE,
			produces = JhMediaType.APPLICATION_DEFAULT_JSON_VALUE)
	public HttpEntity<?> deleteCatalogById(
			@PathVariable String uuid,
			HttpServletRequest request) {
		
		ResponseEntity<?> responseEntity =  null;		
		try{
			responseEntity = cataLogService.deleteCataLogById(uuid,request);
		}catch(Exception e){
			logger.error(e.getMessage());
			return new ResponseEntity<HttpStatus>(
					HttpStatus.INTERNAL_SERVER_ERROR);
		}
		return responseEntity;
	}
	
	@RequestMapping(value="/bycode/{code}",method = RequestMethod.GET,
			produces = JhMediaType.APPLICATION_DEFAULT_JSON_VALUE)
	public HttpEntity<?> getCatalogByCode(
			@PathVariable String code,
			HttpServletRequest request) {
		
		ResponseEntity<?> responseEntity =  null;		

		try{		
			responseEntity = cataLogService.findCataLogByCode(code,request);
		}catch(Exception e){
			logger.error(e.getMessage());
			return new ResponseEntity<HttpStatus>(
					HttpStatus.INTERNAL_SERVER_ERROR);
		}
		return responseEntity;
	}
}
