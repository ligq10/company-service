package com.ligq.shoe.service;

import static org.springframework.hateoas.mvc.ControllerLinkBuilder.linkTo;
import static org.springframework.hateoas.mvc.ControllerLinkBuilder.methodOn;

import java.io.IOException;
import java.net.URI;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.UUID;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.servlet.http.HttpServletRequest;

import net.sf.json.JSONArray;
import net.sourceforge.pinyin4j.format.HanyuPinyinCaseType;
import net.sourceforge.pinyin4j.format.HanyuPinyinOutputFormat;
import net.sourceforge.pinyin4j.format.HanyuPinyinToneType;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
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
import org.springframework.web.client.RestTemplate;

import com.alibaba.fastjson.JSON;
import com.ligq.shoe.constants.CompanyServiceStatus;
import com.ligq.shoe.constants.DeleteStatus;
import com.ligq.shoe.constants.NumberToChinese;
import com.ligq.shoe.controller.CompanyController;
import com.ligq.shoe.entity.Company;
import com.ligq.shoe.entity.Employee;
import com.ligq.shoe.mysql.dynamic.Criteria;
import com.ligq.shoe.mysql.dynamic.Restrictions;
import com.ligq.shoe.repository.CompanyRepository;
import com.ligq.shoe.repository.EmployeeRepository;
import com.ligq.shoe.request.CompanyAddRequest;
import com.ligq.shoe.response.CompanyDetailResponse;
import com.ligq.shoe.response.CompanyResponse;
import com.ligq.shoe.response.HomePageResources;
import com.ligq.shoe.utils.BeanUtils;
import com.ligq.shoe.utils.DateUtils;
import com.ligq.shoe.utils.LinkUtils;
import com.ligq.shoe.utils.Pinyin4jUtil;

@Service
public class CompanyService {
	private final static Logger logger = LoggerFactory.getLogger(CompanyService.class); 
	private final String SECURITY_TOKEN_HEADER = "X-Token";	

	@Autowired
	private CompanyRepository companyRepository;
	@Autowired
	private Environment env;
	
	private RestTemplate restTemplate = new RestTemplate();
	@Autowired
	private EmployeeRepository employeeRepository;
	@Autowired
	private EmployeeService employeeService;


	
	public boolean checkCompanyById(String uuid){
		Company company=companyRepository.findOne(uuid);
		if(null == company){
			return false;	
		}
		return true;
	}
   
	public Company saveCompany(Company company){
		company = companyRepository.save(company);
		return company;
	}

	private String getSpellByChineseCharacter(String characters){
    	StringBuffer pinyinStr = new StringBuffer(); 
		if(StringUtils.isEmpty(characters)){
			return "";
		}			         
        char[] nameCharArr = characters.toCharArray();  
        HanyuPinyinOutputFormat defaultFormat = new HanyuPinyinOutputFormat();  
        defaultFormat.setCaseType(HanyuPinyinCaseType.UPPERCASE);  
        defaultFormat.setToneType(HanyuPinyinToneType.WITHOUT_TONE);
        for(char nameChar : nameCharArr){    	
            String Char = String.valueOf(nameChar);
        	if (isChineseChar(Char)) {              	
    			String pinyin = Pinyin4jUtil.getPinyinAndUpperCase(Char);
    			pinyinStr.append(pinyin);
            }else if(isNumber(Char)){
            	String chineseNumber = NumberToChinese.getNumberToChineseByValue(Integer.valueOf(Char)).getChinese();
    			String pinyin = Pinyin4jUtil.getPinyinAndUpperCase(chineseNumber);
    			pinyinStr.append(pinyin);

            }else{
    			pinyinStr.append(Char);
            }  
        }
	    return pinyinStr.toString();
	             		        
	}
	
	public boolean isNumber(String str){
	    Pattern pattern = Pattern.compile("[0-9]*"); 
	    return pattern.matcher(str).matches();  
	}
	
