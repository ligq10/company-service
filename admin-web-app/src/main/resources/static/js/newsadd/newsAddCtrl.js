/**
 * Created by lixiang@changhongit.com 2015-05-15.
 */
'use strict';
var newsAddControllers=angular.module('newsAddControllers',['newsAddServices']);

/**
 * 人员新增
 */
newsAddControllers.controller('newsAddCtrl',['$scope','$state','$timeout','$upload','loginSession','newsAddFactory',
   function($scope,$state,$timeout,$upload,loginSession,newsAddFactory){
	var loginUser = loginSession.loginUser().userInfo;
	$scope.companyId="e55cab55-30b6-4061-845a-2203be945ce4";
	// 轮播图数据初始化
    $scope.slides =[
                    //"http://img02.tooopen.com/images/20160125/tooopen_sy_155386511951.jpg",
                    //"http://img02.tooopen.com/images/20151231/tooopen_sy_153270994272.jpg"
                    //"/companyserver/images/show/a8b6b36b-02bf-4779-bb6b-3adf00bd3fb2"
                    ];
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
		
	/**
	 * 新增产品保存
	 */
	$scope.newsAdd=function(){
		if(undefined != $scope.slides
				&& null != $scope.slides
				&& $scope.slides.length > 0){
			$scope.news.icon = $scope.slides[0].imageUuid; 
		}
		
		if(undefined != loginUser
				&& null != loginUser
				&& undefined != loginUser.uuid
				&& null != loginUser.uuid){
			$scope.news.createdBy = loginUser.uuid; 
			$scope.news.modifiedBy = loginUser.uuid; 

		}
		newsAddFactory.create($scope.news,function(response){
            if(response.$resolved){
             
                $scope.addSuccess="保存成功,正在跳转..."
                	
            	$timeout(function() {
    				$state.go('newslist');
    		    }, 1000);
        		
            }else{
            	$scope.addSuccess="保存失败！"
            }
        },function(error){
        	$scope.addSuccess="保存失败！"
        });
    }
	
	$scope.imageUpload = function(){
		var files = $scope.image;

		$scope.fileIds = [];
		if(files == null || files == undefined || files.length == 0){
			Message.alert({
				msg : "图片为空!",
				title : "警告:",
				btnok : '确定',btncl : '取消'
			}, "warn", "small");
			return false;
		}

		$scope.upload = $upload.upload({
	        url:'/companyserver/images/multipartfile',
	        method:'POST',
	        header:{
	            "Content-Type":"multipart/form-data"
	        },
	        //data:postEntity
	        file:files
	    }).success(function(response, status, headers, config){
	            if(status==201){
            		$scope.fileIds = response;
            		for(var i= 0;i<$scope.fileIds.length;i++){
        				$scope.slides.push({
        						imageUuid:$scope.fileIds[i],
        	                    url:"/companyserver/images/show/"+$scope.fileIds[i]
        				});
            		}
            		
        			Message.alert({
        				msg : "图片上传成功!",
        				title : "提示:",
        				btnok : '确定',btncl : '取消'
        			}, "success", "small");
	            }else{
	    	    	Message.alert({
	    			    msg: "图片上传失败！",
	    		    	title:"警告提示",
	    		    	btnok: '确定',
	    		    	btncl:'取消'
	    	      	},"warn","small");
	            }
	    }).error(function(data){
	    	Message.alert({
			    msg: "图片上传失败！",
		    	title:"警告提示",
		    	btnok: '确定',
		    	btncl:'取消'
	      	},"warn","small");
	    });
	}

	$scope.clearImage = function(){
		$scope.slides=[];
	}
}]);

