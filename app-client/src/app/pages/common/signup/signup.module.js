(function() {
    'use strict';

    angular.module('pages.common.signup', []).config(routeConfig);

    /** @ngInject */
    function routeConfig($stateProvider) {
        $stateProvider.state('signup', {
            url : '/signup',
            title : '회원가입',
            templateUrl : 'app/pages/common/signup/signup.html',
            controller : 'signupPageCtrl',
            sidebarMeta : {
                icon : 'ion-compose',
                order : 1
            }
        });
    }

})();