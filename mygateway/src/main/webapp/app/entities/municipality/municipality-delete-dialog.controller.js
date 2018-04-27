(function() {
    'use strict';

    angular
        .module('mygatewayApp')
        .controller('MunicipalityDeleteController',MunicipalityDeleteController);

    MunicipalityDeleteController.$inject = ['$uibModalInstance', 'entity', 'Municipality'];

    function MunicipalityDeleteController($uibModalInstance, entity, Municipality) {
        var vm = this;

        vm.municipality = entity;
        vm.clear = clear;
        vm.confirmDelete = confirmDelete;

        function clear () {
            $uibModalInstance.dismiss('cancel');
        }

        function confirmDelete (id) {
            Municipality.delete({id: id},
                function () {
                    $uibModalInstance.close(true);
                });
        }
    }
})();
