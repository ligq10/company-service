/**
 * Created by lixiang@changhongit.com 2015-05-15.
 */
'use strict';
var categoryUpdateControllers=angular.module('categoryUpdateControllers',['categoryUpdateServices']);

/**
 * 人员修改
 */
categoryUpdateControllers.controller('categoryUpdateCtrl',['$scope','$state','$timeout','loginSession','$stateParams','categoryUpdateFactory',
  function($scope,$state,$timeout,loginSession,$stateParams,categoryUpdateFactory){
	$scope.addSuccess="";
	$scope.category = {};
	$scope.category.uuid=$stateParams.uuid;
	$scope.category.name = "";
	$scope.category.code = "";
	$scope.category.sort = "";
	$scope.category.description = "";
	
	//查询某个用户全部信息
	categoryUpdateFactory.findCategoryById({uuid:$scope.category.uuid},function(response){
        if(response==undefined){
        }else{
        	$scope.category = response;
        	$scope.category.sort = response.sort;
        }	
    });
	
	/**
	 * 修改分类
	 */
	$scope.categoryUpdate=function(){
		var categoryEntity={};
		categoryEntity = $scope.category;	
		
		categoryUpdateFactory.updateCategoryById({uuid:$scope.category.uuid},categoryEntity,function(response){
			if(response.$resolved){
                $scope.addSuccess="保存成功,正在跳转..."               	
            	$timeout(function() {
    				$state.go('categorylist');
    		    }, 1000);
            }else{
            	//$scope.addSuccess="保存失败！"
            }
        }/*,function(error){
            // 接口请求异常处理方法
            dealAbnormalResponse(error);
        }*/
        );
    };
	
}]);