(function() {
    'use strict';

    angular.module('pages.search')
        .controller('searchPageCtrl', searchPageCtrl);

    /** @ngInject */
    function searchPageCtrl($scope, $rootScope, $auth, $log, $state, toastr, $uibModal, baProgressModal, editableOptions, editableThemes, ApiService) {

        $rootScope.$broadcast('validatingAccessTokens');

        String.isNullOrEmpty = function (value) {
            return (!value || value == undefined || value == "" || value.length == 0);
        }
        $scope.customizedIsNullOrEmpty = function (value) {
            return (String.isNullOrEmpty(value)) ? "-" : value;
        }
        $scope.thumbnailIsNullOrEmpty = function (value) {
            return (String.isNullOrEmpty(value)) ? "assets/img/no_thumbnail_img.gif" : value;
        };

        var vm = this;
        vm.disabled = undefined;
        
        vm.targetItem = {};
        vm.targetList = [
            {label:'전체', value: 'all'},
            {label:'제목', value: 'title'},
            {label:'책소개', value: 'overview'},
            {label:'주제어', value: 'keyword'},
            {label:'출판사', value: 'publisher'},
            {label:'목차', value: 'contents'},
            {label:'ISBN', value: 'isbn'}
        ];
        vm.targetItem.selected = vm.targetList[0];

        vm.sortItem = {};
        vm.sortList = [
            {label:'정확도순', value:'accuracy'},
            {label:'최신순', value:'recency'},
            {label:'판매량순', value:'sales'}
        ];
        vm.sortItem.selected = vm.sortList[0];

        vm.historyItem = {};
        vm.historyList = [];
        $scope.searchHistoryList = function() {
            ApiService.get('/search/history').success(function(data, status) {
                if (status == 200) {
                    vm.historyList = data;
                } else {
                    toastr.error("검색 중 에러가 발생했습니다.");
                }
            }).error(function(data, status, headers) {
                $log.error("[/search/history] " + status, data);
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
        $scope.searchHistoryList();

        vm.searchQuery = "";
        vm.bookListTableData = [];
        $scope.hasPrev = false;
        $scope.hasNext = false;

        $scope.clickSearchButton = function() {
            if (String.isNullOrEmpty(vm.searchQuery)) {
                toastr.warning("검색어를 입력해주세요.");
                return;
            }
            $scope.searchBookList(1);
        }

        $scope.searchBookList = function(currentPage) {
            if (String.isNullOrEmpty(vm.searchQuery)) {
                return;
            }

            var dataObject = {};
            dataObject = {
                query: vm.searchQuery,
                target: vm.targetItem.selected.value,
                page: currentPage,
                sort: vm.sortItem.selected.value
            };
            ApiService.post('/search/book', dataObject).success(function(data, status) {
                if (status == 200) {
                    vm.bookListTableData = data.documents;
                    $scope.hasPrev = (currentPage == 1) ? false : true;
                    $scope.hasNext = !data.meta._end;
                    $scope.currentPage = currentPage;
                    $scope.searchHistoryList();
                } else {
                    toastr.error("검색 중 에러가 발생했습니다.");
                }
            }).error(function(data, status, headers, config) {
                $log.error("[/search/book] " + status, data);
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

        $scope.open = function (page, size, book) {
            var modal = $uibModal.open({
                animation: true,
                templateUrl: page,
                controller : 'searchPageCtrl',
                size: size,
                resolve: {
                    items: function () {
                        return book;
                    }
                }
            }).result.then(function(items) {
                ApiService.post('/bookmark/add', items).success(function(data, status) {
                    if (status == 201) {
                        toastr.success("저장되었습니다.");
                    } else {
                        toastr.error("저장 중 에러가 발생했습니다.");
                    }
                }).error(function(data, status, headers, config) {
                    $log.error("[/bookmark/add] " + status, data);
                    if (status == 400) {
                        toastr.error("잘못된 요청을 했습니다.");
                    } else if (status == 401 || status == 403) {
                        toastr.error("접근 권한이 없습니다.");
                        $rootScope.$broadcast('initializeAccessTokens');
                    } else if (status == 409) {
                        toastr.warning("이미 북마크에 저장된 책 입니다.");
                    } else {
                        toastr.error(status);
                    }
                });
            });
        };

    }

})();