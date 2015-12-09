'use strict'

var myBankApp = angular.module('MyBankApp', ['ui.router'])
.config(function ($stateProvider, $urlRouterProvider ) {

    $urlRouterProvider.otherwise('/accueil');

    $stateProvider
      .state('accueil', {
        url: '/accueil',
        views: {
          'main': {
            templateUrl: 'app/accueil/accueil.html',
            controller: 'AccueilCtrl'
          }
        }
      })
      .state('comptes', {
        url: '/comptes',
        views: {
          'main': {
            templateUrl: 'app/comptes/comptes.html',
            controller: 'ComptesCtrl'
          }
        }
      })
      .state('operations', {
        url: '/operations',
        views: {
          'main': {
            templateUrl: 'app/operations/operations.html',
            controller: 'OperationsCtrl'
          }
        }
      });
  })
  .run(function($rootScope) {

    $rootScope.message = 'Angular Works!'
  })
;