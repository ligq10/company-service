package com.ligq.shoe.repository;

import java.util.List;

import javax.transaction.Transactional;

import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;

import com.ligq.shoe.entity.ProductPriceToImage;

public interface ProductPriceToImageRepository extends
	PagingAndSortingRepository<ProductPriceToImage, String>,
	JpaSpecificationExecutor<ProductPriceToImage>{

	@Modifying
	@Transactional
	@Query("delete from ProductPriceToImage t where t.productId = ?1")
	public int deleteByProductId(String productId);

	public List<ProductPriceToImage> findByProductId(String productId);
}
