(function() {
    'use strict';

    angular
        .module('mygatewayApp')
        .controller('CustomUserDisableController',CustomUserDisableController);

    CustomUserDisableController.$inject = ['$uibModalInstance', 'entity', 'MyUser'];

    function CustomUserDisableController($uibModalInstance, entity, MyUser) {
        var vm = this;

        vm.customUser = entity;
        vm.clear = clear;
        vm.confirmDelete = confirmDelete;

        function clear () {
            $uibModalInstance.dismiss('cancel');
        }

        function confirmDelete (id) {
            MyUser.disable({id: id},
                function () {
                    $uibModalInstance.close(true);
                });
        }
    }
})();
