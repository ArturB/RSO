(function() {
    'use strict';

    angular
        .module('mygatewayApp')
        .controller('MyGroupController', MyGroupController);

    MyGroupController.$inject = ['MyGroup'];

    function MyGroupController(MyGroup) {

        var vm = this;

        vm.myGroups = [];

        loadAll();

        function loadAll() {
            MyGroup.query(function(result) {
                vm.myGroups = result;
                vm.searchQuery = null;
            });
        }
    }
})();
