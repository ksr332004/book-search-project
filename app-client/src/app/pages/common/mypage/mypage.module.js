(function() {
    'use strict';

    angular.module('pages.common.mypage', []).config(routeConfig);

    /** @ngInject */
    function routeConfig($stateProvider) {
        $stateProvider.state('mypage', {
            url : '/mypage',
            title : '내정보',
            templateUrl : 'app/pages/common/mypage/mypage.html',
            controller : 'mypagePageCtrl',
            sidebarMeta : {
                icon : 'ion-compose',
                order : 1
            }
        });
    }

})();