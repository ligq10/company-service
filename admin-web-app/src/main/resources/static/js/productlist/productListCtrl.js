/**
 * Created by lixiang@changhongit.com 2015-05-15.
 */
'use strict';
var productListControllers=angular.module('productListControllers',['productListServices']);

/**
 * 人员列表
 */
productListControllers.controller('productListCtrl',['$scope','loginSession','productListFactory',
    function($scope,loginSession,productListFactory){
	   var loginUser = loginSession.loginUser().userInfo;
       $scope.currentPage=CURRENTPAGE_INIT;//当前第几页
       $scope.pageSize=PAGESIZE_DEFAULT;
       $scope.products = [];
   	   var search_keyword="";
   	   $scope.companyId='e55cab55-30b6-4061-845a-2203be945ce4';
       //分页
       var refreshProductList=function(){
    	    $scope.products = [];
    	    productListFactory.queryList({uuid:$scope.companyId},{keyword:search_keyword,page:$scope.currentPage,size:$scope.pageSize,sort:'createdTime,desc'},function(response){
    		   if(response._embedded==undefined && $scope.currentPage>0){
    			   $scope.products = [];
    		       $scope.currentPage=CURRENTPAGE_INIT;//当前第几页
    		       $scope.pageSize=PAGESIZE_DEFAULT;
    		       search_keyword="";
    		   }else{
    			   makeEntry(response);
				   $scope.numPages = function () {
					   if(response._embedded){
						   return $scope.page.totalPages;//总页数
					   }
				   };
    		   }
    	   });
       };
       
       //人员list
       refreshProductList();
       // 搜索
       $scope.search=function() {
           $scope.currentPage=CURRENTPAGE_INIT;//当前第几页
           $scope.pageSize=PAGESIZE_DEFAULT;
	       if($scope.search_keyword != undefined){
	    		search_keyword = $scope.search_keyword;
	       }  
	       refreshProductList();
       }
        // 点击下一页，上一页，首页，尾页按钮
       $scope.pageChanged=function(){
    	   refreshProductList();
       };
            
       //删除
       $scope.deleteProduct=function(uuid){

    		   Message.confirm(
		   		  {
		   		    msg: "确定要删除该产品？",
		   		    title:"提示",
		   		  })
		   		 .on( function (e) {
		   		    if(e){	    		   
		   		    	productListFactory.deleteProduct({uuid:uuid},function(response){
	    		    	   if(response.$resolved){
	    		    		   
	    		    		   Message.alert({
	    			   		    	msg: "删除成功！",
	    			 		    	title:"成功提示",
	    			 		    	btnok: '确定',
	    			 		    	btncl:'取消'
	    			            	},"success","small");
	    		    		   refreshProductList(); 
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
       
       //封装数据
       var makeEntry=function(response){
           $scope.page=response.page;
           if(response._embedded==undefined){
               $scope.pagingHidden=true;
           }else{
               $scope.pagingHidden=false;
               $scope.products = response._embedded.productPriceResponses;
           }
       };   
}]);



