(function() {
    'use strict';

    angular
        .module('mygatewayApp')
        .controller('ElectoralDistrictController', ElectoralDistrictController);

    ElectoralDistrictController.$inject = ['ElectoralDistrict', 'Principal', '$q'];

    function ElectoralDistrictController(ElectoralDistrict, Principal, $q) {

        var vm = this;

        vm.electoralDistricts = [];

        loadAll();

        function loadAll() {
            Principal.hasAuthority('ROLE_ADMIN').then(function(authorityOk){
                if(!authorityOk) {
                    return $q.reject();
                }
                return ElectoralDistrict.query();
            }).then(function(result){
                vm.electoralDistricts = result;
                vm.searchQuery = null;
            });

            Principal.hasAuthority('ROLE_GKW_MEMBER').then(function(authorityOk){
                if(!authorityOk) {
                    return $q.reject();
                }
                return Principal.identity();
            }).then(function(account){
                return ElectoralDistrict.findByMunicipalityId({municipalityId:account.municipalityId}).$promise;
            }).then(function(result){
                vm.electoralDistricts = result;
                vm.searchQuery = null;
            });

        }
    }
})();
