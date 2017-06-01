package com.ligq.shoe.service;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.UUID;

import javax.servlet.http.HttpServletRequest;
import javax.swing.SortOrder;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.data.domain.Sort.Order;
import org.springframework.hateoas.Link;
import org.springframework.hateoas.PagedResources;
import org.springframework.hateoas.PagedResources.PageMetadata;
import org.springframework.hateoas.Resource;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;
import org.springframework.util.StringUtils;
import org.springframework.web.client.RestTemplate;

import com.ligq.shoe.constants.DeleteStatus;
import com.ligq.shoe.constants.OrderStatus;
import com.ligq.shoe.entity.OrderToProductItem;
import com.ligq.shoe.entity.Orders;
import com.ligq.shoe.entity.ProductPrice;
import com.ligq.shoe.entity.ShippingInfo;
import com.ligq.shoe.mysql.dynamic.Criteria;
import com.ligq.shoe.mysql.dynamic.Restrictions;
import com.ligq.shoe.repository.OrderRepository;
import com.ligq.shoe.repository.OrderToProductItemRepository;
import com.ligq.shoe.repository.ShippingInfoRepository;
import com.ligq.shoe.request.OrderItemRequest;
import com.ligq.shoe.request.OrderRequest;
import com.ligq.shoe.request.UpdateOrderRequest;
import com.ligq.shoe.response.OrderDetailResponse;
import com.ligq.shoe.response.ShippingInfoResponse;
import com.ligq.shoe.utils.BeanUtils;
import com.ligq.shoe.utils.DateUtils;
import com.ligq.shoe.utils.GenerateUUIDUtils;
import com.ligq.shoe.utils.LinkUtils;

@Service
@SuppressWarnings("all")
public class OrderService {

    private final Logger logger = LoggerFactory.getLogger(getClass());
    private final String SECURITY_TOKEN_HEADER = "X-Token";
    @Autowired
    private RestTemplate restTemplate;
    @Autowired
    private ProductPriceService productPriceService;
    @Autowired
    private OrderRepository ordersRepository;
    @Autowired
    private ShippingInfoService shippingInfoService;
    @Autowired
    private Environment environment;
    @Autowired
    private OrderToProductItemRepository orderToProductItemRepository;
    @Autowired
    private OrderNoService orderNoService;


    public Orders findOneOrder(String uuid) {
        Orders order = ordersRepository.findOne(uuid);
        return order;
    }

    public Orders saveOrder(
    		OrderRequest orderRequest, HttpServletRequest request) {
    	String companyId = "e55cab55-30b6-4061-845a-2203be945ce4";
    	String uuid = UUID.randomUUID().toString();
        Date currentTime = new Date(System.currentTimeMillis());
        Orders orders = new Orders();
        BeanUtils.copyPropertiesIgnoreNullValue(orderRequest, orders);
        Date serviceTime = StringUtils.isEmpty(orderRequest.getServiceTime())?
        		new Date():DateUtils.composeUTCTime(orderRequest.getServiceTime());
        orders.setServiceTime(serviceTime);
        
        String orderNo = orderNoService.getOrderNo();
        orders.setUuid(uuid);
        orders.setCompanyId(companyId);
        orders.setOrderNo(orderNo);
        orders.setCreatedDate(currentTime);
        orders.setCurrentStatus(OrderStatus.WAITING.getStatus());
        

        List<OrderItemRequest> itemList = orderRequest.getItems();
        if (!CollectionUtils.isEmpty(itemList)) {
            this.saveOrderItem(itemList, orders);
        }
        ordersRepository.save(orders);
        // 更新订单状态流程
        ShippingInfo shippingInfoEntity = new ShippingInfo();
        shippingInfoEntity.setUuid(UUID.randomUUID().toString());
        shippingInfoEntity.setOrderId(orders.getUuid());
        shippingInfoEntity.setCreatedDate(currentTime.getTime());
        shippingInfoEntity.setStatus(orders.getCurrentStatus());
        shippingInfoEntity.setComment(orderRequest.getComment());
        shippingInfoService.save(shippingInfoEntity);        
        return orders;
    }