    public boolean isChineseChar(String str){
        Pattern p=Pattern.compile("[\u4e00-\u9fa5]"); 
        Matcher m=p.matcher(str); 
        if(m.find()){ 
        	return  true;
        }
        return false;
    }

	public ResponseEntity<?> saveCompany(
			CompanyAddRequest companyRequest,
			HttpServletRequest request) {
		
		String xToken = request.getHeader("X-Token");

		Date createdTime = new Date(System.currentTimeMillis()); 
		Company company= new Company();
		BeanUtils.copyPropertiesIgnoreNullValue(companyRequest, company,"location");
		if(null != companyRequest.getLocation()){
			company.setLon(companyRequest.getLocation().getLon());
			company.setLat(companyRequest.getLocation().getLat());
		}
		company.setUuid(UUID.randomUUID().toString());	
		company.setIsdeleted(DeleteStatus.NO.getValue());
		company.setCreatedTimestamp(createdTime);	
		company.setModifiedTimestamp(createdTime);	
		company.setServiceStatus(CompanyServiceStatus.SERVICING.name());
		if(null != companyRequest.getBusinessHours()){
			String businessHourStr = JSONArray.fromObject(companyRequest.getBusinessHours()).toString();
			company.setBusinessHours(businessHourStr);
		}
		
		try {						        
			this.saveCompany(company);
		    //Employee employee = employeeService.registerCompanyBossToEmployee(company,xToken);
			
		} catch (Exception e) {			
			logger.error(e.getMessage());
			return new ResponseEntity<HttpStatus>(
					HttpStatus.INTERNAL_SERVER_ERROR);			
		}
		
   
		HttpHeaders headers = new HttpHeaders(); 
		URI uri = linkTo(methodOn(CompanyController.class)
				.getCompanyByID(company.getUuid(), null)).toUri();
		headers.setLocation(uri);
        return new ResponseEntity<Object>(headers,HttpStatus.CREATED);

	}

	public ResponseEntity<?> findCompanyById(String uuid,
			HttpServletRequest request) {
		CompanyDetailResponse companyDetailResponse = new CompanyDetailResponse();
		
		Company company=companyRepository.findOne(uuid);	
		if(null == company){
		    return  new ResponseEntity<>(HttpStatus.NO_CONTENT);
		}
		
		BeanUtils.copyPropertiesIgnoreNullValue(company, companyDetailResponse);
		if(null != company.getCreatedTimestamp()){
			companyDetailResponse.setCreatedTimestamp(DateUtils.composeUTCTime(company.getCreatedTimestamp()));
		}
		
		if(null != company.getModifiedTimestamp()){
			companyDetailResponse.setModifiedTimestamp(DateUtils.composeUTCTime(company.getModifiedTimestamp()));
		}
		if(null != company.getPauseServiceTime()){
			companyDetailResponse.setPauseServiceTime(DateUtils.composeUTCTime(company.getPauseServiceTime()));
		}
		if(StringUtils.isEmpty(company.getBusinessHours()) == false){
			JSONArray jsonArray = JSONArray.fromObject(company.getBusinessHours());
	        List<String> businessHours = JSON.parseArray(jsonArray.toString(), String.class);	
	        companyDetailResponse.setBusinessHours(businessHours);
		}
		Link link = linkTo(methodOn(CompanyController.class)
				.getCompanyByID(uuid, null)).withSelfRel();		
		companyDetailResponse.add(link);
		
	    return  new ResponseEntity<Object>(
	    		new Resource<CompanyDetailResponse>(
	    				companyDetailResponse), HttpStatus.OK);
	}

