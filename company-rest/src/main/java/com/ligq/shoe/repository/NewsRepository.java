package com.ligq.shoe.repository;

import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.repository.PagingAndSortingRepository;

import com.ligq.shoe.entity.News;

public interface NewsRepository  extends
	PagingAndSortingRepository<News, String>,JpaSpecificationExecutor<News>{

}