    private void saveOrderItem(List<OrderItemRequest> itemList, Orders orders) {
        StringBuffer serviceContent = new StringBuffer();
        double orderAmount = 0.0;
        
        for (OrderItemRequest orderItemRequest : itemList) {
            String productId = orderItemRequest.getUuid();
            Double amount = orderItemRequest.getAmount();
            if (StringUtils.isEmpty(productId) == false
            		&& null != amount) {
            	OrderToProductItem orderToProductItem = new OrderToProductItem();
            	String uuid = GenerateUUIDUtils.getOrderProductItemUUIDByOrderIdAndProductId(
            			orders.getUuid(),productId).toString();
            	orderToProductItem.setUuid(uuid);
            	orderToProductItem.setOrderId(orders.getUuid());
            	orderToProductItem.setProductId(productId);           
            	orderToProductItem.setAmount(amount);   
            	
                ProductPrice productPrice = productPriceService.findOne(productId);
                if (null == productPrice) {
                    logger.error("productPrice：【" + productId + "】not exist！");
                    continue;
                }
                
                Double price = null == productPrice.getPrice()?0d:productPrice.getPrice();
                Double subtotal = amount * price;
                orderToProductItem.setPrice(price);
                orderToProductItem.setName(productPrice.getProductName());
                orderToProductItem.setSubtotal(subtotal);
                orderToProductItem.setUnit(productPrice.getUnit());
                orderToProductItemRepository.save(orderToProductItem);

                serviceContent.append(orderToProductItem.getName()).append(" ").append(orderToProductItem.getAmount()).append(" ")
                        .append(orderToProductItem.getUnit()).append(",￥").append(formatDecimal(orderToProductItem.getPrice()))
                        .append("元/").append(orderToProductItem.getUnit()).append(",").append("共￥")
                        .append(formatDecimal((orderToProductItem.getAmount() * orderToProductItem.getPrice()))).append("元;");
                orderAmount += orderToProductItem.getAmount() * orderToProductItem.getPrice();
                
            }
        }
        orders.setServiceContent(serviceContent.toString());
        orders.setOrderAmount(formatDecimalDouble(orderAmount));
    }

    private String formatDecimal(double value) {
        DecimalFormat df = new DecimalFormat("#.00");
        return df.format(value);
    }

    private Double formatDecimalDouble(double value) {
        DecimalFormat df = new DecimalFormat("#.00");
        Double formatValue = Double.valueOf(df.format(value));
        return formatValue;
    }
    

	public ResponseEntity<?> findOrderByUuid(String uuid,
			HttpServletRequest request) {
        Orders order = ordersRepository.findOne(uuid);
        if(null == order){
            return new ResponseEntity<HttpStatus>(HttpStatus.NO_CONTENT);        	
        }
        OrderDetailResponse orderDetailResponse = new OrderDetailResponse();

        BeanUtils.copyPropertiesIgnoreNullValue(order, orderDetailResponse);
        String createdTime = null==order.getCreatedDate()?"":DateUtils.composeUTCTime(order.getCreatedDate());
        orderDetailResponse.setCreatedDate(createdTime);
        String serviceTime = null == order.getServiceTime()?"":DateUtils.composeUTCTime(order.getServiceTime());
        orderDetailResponse.setServiceTime(serviceTime);
        int status = 1;
        if (StringUtils.isEmpty(order.getCurrentStatus()) == false) {
            status = order.getCurrentStatus();
        }
        orderDetailResponse.setCurrentStatusDesc(OrderStatus.getStatusName(status));
        List<ShippingInfo> shippingInfoList = shippingInfoService
        				.findByOrderIdOrderByCreatedDateDesc(order.getUuid());
		List<ShippingInfoResponse> shippingInfos = new ArrayList<ShippingInfoResponse>();
		if (!CollectionUtils.isEmpty(shippingInfoList)) {
            for (ShippingInfo shippingInfo : shippingInfoList) {
                ShippingInfoResponse shippingInfoResponse = new ShippingInfoResponse();
                BeanUtils.copyPropertiesIgnoreNullValue(shippingInfo, shippingInfoResponse);
                shippingInfoResponse.setStatusDesc(OrderStatus.getStatusName(shippingInfo.getStatus()));
                shippingInfoResponse.setOrderId(""+shippingInfo.getOrderId());
                shippingInfoResponse.setUuid(""+shippingInfo.getUuid());
                shippingInfoResponse.setServiceEmployeeId(""+shippingInfo.getServiceEmployeeId());                
                String shippingCreatedTime = null == shippingInfo.getCreatedDate()?
                		"":DateUtils.composeUTCTime(shippingInfo.getCreatedDate());
                shippingInfoResponse.setCreatedDate(shippingCreatedTime);
                shippingInfos.add(shippingInfoResponse);
            }
            orderDetailResponse.setShippingInfos(shippingInfos);
		}
		
		List<OrderToProductItem> orderItems = orderToProductItemRepository.findByOrderId(order.getUuid());
		orderDetailResponse.setOrderItems(orderItems);
	    return  new ResponseEntity<Object>(new Resource<OrderDetailResponse>(orderDetailResponse),
	    		HttpStatus.OK);

	}

