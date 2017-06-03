/**
 * Created by lixiang@changhongit.com 2015-05-15.
 */
/* Services */
'use strict';
var caseAddService=angular.module('caseAddServices',['ngResource']);
var caseAddUrl = '';
caseAddService.factory('caseAddFactory',function($resource){
   var caseAddFactory;
   caseAddFactory=$resource(caseAddUrl,{},{

        create:{
        	url:'/companyserver/cases',
            method:'POST',
            headers:{
                Accept:'application/hal+json'
            }
        }
    });
    return caseAddFactory;
});
