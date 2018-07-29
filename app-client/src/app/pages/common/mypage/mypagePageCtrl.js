(function() {
    'use strict';

    angular.module('pages.common.mypage')
        .controller('mypagePageCtrl', mypagePageCtrl);

    /** @ngInject */
    function mypagePageCtrl($scope, $rootScope, $filter, $log, $state, $uibModal, toastr, ApiService) {
        $rootScope.$broadcast('validatingAccessTokens');

        $scope.getUserInfo = function() {
            ApiService.get('/user/info', null).success(function(data, status) {
                if (data) {
                    console.log(data);
                    $scope.email = data.email;
                    $scope.username = data.name;
                    $scope.registrationDate = data.registrationDate;
                } else {
                    $log.warn(data);
                }
            }).error(function(data, status, headers, config) {
                $log.error(status);
            });
        };
        $scope.getUserInfo();

        $scope.submitForm = function() {
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

            var userInfo = {
                  password    : $scope.password
                , name        : $scope.username
            };

            ApiService.post('/user/update', userInfo).success(function(data, status) {
                toastr.success("수정되었습니다.");
            }).error(function(data, status, headers, config) {
                $log.error(data);
                $log.error(status);
                $log.error(headers);
                $log.error(config);
            });
        };

        $scope.delete = function() {
            var page = 'app/pages/widgets/dangerModal.html';
            var message = '탈퇴하시겠습니까?';
            $scope.open(page, message);
        };

        $scope.open = function (page, message) {
            $uibModal.open({
                animation: true,
                templateUrl: page,
                controller : 'mypagePageCtrl',
                resolve: {
                    items: function () {
                        return message;
                    }
                }
            }).result.then(function() {
                ApiService.get('/user/update', null).success(function(data, status) {
                    $auth.logout();
                    $scope.$emit('menuChangeForUser');
                    $state.go('login');
                }).error(function(data, status, headers, config) {
                    $log.error(data);
                    $log.error(status);
                    $log.error(headers);
                    $log.error(config);
                });
            });
        };

    }

})();