package com.ligq.shoe.service;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.UUID;

import javax.servlet.http.HttpServletRequest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.Link;
import org.springframework.hateoas.PagedResources;
import org.springframework.hateoas.PagedResources.PageMetadata;
import org.springframework.hateoas.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import com.ligq.shoe.constants.OrderStatus;
import com.ligq.shoe.entity.Orders;
import com.ligq.shoe.entity.ShippingInfo;
import com.ligq.shoe.repository.ShippingInfoRepository;
import com.ligq.shoe.request.ShippingInfoAddRequest;
import com.ligq.shoe.response.ShippingInfoResponse;
import com.ligq.shoe.utils.BeanUtils;
import com.ligq.shoe.utils.DateUtils;
import com.ligq.shoe.utils.LinkUtils;


@Service
public class ShippingInfoService {

	private static final Logger logger = LoggerFactory.getLogger(ShippingInfoService.class);
    private static final String SECURITY_TOKEN_HEADER = "X-Token";
    
    @Autowired
    private OrderService orderService;

    @Autowired
    private ShippingInfoRepository shippingInfoRepository;    
    
	public ResponseEntity<?> saveShippingInfo(String uuid,
			ShippingInfoAddRequest shippingInfoAddRequest,
			HttpServletRequest request) {
		
    	Date currentDate = new Date(System.currentTimeMillis());
        if (null == shippingInfoAddRequest.getStatus()) {
            return new ResponseEntity<HttpStatus>(HttpStatus.BAD_REQUEST);
        }
        Orders order = orderService.findOneOrder(uuid);
        if (null == order) {
            logger.error("order not found");
            return new ResponseEntity<HttpStatus>(HttpStatus.BAD_REQUEST);
        }

        ShippingInfo shippingInfoEntity = new ShippingInfo();
        BeanUtils.copyPropertiesIgnoreNullValue(shippingInfoAddRequest, shippingInfoEntity);
        shippingInfoEntity.setUuid(UUID.randomUUID().toString());
        shippingInfoEntity.setOrderId(uuid);
        shippingInfoEntity.setCreatedDate(currentDate.getTime());
        if(StringUtils.isEmpty(shippingInfoAddRequest.getServiceEmployeeId()) == false){
        	shippingInfoEntity.setServiceEmployeeId(
        			shippingInfoAddRequest.getServiceEmployeeId());
        }
        this.save(shippingInfoEntity);
               
        orderService.update(order,shippingInfoEntity);

        HttpHeaders headers = new HttpHeaders();
        return new ResponseEntity<HttpStatus>(headers, HttpStatus.CREATED);
	}
    
    public ShippingInfo save(ShippingInfo shippingInfoEntity) {
        ShippingInfo shippingInfo = shippingInfoRepository.save(shippingInfoEntity);
        return shippingInfo;
    }

    public void delete(ShippingInfo shippingInfoEntity) {
        shippingInfoRepository.delete(shippingInfoEntity);
    }

	public ResponseEntity<?> findShippingInfoByUuid(
			String uuid, HttpServletRequest request) {
        ShippingInfo shippingInfo = shippingInfoRepository.findOne(uuid);

        if (null == shippingInfo) {
            logger.error("shippingInfo not found");
            return new ResponseEntity<HttpStatus>(HttpStatus.NO_CONTENT);
        }

        ShippingInfoResponse shippingInfoResponse = new ShippingInfoResponse();

        BeanUtils.copyPropertiesIgnoreNullValue(shippingInfo, shippingInfoResponse);
        shippingInfoResponse.setStatusDesc(OrderStatus.getStatusName(shippingInfo.getStatus()));
        shippingInfoResponse.setOrderId(""+shippingInfo.getOrderId());
        shippingInfoResponse.setUuid(""+shippingInfo.getUuid());
        shippingInfoResponse.setServiceEmployeeId(""+shippingInfo.getServiceEmployeeId());
        String createdTime = null == shippingInfo.getCreatedDate()?
        		"":DateUtils.composeUTCTime(shippingInfo.getCreatedDate());
        shippingInfoResponse.setCreatedDate(createdTime);

        return new ResponseEntity<Resource<?>>(
        		new Resource<ShippingInfoResponse>(shippingInfoResponse), HttpStatus.OK);

	}
	
	public ResponseEntity<?> findAllShippingInfosByOrderId(
			String orderuuid, HttpServletRequest request) {
		
	
		List<ShippingInfo> shippingInfoList = this.findByOrderIdOrderByCreatedDateDesc(orderuuid);
		

		List<Link> list = LinkUtils.prepareLinks(0, shippingInfoList.size(),
				request, false, "");
		List<ShippingInfoResponse> content = new ArrayList<ShippingInfoResponse>();
		if (null != shippingInfoList && shippingInfoList.isEmpty() == false) {
            for (ShippingInfo shippingInfo : shippingInfoList) {
                ShippingInfoResponse shippingInfoResponse = new ShippingInfoResponse();
                BeanUtils.copyPropertiesIgnoreNullValue(shippingInfo, shippingInfoResponse);
                shippingInfoResponse.setStatusDesc(OrderStatus.getStatusName(shippingInfo.getStatus()));
                shippingInfoResponse.setOrderId(""+shippingInfo.getOrderId());
                shippingInfoResponse.setUuid(""+shippingInfo.getUuid());
                shippingInfoResponse.setServiceEmployeeId(""+shippingInfo.getServiceEmployeeId());                
                String createdTime = null == shippingInfo.getCreatedDate()?
                		"":DateUtils.composeUTCTime(shippingInfo.getCreatedDate());
                shippingInfoResponse.setCreatedDate(createdTime);
                content.add(shippingInfoResponse);
            }
		}

		PagedResources<ShippingInfoResponse> pagedResources = new PagedResources<ShippingInfoResponse>(
				content, new PageMetadata(shippingInfoList.size(), 0,
						shippingInfoList.size(), 1), list);
		return new ResponseEntity<PagedResources<ShippingInfoResponse>>(pagedResources, HttpStatus.OK);
	}

	public ShippingInfo findLatestShippingInfoByOrderId(String orderuuid) {
		ShippingInfo shippingInfo = shippingInfoRepository
				.findFirstByOrderIdOrderByCreatedDateDesc(orderuuid);

		return shippingInfo;

	}



	public List<ShippingInfo> findByOrderIdOrderByCreatedDateDesc(
			String orderId) {
		
		return shippingInfoRepository.findByOrderIdOrderByCreatedDateDesc(orderId);
	}

}
