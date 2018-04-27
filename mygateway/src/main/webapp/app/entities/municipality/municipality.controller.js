(function() {
    'use strict';

    angular
        .module('mygatewayApp')
        .controller('MunicipalityController', MunicipalityController);

    MunicipalityController.$inject = ['Municipality'];

    function MunicipalityController(Municipality) {

        var vm = this;

        vm.municipalities = [];

        loadAll();

        function loadAll() {
            Municipality.query(function(result) {
                vm.municipalities = result;
                vm.searchQuery = null;
            });
        }
    }
})();
