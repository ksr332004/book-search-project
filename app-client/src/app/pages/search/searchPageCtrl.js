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
          {label: 'Option 1', value: 1},
          {label: 'Option 2', value: 2},
          {label: 'Option 3', value: 3},
          {label: 'Option 4', value: 4}
        ];
        
        vm.withSearchItem = {};
        vm.selectWithSearchItems = [
          {label: 'Hot Dog, Fries and a Soda', value: 1},
          {label: 'Burger, Shake and a Smile', value: 2},
          {label: 'Sugar, Spice and all things nice', value: 3},
          {label: 'Baby Back Ribs', value: 4}
        ];

        $scope.peopleTableData = [
            {
              id: 1,
              firstName: 'Mark',
              lastName: 'Otto',
              username: '@mdo',
              email: 'mdo@gmail.com',
              age: '28',
              status: 'info'
            },
            {
              id: 2,
              firstName: 'Jacob',
              lastName: 'Thornton',
              username: '@fat',
              email: 'fat@yandex.ru',
              age: '45',
              status: 'primary'
            },
            {
              id: 3,
              firstName: 'Larry',
              lastName: 'Bird',
              username: '@twitter',
              email: 'twitter@outlook.com',
              age: '18',
              status: 'success'
            },
            {
              id: 4,
              firstName: 'John',
              lastName: 'Snow',
              username: '@snow',
              email: 'snow@gmail.com',
              age: '20',
              status: 'danger'
            },
            {
              id: 5,
              firstName: 'Jack',
              lastName: 'Sparrow',
              username: '@jack',
              email: 'jack@yandex.ru',
              age: '30',
              status: 'warning'
            }
        ];
        
    }

})();