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


        var vm = this;
        vm.disabled = undefined;

        vm.standardTarge = {};
        vm.standardSelectTarge = [
            {label:'제목', value:'title'},
            {label:'내용', value:'contents'}
        ];

        vm.searchQuery = "";

        vm.standardSort = {};
        vm.standardSelectSort = [
            {label:'최신순', value:'id,DESC'},
            {label:'과거순', value:'id,ASC'},
            {label:'카테고리순', value:'category,ASC'},
            {label:'제목순', value:'title,ASC'}
        ];
        vm.standardSort.selected = vm.standardSelectSort[0];

        $scope.hasNext = false;
        $scope.hasPrev = false;

        $scope.searchBookmarkList = function(currentPage) {
            var dataObject = {
                query: (vm.searchQuery == "") ? "none" : vm.searchQuery,
                target: (angular.isUndefined(vm.standardTarge.selected)) ? "title" : vm.standardTarge.selected.value,
                page: currentPage,
                sort: vm.standardSort.selected.value
            };
            console.log(dataObject);
            ApiService.get('/bookmark/view', dataObject).success(function(data, status) {
                if (data) {
                    console.log(data);
                    $scope.bookListTableData = data.content;
                    $scope.hasPrev = !data.first;
                    $scope.hasNext = !data.last;
                    $scope.currentPage = currentPage;
                } else {
                    $log.warn(data);
                }
            }).error(function(data, status, headers, config) {
                $log.error(status);
            });
        };

        $scope.searchBookmarkList(0);

        $scope.searchBookmarkListNext = function(nextPageNum) {
            var dataObject = {
                query: (vm.searchQuery == "") ? "none" : vm.searchQuery,
                target: (angular.isUndefined(vm.standardTarge.selected)) ? "title" : vm.standardTarge.selected.value,
                page: nextPageNum,
                sort: vm.standardSort.selected.value
            };
            console.log(dataObject);
            ApiService.get('/bookmark/view', dataObject).success(function(data, status) {
                if (data) {
                    console.log(data);
                    $scope.bookListTableData = data.content;
                    $scope.hasPrev = !data.first;
                    $scope.hasNext = !data.last;
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
                controller : 'bookmarkPageCtrl',
                size: size,
                resolve: {
                    items: function () {
                        console.log(book);
                        return book;
                    }
                }
            }).result.then(function(data) {
                console.log("get", data);
                ApiService.delete('/bookmark', data).success(function(data, status) {
                    console.log(data);
                    toastr.success("Deleted!");
                }).error(function(data, status, headers, config) {
                    $log.error(status);
                    toastr.error("ERROR!");
                });
            });
        };

    }
})();