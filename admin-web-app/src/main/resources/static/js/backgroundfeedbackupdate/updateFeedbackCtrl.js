/**
 * Created by liguangqiang@changhongit.com 2015-05-15.
 */
'use strict';
var updateFeedbackControllers=angular.module('updateFeedbackControllers',['updateFeedbackServices']);

/**
 * 人员列表
 */
updateFeedbackControllers.controller('updateFeedbackCtrl',['$scope','$stateParams','$state','loginSession','updateFeedbackFactory',
    function($scope,$stateParams,$state,loginSession,updateFeedbackFactory){
	var loginUser = loginSession.loginUser().userInfo;
	$scope.result = "pass";
	$scope.feedbackId = $stateParams.uuid;
	$scope.proofImageUrlListIndex=[];
	$scope.auditscore = 0;//审核评分
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
    $scope.scoreItemList = [
                            {
                            	scoreCode:'creditScore',
                            	scoreDesc:'诚信分'
                            },
                            {
                            	scoreCode:'qualityScore',
                            	scoreDesc:'产品质量分'
                            },{
                            	scoreCode:'serveScore',
                            	scoreDesc:'服务分'
                            }
                            ];
	// 轮播图数据初始化
	$scope.slides =[];
/*    $scope.slides =[
                    "http://img02.tooopen.com/images/20160125/tooopen_sy_155386511951.jpg",
                    "http://img02.tooopen.com/images/20151231/tooopen_sy_153270994272.jpg"
                             
                    ];*/
	updateFeedbackFactory.getFeedbackDetailById({uuid:$scope.feedbackId},function(response){    	
    	if(response.$resolved){
    		$scope.feedback = response;
    		
    		if(response.proofImageUrls != null && response.proofImageUrls.length >0){
    			$scope.slides = response.proofImageUrls;
    		}
    	}
	});
	/**
	 * 轮播图看上一页
	 */
	$scope.prev = function(){
		//$("#myCarousel").carousel('prev');
		angular.element("#myCarousel").carousel('prev');
	}
	/**
	 * 轮播图看下一页
	 */
	$scope.next = function(){
        //$("#myCarousel").carousel('next');
		angular.element("#myCarousel").carousel('next');

        //播放指定图片
        //$("#myCarousel").carousel(0);
        //$("#myCarousel").carousel(1);
        //停止播放
       // $("#myCarousel").carousel('pause');

	}
	
	$scope.plusScore = function(){
		if($scope.auditscore < 20){
			$scope.auditscore = $scope.auditscore+1;
		}
	}
	
	$scope.reduceScore = function(){
		if($scope.auditscore >= 1){
			$scope.auditscore = $scope.auditscore-1;
		}
	}
	
	//$state.go('shoeList');
}]);