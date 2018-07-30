(function() {
    'use strict';

    angular.module('BlurAdmin.theme.components').controller('PageTopCtrl', PageTopCtrl);

    /** @ngInject */
    function PageTopCtrl($scope, $auth) {
        $scope.pageTopMenu = function(flag) {
            if (angular.isUndefined($auth.getToken())
                || $auth.getToken() == null
                || $auth.getToken() === "") {
                flag = flag && false;
            }
            return flag;
        }
    }
})();