(function() {
    'use strict';

    angular.module('pages.common.login')
    .controller('loginPageCtrl', loginPageCtrl);

    /** @ngInject */
    function loginPageCtrl($scope, $auth, $log, $state, toastr, MenuParsingService) {
        
        $auth.removeToken();

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

            var user = {
                  email    : $scope.email
                , password : $scope.password
            };

            $auth.login(user)
            .then(function(response) {
                if (response.status == 200) {
                    $auth.setToken(response.data.Authorization);
                    $scope.$emit('menuChangeForUser');
                    $state.go('search');
                    toastr.success("Welcome!");
                }
            })
            .catch(function(response) {
                toastr.error(response.status, "Permission denied!");
            });

        };

    }

})();

