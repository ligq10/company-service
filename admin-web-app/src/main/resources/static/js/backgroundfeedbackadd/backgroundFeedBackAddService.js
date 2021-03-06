/**
 * Created by  on 2015-12-02.
 */
/* Services */
'use strict';
var backgroundFeedBackAddService=angular.module('backgroundFeedBackAddServices',['ngResource']);
var feedBackAddUrl = '';
backgroundFeedBackAddService.factory('backgroundFeedBackAddFactory',function($resource){
    var backgroundFeedBackAddFactory;
    backgroundFeedBackAddFactory=$resource(feedBackAddUrl,{},{
    	saveFeedback:{
            method:'POST',
            url:'/companyserver/shoecompanies/:uuid/feedbacks/withoutaudit',
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
        },
        searchCompanyList:{
            url:'/companyserver/shoecompanies/audit',
            method:"get",
            headers:{
                Accept:'application/hal+json'
            }
        }
        
    });
    return backgroundFeedBackAddFactory;
});
