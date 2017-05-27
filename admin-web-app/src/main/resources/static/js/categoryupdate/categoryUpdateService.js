/**
 * Created by lixiang@changhongit.com 2015-05-15.
 */
/* Services */
'use strict';
var categoryUpdateService=angular.module('categoryUpdateServices',['ngResource']);
var categoryUpdateUrl = '';
categoryUpdateService.factory('categoryUpdateFactory',function($resource){
   var categoryUpdateFactory;
   categoryUpdateFactory=$resource(categoryUpdateUrl,{},{

        updateCategoryById:{
            url:'/companyserver/catalogs/x/categories/:uuid',
            method:'POST',
            headers:{
                Accept:'application/hal+json'
            }
        },
        findCategoryById:{
            url:'/companyserver/catalogs/x/categories/:uuid',
            method:'GET',
            headers:{
                Accept:'application/hal+json'
            }
        }

    });
    return categoryUpdateFactory;
});
