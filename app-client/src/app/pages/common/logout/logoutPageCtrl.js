(function() {
    'use strict';

    angular.module('pages.common.logout')
    .controller('logoutPageCtrl', logoutPageCtrl);

    /** @ngInject */
    function logoutPageCtrl($scope, $filter, $auth, $log, $state, toastr, baSidebarService, MenuParsingService) {

        $scope.picture1 = $filter('appImage')('app/typography/typo03.png');

        $scope.logout = function() {
            $auth.logout();
            $scope.$emit('menuChangeForUser');
            $state.go('login');
        };

    }

})();
