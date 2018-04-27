(function() {
    'use strict';

    angular
        .module('mygatewayApp')
        .controller('MyGroupDeleteController',MyGroupDeleteController);

    MyGroupDeleteController.$inject = ['$uibModalInstance', 'entity', 'MyGroup'];

    function MyGroupDeleteController($uibModalInstance, entity, MyGroup) {
        var vm = this;

        vm.myGroup = entity;
        vm.clear = clear;
        vm.confirmDelete = confirmDelete;

        function clear () {
            $uibModalInstance.dismiss('cancel');
        }

        function confirmDelete (id) {
            MyGroup.delete({id: id},
                function () {
                    $uibModalInstance.close(true);
                });
        }
    }
})();
