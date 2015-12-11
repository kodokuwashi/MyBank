'use strict';

var myBankApp = angular.module('MyBankApp', ['ui.router', 'restangular', 'MyBankApp.config'])
.config(function ($stateProvider, $urlRouterProvider, RestangularProvider, API_ENDPOINT) {

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

    // Configuration de l'URL des ressources exposées par les services RBRSVA
    RestangularProvider.setBaseUrl(API_ENDPOINT);
    RestangularProvider.setDefaultHttpFields({withCredentials: true});
})
.run(function($rootScope) {

  $rootScope.message = 'Angular Works!'
});
