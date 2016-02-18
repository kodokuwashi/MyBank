angular.module('MyBankApp').controller('AccueilCtrl', function($scope, $modal, comptesFactory, pagination, Restangular) {
    $scope.comptes = []
    $scope.currentPage = 1;
    $scope.comptePerPage = pagination.itemsPerPage;
    $scope.totalComptes = 0;

    $scope.pageChanged = function() {
      loadPage($scope.currentPage);
    };

    var loadPage = function(pageNumber) {
      var promise = pagination.loadPage(comptesFactory.tous, pageNumber);
      promise.then(function(result){
          $scope.comptes = result.items
      });
      $scope.totalComptes = $scope.comptes.length;
    };

    /**
     * Création d'un tarif par défaut
     */
    $scope.creerCompte = function() {
      $modal.open({
        templateUrl: 'app/comptes/compte.creer.html',
        controller: 'CompteCreerCtrl'
      });
    };

    loadPage(1);
});