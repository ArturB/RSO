(function() {
    'use strict';

    angular
        .module('mygatewayApp')
        .controller('PartyDeleteController',PartyDeleteController);

    PartyDeleteController.$inject = ['$uibModalInstance', 'entity', 'Party'];

    function PartyDeleteController($uibModalInstance, entity, Party) {
        var vm = this;

        vm.party = entity;
        vm.clear = clear;
        vm.confirmDelete = confirmDelete;

        function clear () {
            $uibModalInstance.dismiss('cancel');
        }

        function confirmDelete (id) {
            Party.delete({id: id},
                function () {
                    $uibModalInstance.close(true);
                });
        }
    }
})();
