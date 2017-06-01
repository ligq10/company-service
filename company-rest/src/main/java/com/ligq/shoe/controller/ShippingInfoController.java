package com.ligq.shoe.controller;


import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.ligq.shoe.constants.JhMediaType;
import com.ligq.shoe.request.ShippingInfoAddRequest;
import com.ligq.shoe.service.ShippingInfoService;


@Controller
public class ShippingInfoController {

    private static final Logger logger = LoggerFactory.getLogger(OrderController.class);
    @Autowired
    private ShippingInfoService shippingInfoService;
    
    @RequestMapping(value = "/orders/{uuid}/shippinginfos", method = RequestMethod.POST)
    public HttpEntity<?> addShippingInfo(
    		@PathVariable String uuid, 
    		@RequestBody ShippingInfoAddRequest shippingInfoAddRequest,
    		HttpServletRequest request, HttpServletResponse response) {

		ResponseEntity<?> responseEntity =  null;
        try {
        	responseEntity = shippingInfoService
        			.saveShippingInfo(uuid,shippingInfoAddRequest,request);
        } catch (Exception e) {
            logger.error(e.getMessage(),e);
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
        return responseEntity;
    	
    }

    @RequestMapping(value = "/orders/{orderuuid}/shippinginfos", method = RequestMethod.GET,
    		produces = JhMediaType.APPLICATION_DEFAULT_JSON_VALUE)
    @Transactional
    public HttpEntity<?> findAllShippingInfos(
    		@PathVariable String orderuuid,
			@PageableDefault(page = 0, size = 20, sort = "createdDate",
				direction = Direction.DESC) Pageable pageable,
    		HttpServletRequest request, HttpServletResponse response) {
    	
		ResponseEntity<?> responseEntity = null;
		try {
			responseEntity = shippingInfoService
					.findAllShippingInfosByOrderId(orderuuid, request);
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
			return new ResponseEntity<HttpStatus>(HttpStatus.NOT_FOUND);
		}
		return responseEntity;
    }

}
