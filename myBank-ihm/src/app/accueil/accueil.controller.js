angular.module('MyBankApp').controller('AccueilCtrl', function($scope, comptesFactory, pagination, Restangular) {
    $scope.comptes = []
    $scope.currentPage = 1;
    $scope.comptePerPage = pagination.itemsPerPage;
    $scope.totalComptes = 0;

    $scope.pageChanged = function() {
      loadPage($scope.currentPage);
    };

    var loadPage = function(pageNumber) {
      var promise = pagination.loadPage(comptesFactory.tous, pageNumber);
      $scope.comptes = promise
      $scope.totalComptes = $scope.comptes.length;
    };

    loadPage(1);/*
    Restangular.one('compte').get().then(function(result){
      $scope.comptes = result.items
    });*/
});