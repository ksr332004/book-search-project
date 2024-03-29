(function () {
    'use strict';

    angular.module('BlurAdmin.pages', [
        'ui.router',

        'pages.intro',

        'pages.bookmark',
        'pages.search',

        'pages.common.login',
        'pages.common.logout',
        'pages.common.signup',
        'pages.common.mypage'
    ])
        .config(routeConfig);

    /** @ngInject */
    function routeConfig($urlRouterProvider) {
        $urlRouterProvider.otherwise('/login');
    }

})();