	public ResponseEntity<?> updateCompany(String uuid,
			CompanyAddRequest companyRequest, HttpServletRequest request) {
		
		String xToken = request.getHeader("X-Token");
		
		Date currentTime = new Date(System.currentTimeMillis());
		Company company=companyRepository.findOne(uuid);	
		
		if(null == company){			
			logger.error("company not found.");
	        return new ResponseEntity<String>(
	        		"company not found!",HttpStatus.BAD_REQUEST);
		}
		
		if(StringUtils.isEmpty(companyRequest.getServiceStatus()) == false){
			CompanyServiceStatus serviceStatus = CompanyServiceStatus
					.getCompanyServiceStatusByName(companyRequest.getServiceStatus());
			if(StringUtils.isEmpty(serviceStatus)){
		        return new ResponseEntity<String>(
		        		"service status Invalid!",HttpStatus.BAD_REQUEST);
			}
			
			if(CompanyServiceStatus.PAUSE_SREVICE.name()
					.equalsIgnoreCase(companyRequest.getServiceStatus())){
				company.setPauseServiceTime(currentTime);
			}
		}
		
		BeanUtils.copyPropertiesIgnoreNullValue(companyRequest, company,"location");
		company.setModifiedTimestamp(currentTime);
		
		if(null != companyRequest.getLocation()){
			company.setLon(companyRequest.getLocation().getLon());
			company.setLat(companyRequest.getLocation().getLat());
		}
		if(null != companyRequest.getBusinessHours()){
			String businessHourStr = JSONArray.fromObject(companyRequest.getBusinessHours()).toString();
			company.setBusinessHours(businessHourStr);
		}
		try {
			this.saveCompany(company);					
		} catch (Exception e) {
	        logger.error(e.getMessage(),e);
	        return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
		}
        return new ResponseEntity<HttpStatus>(HttpStatus.OK);
	}

	public ResponseEntity<?> deleteCompany(String uuid) {
		Company company=companyRepository.findOne(uuid);	
		if(null == company){
			logger.error("company not found");
		    return  new ResponseEntity<>(HttpStatus.BAD_REQUEST);
		}
		company.setServiceStatus(CompanyServiceStatus.STOP_SERVICE.name());
		company.setIsdeleted(DeleteStatus.YES.getValue());
		companyRepository.save(company);
		Pageable pageable = new PageRequest(0, 10);
        return new ResponseEntity<>(HttpStatus.OK);
	}

	public ResponseEntity<?> findCompanies(String categoryId,
			String subCategoryId, String name, String serviceStatus,
			String groupId, Pageable pageable, HttpServletRequest request) {
		String xToken = request.getHeader("X-Token");

		StringBuffer pathParams = new StringBuffer();
		pathParams.append(StringUtils.isEmpty(categoryId)?"&categoryId=":"&categoryId="+categoryId);
		pathParams.append(StringUtils.isEmpty(subCategoryId)?"&subCategoryId=":"&subCategoryId="+subCategoryId);
		pathParams.append(StringUtils.isEmpty(name)?"&name=":"&name="+name);
		pathParams.append(StringUtils.isEmpty(serviceStatus)?"&serviceStatus=":"&serviceStatus="+serviceStatus);
		pathParams.append(StringUtils.isEmpty(groupId)?"&groupId=":"&groupId="+groupId);

		Page<Company> companyPage = this.findcompanies(categoryId, subCategoryId, 
				name, serviceStatus, groupId,DeleteStatus.NO.getValue(), pageable, xToken);
		if(null == companyPage || companyPage.getContent().isEmpty()){
	        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
		}
		
		List<Link> list = LinkUtils.prepareLinks(pageable.getPageNumber(),
				pageable.getPageSize(), request, companyPage,pathParams.toString());
		
		List<CompanyResponse> content = new ArrayList<CompanyResponse>();
		
		for (Company company : companyPage.getContent()) {
            CompanyResponse companyResponse = new CompanyResponse();
            BeanUtils.copyPropertiesIgnoreNullValue(company, companyResponse);
 
    		if(null != company.getPauseServiceTime()){
    			companyResponse.setPauseServiceTime(
    					DateUtils.composeUTCTime(company.getPauseServiceTime()));
    		}
            Link link = linkTo(methodOn(CompanyController.class)
            		.getCompanyByID(
            				company.getUuid(), request)).withSelfRel();
            companyResponse.add(link);
            content.add(companyResponse);
		}			
				
		PagedResources<CompanyResponse> pagedResources = new PagedResources<CompanyResponse>(
				content, new PageMetadata(companyPage.getSize(), companyPage.getNumber(),
						companyPage.getTotalElements(), companyPage.getTotalPages()),
				list);
		return new ResponseEntity<PagedResources<CompanyResponse>>(pagedResources, HttpStatus.OK); 
		
	}

