(function() {
    'use strict';

    angular
        .module('mygatewayApp')
        .controller('HomeController', HomeController);

    HomeController.$inject = ['$scope', 'Principal', 'LoginService', '$state', 'Municipality', 'ElectoralDistrict', 'ElectoralPeriod'];

    function HomeController ($scope, Principal, LoginService, $state, Municipality, ElectoralDistrict, ElectoralPeriod) {
        var vm = this;

        vm.account = null;
        vm.isAuthenticated = null;
        vm.login = LoginService.open;
        vm.period = null;
        ElectoralPeriod.getCurrentPeriod().then(function (result){
            vm.period = result;
            vm.period.name = ElectoralPeriod.translatePeriod(result.name);
        });

        vm.register = register;
        $scope.$on('authenticationSuccess', function() {
            getAccount();
            $state.go($state.current, {}, {reload: true});
        });
        vm.changePeriod = changePeriod;

        getAccount();

        function getAccount() {
            Principal.identity().then(function (account) {
                vm.account = account;
                vm.isAuthenticated = Principal.isAuthenticated;

                if(vm.isAuthenticated && vm.account) {
                    var role = vm.account.role;
                    if (role === 'ROLE_GKW_MEMBER') {
                        vm.role = 'członka GKW';
                    } else if (role === 'ROLE_GKW_LEADER') {
                        vm.role = 'szefa GKW';
                    } else if (role === 'ROLE_OKW_MEMBER') {
                        vm.role = 'członka OKW';
                    } else if (role === 'ROLE_OKW_LEADER') {
                        vm.role = 'szefa OKW';
                    } else if (role === 'ROLE_ADMIN') {
                        vm.role = 'administratora';
                    }

                    if (vm.account.municipality_id) {
                        vm.municipality = Municipality.get({id: vm.account.municipality_id});
                    }
                    if (vm.account.electoralDistrictId) {
                        vm.electoralDistrict = ElectoralDistrict.get({id: vm.account.electoralDistrictId});
                    }
                }
            });
        }
        function register () {
            $state.go('register');
        }

        function changePeriod(periodName){
            ElectoralPeriod.setDebugPeriod(periodName);
            $state.reload();
        }

    }
})();
