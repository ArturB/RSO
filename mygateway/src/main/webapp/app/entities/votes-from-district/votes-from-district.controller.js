(function() {
    'use strict';

    angular
        .module('mygatewayApp')
        .controller('VotesFromDistrictController', VotesFromDistrictController);

    VotesFromDistrictController.$inject = ['VotesFromDistrict'];

    function VotesFromDistrictController(VotesFromDistrict) {

        var vm = this;

        vm.votesFromDistricts = [];

        loadAll();

        function loadAll() {
            VotesFromDistrict.query(function(result) {
                vm.votesFromDistricts = result;
                vm.searchQuery = null;
            });
        }
    }
})();
