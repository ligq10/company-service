package com.ligq.shoe.controller;


import static org.springframework.hateoas.mvc.ControllerLinkBuilder.linkTo;
import io.swagger.annotations.ApiOperation;

import javax.servlet.http.HttpServletRequest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import com.ligq.shoe.constants.JhMediaType;
import com.ligq.shoe.constants.LogicStatus;
import com.ligq.shoe.entity.Orders;
import com.ligq.shoe.request.OrderRequest;
import com.ligq.shoe.request.UpdateOrderRequest;
import com.ligq.shoe.service.OrderService;


@Controller
public class OrderController {
    Logger logger = LoggerFactory.getLogger(OrderController.class);


    @Autowired
    private Environment environment;
    @Autowired
    private OrderService orderService;
    

    @RequestMapping(value = "/orders", method = RequestMethod.POST,
    		produces = JhMediaType.APPLICATION_DEFAULT_JSON_VALUE)
    public HttpEntity<?> addOrder(
    		@RequestBody OrderRequest orderRequest,
    		HttpServletRequest request) {

    	if(StringUtils.isEmpty(orderRequest.getExpedite())){
    		orderRequest.setExpedite(LogicStatus.NO.getValue());
    	}else{
    		LogicStatus logicStatus = LogicStatus
    				.getLogicStatusByValue(orderRequest.getExpedite());
    		if(null == logicStatus){
                return new ResponseEntity<Object>("Expedite is invalid",
                		HttpStatus.BAD_REQUEST);
    		}
    	}

    	if(StringUtils.isEmpty(orderRequest.getPayStatus())){
    		orderRequest.setPayStatus(LogicStatus.NO.getValue());
    	}else{
    		LogicStatus logicStatus = LogicStatus
    				.getLogicStatusByValue(orderRequest.getPayStatus());
    		if(null == logicStatus){
                return new ResponseEntity<Object>("Expedite is invalid",
                		HttpStatus.BAD_REQUEST);
    		}
    	}
    	
        try {
            Orders orders = orderService.saveOrder(
            		 orderRequest, request);
            HttpHeaders headers = new HttpHeaders();
            headers.setLocation(linkTo(this.getClass())
            		.slash("orders").slash(orders.getUuid()).toUri());

            return new ResponseEntity<HttpStatus>(
            		headers, HttpStatus.CREATED);
        } catch (Exception e) {
            logger.error(e.getMessage(), e);
            return new ResponseEntity<HttpStatus>(
            		HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
    
    @RequestMapping(value = "/orders/{uuid}", method = RequestMethod.GET,
    		produces = JhMediaType.APPLICATION_DEFAULT_JSON_VALUE)
    public HttpEntity<?> findOrderById(
    		@PathVariable String uuid,
    		HttpServletRequest request) {
		ResponseEntity<?> responseEntity =  null;
        try {
        	responseEntity = orderService.findOrderByUuid(uuid,request);
        } catch (Exception e) {
            logger.error(e.getMessage(),e);
            return new ResponseEntity<HttpStatus>(
            		HttpStatus.INTERNAL_SERVER_ERROR);
        }
        return responseEntity;
    }

    @RequestMapping(value = "/orders/{uuid}", method = RequestMethod.POST,
    		produces = JhMediaType.APPLICATION_DEFAULT_JSON_VALUE)
    public HttpEntity<?> findOrderById(
    		@PathVariable String uuid,
    		@RequestBody UpdateOrderRequest updateOrderRequest,
    		HttpServletRequest request) {
    	
    	if(!StringUtils.isEmpty(updateOrderRequest.getPayStatus())){
    		LogicStatus logicStatus = LogicStatus
    				.getLogicStatusByValue(updateOrderRequest.getPayStatus());
    		if(null == logicStatus){
                return new ResponseEntity<Object>("Expedite is invalid",
                		HttpStatus.BAD_REQUEST);
    		}
    	}
   	
		ResponseEntity<?> responseEntity =  null;
        try {
        	responseEntity = orderService.updateOrderByUuid(
        			uuid,updateOrderRequest,request);
        } catch (Exception e) {
            logger.error(e.getMessage(),e);
            return new ResponseEntity<HttpStatus>(
            		HttpStatus.INTERNAL_SERVER_ERROR);
        }
        return responseEntity;
    }
    
    @ApiOperation(value="获取订单列表", notes="")
    @RequestMapping(value = "/orders", method = RequestMethod.GET,
    		produces = JhMediaType.APPLICATION_DEFAULT_JSON_VALUE)
    public HttpEntity<?> searchOrders(
    		@RequestParam(value = "keyword", required = false) String keyword,
    		@RequestParam(value = "currentStatus", required = false) String currentStatus,
    		@PageableDefault(page = 0, size = 20, sort = "createdDate",
				direction = Sort.Direction.DESC) Pageable pageable, 
            HttpServletRequest request) {
    	
		ResponseEntity<?> responseEntity =  null;
        try {
        	responseEntity = orderService
        			.findOrdersByMultiConditions(
        					keyword,currentStatus,pageable,request);
        } catch (Exception e) {
            logger.error(e.getMessage(),e);
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
        return responseEntity;
    	
    }  
}
