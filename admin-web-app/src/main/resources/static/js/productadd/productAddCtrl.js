/**
 * Created by lixiang@changhongit.com 2015-05-15.
 */
'use strict';
var productAddControllers=angular.module('productAddControllers',['productAddServices']);

/**
 * 人员新增
 */
productAddControllers.controller('productAddCtrl',['$scope','$state','$timeout','$upload','loginSession','productAddFactory',
   function($scope,$state,$timeout,$upload,loginSession,productAddFactory){
	$scope.companyId="e55cab55-30b6-4061-845a-2203be945ce4";
	// 轮播图数据初始化
    $scope.slides =[
                    //"http://img02.tooopen.com/images/20160125/tooopen_sy_155386511951.jpg",
                    //"http://img02.tooopen.com/images/20151231/tooopen_sy_153270994272.jpg"
                    //"/companyserver/images/show/a8b6b36b-02bf-4779-bb6b-3adf00bd3fb2"
                    ];
    $scope.categoryList = [];	

	//权限菜单
    productAddFactory.queryAllCategories(function(response){
		$scope.categoryList = [];
		if(response._embedded==undefined){
			$scope.categoryList = [];
		}else{
			for(var i=0; i<response._embedded.categoryResponses.length; i++) {
				$scope.categoryList.push({
					categoryId:response._embedded.categoryResponses[i].uuid,
					categoryName:response._embedded.categoryResponses[i].name,
					checked:false
				});
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
		
	/**
	 * 新增产品保存
	 */
	$scope.productAdd=function(){
		$scope.product.images = $scope.slides; 
		productAddFactory.create({uuid:$scope.companyId},$scope.product,function(response){
            if(response.$resolved){
             
                $scope.addSuccess="保存成功,正在跳转..."
                	
            	$timeout(function() {
    				$state.go('productlist');
    		    }, 1000);
        		
            }else{
            	$scope.addSuccess="保存失败！"
            }
        },function(error){
        	$scope.addSuccess="保存失败！"
        });
    }
	
	$scope.imageUpload = function(){
		var files = $scope.productImage;

		$scope.fileIds = [];
		if(files == null || files == undefined || files.length == 0){
			Message.alert({
				msg : "产品图片为空!",
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
        				msg : "产品图片上传成功!",
        				title : "提示:",
        				btnok : '确定',btncl : '取消'
        			}, "success", "small");
	            }else{
	    	    	Message.alert({
	    			    msg: "产品图片上传失败！",
	    		    	title:"警告提示",
	    		    	btnok: '确定',
	    		    	btncl:'取消'
	    	      	},"warn","small");
	            }
	    }).error(function(data){
	    	Message.alert({
			    msg: "产品图片上传失败！",
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

