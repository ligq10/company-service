/**
 * Created by  on 2015-12-02.
 */
/* Services */
'use strict';
var categoryListService=angular.module('categoryListServices',['ngResource']);
var categoryListUrl = '';
categoryListService.factory('categoryListFactory',function($resource){
    var categoryListFactory;
    categoryListFactory=$resource(categoryListUrl,{},{
    	queryCategoryByCatalogId:{	   		 
	   		 url:"/companyserver/catalogs/:uuid/categories",
	         method:"GET",
	         headers:{
	                Accept:'application/hal+json'
	         }
	    },
	    deleteCategoryById:{
            url:'/companyserver/catalogs/x/categories/:uuid',
            method:'DELETE'
        }
    });
    return categoryListFactory;
});
