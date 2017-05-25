package com.ligq.shoe.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.repository.PagingAndSortingRepository;

import com.ligq.shoe.entity.Category;


public interface CategoryRepository extends
		PagingAndSortingRepository<Category, String>,JpaSpecificationExecutor<Category>{

	Page<Category> findByCatalogId(String cataloguuid, Pageable pageable);

	Page<Category> findByParentId(String parentId, Pageable pageable);

}
