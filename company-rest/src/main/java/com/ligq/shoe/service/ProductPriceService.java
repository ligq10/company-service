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
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.hateoas.Link;
import org.springframework.hateoas.PagedResources;
import org.springframework.hateoas.PagedResources.PageMetadata;
import org.springframework.hateoas.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import com.ligq.shoe.constants.DeleteStatus;
import com.ligq.shoe.constants.ProductPriceStatus;
import com.ligq.shoe.controller.ProductPriceController;
import com.ligq.shoe.entity.Category;
import com.ligq.shoe.entity.ProductPrice;
import com.ligq.shoe.mysql.dynamic.Criteria;
import com.ligq.shoe.mysql.dynamic.Restrictions;
import com.ligq.shoe.repository.ProductPriceRepository;
import com.ligq.shoe.request.AddProductPriceRequest;
import com.ligq.shoe.request.UpdateProductPriceRequest;
import com.ligq.shoe.response.CategoryDetailResponse;
import com.ligq.shoe.response.ProductPriceDetailResponse;
import com.ligq.shoe.response.ProductPriceResponse;
import com.ligq.shoe.utils.BeanUtils;
import com.ligq.shoe.utils.DateUtils;
import com.ligq.shoe.utils.LinkUtils;



@Service
public class ProductPriceService {

	private final static Logger logger = LoggerFactory.getLogger(ProductPriceService.class);

	@Autowired
	private ProductPriceRepository productPriceRepository;
	@Autowired
	private CategoryService categoryService;
	
	public ResponseEntity<?> saveProductPrice(
			String uuid,
			AddProductPriceRequest addProductPriceRequest,
			HttpServletRequest request) throws Exception{
		
		Category  categoryEntiry = categoryService.findCategoryByUuid(
				addProductPriceRequest.getCategoryId());
		if(null == categoryEntiry){
			return new ResponseEntity<Object>(
					"category not found!",HttpStatus.BAD_REQUEST);
		}

        ProductPrice productPriceEntity = new ProductPrice();
        BeanUtils.copyPropertiesIgnoreNullValue(
        		addProductPriceRequest, productPriceEntity);
        Date currentTime = new Date(System.currentTimeMillis());
        productPriceEntity.setUuid(UUID.randomUUID().toString());
		productPriceEntity.setCompanyId(uuid);
		productPriceEntity.setCreatedTime(currentTime);	
		productPriceEntity.setModifiedTime(currentTime);
		productPriceEntity.setStatus(ProductPriceStatus.ONLINE.name());
		productPriceEntity.setStatusValue(ProductPriceStatus.ONLINE.getStatus());
		productPriceEntity.setIsdeleted(DeleteStatus.NO.name());
		productPriceRepository.save(productPriceEntity);
		
		HttpHeaders headers = new HttpHeaders(); 
		headers.setLocation(linkTo(methodOn(ProductPriceController.class)
				.findProductPriceByUuid(
						productPriceEntity.getUuid(),request)).toUri()); 
		
		return new ResponseEntity<HttpStatus>(headers,HttpStatus.CREATED);
	}



	@SuppressWarnings("rawtypes")
	public ResponseEntity<?> findAll(Pageable pageable,
			HttpServletRequest request) throws Exception{
		
	   Page<ProductPrice> productPricePage = productPriceRepository
			   .findByIsdeleted(DeleteStatus.NO.name(),pageable);
	   if(null == productPricePage || productPricePage.getContent().isEmpty()){
		   return new ResponseEntity<HttpStatus>(HttpStatus.NO_CONTENT);
	   }
	   List<Link> list = LinkUtils.prepareLinks(pageable.getPageNumber(),
				pageable.getPageSize(), request, productPricePage,"");
	   List<ProductPriceResponse> result = new ArrayList<ProductPriceResponse>();
       for (ProductPrice productPrice : productPricePage.getContent()) {
    	    ProductPriceResponse productPriceResponse = new ProductPriceResponse();
            BeanUtils.copyProperties(productPrice, productPriceResponse);
            productPriceResponse.add(linkTo(methodOn(ProductPriceController.class)
            		.findProductPriceByUuid(
            				productPrice.getUuid(),request)).withSelfRel());
            result.add(productPriceResponse);
        }
		PagedResources<ProductPriceResponse> pagedResources = new PagedResources<ProductPriceResponse>(
				result, new PageMetadata(productPricePage.getSize(), productPricePage.getNumber(),
						productPricePage.getTotalElements(), productPricePage.getTotalPages()),
				list);
		return new ResponseEntity<PagedResources>(pagedResources, HttpStatus.OK); 	  
	}

