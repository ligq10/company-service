/**
 * Created by lixiang@changhongit.com 2015-05-15.
 */
'use strict';
var newsListControllers=angular.module('newsListControllers',['newsListServices']);

/**
 * 人员列表
 */
newsListControllers.controller('newsListCtrl',['$scope','loginSession','newsListFactory',
    function($scope,loginSession,newsListFactory){
	   var loginUser = loginSession.loginUser().userInfo;
       $scope.currentPage=CURRENTPAGE_INIT;//当前第几页
       $scope.pageSize=PAGESIZE_DEFAULT;
       $scope.newslist = [];
   	   var search_keyword="";

       //分页
       var refreshNewsList=function(){
    	    $scope.newslist = [];
    	    newsListFactory.queryList({keyword:search_keyword,page:$scope.currentPage,size:$scope.pageSize,sort:'modifiedTime,desc'},function(response){
    		   if(response._embedded==undefined && $scope.currentPage>0){
    			   $scope.newslist = [];
    		       $scope.currentPage=CURRENTPAGE_INIT;//当前第几页
    		       $scope.pageSize=PAGESIZE_DEFAULT;
    		       search_keyword="";
    		   }else{
    	           $scope.newslist = response._embedded.newsResponses;
	               $scope.page=response.page;
	               $scope.numPages = function () {
	                   return response.page.totalPages;
	               };
    		   }
    	   });
       };
       
       //人员list
       refreshNewsList();
       // 搜索
       $scope.search=function() {
           $scope.currentPage=CURRENTPAGE_INIT;//当前第几页
           $scope.pageSize=PAGESIZE_DEFAULT;
	       if($scope.search_keyword != undefined){
	    		search_keyword = $scope.search_keyword;
	       }  
	       refreshNewsList();
       }
        // 点击下一页，上一页，首页，尾页按钮
       $scope.pageChanged=function(){
    	   refreshNewsList();
       };
            
       //删除
       $scope.deleteNews=function(uuid){

    		   Message.confirm(
		   		  {
		   		    msg: "确定要删除该人员？",
		   		    title:"提示",
		   		  })
		   		 .on( function (e) {
		   		    if(e){	    		   
		   		    	newsListFactory.deleteNewsByUuid({uuid:uuid},function(response){
	    		    	   if(response.$resolved){
	    		    		   
	    		    		   Message.alert({
	    			   		    	msg: "删除成功！",
	    			 		    	title:"成功提示",
	    			 		    	btnok: '确定',
	    			 		    	btncl:'取消'
	    			            	},"success","small");
	    		    		   refreshNewsList(); 
	    		    	   }else{
	    		    		   Message.alert({
	    			   		    	msg: "删除失败！",
	    			 		    	title:"错误提示",
	    			 		    	btnok: '确定',
	    			 		    	btncl:'取消'
	    			            	},"error","small");
	    		    	   }
	    				   
	    			   });		    		   
		   		    }
		   	   }); 

    	   
       };
       
}]);



