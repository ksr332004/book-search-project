(function() {
    'use strict';

    angular.module('pages.common.logout')
        .controller('logoutPageCtrl', logoutPageCtrl);

    /** @ngInject */
    function logoutPageCtrl($scope, $rootScope, $auth, $state, $uibModal) {

        $rootScope.$broadcast('validatingAccessTokens');

        $scope.logout = function() {
            var page = 'app/pages/widgets/warningModal.html';
            var message = '로그아웃하시겠습니까?';
            $scope.open(page, message);
        };

        $scope.open = function (page, message) {
            $uibModal.open({
                animation: true,
                templateUrl: page,
                controller : 'logoutPageCtrl',
                resolve: {
                    items: function () {
                        return message;
                    }
                }
            }).result.then(function() {
                $auth.logout();
                $scope.$emit('menuChangeForUser');
                $state.go('login');
            });
        };

    }

})();
