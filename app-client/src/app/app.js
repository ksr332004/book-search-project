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
.factory('ApiService', function($http, $auth) {

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
    factory.delete = function(serviceName, getData) {
        var http = $http({
            method : 'DELETE',
            url : 'http://localhost:8080/api' + serviceName,
            data : getData,
            dataType : "json",
            headers : {
                'Content-Type' : 'application/json; charset=utf-8;'
            }
        });
        return http;
    };

    return factory;
})
.factory('MenuParsingService', function($http, $auth, $log) {

    var factory = {};
    factory.parse = function() {
        var menuList = [];

        $log.debug('MenuParsingService is occurred.');
        $log.debug('token : ', $auth.getToken());

        if (angular.isUndefined($auth.getToken())
            || $auth.getToken() == null
            || $auth.getToken() == "") {
            var menu = [
                {
                    title : 'Log in',
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
            var menu = [
                {
                    title : 'Book Search',
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
                    title : 'Bookmark',
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
                    title : 'Log out',
                    templateUrl : 'app/pages/common/logout/logout.html',
                    controller : 'logoutPageCtrl',
                    url : '/logout',
                    stateRef : 'logout',
                    icon : 'ion-log-out',
                    order : 3,
                    sidebarMeta : {
                        icon : 'ion-log-out',
                        order : 1,
                    }
                }
            ];
            menuList.push(menu);
        }
        $log.debug('parse : ', menuList);
        return menuList;
    };
    return factory;
})
.run(function($rootScope, $window, $state, $auth, $log, MenuParsingService) {
    $log.debug('================================================================');
    $log.debug('run is started.....');
    $log.debug('================================================================');

    $rootScope.$on('validatingAccessTokens', function() {
        $log.debug('validatingAccessTokens is occurred.');
        if (angular.isUndefined($auth.getToken())
            || $auth.getToken() == null
            || $auth.getToken() == "") {
            $window.location.href = 'login';
            $rootScope.$broadcast('menuChangeForUser');
        }
    });
});