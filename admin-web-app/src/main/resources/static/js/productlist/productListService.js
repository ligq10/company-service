/**
 * Created by lixiang@changhongit.com 2015-05-15.
 */
/* Services */
'use strict';
var productListService=angular.module('productListServices',['ngResource']);
var productUrl = '';
productListService.factory('productListFactory',function($resource){
   var productListFactory;
   productListFactory=$resource(productUrl,{},{
        // 无条件查询终端列表
        queryList:{
        	url:'/companyserver/companies/:uuid/productprices',
            method:'GET',
            headers:{
                Accept:'application/hal+json'
            }
        },
        deleteProduct:{
            url:'/companyserver/productprices/:uuid',
            method:'DELETE'
        }
    });
    return productListFactory;
});
