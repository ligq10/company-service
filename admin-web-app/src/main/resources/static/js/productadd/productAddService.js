/**
 * Created by lixiang@changhongit.com 2015-05-15.
 */
/* Services */
'use strict';
var productAddService=angular.module('productAddServices',['ngResource']);
var productAddUrl = '';
productAddService.factory('productAddFactory',function($resource){
   var productAddFactory;
   productAddFactory=$resource(productAddUrl,{},{

        create:{
        	url:'/companyserver/companies/:uuid/productprices',
            method:'POST',
            headers:{
                Accept:'application/hal+json'
            }
        },
        delete:{
            url:'/companyserver/users/:uuid',
            method:'DELETE'
        },
        queryAllCategories:{
        	url:'/companyserver/catalogs/03c7bc1b-52dd-3175-bab4-2e06e90efbd9/categories',
            method:'GET',
            headers:{
                Accept:'application/hal+json'
            }
        }
    });
    return productAddFactory;
});
