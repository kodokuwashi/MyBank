'use strict';

angular.module('MyBankApp')
  .controller('CompteCreerCtrl', function ($scope, $modalInstance, $state, comptes) {
    $scope.inError = false;

    $scope.cancel = function () {
      $modalInstance.dismiss();
    };

    $scope.create = function () {
      var compte = {
        libelle: $scope.libelle,
        proprietaire: $scope.proprietaire,
        solde: parseFloat($scope.solde)
      };

      comptes.creerCompte(compte).then(
        function () {
          $scope.inError = false;
          $modalInstance.dismiss();
          $state.go('compte', {libelle: $scope.libelle});
        },
        function (response) {
          $scope.creationError = response.data;
          $scope.inError = true;
        }
      );
    };
  });