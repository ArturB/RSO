(function() {
    'use strict';

    angular
        .module('mygatewayApp')
        .controller('CandidateController', CandidateController);

    CandidateController.$inject = ['Candidate', 'Principal', '$q'];

    function CandidateController(Candidate, Principal, $q) {

        var vm = this;

        vm.candidates = [];

        loadAll();

        function loadAll() {
            Principal.hasAuthority('ROLE_ADMIN').then(function(authorityOk){
                if(!authorityOk) {
                    return $q.reject();
                }
                return Candidate.query();
            }).then(function(result){
                vm.candidates = result;
                vm.searchQuery = null;
            });

            Principal.hasAuthority('ROLE_GKW_MEMBER').then(function(authorityOk){
                if(!authorityOk) {
                    return $q.reject();
                }
                return Principal.identity();
            }).then(function(account){
                return Candidate.findByMunicipalityId({municipalityId:account.municipalityId}).$promise;
            }).then(function(result){
                vm.candidates = result;
                vm.searchQuery = null;
            });

        }
    }
})();
