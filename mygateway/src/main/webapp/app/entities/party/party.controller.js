(function() {
    'use strict';

    angular
        .module('mygatewayApp')
        .controller('PartyController', PartyController);

    PartyController.$inject = ['Party'];

    function PartyController(Party) {

        var vm = this;

        vm.parties = [];

        loadAll();

        function loadAll() {
            Party.query(function(result) {
                vm.parties = result;
                vm.searchQuery = null;
            });
        }
    }
})();
