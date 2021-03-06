/**
 * Created by Administrator on 15-1-28.
 */
'use strict';
var adminApp=angular.module('adminApp',['ui.router','ui.tree','angularFileUpload','personnelAddControllers','orderListControllers','orderUpdateControllers',
                  'personnelListControllers','personnelUpdateControllers','companyUpdateControllers','categoryListControllers','categoryAddControllers',
                  'categoryUpdateControllers','productListControllers','productAddControllers','productUpdateControllers',
                  'newsListControllers','newsAddControllers','newsUpdateControllers','caseListControllers','caseAddControllers','caseUpdateControllers']);
adminApp.config(function($stateProvider, $urlRouterProvider, $httpProvider,$rootScopeProvider){
    $httpProvider.defaults.headers.common['X-Token'] = $.cookie('X-Token');

// For any unmatched url, redirect to /index
    $urlRouterProvider.otherwise("/index");
    // Now set up the states
    $stateProvider.
        state('index', {
            url: "/index",
            template:"欢迎使用",
            permission: 'PASS'
        })
        .state('unauthorized', {
            url: "/unauthorized",//无权限
            template: "<div class='alert alert alert-danger'>您没有权限访问此页面</div>",
            permission: 'PASS'
        })
        .state('personnellist',{
            url:"/personnellist",
            templateUrl:'templates/personnel/personnel-list.html',
            controller:'personnelListCtrl',
            permission:'PERSONNEL_LIST',
            father:'PERSONNEL_MANAGER'
        })
        .state('personneladd',{
            url:"/personneladd",
            templateUrl:'templates/personnel/personnel-add.html',
            controller:'personnelAddCtrl',
            permission:'PERSONNEL_ADD',
            father:'PERSONNEL_MANAGER'            
        })
        .state('personnelupdate',{
            url:"/personnelupdate/:uuid",
            templateUrl:'templates/personnel/personnel-update.html',
            controller:'personnelUpdateCtrl',
            permission:'PERSONNEL_UPDATE',
            father:'PERSONNEL_MANAGER'
        })
/*        .state('auditshoecompanylist',{
            url:"/auditshoecompanylist",
            templateUrl:'templates/shoecompanymanager/audit_shoe_company_list.html',
            controller:'auditShoeCompanyListCtrl',
            permission:'COMPANY_AUDIT_LIST',
            father:'COMPANY_AUDIT_MANAGER'            
        })
        .state('shoecompanylist',{
            url:"/shoecompanylist",
            templateUrl:'templates/shoecompanymanager/sho-company-list.html',
            controller:'shoecompanyListCtrl',
            permission:'COMPANY_LIST',
            father:'COMPANY_AUDIT_MANAGER'            
        })
        .state('shoecompanyadd',{
            url:"/shoecompanyadd",
            templateUrl:'templates/shoecompanymanager/shoe-company-add.html',
            controller:'shoecompanyAddCtrl',
            permission:'COMPANY_AUDIT_LIST',
            father:'COMPANY_AUDIT_MANAGER'            
        })
        .state('shoecompanyupdate',{
            url:"/shoecompanyupdate/:uuid",
            templateUrl:'templates/shoecompanymanager/shoe-company-update.html',
            controller:'shoecompanyUpdateCtrl',
            permission:'COMPANY_AUDIT_LIST',
            father:'COMPANY_AUDIT_MANAGER'            
        })
        .state('shoecompanyaudit',{
            url:"/shoecompanyaudit/:uuid",
            templateUrl:'templates/shoecompanyaudit/add-shoe-company-audit.html',
            controller:'addShoeCompanyAuditCtrl',
            permission:'COMPANY_AUDIT',
            father:'COMPANY_AUDIT_MANAGER'             
        })
        .state('primaryauditfeedbacklist',{
            url:"/primaryauditfeedbacklist",
            templateUrl:'templates/feedbackaudit/primary_audit_feedback_list.html',
            controller:'primaryAuditFeedbackListCtrl',
            permission:'PRIMARY_FEEDBACK_AUDIT_LIST',
            father:'FEEDBACK_AUDIT_MANAGER'             
        })
        .state('primaryauditfeedback',{
            url:"/primaryauditfeedback/:uuid",
            templateUrl:'templates/feedbackaudit/primary_audit_feedback.html',
            controller:'primaryAuditFeedbackCtrl',
            permission:'PRIMARY_FEEDBACK_AUDIT',
            father:'FEEDBACK_AUDIT_MANAGER'             
        })
        .state('middleauditfeedbacklist',{
            url:"/middleauditfeedbacklist",
            templateUrl:'templates/feedbackaudit/middle_audit_feedback_list.html',
            controller:'middleAuditFeedbackListCtrl',
            permission:'MIDDLE_FEEDBACK_AUDIT_LIST',
            father:'FEEDBACK_AUDIT_MANAGER'             
        })
        .state('middleauditfeedback',{
            url:"/middleauditfeedback/:uuid",
            templateUrl:'templates/feedbackaudit/middle_audit_feedback.html',
            controller:'middleAuditFeedbackCtrl',
            permission:'MIDDLE_FEEDBACK_AUDIT',
            father:'FEEDBACK_AUDIT_MANAGER'             
        })
        .state('backgroundfeedbacklist',{
            url:"/backgroundfeedbacklist",
            templateUrl:'templates/feedback/feedback-list.html',
            controller:'backgroundFeedBackListCtrl',
            permission:'FEEDBACK_LIST',
            father:'FEEDBACK_AUDIT_MANAGER'             
        })
        .state('backgroundfeedbackadd',{
            url:"/backgroundfeedbackadd",
            templateUrl:'templates/feedback/feedback-add.html',
            controller:'backgroundFeedBackAddCtrl',
            permission:'FEEDBACK_ADD',
            father:'FEEDBACK_AUDIT_MANAGER'             
        })
        .state('feedbackdetail',{
            url:"/feedbackdetail/:uuid",
            templateUrl:'templates/feedback/feedback-update.html',
            controller:'updateFeedbackCtrl',
            permission:'FEEDBACK_UPDATE',
            father:'FEEDBACK_AUDIT_MANAGER'             
        })*/
        .state('caselist',{
            url:"/caselist",
            templateUrl:'templates/case/case-list.html',
            controller:'caseListCtrl',
            permission:'PERSONNEL_UPDATE',
            father:'PERSONNEL_MANAGER'
        })
        .state('caseadd',{
            url:"/caseadd",
            templateUrl:'templates/case/case-add.html',
            controller:'caseAddCtrl',
            permission:'PERSONNEL_UPDATE',
            father:'PERSONNEL_MANAGER'
        })
        .state('caseupdate',{
            url:"/caseupdate/:uuid",
            templateUrl:'templates/case/case-update.html',
            controller:'caseUpdateCtrl',
            permission:'PERSONNEL_UPDATE',
            father:'PERSONNEL_MANAGER'
        })
        .state('newslist',{
            url:"/newslist",
            templateUrl:'templates/news/news-list.html',
            controller:'newsListCtrl',
            permission:'PERSONNEL_UPDATE',
            father:'PERSONNEL_MANAGER'
        })
        .state('newsadd',{
            url:"/newsadd",
            templateUrl:'templates/news/news-add.html',
            controller:'newsAddCtrl',
            permission:'PERSONNEL_UPDATE',
            father:'PERSONNEL_MANAGER'
        })
        .state('newsupdate',{
            url:"/newsupdate/:uuid",
            templateUrl:'templates/news/news-update.html',
            controller:'newsUpdateCtrl',
            permission:'PERSONNEL_UPDATE',
            father:'PERSONNEL_MANAGER'
        })
        .state('orderlist',{
            url:"/orderlist",
            templateUrl:'templates/order/order-list.html',
            controller:'orderListCtrl',
            permission:'PERSONNEL_UPDATE',
            father:'PERSONNEL_MANAGER'
        })
        .state('orderupdate',{
            url:"/orderupdate/:uuid",
            templateUrl:'templates/order/order-update.html',
            controller:'orderUpdateCtrl',
            permission:'PERSONNEL_UPDATE',
            father:'PERSONNEL_MANAGER'
        })
        .state('productlist',{
            url:"/productlist",
            templateUrl:'templates/product/product-list.html',
            controller:'productListCtrl',
            permission:'PERSONNEL_UPDATE',
            father:'PERSONNEL_MANAGER'
        })
        .state('productadd',{
            url:"/productadd",
            templateUrl:'templates/product/product-add.html',
            controller:'productAddCtrl',
            permission:'PERSONNEL_UPDATE',
            father:'PERSONNEL_MANAGER'
        })
        .state('productupdate',{
            url:"/productupdate/:uuid",
            templateUrl:'templates/product/product-update.html',
            controller:'productUpdateCtrl',
            permission:'PERSONNEL_UPDATE',
            father:'PERSONNEL_MANAGER'
        })
        .state('companyupdate',{
            url:"/companyupdate",
            templateUrl:'templates/company/company-update.html',
            controller:'companyUpdateCtrl',
            permission:'PERSONNEL_UPDATE',
            father:'PERSONNEL_MANAGER'
        })
        .state('categorylist',{
            url:"/categorylist",
            templateUrl:'templates/category/category-list.html',
            controller:'categoryListCtrl',
            permission:'PERSONNEL_UPDATE',
            father:'PERSONNEL_MANAGER'
        })
        .state('categoryadd',{
            url:"/categoryadd",
            templateUrl:'templates/category/category-add.html',
            controller:'categoryAddCtrl',
            permission:'PERSONNEL_UPDATE',
            father:'PERSONNEL_MANAGER'
        })
        .state('categoryupdate',{
            url:"/categoryupdate/:uuid",
            templateUrl:'templates/category/category-update.html',
            controller:'categoryUpdateCtrl',
            permission:'PERSONNEL_UPDATE',
            father:'PERSONNEL_MANAGER'
        });
});