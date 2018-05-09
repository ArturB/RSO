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

        loadAll();

        function loadAll() {
            Principal.identity().then(function (account) {
                CustomUser.findByMunicipalityId({municipalityId: account.municipalityId}, function (result) {
                    vm.customUsers = result;
                    vm.searchQuery = null;
                });
                return Municipality.get({id: account.municipalityId}, function (municipality) {
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

        vm.electoralDistrictNamesCache = [];
        vm.GetElectoralDistrictName = function (id) {
            if (!id) {
                return;
            }
            if (!vm.electoralDistrictNamesCache[id]) {
                vm.electoralDistrictNamesCache[id] = ElectoralDistrict.get({id: id});
            }
            return vm.electoralDistrictNamesCache[id].name;
        };

        vm.toEdNumber = function(id){
            if(id){
                return id;
            }else{
                return -1;
            }
        };

        $scope.minRole = function (arr) {
            return $filter('min')
            ($filter('map')(arr, 'role'));
        }
    }

})();
