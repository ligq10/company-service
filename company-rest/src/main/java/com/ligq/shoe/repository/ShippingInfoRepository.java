package com.ligq.shoe.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.repository.PagingAndSortingRepository;

import com.ligq.shoe.entity.ShippingInfo;

public interface ShippingInfoRepository   extends
	PagingAndSortingRepository<ShippingInfo, String>,JpaSpecificationExecutor<ShippingInfo>{

	List<ShippingInfo> findByOrderIdOrderByCreatedDateDesc(String orderId);

	ShippingInfo findFirstByOrderIdOrderByCreatedDateDesc(String orderuuid);

}
