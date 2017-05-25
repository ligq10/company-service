package com.ligq.shoe.repository;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.repository.PagingAndSortingRepository;

import com.ligq.shoe.entity.ProductPrice;

public interface ProductPriceRepository extends
	PagingAndSortingRepository<ProductPrice, String>,JpaSpecificationExecutor<ProductPrice>{

	Page<ProductPrice> findByIsdeleted(String name, Pageable pageable);

	List<ProductPrice> findByCompanyIdAndIsdeleted(String uuid, String name);

}
