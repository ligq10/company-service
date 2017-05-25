package com.ligq.shoe.repository;

import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.repository.PagingAndSortingRepository;

import com.ligq.shoe.entity.Company;

public interface CompanyRepository extends
	PagingAndSortingRepository<Company, String>,JpaSpecificationExecutor<Company>{

}
