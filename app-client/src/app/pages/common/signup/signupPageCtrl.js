(function() {
    'use strict';

    angular.module('pages.common.signup')
        .controller('signupPageCtrl', signupPageCtrl);

    /** @ngInject */
    function signupPageCtrl($scope, $auth, $filter, $log, $state, $uibModal, toastr, baSidebarService) {

        $scope.submitSignupForm = function() {
            if ($scope.email == undefined) {
                toastr.error("Please fill out the form.", 'Error!');
                return;
            }
            if ($scope.username == undefined) {
                toastr.error("Please fill out the form.", 'Error!');
                return;
            }
            if ($scope.password == undefined) {
                toastr.error("Please fill out the form.", 'Error!');
                return;
            }
            if ($scope.confirmPassword == undefined) {
                toastr.error("Please fill out the form.", 'Error!');
                return;
            }
            if ($scope.password != $scope.confirmPassword) {
                toastr.error("Please check your password.", 'Error!');
                return;
            }

            var user = {
                email       : $scope.email
                , password    : $scope.password
                , name        : $scope.username
            };

            $auth.removeToken();
            $auth.signup(user)
                .then(function(response) {
                    console.log('then', response);
                    if (response.status == 201) {
                        toastr.success("Welcome to Book Search service.");
                        $state.go('login');
                    } else {
                        $log.warn(response);
                        toastr.error("(" + response.status + ") " + response.data);
                    }
                })
                .catch(function(response) {
                    console.log('catch', response);
                    toastr.error(
                        (angular.isUndefined(response.data.errors[0]) || response.data.errors[0] == null) ? "" : response.data.errors[0]
                        , "Error!");
                });

        };

    }

})();