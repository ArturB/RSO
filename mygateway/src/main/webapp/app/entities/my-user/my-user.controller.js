(function() {
    'use strict';

    angular
        .module('mygatewayApp')
        .controller('MyUserController', MyUserController);

    MyUserController.$inject = ['MyUser'];

    function MyUserController(MyUser) {

        var vm = this;

        vm.myUsers = [];

        loadAll();

        function loadAll() {
            MyUser.query(function(result) {
                vm.myUsers = result;
                vm.searchQuery = null;
            });
        }
    }
})();
