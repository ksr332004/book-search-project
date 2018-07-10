/**
 * @author v.lugovsky created on 16.12.2015
 */
(function() {
    'use strict';

    angular.module('pages.common.login', []).config(routeConfig);

    /** @ngInject */
    function routeConfig($stateProvider) {
        $stateProvider.state('login', {
            url : '/login',
            title : 'Log in',
            templateUrl : 'app/pages/common/login/login.html',
            controller : 'loginPageCtrl',
            sidebarMeta : {
                icon : 'ion-log-in',
                order : 0
            }
        });
    }

})();
