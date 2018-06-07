(function () {
    'use strict';

    angular
        .module('mygatewayApp')
        .controller('CustomUserController', CustomUserController);

    CustomUserController.$inject = ['CustomUser', 'ElectoralDistrict', '$scope', '$filter', 'Principal', 'Municipality'];

    function CustomUserController(CustomUser, ElectoralDistrict, $scope, $filter, Principal, Municipality) {

        var vm = this;

        vm.customUsers = [];
        vm.municipality = [];
        vm.userIsGkwLeader = false;
        vm.account = null;

        loadAll();

        function loadAll() {
            Principal.identity().then(function (account) {
                vm.account = account;
                CustomUser.findByMunicipalityId({municipalityId: account.municipality_id}, function (result) {
                    vm.customUsers = result;
                    vm.searchQuery = null;
                });
                return Municipality.get({id: account.municipality_id}, function (municipality) {
                    vm.municipality = municipality;
                });
            });
        }

        vm.translateRole = function (role) {
            if (role === 'ROLE_GKW_MEMBER') {
                return 'Członek GKW';
            } else if (role === 'ROLE_GKW_LEADER') {
                return 'Szef GKW';
            } else if (role === 'ROLE_OKW_MEMBER') {
                return 'Członek OKW';
            } else if (role === 'ROLE_OKW_LEADER') {
                return 'Szef OKW';
            }
            return 'E712 Nieznana rola ' + role;
        };

        vm.electoral_district_namesCache = [];
        vm.GetElectoralDistrictName = function (id) {
            if (!id) {
                return;
            }
            if (!vm.electoral_district_namesCache[id]) {
                vm.electoral_district_namesCache[id] = ElectoralDistrict.get({id: id});
            }
            return vm.electoral_district_namesCache[id].name;
        };

        $scope.minRole = function (arr) {
            return $filter('min')
            ($filter('map')(arr, 'role'));
        };

        vm.canDisableUser = function(customUser){
            if(customUser.id === vm.account.id ){
                return false;
            }
            if(customUser.authorities.indexOf('ROLE_GKW_LEADER') !== -1){
                return false;
            }
            if(customUser.authorities.indexOf('ROLE_GKW_MEMBER') !== -1 ){
                if(vm.account.authorities.indexOf('ROLE_GKW_LEADER') === -1){
                    return false;
                }
            }
            return true;
        };

    }

})();
