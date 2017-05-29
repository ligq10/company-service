package com.ligq.shoe.repository;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.data.rest.core.annotation.RestResource;

import com.ligq.shoe.entity.ProductPrice;

public interface ProductPriceRepository extends
	PagingAndSortingRepository<ProductPrice, String>,JpaSpecificationExecutor<ProductPrice>{

	Page<ProductPrice> findByIsdeleted(String name, Pageable pageable);

	List<ProductPrice> findByCompanyIdAndIsdeleted(String uuid, String name);

	@RestResource(exported = false)
	@Query(value = "SELECT pp.* FROM product_price pp LEFT JOIN catalog c ON pp.category_id = c.uuid where (pp.product_name LIKE %:keyword% OR c.name LIKE %:keyword%) AND pp.company_id =:companyId AND isdeleted ='NO'\n#pageable\n", 
		countQuery = "SELECT count(*) FROM product_price pp LEFT JOIN catalog c ON pp.category_id = c.uuid where (pp.product_name LIKE %:keyword% OR c.name LIKE %:keyword%) AND pp.company_id =:companyId AND isdeleted ='NO'", 
		nativeQuery = true)
	Page<ProductPrice> findProductPricesByKeyword(
			@Param("companyId") String companyId, @Param("keyword") String keyword,Pageable pageable);

}