	public ResponseEntity<?> findProductPriceByUuid(
			String uuid, HttpServletRequest request) throws Exception{
		ProductPrice productPrice = productPriceRepository.findOne(uuid);
		if(null == productPrice){
			return new ResponseEntity<HttpStatus>(HttpStatus.NO_CONTENT);
		}
   	    ProductPriceDetailResponse productPriceResponse = new ProductPriceDetailResponse();    	 
		BeanUtils.copyProperties(productPrice, productPriceResponse);
		if(null != productPrice.getCreatedTime()){
			productPriceResponse.setCreatedTime(
					DateUtils.composeUTCTime(productPrice.getCreatedTime()));
			
		}
		if(null != productPrice.getModifiedTime()){
			productPriceResponse.setModifiedTime(
					DateUtils.composeUTCTime(productPrice.getModifiedTime()));
		}
        productPriceResponse.add(linkTo(methodOn(ProductPriceController.class)
        		.findProductPriceByUuid(productPrice.getUuid(),request)).withSelfRel());
        return new ResponseEntity<Resource>(
        		new Resource<ProductPriceDetailResponse>(
        				productPriceResponse), HttpStatus.OK);

	}

	public ResponseEntity<?> updateProductPriceByUuid(
			String uuid, 
			UpdateProductPriceRequest updateProductPriceRequest, 
			HttpServletRequest request) throws Exception{
		
		if(!StringUtils.isEmpty(updateProductPriceRequest.getCategoryId())){
			Category  categoryEntiry = categoryService.findCategoryByUuid(
					updateProductPriceRequest.getCategoryId());
			if(null == categoryEntiry){
				return new ResponseEntity<Object>(
						"category not found!",HttpStatus.BAD_REQUEST);
			}
		}
		
		ProductPrice productPrice = productPriceRepository.findOne(uuid);
		if(null == productPrice){
			return new ResponseEntity<String>("ProductPrice not found",HttpStatus.BAD_REQUEST);
		}
		
		BeanUtils.copyPropertiesIgnoreNullValue(updateProductPriceRequest, productPrice);
		Date currentTime = new Date(System.currentTimeMillis());
		productPrice.setModifiedTime(currentTime);
		if(StringUtils.isEmpty(updateProductPriceRequest.getStatus()) == false){
			ProductPriceStatus productPriceStatus = ProductPriceStatus
					.getProductPriceStatusByName(updateProductPriceRequest.getStatus());
			if(null == productPriceStatus){
				return new ResponseEntity<String>("status is invalid",HttpStatus.BAD_REQUEST);			
			}
			productPrice.setStatus(productPriceStatus.name());
			productPrice.setStatusValue(productPriceStatus.getStatus());
		}
		productPriceRepository.save(productPrice);
		return new ResponseEntity<HttpStatus>(HttpStatus.OK); 	  
	}

	public ResponseEntity<?> deleteProductPriceByUuid(String uuid, 
			HttpServletRequest request) throws Exception{
		ProductPrice productPrice = productPriceRepository.findOne(uuid);
		if(null == productPrice){
			return new ResponseEntity<String>(
					"ProductPrice not found",HttpStatus.BAD_REQUEST);
		}
		productPriceRepository.delete(productPrice);
		return new ResponseEntity<HttpStatus>(HttpStatus.OK);
	}

	public ResponseEntity<?> batchDeleteProductPriceByIds(
			List<String> productPriceIds,
			HttpServletRequest request) {
		if(null == productPriceIds){
			return new ResponseEntity<HttpStatus>(HttpStatus.BAD_REQUEST);
		}
		String companyId = "";
		for(String uuid : productPriceIds){
			ProductPrice productPrice = productPriceRepository.findOne(uuid);
			if(null != productPrice){
				companyId = productPrice.getCompanyId();
				productPriceRepository.delete(productPrice);
			}		
		}
		return new ResponseEntity<HttpStatus>(HttpStatus.OK);
	}

