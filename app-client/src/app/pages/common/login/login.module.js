(function() {
    'use strict';

    angular.module('pages.common.login', ['ui.select', 'ngSanitize']).config(routeConfig);

    /** @ngInject */
    function routeConfig($stateProvider) {
        $stateProvider.state('login', {
            url : '/login',
            title : '로그인',
            templateUrl : 'app/pages/common/login/login.html',
            controller : 'loginPageCtrl',
            sidebarMeta : {
                icon : 'ion-log-in',
                order : 0
            }
        });
    }

})();
