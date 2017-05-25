package com.ligq.shoe.repository;

import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.repository.PagingAndSortingRepository;

import com.ligq.shoe.entity.CataLog;


public interface CataLogRepository extends
		PagingAndSortingRepository<CataLog, String>,JpaSpecificationExecutor<CataLog>{

}