	public ResponseEntity<?> findOrdersByMultiConditions(String keyword,
			String currentStatus, Pageable pageable,
			HttpServletRequest request) {
		
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
		pathParams.append(StringUtils.isEmpty(currentStatus)?"":"&currentStatus="+currentStatus);
		pathParams.append(sort);
		List<Integer> currentStatusList = new ArrayList<Integer>();
		if(StringUtils.isEmpty(currentStatus)){
			String[] currentStatusArr = currentStatus.split(",");
			if(null != currentStatusArr && currentStatusArr.length > 0){
				for(String status : currentStatusArr){
					Integer statusTemp = Integer.valueOf(status);
					currentStatusList.add(statusTemp);
				}
			}
		}
		
		Page<Orders> orderPage = this.findOrdersByMultiConditions(keyword,currentStatusList,pageable);
		if(null == orderPage
				|| orderPage.getContent().isEmpty()){
        	return new ResponseEntity<>(HttpStatus.NO_CONTENT);
		}

		List<OrderDetailResponse> content = new ArrayList<OrderDetailResponse>();
		for(Orders order : orderPage.getContent()){
	        OrderDetailResponse orderDetailResponse = new OrderDetailResponse();

	        BeanUtils.copyPropertiesIgnoreNullValue(order, orderDetailResponse);
	        String createdTime = null==order.getCreatedDate()?"":DateUtils.composeUTCTime(order.getCreatedDate());
	        orderDetailResponse.setCreatedDate(createdTime);
	        String serviceTime = null == order.getServiceTime()?"":DateUtils.composeUTCTime(order.getServiceTime());
	        orderDetailResponse.setServiceTime(serviceTime);
	        int status = 1;
	        if (StringUtils.isEmpty(order.getCurrentStatus()) == false) {
	            status = order.getCurrentStatus();
	        }
	        orderDetailResponse.setCurrentStatusDesc(OrderStatus.getStatusName(status));
	        List<ShippingInfo> shippingInfoList = shippingInfoService
	        				.findByOrderIdOrderByCreatedDateDesc(order.getUuid());
			List<ShippingInfoResponse> shippingInfos = new ArrayList<ShippingInfoResponse>();
			if (!CollectionUtils.isEmpty(shippingInfoList)) {
	            for (ShippingInfo shippingInfo : shippingInfoList) {
	                ShippingInfoResponse shippingInfoResponse = new ShippingInfoResponse();
	                BeanUtils.copyPropertiesIgnoreNullValue(shippingInfo, shippingInfoResponse);
	                shippingInfoResponse.setStatusDesc(OrderStatus.getStatusName(shippingInfo.getStatus()));
	                shippingInfoResponse.setOrderId(""+shippingInfo.getOrderId());
	                shippingInfoResponse.setUuid(""+shippingInfo.getUuid());
	                shippingInfoResponse.setServiceEmployeeId(""+shippingInfo.getServiceEmployeeId());                
	                String shippingCreatedTime = null == shippingInfo.getCreatedDate()?
	                		"":DateUtils.composeUTCTime(shippingInfo.getCreatedDate());
	                shippingInfoResponse.setCreatedDate(shippingCreatedTime);
	                shippingInfos.add(shippingInfoResponse);
	            }
	            orderDetailResponse.setShippingInfos(shippingInfos);
			}
			
			List<OrderToProductItem> orderItems = orderToProductItemRepository
					.findByOrderId(order.getUuid());
			orderDetailResponse.setOrderItems(orderItems);
			content.add(orderDetailResponse);
		}

        List<Link> linkList = LinkUtils.prepareLinks(pageable.getPageNumber(), pageable.getPageSize(),
        		request, orderPage, pathParams.toString());
        
		PagedResources<OrderDetailResponse> pagedResources = new PagedResources<OrderDetailResponse>(
				content, new PageMetadata(orderPage.getSize(), orderPage.getNumber(),
						orderPage.getTotalElements(), orderPage.getTotalPages()),
						linkList);
        return new ResponseEntity<>(pagedResources, HttpStatus.OK);
	}

