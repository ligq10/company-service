/**
 * Created by lixiang@changhongit.com 2015-05-15.
 */
/* Services */
'use strict';
var caseUpdateService=angular.module('caseUpdateServices',['ngResource']);
var caseUpdateUrl = '';
caseUpdateService.factory('caseUpdateFactory',function($resource){
   var caseUpdateFactory;
   caseUpdateFactory=$resource(caseUpdateUrl,{},{

        update:{
            url:'/companyserver/cases/:uuid',
            method:'PATCH',
            headers:{
                Accept:'application/hal+json'
            }
        },
        findCaseById:{
            url:'/companyserver/cases/:uuid',
            method:'GET',
            headers:{
                Accept:'application/hal+json'
            }
        }
    });
    return caseUpdateFactory;
});
