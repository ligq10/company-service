package com.ligq.shoe.repository;


import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

import com.ligq.shoe.entity.DataDictType;

@RepositoryRestResource(exported = false)
public interface DataDictTypeRepository extends
	PagingAndSortingRepository<DataDictType, String>{

	DataDictType findByTypeCode(String typeCode);

}
