/**
 * Created by wangjingxue@changhongit.com 2015-02-02.
 */
'use strict';
var orderUpdateControllers=angular.module('orderUpdateControllers',['orderUpdateServices']);

orderUpdateControllers.controller('orderUpdateCtrl',['$scope','$timeout','$state','$stateParams','$upload','$rootScope','orderUpdateFactory',
    function($scope,$timeout,$state,$stateParams,$upload,$rootScope,orderUpdateFactory) {

	$scope.uuid=$stateParams.uuid;
	$scope.companyId = "e55cab55-30b6-4061-845a-2203be945ce4";
	$scope.shippingInfos = [];
	$scope.orderItems = [];
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
                         }

                     ];

    function initOrderStatus(order){
    	if(undefined == order
    		|| null == order){
    		return false;
    	}
    	
    	if(2 == order.currentStatus){
    	    $scope.statusList = [

    	                         {
    	                     	  value:'6',
    	                          desc:'商家已接单'
    	                         },
    	                         {
    	                          value:'11',
    	                          desc:'订单取消'
    	                         }
    	                     ];
    	}else if(6 == order.currentStatus){
    	    $scope.statusList = [
    	                         {
    	                          value:'7',
    	                          desc:'送货中'
    	                         },
    	                         {
    	                          value:'11',
    	                          desc:'订单取消'
    	                         }
    	                     ];

    	}else if(7 == order.currentStatus){
    	    $scope.statusList = [
    	                         {
    	                          value:'8',
    	                          desc:'已完成'
    	                         },
    	                         {
    	                          value:'11',
    	                          desc:'订单取消'
    	                         }
    	                     ];    		
    	}
    }
	
    orderUpdateFactory.findOrderByUuid({uuid:$scope.uuid},function(response){
    	
    	if(response.$resolved){
    		$scope.order = response;
    		$scope.shippingInfos = $scope.order.shippingInfos;
    		$scope.orderItems = $scope.order.orderItems;
    		initOrderStatus($scope.order);
    	}
	});
	
	$scope.orderUpdate = function(){
		if(undefined == $scope.status
				|| null == $scope.status
				|| $scope.status <= $scope.order.currentStatus){
			Message.alert({
				msg : "更新状态有误!",
				title : "提示:",
				btnok : '确定',btncl : '取消'
			}, "warn", "small");
			return false;
		}
		var postEntity = new Object();
		postEntity.status = $scope.status;
		postEntity.comment = $scope.comment;
		postEntity.payStatus = $scope.order.payStatus;
		postEntity.settAmount = $scope.order.settAmount;
		orderUpdateFactory.saveShippingInfoByOrderId({uuid: $scope.uuid},postEntity,function(response){				
			if(response.$resolved){
				$state.go('orderlist');
				Message.alert({
					msg : "更新成功!",
					title : "提示:",
					btnok : '确定',btncl : '取消'
				}, "warn", "small");
				
			}else{
				Message.alert({
					msg : "更新失败!",
					title : "警告:",
					btnok : '确定',btncl : '取消'
				}, "warn", "small");
			}
	 	 },function(error){	
				Message.alert({
					msg : "更新失败!",
					title : "警告:",
					btnok : '确定',btncl : '取消'
				}, "warn", "small");
	 	 });
	}
	
}]);
