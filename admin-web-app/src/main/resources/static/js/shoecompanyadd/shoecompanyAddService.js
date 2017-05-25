/**
 * Created by  on 2015-12-02.
 */
/* Services */
'use strict';
var shoecompanyAddService=angular.module('shoecompanyAddServices',['ngResource']);
var shoecompanyAddUrl = '';
shoecompanyAddService.factory('shoecompanyAddFactory',function($resource){
    var shoecompanyAddFactory;
    shoecompanyAddFactory=$resource(shoecompanyAddUrl,{},{
    	saveShoeCompany:{
            method:'POST',
            url:'/companyserver/shoecompanies/withoutaudit',
            headers:{
                Accept:'application/hal+json'
            }
    	},
        getCheckCode:{
            url:'/companyserver/sendcheckcode',
            method:"POST",
            headers:{
                Accept:'application/hal+json'
            }
        }   		
    });
    return shoecompanyAddFactory;
});
