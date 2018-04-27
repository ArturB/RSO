(function() {
    'use strict';

    angular
        .module('mygatewayApp')
        .controller('MyGroupDialogController', MyGroupDialogController);

    MyGroupDialogController.$inject = ['$timeout', '$scope', '$stateParams', '$uibModalInstance', 'entity', 'MyGroup'];

    function MyGroupDialogController ($timeout, $scope, $stateParams, $uibModalInstance, entity, MyGroup) {
        var vm = this;

        vm.myGroup = entity;
        vm.clear = clear;
        vm.save = save;

        $timeout(function (){
            angular.element('.form-group:eq(1)>input').focus();
        });

        function clear () {
            $uibModalInstance.dismiss('cancel');
        }

        function save () {
            vm.isSaving = true;
            if (vm.myGroup.id !== null) {
                MyGroup.update(vm.myGroup, onSaveSuccess, onSaveError);
            } else {
                MyGroup.save(vm.myGroup, onSaveSuccess, onSaveError);
            }
        }

        function onSaveSuccess (result) {
            $scope.$emit('mygatewayApp:myGroupUpdate', result);
            $uibModalInstance.close(result);
            vm.isSaving = false;
        }

        function onSaveError () {
            vm.isSaving = false;
        }


    }
})();
