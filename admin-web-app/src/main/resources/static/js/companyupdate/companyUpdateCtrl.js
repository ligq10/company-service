/**
 * Created by wangjingxue@changhongit.com 2015-02-02.
 */
'use strict';
var companyUpdateControllers=angular.module('companyUpdateControllers',['companyUpdateServices']);

companyUpdateControllers.controller('companyUpdateCtrl',['$scope','$timeout','$state','$stateParams','$upload','$rootScope','companyUpdateFactory',
    function($scope,$timeout,$state,$stateParams,$upload,$rootScope,companyUpdateFactory) {
    $scope.uuid = "e55cab55-30b6-4061-845a-2203be945ce4";
	
    $scope.scoreTypeList = [
            {
            	scoreValue:'plus',
            	scoreDesc:'加分'
            },
            {
            	scoreValue:'reduce',
            	scoreDesc:'减分'
            }
    ];
	
    companyUpdateFactory.findCompany({uuid:$scope.uuid},function(response){
    	
    	if(response.$resolved){
    		$scope.comapny = response;
    	}
	});
	
	$scope.submitCompany = function(){

		companyUpdateFactory.saveCompany({uuid: $scope.uuid},$scope.comapny,function(response){				
			if(response.$resolved){
				$state.go('companyupdate');
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
