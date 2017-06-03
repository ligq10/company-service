/**
 * Created by lixiang@changhongit.com 2015-05-15.
 */
/* Services */
'use strict';
var newsAddService=angular.module('newsAddServices',['ngResource']);
var newsAddUrl = '';
newsAddService.factory('newsAddFactory',function($resource){
   var newsAddFactory;
   newsAddFactory=$resource(newsAddUrl,{},{

        create:{
        	url:'/companyserver/news',
            method:'POST',
            headers:{
                Accept:'application/hal+json'
            }
        }
    });
    return newsAddFactory;
});
