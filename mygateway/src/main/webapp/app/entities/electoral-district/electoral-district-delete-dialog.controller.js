(function() {
    'use strict';

    angular
        .module('mygatewayApp')
        .controller('ElectoralDistrictDeleteController',ElectoralDistrictDeleteController);

    ElectoralDistrictDeleteController.$inject = ['$uibModalInstance', 'entity', 'ElectoralDistrict'];

    function ElectoralDistrictDeleteController($uibModalInstance, entity, ElectoralDistrict) {
        var vm = this;

        vm.electoralDistrict = entity;
        vm.clear = clear;
        vm.confirmDelete = confirmDelete;

        function clear () {
            $uibModalInstance.dismiss('cancel');
        }

        function confirmDelete (id) {
            ElectoralDistrict.delete({id: id},
                function () {
                    $uibModalInstance.close(true);
                });
        }
    }
})();
