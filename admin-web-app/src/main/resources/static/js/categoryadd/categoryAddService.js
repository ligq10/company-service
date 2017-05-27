/**
 * Created by lixiang@changhongit.com 2015-05-15.
 */
/* Services */
'use strict';
var categoryAddService=angular.module('categoryAddServices',['ngResource']);
var categoryAddUrl = '';
categoryAddService.factory('categoryAddFactory',function($resource){
   var categoryAddFactory;
   categoryAddFactory=$resource(categoryAddUrl,{},{
        create:{
        	url:'/companyserver/catalogs/03c7bc1b-52dd-3175-bab4-2e06e90efbd9/categories',
            method:'POST',
            headers:{
                Accept:'application/hal+json'
            }
        }
    });
    return categoryAddFactory;
});