	private Page<Orders> findOrdersByMultiConditions(String keyword,
			List<Integer> currentStatus, Pageable pageable){
		
		Criteria<Orders> criteria = new Criteria<Orders>(); 
		
		if(StringUtils.isEmpty(keyword) == false){
			criteria.add(Restrictions.like("orderNo", keyword, true));
			criteria.add(Restrictions.like("customerName", keyword, true));
			criteria.add(Restrictions.like("customerTel", keyword, true));
		}

		
		if(!CollectionUtils.isEmpty(currentStatus)){
			criteria.add(Restrictions.in("currentStatus",currentStatus, true));
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
		
		Page<Orders> productPricePage = ordersRepository
				.findAll(criteria, pageable);
		return productPricePage;
	}
	

    private Set<Integer> analysisStatus(String statusStr){
    	if(StringUtils.isEmpty(statusStr)){
    		return null;
    	}
    	try{
    		String[] statusArr=statusStr.trim().split(",");
    		Set<Integer> statusSet = new HashSet<Integer>();
    		for(String str : statusArr){
    			if(StringUtils.isEmpty(str.trim())){
    				continue;
    			}
    			try{
    				statusSet.add(Integer.parseInt(str));
    			}catch(Exception e){
    				logger.error(e.getMessage());
    			}
    		}
    		return statusSet;
    	}catch(Exception e){
    		logger.error(e.getMessage(),e);
    	}
		return null;
    }

	public ResponseEntity<?> updateOrderByUuid(String uuid,
			UpdateOrderRequest updateOrderRequest, HttpServletRequest request) {
		Orders order = this.findOneOrder(uuid);
		if(null == order){
	        return new ResponseEntity<HttpStatus>(HttpStatus.BAD_REQUEST);
		}
		BeanUtils.copyPropertiesIgnoreNullValue(updateOrderRequest, order);
		ordersRepository.save(order);
        return new ResponseEntity<HttpStatus>(HttpStatus.OK);
	}

    public void update(
    		Orders order,ShippingInfo shippingInfo) {
    	
        String employeeId = order.getServiceEmployeeId();
        if (null != shippingInfo 
        		&& shippingInfo.getStatus() == OrderStatus.HAD_DISTRIBUTI.getStatus()
        		&& !StringUtils.isEmpty(shippingInfo.getServiceEmployeeId())) {// 已分配
            order.setServiceEmployeeId(shippingInfo.getServiceEmployeeId());// 老板指派员工
        }
        
        if (null != shippingInfo
        		&& shippingInfo.getStatus() == OrderStatus.SERVICE_COMPLETION.getStatus()) {// (服务已完成）
            order.setSettAmount(shippingInfo.getSettAmount());
        }
        if (null != shippingInfo 
        		&& shippingInfo.getStatus() == OrderStatus.HAD_REFUSE.getStatus()) {// 已拒绝（员工）
            order.setCurrentStatus(OrderStatus.WAITING_DISTRIBUTI.getStatus());// 员工拒绝后订单状态变为待分配
            order.setServiceEmployeeId(null);
        } else {
            order.setCurrentStatus(shippingInfo.getStatus());
        }
        ordersRepository.save(order);
    }
}
