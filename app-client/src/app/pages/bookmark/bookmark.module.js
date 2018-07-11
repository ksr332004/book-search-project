(function() {
    'use strict';

    angular.module('pages.bookmark', ['ui.select', 'ngSanitize'])
        .config(routeConfig);

    /** @ngInject */
    function routeConfig($stateProvider) {
        $stateProvider.state('bookmark', {
            url : '/bookmark',
            title : 'Bookmark',
            templateUrl : 'app/pages/bookmark/bookmark.html',
            controller : 'bookmarkPageCtrl',
            sidebarMeta : {
                icon : 'ion-compose',
                order : 1
            }
        });
    }

})();