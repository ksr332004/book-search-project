(function() {
    'use strict';

    angular.module('BlurAdmin.theme.components').controller('PageTopCtrl', PageTopCtrl);

    /** @ngInject */
    function PageTopCtrl($scope, $log, $rootScope) {
    	
    	// $scope.initialize = function() {
            
     //        // var session_data = SessionInfo.getUserInfo();
     //        // if (session_data.email == undefined) {
     //        //     $scope.name_email = '';
     //        // } else if (session_data.email != 'guest') {
     //        // 	$scope.name_email = session_data.name + "(" + session_data.email + ")";
     //        // }
            
     //    };

     //    $scope.$on('userInfoChangeForUser', function() {
     //        $scope.initialize();
     //    });
     //    $scope.$on('userStateChangeSuccess', function() {
     //        $scope.initialize();
     //    });
    }
})();