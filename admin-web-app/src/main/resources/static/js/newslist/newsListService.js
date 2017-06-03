/**
 * Created by lixiang@changhongit.com 2015-05-15.
 */
/* Services */
'use strict';
var newsListService=angular.module('newsListServices',['ngResource']);
var newsUrl = '';
newsListService.factory('newsListFactory',function($resource){
   var newsListFactory;
   newsListFactory=$resource(newsUrl,{},{
        // 无条件查询终端列表
        queryList:{
        	url:'/companyserver/news',
            method:'GET',
            headers:{
                Accept:'application/hal+json'
            }
        },
        deleteNewsByUuid:{
            url:'/companyserver/news/:uuid',
            method:'DELETE'
        }
    });
    return newsListFactory;
});
