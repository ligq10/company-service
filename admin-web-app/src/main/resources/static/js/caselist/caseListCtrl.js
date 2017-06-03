/**
 * Created by lixiang@changhongit.com 2015-05-15.
 */
'use strict';
var caseListControllers=angular.module('caseListControllers',['caseListServices']);

/**
 * 人员列表
 */
caseListControllers.controller('caseListCtrl',['$scope','loginSession','caseListFactory',
    function($scope,loginSession,caseListFactory){
	   var loginUser = loginSession.loginUser().userInfo;
       $scope.currentPage=CURRENTPAGE_INIT;//当前第几页
       $scope.pageSize=PAGESIZE_DEFAULT;
       $scope.caselist = [];
   	   var search_keyword="";

       //分页
       var refreshCaseList=function(){
    	    $scope.caselist = [];
    	    caseListFactory.queryList({keyword:search_keyword,page:$scope.currentPage,size:$scope.pageSize,sort:'modifiedTime,desc'},function(response){
    		   if(response._embedded==undefined && $scope.currentPage>0){
    			   $scope.caselist = [];
    		       $scope.currentPage=CURRENTPAGE_INIT;//当前第几页
    		       $scope.pageSize=PAGESIZE_DEFAULT;
    		       search_keyword="";
    		   }else{
    	           $scope.caselist = response._embedded.caseResponses;
	               $scope.page=response.page;
	               $scope.numPages = function () {
	                   return response.page.totalPages;
	               };
    		   }
    	   });
       };
       
       //人员list
       refreshCaseList();
       // 搜索
       $scope.search=function() {
           $scope.currentPage=CURRENTPAGE_INIT;//当前第几页
           $scope.pageSize=PAGESIZE_DEFAULT;
	       if($scope.search_keyword != undefined){
	    		search_keyword = $scope.search_keyword;
	       }  
	       refreshCaseList();
       }
        // 点击下一页，上一页，首页，尾页按钮
       $scope.pageChanged=function(){
    	   refreshCaseList();
       };
            
       //删除
       $scope.deleteCase=function(uuid){

    		   Message.confirm(
		   		  {
		   		    msg: "确定要删除？",
		   		    title:"提示",
		   		  })
		   		 .on( function (e) {
		   		    if(e){	    		   
		   		    	caseListFactory.deleteCaseByUuid({uuid:uuid},function(response){
	    		    	   if(response.$resolved){
	    		    		   
	    		    		   Message.alert({
	    			   		    	msg: "删除成功！",
	    			 		    	title:"成功提示",
	    			 		    	btnok: '确定',
	    			 		    	btncl:'取消'
	    			            	},"success","small");
	    		    		   refreshCaseList(); 
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



