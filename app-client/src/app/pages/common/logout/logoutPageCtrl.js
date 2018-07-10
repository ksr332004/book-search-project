(function() {
    'use strict';

    angular.module('pages.common.logout')
    .controller('logoutPageCtrl', logoutPageCtrl);

    /** @ngInject */
    function logoutPageCtrl($scope, $filter, $location, $log, $state, toastr, baSidebarService, MessageService, SessionInfo) {

        $scope.picture1 = $filter('appImage')('app/typography/typo03.png');

    }

})();
