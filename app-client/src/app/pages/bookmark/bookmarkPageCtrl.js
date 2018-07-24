(function() {
    'use strict';

    angular.module('pages.bookmark')
    .controller('bookmarkPageCtrl', bookmarkPageCtrl);

    /** @ngInject */
    function bookmarkPageCtrl($scope, $rootScope, $auth, $log, $state, toastr, $uibModal, baProgressModal, editableOptions, editableThemes, ApiService, MenuParsingService) {
    	
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
        {label:'제목', value: 'title'},
        {label:'목차', value: 'contents'}
      ];

      $scope.hasPrev = false;
      $scope.hasNext = false;

      vm.searchQuery = "";

      $scope.searchBookmarkList = function(currentPage) {
        var dataObject = {
          query: (vm.searchQuery == "") ? "none" : vm.searchQuery,
          target: (angular.isUndefined(vm.standardItem.selected)) ? "title" : vm.standardItem.selected.value,
          page: currentPage
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

      $scope.searchBookmarkList();
        

      $scope.searchBookmarkListNext = function(nextPageNum) {
        var dataObject = {
          query: (vm.searchQuery == "") ? "none" : vm.searchQuery,
          target: (angular.isUndefined(vm.standardItem.selected)) ? "title" : vm.standardItem.selected.value,
          page: nextPageNum
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
          ApiService.delete('/bookmark/delet', data).success(function(data, status) {
            if (data) {
                console.log(data);
                toastr.success("SAVED!");
            } else {
                $log.warn(data);
                toastr.error("FAIL!");
            }
          }).error(function(data, status, headers, config) {
              $log.error(status);
              toastr.error("ERROR!");
          });
        });
      };
    	
    }

})();