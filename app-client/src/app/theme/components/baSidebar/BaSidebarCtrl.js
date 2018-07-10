/**
 * @author v.lugovksy
 * created on 16.12.2015
 */
(function () {
  'use strict';

  angular.module('BlurAdmin.theme.components')
    .controller('BaSidebarCtrl', BaSidebarCtrl);

  /** @ngInject */
  function BaSidebarCtrl($scope, $log, $rootScope, baSidebarService, SessionInfo, MenuParsingService) {

      $scope.initialize = function() {
    	  $log.debug('BaSidebarCtrl initialize.');
          var names = MenuParsingService.parse(SessionInfo.getUserInfo());
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
          if (baSidebarService.canSidebarBeHidden()) {
              baSidebarService.setMenuCollapsed(true);
          }
      });

      $scope.$on('menuChangeForUser', function() {
          $log.debug('menuChangeForUser is occurred.');
          var names = MenuParsingService.parse(SessionInfo.getUserInfo());
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
          $scope.menuItems = uniqueMenus;
      });

      $scope.$on('menuInitForUser', function() {
          $log.debug('menuInitForUser is occurred.');
          var names = MenuParsingService.parse(SessionInfo.getUserInfo());
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
          $scope.menuItems = uniqueMenus;
      });

      $scope.initialize();
  }
})();