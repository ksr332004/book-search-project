(function() {
    'use strict';

    angular.module('pages.bookmark')
        .controller('bookmarkPageCtrl', bookmarkPageCtrl);

    /** @ngInject */
    function bookmarkPageCtrl($scope, $rootScope, $auth, $log, $state, toastr, $uibModal, baProgressModal, editableOptions, editableThemes, ApiService) {

        $rootScope.$broadcast('validatingAccessTokens');

        String.isNullOrEmpty = function (value) {
            return (!value || value == undefined || value === "" || value.length == 0);
        };
        $scope.customizedIsNullOrEmpty = function (value) {
            return (String.isNullOrEmpty(value)) ? "-" : value;
        };
        $scope.thumbnailIsNullOrEmpty = function (value) {
            return (String.isNullOrEmpty(value)) ? "assets/img/no_thumbnail_img.gif" : value;
        };


        var vm = this;
        vm.disabled = undefined;

        vm.targetItem = {};
        vm.targetList = [
            {label:'제목', value:'title'},
            {label:'내용', value:'contents'}
        ];
        vm.targetItem.selected = vm.targetList[0];

        vm.sortItem = {};
        vm.sortList = [
            {label:'최신순', value:'id,DESC'},
            {label:'과거순', value:'id,ASC'},
            {label:'카테고리순', value:'category,ASC'},
            {label:'제목순', value:'title,ASC'}
        ];
        vm.sortItem.selected = vm.sortList[0];

        vm.searchQuery = "";
        vm.bookListTableData = [];
        $scope.currentPage = 0;
        $scope.hasNext = false;
        $scope.hasPrev = false;

        $scope.clickSearchButton = function() {
            if (String.isNullOrEmpty(vm.searchQuery)) {
                toastr.warning("검색어를 입력해주세요.");
                return;
            }
            $scope.searchBookmarkList(0);
        }

        $scope.searchBookmarkList = function(currentPage) {
            var dataObject = {
                query: (vm.searchQuery == "") ? "none" : vm.searchQuery,
                target: vm.targetItem.selected.value,
                page: currentPage,
                sort: vm.sortItem.selected.value
            };
            ApiService.get('/bookmark/view', dataObject).success(function(data, status) {
                if (status == 200) {
                    vm.bookListTableData = data.content;
                    $scope.hasPrev = !data.first;
                    $scope.hasNext = !data.last;
                    $scope.currentPage = currentPage;
                } else {
                    toastr.error("검색 중 에러가 발생했습니다.");
                }
            }).error(function(data, status, headers, config) {
                $log.error("[/bookmark/view] " + status, data);
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
        $scope.searchBookmarkList(0);

        $scope.open = function (page, size, book) {
            var modal = $uibModal.open({
                animation: true,
                templateUrl: page,
                controller : 'bookmarkPageCtrl',
                size: size,
                resolve: {
                    items: function () {
                        return book;
                    }
                }
            }).result.then(function(data) {
                ApiService.delete('/bookmark', data).success(function(data, status) {
                    if (status == 200) {
                        toastr.success("삭제되었습니다.");
                        $scope.searchBookmarkList($scope.currentPage);
                    } else {
                        toastr.error("삭제 중 에러가 발생했습니다.");
                    }
                }).error(function(data, status, headers, config) {
                    $log.error("[/bookmark/delete] " + status, data);
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