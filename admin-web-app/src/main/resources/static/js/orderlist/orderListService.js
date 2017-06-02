/**
 * Created by lixiang@changhongit.com 2015-05-15.
 */
/* Services */
'use strict';
var orderListService=angular.module('orderListServices',['ngResource']);
var orderUrl = '';
orderListService.factory('orderListFactory',function($resource){
   var orderListFactory;
   orderListFactory=$resource(orderUrl,{},{
        // 无条件查询终端列表
        queryList:{
        	url:'/companyserver/orders',
            method:'GET',
            headers:{
                Accept:'application/hal+json'
            }
        }
    });
    return orderListFactory;
});
