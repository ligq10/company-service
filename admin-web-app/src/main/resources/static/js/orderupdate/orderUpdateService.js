/**
 * Created by  on 2015-12-02.
 */
/* Services */
'use strict';
var orderUpdateService=angular.module('orderUpdateServices',['ngResource']);
var orderUpdateUrl = '';
orderUpdateService.factory('orderUpdateFactory',function($resource){
    var orderUpdateFactory;
    orderUpdateFactory=$resource(orderUpdateUrl,{},{
    	updateOrderByUuid:{
            method:'POST',
            url:'/companyserver/orders/:uuid',
            headers:{
                Accept:'application/hal+json;charset=UTF-8'
            }
    	},
    	saveShippingInfoByOrderId:{
            method:'POST',
            url:'/companyserver/orders/:uuid/shippinginfos',
            headers:{
                Accept:'application/hal+json;charset=UTF-8'
            }
    	},
    	findOrderByUuid:{
            url:'/companyserver/orders/:uuid',
            method:"GET",
            headers:{
                Accept:'application/hal+json;charset=UTF-8'
            }
        }      	   
    });
    return orderUpdateFactory;
});
