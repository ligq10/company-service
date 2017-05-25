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

import com.ligq.shoe.controller.CatalogController;
import com.ligq.shoe.entity.CataLog;
import com.ligq.shoe.repository.CataLogRepository;
import com.ligq.shoe.request.CataLogAddRequest;
import com.ligq.shoe.request.CataLogUpdateRequest;
import com.ligq.shoe.response.CataLogDetailResponse;
import com.ligq.shoe.response.CataLogResponse;
import com.ligq.shoe.utils.BeanUtils;
import com.ligq.shoe.utils.DateUtils;
import com.ligq.shoe.utils.GenerateUUIDUtils;
import com.ligq.shoe.utils.LinkUtils;



@Service
public class CataLogService {
	private final Logger logger = LoggerFactory.getLogger(getClass());
	
	@Autowired
	private CataLogRepository cataLogRepository;
	@Autowired
	private CategoryService categoryService;

	public ResponseEntity<?> saveCataLog(CataLogAddRequest cataLogAddRequest,
			HttpServletRequest request)throws Exception{
		
		CataLog cataLog = new CataLog();
		String uuid = null; 
		if(StringUtils.isEmpty(cataLogAddRequest.getCode()) == false){
			uuid = GenerateUUIDUtils.getUUIDByCode(cataLogAddRequest.getCode()).toString();
			CataLog cataLogEntiry = cataLogRepository.findOne(uuid);
			if(null != cataLogEntiry){
				return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
			}
		}else{
			if(StringUtils.isEmpty(cataLogAddRequest.getUuid()) == false){
				uuid = cataLogAddRequest.getUuid();
			}else{
				uuid = UUID.randomUUID().toString();
			}
		}
		Date currentTime = new Date(System.currentTimeMillis());
		BeanUtils.copyPropertiesIgnoreNullValue(cataLogAddRequest, cataLog);
		cataLog.setUuid(uuid);
		cataLog.setCreatedTimestamp(currentTime);
		cataLog.setModifiedTimestamp(currentTime);
		try{			
			cataLogRepository.save(cataLog);
		}catch(Exception e){
			logger.error(e.getMessage(),e);
			return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
		}
		
		HttpHeaders headers = new HttpHeaders(); 
		headers.setLocation(linkTo(methodOn(CatalogController.class)
				.findCatalogById(uuid, request)).toUri()); 
		
		return new ResponseEntity<HttpStatus>(headers, HttpStatus.CREATED);
	}
	
	
    public ResponseEntity<?> findCataLogById(String uuid,
    			HttpServletRequest request)throws Exception{
    	
    	CataLog cataLog=cataLogRepository.findOne(uuid);
    	if(null == cataLog){
			return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    	}
    	
    	CataLogDetailResponse cataLogDetailResponse = new CataLogDetailResponse();	
    	BeanUtils.copyProperties(cataLog, cataLogDetailResponse);
    	cataLogDetailResponse.setCreatedTimestamp(
    			DateUtils.composeUTCTime(cataLog.getCreatedTimestamp()));
    	cataLogDetailResponse.setModifiedTimestamp(
    			DateUtils.composeUTCTime(cataLog.getModifiedTimestamp()));
    	
		Link link = linkTo(methodOn(CatalogController.class)
				.findCatalogById(uuid, request)).withSelfRel();		
		cataLogDetailResponse.add(link);
		
		return new ResponseEntity<CataLogDetailResponse>(
				cataLogDetailResponse,HttpStatus.OK);
    }
    
    
    public ResponseEntity<?> deleteCataLogById(String uuid,
    		HttpServletRequest request)throws Exception{
    	
    	
    	CataLog cataLog=cataLogRepository.findOne(uuid);
    	if(null == cataLog){	
			return new ResponseEntity<>("catalog not fund",HttpStatus.BAD_REQUEST);
    	}
    	try{
    		Pageable pageable = new PageRequest(0, 20);
    		categoryService.deleteCategoryByCatalogUuid(cataLog.getUuid(), pageable);
    		cataLogRepository.delete(cataLog);
    	}catch(Exception e){
    		logger.error(e.getMessage(),e);;
			return new ResponseEntity<>(e.getMessage(),HttpStatus.BAD_REQUEST);

    	}

		return new ResponseEntity<HttpStatus>(HttpStatus.OK);

    }


	public ResponseEntity<?> findCatalogs(Pageable pageable,
			HttpServletRequest request) {
		Page<CataLog> catalogPage = cataLogRepository.findAll(pageable);
		if(null == catalogPage || catalogPage.getContent().isEmpty()){
	        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
		}
		
		List<Link> list = LinkUtils.prepareLinks(pageable.getPageNumber(),
				pageable.getPageSize(), request, catalogPage,"");
		
		List<CataLogResponse> content = new ArrayList<CataLogResponse>();
		for(CataLog cataLog : catalogPage.getContent()){
			CataLogResponse cataLogResponse = new CataLogResponse();
			BeanUtils.copyPropertiesIgnoreNullValue(cataLog, cataLogResponse);
			content.add(cataLogResponse);
		}

		PagedResources<CataLogResponse> pagedResources = new PagedResources<CataLogResponse>(
				content, new PageMetadata(catalogPage.getSize(), catalogPage.getNumber(),
						catalogPage.getTotalElements(), catalogPage.getTotalPages()),
				list);
		return new ResponseEntity(pagedResources, HttpStatus.OK);
	}


	public ResponseEntity<?> updateCataLog(String uuid,
			CataLogUpdateRequest cataLogUpdateRequest,
			HttpServletRequest request) {
		CataLog catalogEntity = cataLogRepository.findOne(uuid);
		if(null == catalogEntity){
			return new ResponseEntity<>("catalog not fund",HttpStatus.BAD_REQUEST);
		}
		Date currentTime = new Date(System.currentTimeMillis());
		BeanUtils.copyPropertiesIgnoreNullValue(cataLogUpdateRequest, catalogEntity);
		catalogEntity.setModifiedTimestamp(currentTime);

		try {
			cataLogRepository.save(catalogEntity);
		} catch (Exception e) {
			logger.error(e.getMessage(),e);
			return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
		}
		return new ResponseEntity<HttpStatus>(HttpStatus.OK);
	}


	public ResponseEntity<?> findCataLogByCode(String code,
			HttpServletRequest request) {
		String uuid = GenerateUUIDUtils.getUUIDByCode(code).toString();
    	CataLog cataLog=cataLogRepository.findOne(uuid);
    	if(null == cataLog){
			return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    	}
    	
    	CataLogDetailResponse cataLogDetailResponse = new CataLogDetailResponse();	
    	BeanUtils.copyProperties(cataLog, cataLogDetailResponse);
    	cataLogDetailResponse.setCreatedTimestamp(
    			DateUtils.composeUTCTime(cataLog.getCreatedTimestamp()));
    	cataLogDetailResponse.setModifiedTimestamp(
    			DateUtils.composeUTCTime(cataLog.getModifiedTimestamp()));
    	
		Link link = linkTo(methodOn(CatalogController.class)
				.findCatalogById(uuid, request)).withSelfRel();		
		cataLogDetailResponse.add(link);
		
		return new ResponseEntity<CataLogDetailResponse>(
				cataLogDetailResponse,HttpStatus.OK);
	}
}
