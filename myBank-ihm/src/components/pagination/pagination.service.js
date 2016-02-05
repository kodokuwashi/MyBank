'use strict';

angular.module('MyBankApp')
/**
 * Permet de gérer la pagination et le chargement de pages de manière unifiée
 * dans l'ensemble de l'application.
 *
 * Le paramètre "itemsPerPage" est configurable via injection du paginationProvider
 */
  .provider('pagination', function() {
    this.itemsPerPage = 30;

    this.$get = function() {
      return {
        /**
         * Nombre d'éléments par page
         */
        itemsPerPage: this.itemsPerPage,

        /**
         * Charge une page à partir d'une fonction de chargement
         *
         * @param callback fonction pour charger les éléments
         * @param pageNumber numéro de la page à charger
         * @returns éléments constituant la page
         */
        loadPage: function(callback, pageNumber) {
          var offset = (pageNumber - 1) * this.itemsPerPage;
          return callback(offset, this.itemsPerPage);
        }
      };
    }
  });