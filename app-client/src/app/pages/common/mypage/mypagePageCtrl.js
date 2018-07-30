(function() {
    'use strict';

    angular.module('pages.common.mypage')
        .controller('mypagePageCtrl', mypagePageCtrl);

    /** @ngInject */
    function mypagePageCtrl($scope, $rootScope, $filter, $log, $state, $auth, $uibModal, toastr, ApiService) {
        $rootScope.$broadcast('validatingAccessTokens');

        $scope.getUserInfo = function() {
            ApiService.get('/user/info', null).success(function(data, status) {
                if (status == 200) {
                    $scope.email = data.email;
                    $scope.username = data.name;
                    $scope.registrationDate = data.registrationDate;
                } else {
                    toastr.error("정보를 불러오는 중 에러가 발생했습니다.");
                }
            }).error(function(data, status, headers, config) {
                $log.error("[/user/info] " + status, data);
                if (status == 400) {
                    toastr.error("잘못된 요청을 했습니다.");
                } else if (status == 401 || status == 403) {
                    toastr.error("접근 권한이 없습니다.");
                    $rootScope.$broadcast('initializeAccessTokens');
                } else {
                    toastr.error(status);
                }
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
                if (status == 200) {
                    toastr.success("수정되었습니다.");
                } else {
                    toastr.error("수정 중 에러가 발생했습니다.");
                }
            }).error(function(data, status, headers, config) {
                $log.error("[/user/update] " + status, data);
                if (status == 400) {
                    toastr.error("잘못된 요청을 했습니다.");
                } else if (status == 401 || status == 403) {
                    toastr.error("접근 권한이 없습니다.");
                    $rootScope.$broadcast('initializeAccessTokens');
                } else {
                    toastr.error(status);
                }
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
                ApiService.get('/user/delete', null).success(function(data, status) {
                    if (status == 200) {
                        $auth.logout();
                        $scope.$emit('menuChangeForUser');
                        $state.go('login');
                    } else {
                        toastr.error("탈퇴 중 에러가 발생했습니다.");
                    }
                }).error(function(data, status, headers, config) {
                    $log.error("[/user/delete] " + status, data);
                    if (status == 400) {
                        toastr.error("잘못된 요청을 했습니다.");
                    } else if (status == 401 || status == 403) {
                        toastr.error("접근 권한이 없습니다.");
                        $rootScope.$broadcast('initializeAccessTokens');
                    } else {
                        toastr.error(status);
                    }
                });
            });
        };

    }

})();