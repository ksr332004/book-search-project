(function() {
    'use strict';

    angular.module('pages.bookmark', ['ui.select', 'ngSanitize'])
        .config(routeConfig);

    /** @ngInject */
    function routeConfig($stateProvider) {
        $stateProvider.state('bookmark', {
            url : '/bookmark',
            title : '북마크',
            templateUrl : 'app/pages/bookmark/bookmark.html',
            controller : 'bookmarkPageCtrl',
            controllerAs: 'vm',
            sidebarMeta : {
                icon : 'ion-compose',
                order : 1
            }
        });
    }

})();