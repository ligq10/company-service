/**
 * Created by lixiang@changhongit.com 2015-05-15.
 */
/* Services */
'use strict';
var caseListService=angular.module('caseListServices',['ngResource']);
var caseUrl = '';
caseListService.factory('caseListFactory',function($resource){
   var caseListFactory;
   caseListFactory=$resource(caseUrl,{},{
        // 无条件查询终端列表
        queryList:{
        	url:'/companyserver/cases',
            method:'GET',
            headers:{
                Accept:'application/hal+json'
            }
        },
        deleteCaseByUuid:{
            url:'/companyserver/cases/:uuid',
            method:'DELETE'
        }
    });
    return caseListFactory;
});
