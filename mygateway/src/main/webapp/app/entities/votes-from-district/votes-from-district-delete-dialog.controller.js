(function() {
    'use strict';

    angular
        .module('mygatewayApp')
        .controller('VotesFromDistrictDeleteController',VotesFromDistrictDeleteController);

    VotesFromDistrictDeleteController.$inject = ['$uibModalInstance', 'entity', 'VotesFromDistrict'];

    function VotesFromDistrictDeleteController($uibModalInstance, entity, VotesFromDistrict) {
        var vm = this;

        vm.votesFromDistrict = entity;
        vm.clear = clear;
        vm.confirmDelete = confirmDelete;

        function clear () {
            $uibModalInstance.dismiss('cancel');
        }

        function confirmDelete (id) {
            VotesFromDistrict.delete({id: id},
                function () {
                    $uibModalInstance.close(true);
                });
        }
    }
})();
