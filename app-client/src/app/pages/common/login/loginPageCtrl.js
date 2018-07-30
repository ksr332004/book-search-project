(function() {
    'use strict';

    angular.module('pages.common.login')
    .controller('loginPageCtrl', loginPageCtrl);

    /** @ngInject */
    function loginPageCtrl($scope, $rootScope, $auth, $log, $state, toastr) {
        
        $auth.removeToken();

        $scope.login = function() {
            if ($scope.email == undefined) {
                toastr.error("아이디(이메일)을 입력해주세요.");
                return;
            }
            if ($scope.password == undefined) {
                toastr.error("비밀번호를 입력해주세요.");
                return;
            }
            if ($scope.password.replace(/^\s+|\s+$/g, '') == '') {
                toastr.error("잘못된 비밀번호 형식입니다.");
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
                    toastr.success("로그인 되었습니다.");
                }
            })
            .catch(function(response) {
                toastr.error("아이디와 비밀번호를 다시 확인해주세요.", "접근 할 수 없는 사용자 입니다.");
            });

        };

    }

})();

