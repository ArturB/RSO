(function() {
    'use strict';

    angular
        .module('mygatewayApp')
        .controller('CandidateController', CandidateController);

    CandidateController.$inject = ['$state', 'Candidate', 'Principal', '$q', 'ParseLinks', 'paginationConstants', 'pagingParams',
        'Municipality', 'Party'];

    function CandidateController($state, Candidate, Principal, $q, ParseLinks,  paginationConstants, pagingParams, Municipality, Party) {
        var vm = this;

        vm.candidates = [];
        vm.itemsPerPage = paginationConstants.itemsPerPage;

        loadAll();

        function loadAll() {
            Principal.hasAuthority('ROLE_ADMIN').then(function(authorityOk){
                if(!authorityOk) {
                    return $q.reject();
                }
                return Candidate.query(
                    {
                        page: pagingParams.page - 1,
                        size: vm.itemsPerPage
                    }, onSuccess, onError);
            });

            function onSuccess(data, headers) {
                vm.links = ParseLinks.parse(headers('link'));
                vm.totalItems = headers('X-Total-Count');
                vm.queryCount = vm.totalItems;
                vm.candidates= data;
                vm.page = pagingParams.page;


                angular.forEach(vm.candidates, function(candidate){
                    candidate.municipality = Municipality.get({id:candidate.municipality_id});
                    candidate.party = Party.get({id:candidate.party_id});
                });
            }
            function onError(error) {
                AlertService.error(error.data.message);
            }

            Principal.hasAuthority('ROLE_GKW_MEMBER').then(function(authorityOk){
                if(!authorityOk) {
                    return $q.reject();
                }
                return Principal.identity();
            }).then(function(account){
                return Candidate.findByMunicipalityId({municipalityId:account.municipality_id}).$promise;
            }).then(function(result){
                vm.candidates = result;
            });
        }

        vm.transition = function() {
            $state.transitionTo($state.$current, {
                page: vm.page
            });
        }
    }
})();
