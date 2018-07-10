(function() {
    'use strict';

    angular.module('pages.common.login')
    .controller('loginPageCtrl', loginPageCtrl);

    /** @ngInject */
    function loginPageCtrl($scope, $log, $state, toastr, MessageService, MenuParsingService, SessionInfo) {

        $scope.login = function() {
            if ($scope.userid == undefined || $scope.userid.replace(/^\s+|\s+$/g, '') == '') {
                toastr.error("Please fill out the form.", 'Error!');
                return;
            }
            if ($scope.userpw == undefined || $scope.userpw.replace(/^\s+|\s+$/g, '') == '') {
                toastr.error("Please fill out the form.", 'Error!');
                return;
            }

            var dataObject = {
                user_id : $scope.userid,
                user_pw : $scope.userpw
            };
        };

    }

})();

