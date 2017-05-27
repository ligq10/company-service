/**
 * Created by lixiang@changhongit.com 2015-05-15.
 */
'use strict';
var categoryAddControllers=angular.module('categoryAddControllers',['categoryAddServices']);

/**
 * 人员新增
 */
categoryAddControllers.controller('categoryAddCtrl',['$scope','$state','$timeout','loginSession','categoryAddFactory',
 function($scope,$state,$timeout,loginSession,categoryAddFactory){
	$scope.category=new Object();
	$scope.category.name = "";
	$scope.category.code = "";
	$scope.category.sort = "";
	$scope.category.description = "";

  
	/**
	 * 新增分类
	 */
	$scope.categoryAdd=function(){
		var categoryEntity={};
		categoryEntity = $scope.category;	
		categoryAddFactory.create(categoryEntity,function(response){
            if(response.$resolved){
               
                $scope.addSuccess="保存成功,正在跳转..."
                	
            	$timeout(function() {
    				$state.go('categorylist');
    		    }, 1000);
        		
            }else{
            	$scope.addSuccess="保存失败！"
            }
        },function(error){
        	$scope.addSuccess="保存失败！"
        });
    }
	
	
}]);

