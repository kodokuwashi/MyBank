'use strict';

angular.module('MyBankApp')
  .controller('CompteCreerCtrl', function ($scope, $uibModalInstance) {
    $scope.inError = false;

    $scope.ok = function () {
      $uibModalInstance.close();
    };

    $scope.cancel = function () {
      $uibModalInstance.dismiss('cancel');
    };
  });
