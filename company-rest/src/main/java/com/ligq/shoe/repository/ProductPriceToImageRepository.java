package com.ligq.shoe.repository;

import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.repository.PagingAndSortingRepository;

import com.ligq.shoe.entity.ProductPriceToImage;

public interface ProductPriceToImageRepository extends
	PagingAndSortingRepository<ProductPriceToImage, String>,
	JpaSpecificationExecutor<ProductPriceToImage>{

}
