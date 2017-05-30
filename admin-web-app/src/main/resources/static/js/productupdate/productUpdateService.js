/**
 * Created by lixiang@changhongit.com 2015-05-15.
 */
/* Services */
'use strict';
var productUpdateService=angular.module('productUpdateServices',['ngResource']);
var productUpdateUrl = '';
productUpdateService.factory('productUpdateFactory',function($resource){
   var productUpdateFactory;
   productUpdateFactory=$resource(productUpdateUrl,{},{

        create:{
        	url:'/companyserver/companies/:uuid/productprices',
            method:'POST',
            headers:{
                Accept:'application/hal+json'
            }
        },
        update:{
            url:'/companyserver/productprices/:uuid',
            method:'POST',
            headers:{
                Accept:'application/hal+json'
            }
        },
        findProductById:{
            url:'/companyserver/productprices/:uuid',
            method:'GET',
            headers:{
                Accept:'application/hal+json'
            }
        },
        queryAllCategories:{
        	url:'/companyserver/catalogs/03c7bc1b-52dd-3175-bab4-2e06e90efbd9/categories',
            method:'GET',
            headers:{
                Accept:'application/hal+json'
            }
        }
    });
    return productUpdateFactory;
});
