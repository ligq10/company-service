/**
 * Created by lixiang@changhongit.com 2015-05-15.
 */
/* Services */
'use strict';
var newsUpdateService=angular.module('newsUpdateServices',['ngResource']);
var newsUpdateUrl = '';
newsUpdateService.factory('newsUpdateFactory',function($resource){
   var newsUpdateFactory;
   newsUpdateFactory=$resource(newsUpdateUrl,{},{

        update:{
            url:'/companyserver/news/:uuid',
            method:'PATCH',
            headers:{
                Accept:'application/hal+json'
            }
        },
        findNewsById:{
            url:'/companyserver/news/:uuid',
            method:'GET',
            headers:{
                Accept:'application/hal+json'
            }
        }
    });
    return newsUpdateFactory;
});
