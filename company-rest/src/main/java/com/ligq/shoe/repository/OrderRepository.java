package com.ligq.shoe.repository;

import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.repository.PagingAndSortingRepository;

import com.ligq.shoe.entity.Orders;

public interface OrderRepository  extends
	PagingAndSortingRepository<Orders, String>,JpaSpecificationExecutor<Orders>{

}
