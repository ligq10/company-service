/**
 * Created by wangjingxue@changhongit.com 2015-02-02.
 */
'use strict';
var categoryListControllers=angular.module('categoryListControllers',['categoryListServices']);

categoryListControllers.controller('categoryListCtrl',['$scope','$stateParams','$upload','$rootScope','categoryListFactory',
    function($scope,$stateParams,$upload,$rootScope,categoryListFactory) {
    $scope.catalogUuid = "03c7bc1b-52dd-3175-bab4-2e06e90efbd9";
	var search_keyword="";

	$scope.categoryList = [];
    $scope.pageSize=PAGESIZE_DEFAULT;
	$scope.currentPage=CURRENTPAGE_INIT;
	
	var initcategoryList = function(){
		var queryEntity = {
				page:$scope.currentPage,
				size:$scope.pageSize
		}
		refreshCategoryList(queryEntity);
	}
	
	/**
	 * 刷新订单列表
	 */
	var refreshCategoryList = function(queryEntity){
		var queryEntity = queryEntity
		if(queryEntity==undefined){
			queryEntity = {};
			queryEntity.page=$scope.currentPage,
			queryEntity.size=$scope.pageSize
			//queryEntity.auditStatus=3
		}else{
			//queryEntity.auditStatus=3
		}
		categoryListFactory.queryCategoryByCatalogId({uuid:$scope.catalogUuid},queryEntity,function(response){
			if(response.$resolved){
	            if(response._embedded==undefined){
	            	$scope.categoryList = [];
	            	$scope.currentPage = 0;
	            	$scope.numPages = 0;
	            	$scope.pageSize = 0;
	            	return false;
	            }else{
	            	$scope.categoryList=[];
	            	$scope.page=response.page;
	                $scope.numPages = function () {
	                    return response.page.totalPages;
	                };
	                for(var i=0;i< response._embedded.categoryResponses.length;i++){
	                	$scope.categoryList[i]= response._embedded.categoryResponses[i];
	                }
	            }

			}else{
	        	$scope.categoryList = [];
	        	$scope.currentPage = 0;
	        	$scope.numPages = 0;
	        	$scope.pageSize = 0;
			}

		});
	}					

	initcategoryList();
	
    // 搜索
    $scope.search=function() {
        $scope.currentPage=CURRENTPAGE_INIT;//当前第几页
        $scope.pageSize=PAGESIZE_DEFAULT;
	       if($scope.search_keyword != undefined){
	    		search_keyword = $scope.search_keyword;
	       }  
	       refreshOrderList();
    }

	
    //shoe翻页 点击下一页，上一页，首页，尾页按钮
    $scope.pageChanged=function(){
		var queryEntity = {
				page:$scope.currentPage,
				size:$scope.pageSize
		}
		refreshCategoryList(queryEntity);
    }
    
    //删除
    $scope.deletecategory=function(uuid){

 		   Message.confirm(
		   		  {
		   		    msg: "确定要删除该分类？",
		   		    title:"提示",
		   		  })
		   		 .on( function (e) {
		   		    if(e){	    		   
		   		    	categoryListFactory.deleteCategoryById({uuid:uuid},function(response){
	    		    	   if(response.$resolved){
	    		    		   
	    		    		   Message.alert({
	    			   		    	msg: "删除成功！",
	    			 		    	title:"成功提示",
	    			 		    	btnok: '确定',
	    			 		    	btncl:'取消'
	    			            	},"success","small");
	    		    		   refreshCategoryList(); 
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