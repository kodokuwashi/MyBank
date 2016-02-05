'use strict';


angular.module('MyBankApp')
  .factory('comptesFactory', function(Restangular) {
    var comptes = Restangular.one('compte');

    return {
      /**
       * Retourne le détail d'un tarif
       */
      "get": function(reference) {
        return Restangular.one('compte', reference).get();
      },

      /**
       * Permet de récupérer l'ensemble des tarifs
       * @param {number} offset - Offset de récupération
       * @param {number} limit - Nombre limite de tarifs à retourner
       */
      "tous": function(offset, limit) {
        return comptes.get({offset: offset, limit: limit});
      }
    }
  });