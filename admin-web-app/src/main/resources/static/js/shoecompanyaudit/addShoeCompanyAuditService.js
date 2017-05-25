/**
 * Created by  on 2015-12-02.
 */
/* Services */
'use strict';
var addShoeCompanyAuditService=angular.module('addShoeCompanyAuditServices',['ngResource']);
var addShoeCompanyAuditUrl = '';
addShoeCompanyAuditService.factory('addShoeCompanyAuditFactory',function($resource){
    var addShoeCompanyAuditFactory;
    addShoeCompanyAuditFactory=$resource(addShoeCompanyAuditUrl,{},{
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
    return addShoeCompanyAuditFactory;
});
