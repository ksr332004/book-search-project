(function() {
    'use strict';

    angular.module('pages.search')
    .controller('searchPageCtrl', searchPageCtrl);

    /** @ngInject */
    function searchPageCtrl($scope, $log, $state, toastr, editableOptions, editableThemes, MessageService, MenuParsingService, SessionInfo) {
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
        vm.selectWithSearchItems = [
          {query: 'Hot Dog, Fries and a Soda'},
          {query: 'Burger, Shake and a Smile'}
        ];

        $scope.hasPrev = false;
        $scope.hasNext = false;

        vm.searchQuery = "";

        $scope.searchBookList = function(currentPage) {
          var dataObject = {
            query: vm.searchQuery,
            target: (angular.isUndefined(vm.standardItem.selected)) ? "" : vm.standardItem.selected.value,
            page: currentPage
          };
          console.log(dataObject);
          MessageService.interactWithServer('/search/book', dataObject).success(function(data, status) {
            if (data) {
              console.log(data);
              $scope.bookListTableData = data.documents;
              $scope.hasPrev = (currentPage == 1) ? false : true;
              $scope.hasNext = !data.meta._end;
              $scope.currentPage = currentPage;
            } else {
              $log.warn(data);
            }
          }).error(function(data, status, headers, config) {
            $log.error(status);
          });
        };
        

        $scope.searchBookListNext = function(nextPageNum) {
          var dataObject = {
            query: vm.searchQuery,
            target: (angular.isUndefined(vm.standardItem.selected)) ? "" : vm.standardItem.selected.value,
            page: nextPageNum
          };
          console.log(dataObject);
            MessageService.interactWithServer('/search/book', dataObject).success(function(data, status) {
                if (data) {
                    console.log(data);
                  $scope.bookListTableData = data.documents;
                  $scope.hasPrev = (nextPageNum == 1) ? false : true;
                  $scope.hasNext = !data.meta._end;
                  $scope.currentPage = nextPageNum;
                  console.log($scope.currentPage);
                } else {
                    $log.warn(data);
                }
            }).error(function(data, status, headers, config) {
                $log.error(status);
            });
        };

    }

})();