	private Page<Company> findcompanies(String categoryId,
			String subCategoryId, String name,
			String serviceStatus,String groupId,String isdeleted,
			Pageable pageable,String xToken){
		Criteria<Company> criteria = new Criteria<Company>(); 
		
		if(StringUtils.isEmpty(isdeleted) == false){
			criteria.add(Restrictions.eq("isdeleted", isdeleted, true));
		}
		
		if(StringUtils.isEmpty(categoryId) == false){
			criteria.add(Restrictions.eq("categoryId", categoryId, true));
		}

		if(StringUtils.isEmpty(subCategoryId) == false){
			criteria.add(Restrictions.eq("subCategoryId", subCategoryId, true));
		}
		
		if(StringUtils.isEmpty(serviceStatus) == false){
			criteria.add(Restrictions.eq("serviceStatus", serviceStatus, true));
		}
		
		if(StringUtils.isEmpty(name) == false){
			criteria.add(Restrictions.like("name", name, true));
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
		
		Page<Company> companyPage = companyRepository.findAll(criteria, pageable);
		return companyPage;
	}

	public ResponseEntity<?> getHomePageResourcesByUuid(String uuid,
			HttpServletRequest request) {
		
		Company company=companyRepository.findOne(uuid);	
        if (null == company){
            return new ResponseEntity<HttpStatus>(HttpStatus.NO_CONTENT);
        }
		HomePageResources homePageResources = new HomePageResources();
		homePageResources.setUuid(company.getUuid());
		homePageResources.setShortName(company.getShortName());
		homePageResources.setPageUrl(company.getPageurl());
        
		Link link = linkTo(methodOn(CompanyController.class)
				.getCompanyByID(
						homePageResources.getUuid(), null)).withSelfRel();
		homePageResources.add(link);
		
		return new ResponseEntity<Resource>(
				new Resource<HomePageResources>(
						homePageResources), HttpStatus.OK);
	}

	public ResponseEntity<?> deleteCompaniesByIds(List<String> companyIds,
			HttpServletRequest request) {
		if(null == companyIds || companyIds.isEmpty()){
            return new ResponseEntity<HttpStatus>(HttpStatus.BAD_REQUEST);
		}
		for(String uuid : companyIds){
			Company company=companyRepository.findOne(uuid);
			if(null != company){
				company.setServiceStatus(CompanyServiceStatus.STOP_SERVICE.name());
				company.setIsdeleted(DeleteStatus.YES.getValue());
				companyRepository.save(company);

			}
	

		}
		
        return new ResponseEntity<>(HttpStatus.OK);
	}

	public ResponseEntity<?> batchUpdateCompanyServiceStatus(List<String> companyIds) {
		if(null == companyIds || companyIds.isEmpty()){
            return new ResponseEntity<HttpStatus>(HttpStatus.BAD_REQUEST);
		}
		for(String uuid : companyIds){
			Company company=companyRepository.findOne(uuid);
			if(null != company){
				company.setServiceStatus(CompanyServiceStatus.SERVICING.name());
				companyRepository.save(company);

			}
		}
		
        return new ResponseEntity<>(HttpStatus.OK);
    }

	public Company findOne(String uuid) {
		return companyRepository.findOne(uuid);
	}

}