	public ResponseEntity<?> findProductPricesbycondition(String uuid, String productName,
			String status, Pageable pageable, HttpServletRequest request) {
		StringBuffer pathParams = new StringBuffer();
		pathParams.append(StringUtils.isEmpty(productName)?"&productName=":"&productName="+productName);
		pathParams.append(StringUtils.isEmpty(status)?"&status=":"&status="+status);
		Page<ProductPrice> productPricePage = this.findByProductPriceId(
				uuid, productName, status, pageable);
	    if(null == productPricePage || productPricePage.getContent().isEmpty()){
		   return new ResponseEntity<HttpStatus>(HttpStatus.NO_CONTENT);
	    }
	    List<Link> list = LinkUtils.prepareLinks(pageable.getPageNumber(),
				pageable.getPageSize(), request, productPricePage,pathParams.toString());
	    List<ProductPriceResponse> result = new ArrayList<ProductPriceResponse>();
        for (ProductPrice productPrice : productPricePage.getContent()) {
    	    ProductPriceResponse productPriceResponse = new ProductPriceResponse();
            BeanUtils.copyProperties(productPrice, productPriceResponse);
            productPriceResponse.add(linkTo(methodOn(ProductPriceController.class)
            		.findProductPriceByUuid(productPrice.getUuid(),request)).withSelfRel());
            result.add(productPriceResponse);
         }
		 PagedResources<ProductPriceResponse> pagedResources = new PagedResources<ProductPriceResponse>(
				result, new PageMetadata(productPricePage.getSize(), productPricePage.getNumber(),
						productPricePage.getTotalElements(), productPricePage.getTotalPages()),
				list);
		 return new ResponseEntity<PagedResources>(pagedResources, HttpStatus.OK); 	  
	}
	

	@SuppressWarnings("unused")
	private Page<ProductPrice> findByProductPriceId(String uuid,
			String productName, String status, Pageable pageable){
		
		Criteria<ProductPrice> criteria = new Criteria<ProductPrice>(); 
		criteria.add(Restrictions.eq("isdeleted", DeleteStatus.NO.name(), true));
		if(StringUtils.isEmpty(uuid) == false){
			criteria.add(Restrictions.eq("companyId", uuid, true));
		}

		if(StringUtils.isEmpty(status) == false){
			criteria.add(Restrictions.eq("status", status, true));
		}
		
		if(StringUtils.isEmpty(productName) == false){
			criteria.add(Restrictions.like("productName", productName, true));
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
		
		Page<ProductPrice> productPricePage = productPriceRepository
				.findAll(criteria, pageable);
		return productPricePage;
	}



	public ResponseEntity<?> findProductPricesbycondition(String uuid,
			String keyword, Pageable pageable, HttpServletRequest request) {
		StringBuffer pathParams = new StringBuffer();
		pathParams.append(StringUtils.isEmpty(keyword)?"&keyword=":"&keyword="+keyword);
		Page<ProductPrice> productPricePage = productPriceRepository.findProductPricesByKeyword(
				uuid, keyword, pageable);
	    if(null == productPricePage || productPricePage.getContent().isEmpty()){
		   return new ResponseEntity<HttpStatus>(HttpStatus.NO_CONTENT);
	    }
	    List<Link> list = LinkUtils.prepareLinks(pageable.getPageNumber(),
				pageable.getPageSize(), request, productPricePage,pathParams.toString());
	    List<ProductPriceResponse> result = new ArrayList<ProductPriceResponse>();
        for (ProductPrice productPrice : productPricePage.getContent()) {
    	    ProductPriceResponse productPriceResponse = new ProductPriceResponse();
            BeanUtils.copyProperties(productPrice, productPriceResponse);
            productPriceResponse.add(linkTo(methodOn(ProductPriceController.class)
            		.findProductPriceByUuid(productPrice.getUuid(),request)).withSelfRel());
            Category category = categoryService.findCategoryByUuid(productPrice.getCategoryId());
            if(null != category){
        		CategoryDetailResponse categoryResponse = new CategoryDetailResponse();
        		BeanUtils.copyProperties(category, categoryResponse);
        		categoryResponse.setCreatedTime(
        				DateUtils.composeUTCTime(category.getCreatedTime()));
        		productPriceResponse.setCateory(categoryResponse);
            }
            result.add(productPriceResponse);
         }
       // Page<ProductPriceResponse> content = new PageImpl(result,pageable,productPricePage.getTotalElements());
		 PagedResources<ProductPriceResponse> pagedResources = new PagedResources<ProductPriceResponse>(
				result, new PageMetadata(productPricePage.getSize(), productPricePage.getNumber(),
						productPricePage.getTotalElements(), productPricePage.getTotalPages()),
				list);
		 return new ResponseEntity<PagedResources<ProductPriceResponse>>(
				 pagedResources, HttpStatus.OK); 	 
	}
	
}
