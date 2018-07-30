(function() {
    'use strict';

    angular.module('pages.common.signup')
        .controller('signupPageCtrl', signupPageCtrl);

    /** @ngInject */
    function signupPageCtrl($scope, $auth, $filter, $log, $state, $uibModal, toastr, baSidebarService) {

        $scope.submitSignupForm = function() {
            if ($scope.email == undefined) {
                toastr.error("아이디(이메일)을 입력해주세요.");
                return;
            }
            if ($scope.username == undefined) {
                toastr.error("이름을 입력해주세요.");
                return;
            }
            if ($scope.password == undefined) {
                toastr.error("비밀번호를 입력해주세요.");
                return;
            }
            if ($scope.confirmPassword == undefined) {
                toastr.error("비밀번호 확인을 입력해주세요.");
                return;
            }
            if ($scope.password != $scope.confirmPassword) {
                toastr.error("비밀번호를 다시 확인해주세요.");
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
                    if (response.status === 201) {
                        toastr.success("가입해주셔서 감사합니다.");
                        $state.go('login');
                    } else {
                        $log.warn(response);
                        toastr.error("가입정보를 다시 확인해주시기 바랍니다.");
                    }
                })
                .catch(function(response) {
                    console.log('catch', response);
                    if (response.status === 409) {
                        toastr.error("이미 가입된 사용자 입니다.");
                    } else {
                        toastr.error(
                            (angular.isUndefined(response.data.errors[0]) || response.data.errors[0] == null) ? "" : response.data.errors[0]
                            , "Error!");
                    }
                });

        };

    }

})();