(function() {
    'use strict';

    angular.module('pages.common.login')
    .controller('loginPageCtrl', loginPageCtrl);

    /** @ngInject */
    function loginPageCtrl($scope, $log, $state, toastr, MessageService, MenuParsingService, SessionInfo) {

        $scope.login = function() {
            if ($scope.email == undefined) {
                toastr.error("Please fill out the form.", 'Error!');
                return;
            }
            if ($scope.password == undefined) {
                toastr.error("Please fill out the form.", 'Error!');
                return;
            }
            if ($scope.password.replace(/^\s+|\s+$/g, '') == '') {
                toastr.error("Please check your password.", 'Error!');
                return;
            }

            var dataObject = {
                  email    : $scope.email
                , password : $scope.password
            };

            MessageService.interactWithServer('/auth/login', dataObject).success(function(data, status) {
                $log.debug(data);
                if (data) {
                    // if (data.status == 'S') {
                    //     var menuList = MenuParsingService.parse(data.resultdata);
                    //     SessionInfo.setUserInfo(data.resultdata);
                    //     $scope.$emit('menuChangeForUser');
                    //     $state.go('intro');
                    // } else {
                    //     toastr.error(data.message);
                    // }
                } else {
                    $log.warn(data);
                }
            }).error(function(data, status, headers, config) {
                toastr.error(status, "Permission denied!");
            });
        };

    }

})();

