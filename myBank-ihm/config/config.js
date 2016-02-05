/***************
 * Ce fichier correspond à la configuration de développement pour l'IHM
 * AngularJS du RBRSVA.
 *
 * La configuration "réelle" est générée lors du déploiement par Ansible
 ***************/
angular.module("MyBankApp.config", [])
  .constant("API_ENDPOINT", "http://127.0.0.1:8080/myBank-services/api");
