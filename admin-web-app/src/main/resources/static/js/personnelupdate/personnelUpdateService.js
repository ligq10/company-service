/**
 * Created by lixiang@changhongit.com 2015-05-15.
 */
/* Services */
'use strict';
var personnelUpdateService=angular.module('personnelUpdateServices',['ngResource']);
var personnelUpdateUrl = '';
personnelUpdateService.factory('personnelUpdateFactory',function($resource){
   var personnelUpdateFactory;
   personnelUpdateFactory=$resource(personnelUpdateUrl,{},{
        // 无条件查询终端列表
        queryList:{
        	url:'/users/search/byKeyword',
            method:'GET',
            headers:{
                Accept:'application/hal+json'
            }
        },
       registerUser:{
           url:'/users/register',
           method:'POST',
           headers:{
               'Content-Type':'application/vnd.jiahua.commands.registerUser.v1+json'
           }
       },
        create:{
        	url:'/users/command',
            method:'POST',
            headers:{
            	'Content-Type':'application/vnd.jiahua.commands.addUser.v1+json'
            }
        },
        updateUser:{
            url:'/companyserver/employees/:uuid',
            method:'PATCH',
            headers:{
                Accept:'application/hal+json'
            }
        },
        delete:{
            url:'/companyserver/users/:uuid',
            method:'DELETE'
        },
        resetPassword:{
        	url:'/security/users/password.reset',
            method:'POST',
            headers:{
                'Content-Type':'application/vnd.jiahua.commands.UpdateUserPassword.v1+json'
            }
        },
        searchAllRoles:{
        	url: '/security/roles',
        	method: 'GET',
        	headers: {
        		'Content-Type':'application/json'
        	}
        }, 
        checkLoginName:{
            url:'/companyserver/employees/checkloginname',
            params:{loginname:"loginname"},
            method:'GET'
        }, 
       getFirstAddressData:{
    	   url:'/groups/100000/childrens',
    	   method:'GET'
       },
       getNextAddressData:{
    	   url:'/groups/:code/childrens',
    	   method:'GET'
       },
       personnelDetail:{
    	   url:'/companyserver/employees/:uuid',
    	   method:'GET'
       },
       getOneUser:{
    	   url:'/companyserver/users/search',
           params:{loginname:'loginname'},
    	   method:'GET'
       }
    });
    return personnelUpdateFactory;
});
