(function() {
    'use strict';

    angular.module('pages.search')
        .controller('searchPageCtrl', searchPageCtrl);

    /** @ngInject */
    function searchPageCtrl($scope, $rootScope, $auth, $log, $state, toastr, $uibModal, baProgressModal, editableOptions, editableThemes, ApiService, MenuParsingService) {

        $rootScope.$broadcast('validatingAccessTokens');

        String.isNullOrEmpty = function (value) {
            return (!value || value == undefined || value == "" || value.length == 0);
        }

        $scope.customizedIsNullOrEmpty = function (value) {
            return (String.isNullOrEmpty(value)) ? "-" : value;
        }

        var vm = this;
        vm.disabled = undefined;

        vm.standardItem = {};
        vm.standardSelectItems = [
            {label:'전체', value: 'all'},
            {label:'제목', value: 'title'},
            {label:'ISBN', value: 'isbn'},
            {label:'주제어', value: 'keyword'},
            {label:'목차', value: 'contents'},
            {label:'책소개', value: 'overview'},
            {label:'출판사', value: 'publisher'}
        ];

        vm.withSearchItem = {};
        vm.selectWithSearchItems = [];
        $scope.searchHistoryList = function() {
            ApiService.get('/search/history').success(function(data, status) {
                if (data) {
                    vm.selectWithSearchItems = data;
                } else {
                    $log.warn(data);
                }
            }).error(function(data, status, headers, config) {
                $log.error(data);
                $log.error(status);
                $log.error(headers);
                $log.error(config);
            });
        };

        $scope.searchHistoryList();


        $scope.hasPrev = false;
        $scope.hasNext = false;

        vm.searchQuery = "";

        $scope.bookListTableData = [];

        $scope.searchBookList = function(checkButton, currentPage) {
            var dataObject = {};
            if (checkButton == 'searchBtn') {
                if (String.isNullOrEmpty(vm.searchQuery)) {
                    toastr.error('Please fill out the form.', 'Error!');
                    return;
                }
                dataObject = {
                    query: vm.searchQuery,
                    target: (angular.isUndefined(vm.standardItem.selected)) ? 'all' : vm.standardItem.selected.value,
                    page: currentPage
                };
            } else {
                if (angular.isUndefined(vm.withSearchItem.selected)) {
                    toastr.error('Please fill out the form.', 'Error!');
                    return;
                }
                dataObject = {
                    query: vm.withSearchItem.selected.search,
                    target: vm.withSearchItem.selected.target,
                    page: currentPage
                };
            }

            console.log("searchBookList", dataObject);
            ApiService.post('/search/book', dataObject).success(function(data, status) {
                if (data) {
                    console.log("searchBookList > data", data);
                    $scope.bookListTableData = data.documents;
                    $scope.hasPrev = (currentPage == 1) ? false : true;
                    $scope.hasNext = !data.meta._end;
                    $scope.checkButton = checkButton;
                    $scope.currentPage = currentPage;
                    $scope.searchHistoryList();
                } else {
                    $log.warn(data);
                }
            }).error(function(data, status, headers, config) {
                $log.error(data);
                $log.error(status);
                $log.error(headers);
                $log.error(config);
            });
        };


        $scope.searchBookListNext = function(checkButton, nextPageNum) {
            var dataObject = {};
            if (checkButton == 'searchBtn') {
                dataObject = {
                    query: vm.searchQuery,
                    target: (angular.isUndefined(vm.standardItem.selected)) ? "all" : vm.standardItem.selected.value,
                    page: nextPageNum
                };
            } else {
                dataObject = {
                    query: vm.withSearchItem.selected.search,
                    target: vm.withSearchItem.selected.target,
                    page: nextPageNum
                };
            }
            console.log("searchBookListNext", dataObject);
            ApiService.post('/search/book', dataObject).success(function(data, status) {
                if (data) {
                    console.log("searchBookListNext > ", data);
                    $scope.bookListTableData = data.documents;
                    $scope.hasPrev = (nextPageNum == 1) ? false : true;
                    $scope.hasNext = !data.meta._end;
                    $scope.checkButton = checkButton;
                    $scope.currentPage = nextPageNum;
                    console.log($scope.currentPage);
                } else {
                    $log.warn(data);
                }
            }).error(function(data, status, headers, config) {
                $log.error(status);
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
                        console.log(book);
                        return book;
                    }
                }
            }).result.then(function(items) {
                // $uibModal.open({
                //   animation: true,
                //   templateUrl: 'app/pages/widgets/infoModal.html',
                //   controller : 'searchPageCtrl',
                //   resolve: {
                //     items: function () {
                //         return 'Do you want to add this book?';
                //     }
                //   }
                // }).result.then(function() {
                // });
                console.log("get", items);
                ApiService.post('/bookmark/add', items).success(function(data, status) {
                    if (data) {
                        console.log(data);
                        toastr.success("SAVED!");
                    }
                }).error(function(data, status, headers, config) {
                    $log.error(status);
                    toastr.error("ERROR!");
                });
            });
        };

    }

})();