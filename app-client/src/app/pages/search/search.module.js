(function() {
    'use strict';

    angular.module('pages.search', ['ui.select', 'ngSanitize'])
        .config(routeConfig);

    /** @ngInject */
    function routeConfig($stateProvider) {
        $stateProvider.state('search', {
            url : '/search',
            title : '책 검색',
            templateUrl : 'app/pages/search/search.html',
            controller : 'searchPageCtrl',
            controllerAs: 'vm',
            sidebarMeta : {
                icon : 'ion-compose',
                order : 1
            }
        });
    }

})();