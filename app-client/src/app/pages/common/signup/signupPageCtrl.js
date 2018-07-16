(function() {
    'use strict';

    angular.module('pages.common.signup')
    .controller('signupPageCtrl', signupPageCtrl);

    /** @ngInject */
    function signupPageCtrl($scope, $filter, $log, $state, $uibModal, toastr, baSidebarService, MessageService, SessionInfo) {

      var toastrObject = {
        "autoDismiss": false,
        "positionClass": "toast-top-center",
        "type": "success",
        "timeOut": "5000",
        "extendedTimeOut": "2000",
        "allowHtml": false,
        "closeButton": false,
        "tapToDismiss": true,
        "progressBar": false,
        "newestOnTop": true,
        "maxOpened": 0,
        "preventDuplicates": false,
        "preventOpenDuplicates": false
      };

    	$scope.submitSignupForm = function() {
        if ($scope.email == undefined) {
              toastr.error("Please fill out the form.", 'Error!');
              return;
        }
        if ($scope.username == undefined) {
              toastr.error("Please fill out the form.", 'Error!');
              return;
        }
        if ($scope.password == undefined) {
              toastr.error("Please fill out the form.", 'Error!');
              return;
        }
        if ($scope.confirmPassword == undefined) {
              toastr.error("Please fill out the form.", 'Error!');
              return;
        }
        if ($scope.password != $scope.confirmPassword) {
            toastr.error("Please check your password.", 'Error!');
            return;
        }

        var dataObject = {
              email       : $scope.email  
            , password    : $scope.password
            , name        : $scope.username
        };

        MessageService.interactWithServer('/auth/signup', dataObject).success(function(data, status) {
            if (status == 201) {
                toastr.success('Welcome!', toastrObject);
                $state.go('login');
            } else {
                $log.warn("(" + status + ") " + data);
                toastr.error("(" + status + ") " + data);
            }
        }).error(function(data, status, headers, config) {
        	  $log.debug("data : ", data);
            
            toastr.error(
              (angular.isUndefined(data.errors[0])) ? "" : data.errors[0]
              , "Error!"
              , toastrObject
            );

        });
      };
        
    }

})();