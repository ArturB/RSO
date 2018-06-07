(function() {
    'use strict';

    angular
        .module('mygatewayApp')
        .controller('ElectoralDistrictController', ElectoralDistrictController);

    ElectoralDistrictController.$inject = ['ElectoralDistrict', 'Principal', '$q', 'Municipality'];

    function ElectoralDistrictController(ElectoralDistrict, Principal, $q, Municipality) {

        var vm = this;

        vm.electoralDistricts = [];

        loadAll();

        function loadAll() {
            Principal.hasAuthority('ROLE_ADMIN').then(function(authorityOk){
                if(!authorityOk) {
                    return $q.reject();
                }
                return ElectoralDistrict.query().$promise;
            }).then(function(result){
                vm.electoralDistricts = [];
                angular.forEach(result, function(district){
                    district.municipality = Municipality.get({id:district.municipality_id});
                    vm.electoralDistricts.push(district);
                });
                vm.searchQuery = null;
            });

            Principal.hasAuthority('ROLE_GKW_MEMBER').then(function(authorityOk){
                if(!authorityOk) {
                    return $q.reject();
                }
                return Principal.identity();
            }).then(function(account){
                return ElectoralDistrict.findByMunicipalityId({municipalityId:account.municipality_id}).$promise;
            }).then(function(result){
                vm.electoralDistricts = [];
                angular.forEach(result, function(district){
                    district.municipality = Municipality.get({id:district.municipality_id});
                    vm.electoralDistricts.push(district);
                });
                vm.searchQuery = null;
            });

        }
    }
})();
