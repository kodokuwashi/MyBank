angular.module('MyBankApp').controller('ComptesCtrl', function($scope, $uibModal, comptesFactory, pagination, Restangular) {
    $scope.comptes = []
    $scope.currentPage = 1;
    $scope.totalComptes = 0;

    $scope.pageChanged = function() {
      loadPage($scope.currentPage);
    };

    var loadPage = function(pageNumber) {
      var promise = pagination.loadPage(comptesFactory.tous, pageNumber);
      promise.then(function(result){
        $scope.comptes = result.items
      })
    };

    /**
     * Création d'un tarif par défaut
     */
    $scope.creerCompte = function() {
      $uibModal.open({
        templateUrl: 'app/compte/compte.creer.html',
        controller: 'CompteCreerCtrl'
      });
    };

    loadPage(1);
});