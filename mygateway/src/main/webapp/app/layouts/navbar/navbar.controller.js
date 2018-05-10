(function() {
    'use strict';

    angular
        .module('mygatewayApp')
        .controller('NavbarController', NavbarController);

    NavbarController.$inject = ['$state', 'Auth', 'Principal', 'ProfileService', 'LoginService', 'Municipality', '$location'];

    function NavbarController ($state, Auth, Principal, ProfileService, LoginService, Municipality, $location) {
        var vm = this;

        vm.isNavbarCollapsed = true;
        vm.isAuthenticated = Principal.isAuthenticated;

        ProfileService.getProfileInfo().then(function(response) {
            vm.inProduction = response.inProduction;
            vm.swaggerEnabled = response.swaggerEnabled;
        });

        vm.login = login;
        vm.logout = logout;
        vm.toggleNavbar = toggleNavbar;
        vm.collapseNavbar = collapseNavbar;
        vm.$state = $state;

        function login() {
            collapseNavbar();
            LoginService.open();
            vm.isAuthenticated = Principal.isAuthenticated();
        }

        function logout() {
            collapseNavbar();
            Auth.logout();
            // $state.go('home', {reload:true});
            $location.path('/');
            vm.isAuthenticated = Principal.isAuthenticated();
        }

        function toggleNavbar() {
            vm.isNavbarCollapsed = !vm.isNavbarCollapsed;
        }

        function collapseNavbar() {
            vm.isNavbarCollapsed = true;
        }

        vm.account = null;
        vm.isAuthenticated = null;
        vm.municipality = null;
        Principal.identity().then(function(account) {
            vm.account = account;
            vm.isAuthenticated = Principal.isAuthenticated();
            if(account){
                if(account.role !== 'ROLE_ADMIN'){
                    return Municipality.get({id : vm.account.municipalityId }).$promise;
                }
            }
        }).then(function( municipality){
            vm.municipality = municipality;
        });

    }
})();
