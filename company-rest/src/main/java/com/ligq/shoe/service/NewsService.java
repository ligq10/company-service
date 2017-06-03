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

import com.ligq.shoe.controller.NewsController;
import com.ligq.shoe.entity.News;
import com.ligq.shoe.mysql.dynamic.Criteria;
import com.ligq.shoe.mysql.dynamic.Restrictions;
import com.ligq.shoe.repository.NewsRepository;
import com.ligq.shoe.request.AddNewsRequest;
import com.ligq.shoe.response.NewsResponse;
import com.ligq.shoe.utils.BeanUtils;
import com.ligq.shoe.utils.DateUtils;
import com.ligq.shoe.utils.LinkUtils;

@Service
public class NewsService {

    private static final Logger logger = LoggerFactory.getLogger(NewsService.class);
    @Autowired
    private NewsRepository newsRepository;
    
	public ResponseEntity<?> saveNews(
			AddNewsRequest addNewsRequest,
			HttpServletRequest request) {
		News news = new News();
		news.setUuid(UUID.randomUUID().toString());
		BeanUtils.copyPropertiesIgnoreNullValue(addNewsRequest, news);
		Date currentTime = new Date();
		news.setCreatedTime(currentTime);
		news.setModifiedTime(currentTime);
		newsRepository.save(news);
		HttpHeaders headers = new HttpHeaders(); 
		headers.setLocation(linkTo(methodOn(NewsController.class)
				.findNewsByUuid(news.getUuid(), request)).toUri()); 
        return new ResponseEntity<HttpStatus>(headers,HttpStatus.CREATED);
	}

	public ResponseEntity<?> findNewsByUuid(String uuid,
			HttpServletRequest request) {
		News newsEntiry = newsRepository.findOne(uuid);
		if(null == newsEntiry){
			return new ResponseEntity<>(HttpStatus.NO_CONTENT);
		}
		NewsResponse newsResponse = new NewsResponse();
		BeanUtils.copyProperties(newsEntiry, newsResponse);
		newsResponse.setCreatedTime(
				DateUtils.composeUTCTime(newsEntiry.getCreatedTime()));
		newsResponse.setModifiedTime(
				DateUtils.composeUTCTime(newsEntiry.getModifiedTime()));
		Link link = linkTo(methodOn(NewsController.class)
				.findNewsByUuid(uuid, request)).withSelfRel();		
		newsResponse.add(link);
		
		return new ResponseEntity<NewsResponse>(
				newsResponse, HttpStatus.OK);
	}

	public ResponseEntity<?> findNewsbycondition(String keyword,
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
		Page<News> contentPage = this.findNewsByMultiConditions(keyword,pageable);
		if(null == contentPage
				|| contentPage.getContent().isEmpty()){
			return new ResponseEntity<>(HttpStatus.NO_CONTENT);			
		}
		
		List<NewsResponse> content = new ArrayList<NewsResponse>();

		for(News newsEntiry : contentPage.getContent()){
			NewsResponse newsResponse = new NewsResponse();
			BeanUtils.copyProperties(newsEntiry, newsResponse);
			newsResponse.setCreatedTime(
					DateUtils.composeUTCTime(newsEntiry.getCreatedTime()));
			newsResponse.setModifiedTime(
					DateUtils.composeUTCTime(newsEntiry.getModifiedTime()));
			Link link = linkTo(methodOn(NewsController.class)
					.findNewsByUuid(newsEntiry.getUuid(), request)).withSelfRel();		
			newsResponse.add(link);
			content.add(newsResponse);
		}
        List<Link> linkList = LinkUtils.prepareLinks(pageable.getPageNumber(), pageable.getPageSize(),
        		request, contentPage, pathParams.toString());
        
		PagedResources<NewsResponse> pagedResources = new PagedResources<NewsResponse>(
				content, new PageMetadata(contentPage.getSize(), contentPage.getNumber(),
						contentPage.getTotalElements(), contentPage.getTotalPages()),
						linkList);
        return new ResponseEntity<>(pagedResources, HttpStatus.OK);
	}

	private Page<News> findNewsByMultiConditions(String keyword,
			 Pageable pageable){
		
		Criteria<News> criteria = new Criteria<News>(); 
		
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
		
		Page<News> page = newsRepository
				.findAll(criteria, pageable);
		return page;
	}

	public ResponseEntity<?> deleteNewsByUuid(String uuid,
			HttpServletRequest request) {
		News newsEntiry = newsRepository.findOne(uuid);
		if(null == newsEntiry){
			return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
		}
		
		newsRepository.delete(newsEntiry);
		return new ResponseEntity<>(HttpStatus.OK);

	}

	public ResponseEntity<?> updateNewsByUuid(String uuid,
			AddNewsRequest addNewsRequest, HttpServletRequest request) {
		News newsEntiry = newsRepository.findOne(uuid);
		if(null == newsEntiry){
			return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
		}
		BeanUtils.copyPropertiesIgnoreNullValue(addNewsRequest, newsEntiry);
		Date currentTime = new Date();
		newsEntiry.setModifiedTime(currentTime);
		newsRepository.save(newsEntiry);
		
		return new ResponseEntity<>(HttpStatus.OK);
	}
}
