(function () {
  'use strict';

  angular.module('pages.intro', []).config(routeConfig);

  /** @ngInject */
  function routeConfig($stateProvider) {
    $stateProvider.state('intro', {
          url: '/intro',
          title: 'Book Search',
          templateUrl: 'app/pages/intro/intro.html',
          controller: 'introPageCtrl',
          sidebarMeta: {
            icon: 'fa fa-book',
            order: 100
          }
        });
  }

})();
