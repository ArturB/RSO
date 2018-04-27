/**
 * Created by defacto on 4/22/2018.
 */
(function() {
    'use strict';

    angular
        .module('mygatewayApp')
        .controller('VotesDesignationController', VotesDesignationController);

    VotesDesignationController.$inject = ['VotesDesignation', 'Candidate', '$q', 'Principal'];

    function VotesDesignationController(VotesDesignation, Candidate, $q, Principal) {
        var vm = this;

        vm.candidatesVotes = [];
        vm.invalidVotes = 0;

        loadAll();

        function loadAll() {
            Principal.hasAuthority('ROLE_GKW_MEMBER').then(function(authorityOk){
                if(!authorityOk) {
                    return $q.reject();
                }
                return Principal.identity();
            }).then(function(account){
                return Candidate.findByMunicipalityId({municipalityId:account.municipalityId}).$promise;
            }).then(function(result){
                angular.forEach(result, function(candidate){
                    vm.candidatesVotes.push({
                        candidate:candidate,
                        votesCount:0
                    });
                });
            });
        }
    }
})();
