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
  
  'ui.select',
  'ngSanitize'
  
])
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
.factory('MessageService', function($http) {
    var factory = {};
    factory.interactWithServer = function(serviceName, conditionData) {
        return $http({
            method : 'POST',
            url : 'http://localhost:8080/api' + serviceName,
            data : conditionData,
            dataType : "json",
            headers : {
                'Content-Type' : 'application/json; charset=utf-8;'
            }
        });
    };
    return factory;
})
.factory('MenuParsingService', function($http, $log) {
    var factory = {};
    factory.parse = function(userInfo) {
        var menuList = [];

        if (userInfo.id == undefined) {
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
        return menuList;
    };
    return factory;
})
.service('SessionInfo', function($rootScope) {
    this.localStorageKey = "__SESSION_INFO";
    try {
        $rootScope.currentUserInfo = JSON.parse(localStorage.getItem(this.localStorageKey) || "{}");
    } catch (e) {
        $rootScope.currentUserInfo = {};
    }
    this.getUserInfo = function() {
        return $rootScope.currentUserInfo;
    };
    this.setUserInfo = function(info) {
        angular.extend($rootScope.currentUserInfo, info);
        localStorage.setItem(this.localStorageKey, JSON.stringify($rootScope.currentUserInfo));
    };
    this.reset = function() {
        $rootScope.currentUserInfo = {};
        localStorage.setItem(this.localStorageKey, {});
    };
}) 
.service('SessionService', function($http, $log, $q, $rootScope, SessionInfo, baSidebarService, $state) {
    var userIsAuthenticated = false;
    $log.debug('SessionService is started.....');
    this.isLogon = function() {
        var deferred = $q.defer();
        var dataObject = {};
        if (SessionInfo.getUserInfo().email != undefined) {
            dataObject.uid = SessionInfo.getUserInfo().email;
        } else {
            dataObject.uid = 'guest';
            $rootScope.$emit('menuInitializer');
            return deferred.promise;
        }
        $log.debug(dataObject);
        $http({
            method : 'POST',
            url : 'http://localhost:8080/api/user/' + dataObject.uid,
            data : dataObject,
            dataType : "json",
            headers : {
                'Content-Type' : 'application/json; charset=utf-8'
            }
        })
        .success(function(data, status, headers, config) {
            $log.debug(data);
            if (data != undefined) {
                SessionInfo.reset();
                SessionInfo.setUserInfo(data);
                userIsAuthenticated = true;
                $log.debug('menuInitializer is called.....');
                $rootScope.$emit('menuInitializer');
            } else {
                userIsAuthenticated = false;
            }
            deferred.resolve(userIsAuthenticated);
        })
        .error(function(data, status, headers, config) {
            userIsAuthenticated = false;
            deferred.reject(userIsAuthenticated);
        });
        return deferred.promise;
    };
})
.run(function($rootScope, $state, $log, SessionService, SessionInfo, MenuParsingService) {
    $log.debug('run is started.....');
    $log.debug('================================================================');
    $log.debug(SessionService.isLogon());
    $log.debug('================================================================');

    $rootScope.$on('menuInitializer', function() {
        $log.debug('menuInitializer is occurred.');
        var menu_list = MenuParsingService.parse(SessionInfo.getUserInfo());
        if (menu_list.length == 1) {
            menu_list = menu_list[0];
        }
        $log.debug('menuInitializer : ', menu_list);
        if (menu_list[0].stateRef == 'login') {
            $state.go('login');
        }
        $rootScope.$broadcast('menuChangeForUser');
    });
    
    /*
     * $log.debug(SessionInfo.getMenuInfo());
     * $log.debug(SessionService.isLogon()); $log.debug($state.get());
     */
});