/**
 * Created by lixiang@changhongit.com 2015-05-15.
 */
'use strict';
var orderListControllers=angular.module('orderListControllers',['orderListServices']);

/**
 * 人员列表
 */
orderListControllers.controller('orderListCtrl',['$scope','loginSession','orderListFactory',
    function($scope,loginSession,orderListFactory){
	   var loginUser = loginSession.loginUser().userInfo;
       $scope.currentPage=CURRENTPAGE_INIT;//当前第几页
       $scope.pageSize=PAGESIZE_DEFAULT;
       $scope.orders = [];
   	   var search_keyword="";
   	   var order_status="";
       $scope.statusList = [
                               {
                               	value:'1',
                               	desc:'待确认'
                               },
                               {
                        	    value:'2',
                               	desc:'已确认'
                               },
                               {
	                       	    value:'6',
	                            desc:'商家已接单'
	                           },
                               {
                                value:'7',
                                desc:'送货中'
                               },
                               {
                                value:'8',
                                desc:'已完成'
                               },
                               {
                                value:'11',
                                desc:'订单取消'
                               },

                           ];

       //分页
       var refreshOrderList=function(){
    	    $scope.orders = [];
    	    var sorts = ["currentStatus,asc","createdDate,desc"];
    	    orderListFactory.queryList({keyword:search_keyword,currentStatus:order_status,page:$scope.currentPage,size:$scope.pageSize,sort:sorts},function(response){
    		   if(response._embedded==undefined && $scope.currentPage>0){
    			   $scope.orders = [];
    		       $scope.currentPage=CURRENTPAGE_INIT;//当前第几页
    		       $scope.pageSize=PAGESIZE_DEFAULT;
    		       search_keyword="";
    		       order_status = "";
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
       refreshOrderList();
       // 搜索
       $scope.search=function() {
           $scope.currentPage=CURRENTPAGE_INIT;//当前第几页
           $scope.pageSize=PAGESIZE_DEFAULT;
	       if($scope.search_keyword != undefined){
	    		search_keyword = $scope.search_keyword;
	       }
	       if(undefined != $scope.currentStatus){
		       order_status = $scope.currentStatus;
	       }else{
		       order_status = "";
	       }
	       refreshOrderList();
       }
        // 点击下一页，上一页，首页，尾页按钮
       $scope.pageChanged=function(){
    	   refreshOrderList();
       };
            
       
       //封装数据
       var makeEntry=function(response){
           $scope.page=response.page;
           if(response._embedded==undefined){
               $scope.pagingHidden=true;
           }else{
               $scope.pagingHidden=false;
               $scope.orders = response._embedded.orderDetailResponses;
           }
       };   
}]);



