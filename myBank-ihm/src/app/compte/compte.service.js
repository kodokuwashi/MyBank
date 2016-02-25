'use strict';


angular.module('MyBankApp')
  .factory('operationsFactory', function(Restangular) {
    var comptes = Restangular.one('operation');

    return {
      /**
       * Retourne le détail d'un tarif
       */
      "get": function(reference) {
        return Restangular.one('operations', reference).get();
      },

      /**
       * Permet de récupérer l'ensemble des tarifs
       * @param {number} offset - Offset de récupération
       * @param {number} limit - Nombre limite de tarifs à retourner
       */
      "tous": function(offset, limit, idCompte) {
        return comptes.get({offset: offset, limit: limit, idCompte: idCompte});
      }
    }
  });