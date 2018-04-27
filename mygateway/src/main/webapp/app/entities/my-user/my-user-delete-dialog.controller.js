(function() {
    'use strict';

    angular
        .module('mygatewayApp')
        .controller('MyUserDeleteController',MyUserDeleteController);

    MyUserDeleteController.$inject = ['$uibModalInstance', 'entity', 'MyUser'];

    function MyUserDeleteController($uibModalInstance, entity, MyUser) {
        var vm = this;

        vm.myUser = entity;
        vm.clear = clear;
        vm.confirmDelete = confirmDelete;

        function clear () {
            $uibModalInstance.dismiss('cancel');
        }

        function confirmDelete (id) {
            MyUser.delete({id: id},
                function () {
                    $uibModalInstance.close(true);
                });
        }
    }
})();
