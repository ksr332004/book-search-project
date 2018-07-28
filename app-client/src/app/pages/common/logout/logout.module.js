/**
 * @author v.lugovsky
 * created on 16.12.2015
 */
(function () {
    'use strict';

    angular.module('pages.common.logout', [])
        .config(routeConfig);

    /** @ngInject */
    function routeConfig($stateProvider) {
        $stateProvider
            .state('logout', {
                url: '/logout',
                title: 'Log out',
                templateUrl: 'app/pages/common/logout/logout.html',
                controller: 'logoutPageCtrl',
            });
    }

})();
