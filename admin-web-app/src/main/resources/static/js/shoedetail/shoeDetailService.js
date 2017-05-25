/**
 * Created by  on 2015-12-02.
 */
/* Services */
'use strict';
var shoeDetailService=angular.module('shoeDetailServices',['ngResource']);
var shoeDetailUrl = '';
shoeDetailService.factory('shoeDetailFactory',function($resource){
    var shoeDetailFactory;
    shoeDetailFactory=$resource(shoeDetailUrl,{},{
    	saveShoeCompany:{
            method:'POST',
            url:'/companyserver/shoecompanies',
            headers:{
                Accept:'application/hal+json'
            }
    	},
    	getShoeComapnyDetailById:{
            url:'/companyserver/shoecompanies/:uuid',
            method:'GET',
            headers:{
            	Accept:'application/hal+json'
            }
        },
        saveAudit:{
        	url:'/companyserver/audits',
            method:'POST',
            headers:{
                Accept:'application/hal+json'
            }
        }
    });
    return shoeDetailFactory;
});
