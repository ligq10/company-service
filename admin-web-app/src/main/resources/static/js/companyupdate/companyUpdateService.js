/**
 * Created by  on 2015-12-02.
 */
/* Services */
'use strict';
var companyUpdateService=angular.module('companyUpdateServices',['ngResource']);
var companyUpdateUrl = '';
companyUpdateService.factory('companyUpdateFactory',function($resource){
    var companyUpdateFactory;
    companyUpdateFactory=$resource(companyUpdateUrl,{},{
    	saveCompany:{
            method:'POST',
            url:'/companyserver/companies/:uuid',
            headers:{
                Accept:'application/hal+json;charset=UTF-8'
            }
    	},
        findCompany:{
            url:'/companyserver/companies/:uuid',
            method:"GET",
            headers:{
                Accept:'application/hal+json;charset=UTF-8'
            }
        }      	
        // 查询树的根节点
/*        getTreeOfParent:{
            url:'/groups/guanhutong',
            method:"GET",
            headers:{
                Accept:'application/json'
            }
        },
        // 查询子节点
        getChildren:{
            url:'/groups/:uuid/childrens',
            method:"GET",
            headers:{
                Accept:'application/json'
            }
        },
        // 无条件查询终端列表
        queryList:{
            method:'GET',
            headers:{
                Accept:'application/json'
            }
        },
        create:{
            method:'POST',
            headers:{
                Accept:'application/json'
            }
        },
        delete:{
            url:'/groups/:uuid',
            method:'DELETE'
        },
        update:{
            url:'/groups/:uuid',
            method:'PATCH',
            headers:{
                Accept:'application/json'
            }
        }
*/    
    });
    return companyUpdateFactory;
});
