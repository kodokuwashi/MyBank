angular.module('MyBankApp').controller('CompteCtrl', function($scope, $stateParams, comptesFactory, pagination, Restangular) {
    $scope.Operations = []
    $scope.currentPage = 1;
    $scope.totalOperations = 0;

    $scope.idCompte = $stateParams.idCompte

    $scope.pageChanged = function() {
      loadPage($scope.currentPage);
    };

    var loadPage = function(pageNumber) {
      var promise = pagination.loadPage(OperationsFactory.tous, pageNumber);
      promise.then(function(result){
        $scope.operations = result.items
      })
    };

    loadPage(1);
});