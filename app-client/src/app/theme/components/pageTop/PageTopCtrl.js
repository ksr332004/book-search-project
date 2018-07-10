(function() {
    'use strict';

    angular.module('BlurAdmin.theme.components').controller('PageTopCtrl', PageTopCtrl);

    /** @ngInject */
    function PageTopCtrl($scope, $log, $rootScope, SessionInfo) {
    	
    	$scope.initialize = function() {
            
            var session_data = SessionInfo.getUserInfo();
            if (session_data.email == undefined) {
                $scope.name_email = '';
            } else if (session_data.email != 'guest') {
            	$scope.name_email = session_data.name + "(" + session_data.email + ")";
            }  
            
        };

        $scope.$on('menuChangeForUser', function() {
            $scope.initialize();
        });
        $scope.$on('menuInitForUser', function() {
            $scope.initialize();
        });
        $scope.$on('stateChangeSuccess', function() {
            $scope.initialize();
        });
    }
})();