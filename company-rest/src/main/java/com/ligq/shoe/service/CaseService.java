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
import org.springframework.data.domain.Pageable;
import org.springframework.hateoas.Link;
import org.springframework.hateoas.PagedResources;
import org.springframework.hateoas.PagedResources.PageMetadata;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import com.ligq.shoe.controller.CaseController;
import com.ligq.shoe.entity.Case;
import com.ligq.shoe.mysql.dynamic.Criteria;
import com.ligq.shoe.mysql.dynamic.Restrictions;
import com.ligq.shoe.repository.CaseRepository;
import com.ligq.shoe.request.AddCaseRequest;
import com.ligq.shoe.response.CaseResponse;
import com.ligq.shoe.utils.BeanUtils;
import com.ligq.shoe.utils.DateUtils;
import com.ligq.shoe.utils.LinkUtils;

@Service
public class CaseService {

    private static final Logger logger = LoggerFactory.getLogger(CaseService.class);
    @Autowired
    private CaseRepository caseRepository;
    
	public ResponseEntity<?> saveCase(
			AddCaseRequest addCaseRequest,
			HttpServletRequest request) {
		
		Case caseEntity = new Case();
		caseEntity.setUuid(UUID.randomUUID().toString());
		BeanUtils.copyPropertiesIgnoreNullValue(addCaseRequest, caseEntity);
		Date currentTime = new Date();
		caseEntity.setCreatedTime(currentTime);
		caseEntity.setModifiedTime(currentTime);
		caseRepository.save(caseEntity);
		HttpHeaders headers = new HttpHeaders(); 
		headers.setLocation(linkTo(methodOn(CaseController.class)
				.findCaseByUuid(caseEntity.getUuid(), request)).toUri()); 
        return new ResponseEntity<HttpStatus>(headers,HttpStatus.CREATED);
	}

	public ResponseEntity<?> findCaseByUuid(String uuid,
			HttpServletRequest request) {
		Case caseEntity = caseRepository.findOne(uuid);
		if(null == caseEntity){
			return new ResponseEntity<>(HttpStatus.NO_CONTENT);
		}
		CaseResponse caseResponse = new CaseResponse();
		BeanUtils.copyProperties(caseEntity, caseResponse);
		caseResponse.setCreatedTime(
				DateUtils.composeUTCTime(caseEntity.getCreatedTime()));
		caseResponse.setModifiedTime(
				DateUtils.composeUTCTime(caseEntity.getModifiedTime()));
		Link link = linkTo(methodOn(CaseController.class)
				.findCaseByUuid(uuid, request)).withSelfRel();		
		caseResponse.add(link);
		
		return new ResponseEntity<CaseResponse>(
				caseResponse, HttpStatus.OK);
	}

	public ResponseEntity<?> findCasebycondition(String keyword,
			Pageable pageable, HttpServletRequest request) {
        StringBuffer sort = new StringBuffer();
		if(null != pageable.getSort()){
        	pageable.getSort().forEach(item -> {
        		sort
        		.append("&sort=")       		
        		.append(item.getProperty())
        		.append(",")
        		.append(item.getDirection().name());
        	});
        }
		
		StringBuffer pathParams = new StringBuffer();	
		pathParams.append(StringUtils.isEmpty(keyword)?"&keyword=":"&keyword="+keyword);
		pathParams.append(sort);
		Page<Case> contentPage = this.findCaseByMultiConditions(keyword,pageable);
		if(null == contentPage
				|| contentPage.getContent().isEmpty()){
			return new ResponseEntity<>(HttpStatus.NO_CONTENT);			
		}
		
		List<CaseResponse> content = new ArrayList<CaseResponse>();

		for(Case caseEntiry : contentPage.getContent()){
			CaseResponse caseResponse = new CaseResponse();
			BeanUtils.copyProperties(caseEntiry, caseResponse);
			caseResponse.setCreatedTime(
					DateUtils.composeUTCTime(caseEntiry.getCreatedTime()));
			caseResponse.setModifiedTime(
					DateUtils.composeUTCTime(caseEntiry.getModifiedTime()));
			Link link = linkTo(methodOn(CaseController.class)
					.findCaseByUuid(caseEntiry.getUuid(), request)).withSelfRel();		
			caseResponse.add(link);
			content.add(caseResponse);
		}
        List<Link> linkList = LinkUtils.prepareLinks(pageable.getPageNumber(), pageable.getPageSize(),
        		request, contentPage, pathParams.toString());
        
		PagedResources<CaseResponse> pagedResources = new PagedResources<CaseResponse>(
				content, new PageMetadata(contentPage.getSize(), contentPage.getNumber(),
						contentPage.getTotalElements(), contentPage.getTotalPages()),
						linkList);
        return new ResponseEntity<>(pagedResources, HttpStatus.OK);
	}

	private Page<Case> findCaseByMultiConditions(String keyword,
			 Pageable pageable){
		
		Criteria<Case> criteria = new Criteria<Case>(); 
		
		if(StringUtils.isEmpty(keyword) == false){
			criteria.add(Restrictions.like("title", keyword, true));
			criteria.add(Restrictions.like("intro", keyword, true));
			criteria.add(Restrictions.like("content", keyword, true));
		}

/*		c.add(Restrictions.like(fieldName, value, true));  
        c.add(Restrictions.eq("level", searchParam.getLevel(), false));  
        c.add(Restrictions.eq("mainStatus", searchParam.getMainStatus(), true));  
        c.add(Restrictions.eq("flowStatus", searchParam.getFlowStatus(), true));  
        c.add(Restrictions.eq("createUser.userName", searchParam.getCreateUser(), true));  
        c.add(Restrictions.lte("submitTime", searchParam.getStartSubmitTime(), true));  
        c.add(Restrictions.gte("submitTime", searchParam.getEndSubmitTime(), true));  
        c.add(Restrictions.eq("needFollow", searchParam.getIsfollow(), true));  
        c.add(Restrictions.ne("flowStatus", CaseConstants.CASE_STATUS_DRAFT, true));  
        c.add(Restrictions.in("solveTeam.code",teamCodes, true)); */ 
		
		Page<Case> page = caseRepository
				.findAll(criteria, pageable);
		return page;
	}

	public ResponseEntity<?> deleteCaseByUuid(String uuid,
			HttpServletRequest request) {
		Case caseEntiry = caseRepository.findOne(uuid);
		if(null == caseEntiry){
			return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
		}
		
		caseRepository.delete(caseEntiry);
		return new ResponseEntity<>(HttpStatus.OK);

	}

	public ResponseEntity<?> updateCaseByUuid(String uuid,
			AddCaseRequest addCaseRequest, HttpServletRequest request) {
		Case caseEntiry = caseRepository.findOne(uuid);
		if(null == caseEntiry){
			return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
		}
		BeanUtils.copyPropertiesIgnoreNullValue(addCaseRequest, caseEntiry);
		Date currentTime = new Date();
		caseEntiry.setModifiedTime(currentTime);
		caseRepository.save(caseEntiry);
		return new ResponseEntity<>(HttpStatus.OK);
	}
}
