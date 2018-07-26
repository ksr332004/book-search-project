/**
 * @author v.lugovksy
 * created on 16.12.2015
 */
(function () {
  'use strict';

  angular.module('BlurAdmin.theme.components')
    .controller('BaSidebarCtrl', BaSidebarCtrl);

  /** @ngInject */
  function BaSidebarCtrl($scope, $log, $rootScope, baSidebarService, MenuParsingService) {

      $scope.initialize = function() {
          var names = MenuParsingService.parse();
          var uniqueNames = {};
          var uniqueMenus = [];
          var len = names.length;
          for(var i=0;i<len;i++) {
              var order = names[i]['order'];
              if(uniqueNames['' + order] == undefined) {
                  uniqueMenus.push(names[i]);
                  uniqueNames['' + order] = 'Y';
              }
          }
          $scope.menuItems = uniqueMenus[0];
          if($scope.menuItems != undefined) {
              $scope.defaultSidebarState = $scope.menuItems[0].stateRef;
          }
      };

      $scope.hoverItem = function($event) {
          $scope.showHoverElem = true;
          $scope.hoverElemHeight = $event.currentTarget.clientHeight;
          var menuTopValue = 66;
          $scope.hoverElemTop = $event.currentTarget.getBoundingClientRect().top - menuTopValue;
      };

      $scope.$on('$stateChangeSuccess', function() {
        $log.debug('stateChangeSuccess is occurred.');
          if (baSidebarService.canSidebarBeHidden()) {
              baSidebarService.setMenuCollapsed(true);
          }
      });

      $scope.$on('menuChangeForUser', function() {
          $scope.initialize();
      });

      $scope.initialize();
  }
})();