(function () {
  'use strict';

  angular.module('BlurAdmin.pages', [
    'ui.router',

    'pages.intro',

//    'pages.manage',
//    'pages.mypage',

    'pages.common.login',
    'pages.common.logout',
    'pages.common.signup'
  ])
      .config(routeConfig);

  /** @ngInject */
  function routeConfig($urlRouterProvider, baSidebarServiceProvider) {
    $urlRouterProvider.otherwise('/intro');
  }

})();
