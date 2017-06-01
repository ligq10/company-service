package com.ligq.shoe.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.repository.PagingAndSortingRepository;

import com.ligq.shoe.entity.OrderToProductItem;

public interface OrderToProductItemRepository  extends
	PagingAndSortingRepository<OrderToProductItem, String>,JpaSpecificationExecutor<OrderToProductItem>{

	List<OrderToProductItem> findByOrderId(String orderId);

}
