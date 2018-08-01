'use strict';

var appClient = angular.module('BlurAdmin', [
    'ngAnimate',
    'ui.bootstrap',
    'ui.sortable',
    'ui.router',
    'ngTouch',
    'toastr',
    'smart-table',
    "xeditable",
    'ui.slimscroll',
    'ngJsTree',
    'angular-progress-button-styles',

    'BlurAdmin.theme',
    'BlurAdmin.pages',
    'ngRoute',

    'satellizer',
    'ui.select',
    'ngSanitize'
])
    .config(function($authProvider) {
        $authProvider.storageType = 'localStorage';
        $authProvider.tokenHeader = 'Authorization';
        $authProvider.tokenType = 'Bearer';
        $authProvider.baseUrl = 'http://localhost:8080';
        $authProvider.loginUrl = '/api/auth/login';
        $authProvider.signupUrl = '/api/auth/signup';
    })
    .directive('ngHtml', [ '$compile', function($compile) {
        return function(scope, elem, attrs) {
            if (attrs.ngHtml) {
                elem.html(scope.$eval(attrs.ngHtml));
                $compile(elem.contents())(scope);
            }
            scope.$watch(attrs.ngHtml, function(newValue, oldValue) {
                if (newValue && newValue !== oldValue) {
                    elem.html(newValue);
                    $compile(elem.contents())(scope);
                }
            });
        };
    }])
    .factory('ApiService', function($http) {

        var factory = {};
        factory.post = function(serviceName, getData) {
            var http = $http({
                method : 'POST',
                url : 'http://localhost:8080/api' + serviceName,
                data : getData,
                dataType : "json",
                headers : {
                    'Content-Type' : 'application/json; charset=utf-8;'
                }
            });
            return http;
        };
        factory.get = function(serviceName, getParams) {
            var http = $http({
                method : 'GET',
                url : 'http://localhost:8080/api' + serviceName,
                params: getParams,
                dataType : "json",
                headers : {
                    'Content-Type' : 'application/json; charset=utf-8;'
                }
            });
            return http;
        };
        factory.put = function(serviceName, getData) {
            var http = $http({
                method : 'PUT',
                url : 'http://localhost:8080/api' + serviceName,
                data : getData,
                dataType : "json",
                headers : {
                    'Content-Type' : 'application/json; charset=utf-8;'
                }
            });
            return http;
        };
        factory.delete = function(serviceName, getPath) {
            var http = $http({
                method : 'DELETE',
                url : 'http://localhost:8080/api' + serviceName + "/" + getPath,
                dataType : "json",
                headers : {
                    'Content-Type' : 'application/json; charset=utf-8;'
                }
            });
            return http;
        };

        return factory;
    })
    .factory('MenuParsingService', function($auth, $log) {

        var factory = {};
        factory.parse = function() {
            var menuList = [];
            var menu = [];

            $log.debug('MenuParsingService is occurred.');

            if (!$auth.isAuthenticated()) {
                menu = [
                    {
                        title : '로그인',
                        templateUrl : 'app/pages/common/login/login.html',
                        controller : 'loginPageCtrl',
                        url : '/login',
                        stateRef : 'login',
                        icon : 'ion-log-in',
                        order : 1,
                        sidebarMeta : {
                            icon : 'ion-log-in',
                            order : 1
                        }
                    }
                ];
                menuList.push(menu);
            } else {
                menu = [
                    {
                        title : '책 검색',
                        templateUrl : 'app/pages/search/search.html',
                        controller : 'searchPageCtrl',
                        url : '/search',
                        stateRef : 'search',
                        icon : 'ion-search',
                        order : 1,
                        sidebarMeta : {
                            icon : 'ion-search',
                            order : 1,
                        }
                    },
                    {
                        title : '북마크',
                        templateUrl : 'app/pages/bookmark/bookmark.html',
                        controller : 'bookmarkPageCtrl',
                        url : '/bookmark',
                        stateRef : 'bookmark',
                        icon : 'ion-heart',
                        order : 2,
                        sidebarMeta : {
                            icon : 'ion-heart',
                            order : 2,
                        }
                    },
                    {
                        title : '내정보',
                        templateUrl : 'app/pages/common/mypage/mypage.html',
                        controller : 'mypagePageCtrl',
                        url : '/mypage',
                        stateRef : 'mypage',
                        icon : 'ion-person',
                        order : 3,
                        sidebarMeta : {
                            icon : 'ion-person',
                            order : 1,
                        }
                    },
                    {
                        title : '로그아웃',
                        templateUrl : 'app/pages/common/logout/logout.html',
                        controller : 'logoutPageCtrl',
                        url : '/logout',
                        stateRef : 'logout',
                        icon : 'ion-log-out',
                        order : 4,
                        sidebarMeta : {
                            icon : 'ion-log-out',
                            order : 1,
                        }
                    }
                ];
                menuList.push(menu);
            }
            return menuList;
        };
        return factory;
    })
    .run(function($rootScope, $state, $auth, $log) {
        $log.debug('================================================================');
        $log.debug('run is started.....');
        $log.debug('================================================================');

        $rootScope.$on('validatingAccessTokens', function() {
            $log.debug('validatingAccessTokens is occurred.');

            $rootScope.$broadcast('menuChangeForUser');
            if (!$auth.isAuthenticated()) {
                $state.go('login');
            }
        });

        $rootScope.$on('initializeAccessTokens', function() {
            $log.debug('initializeAccessTokens is occurred.');
            $auth.logout();
            $rootScope.$broadcast('menuChangeForUser');
            $state.go('login');
        });
    });