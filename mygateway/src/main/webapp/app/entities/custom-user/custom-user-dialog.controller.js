(function() {
    'use strict';

    angular
        .module('mygatewayApp')
        .controller('CustomUserDialogController', CustomUserDialogController);

    CustomUserDialogController.$inject = ['$timeout', '$scope', '$stateParams', '$uibModalInstance', 'entity', 'CustomUser', 'ElectoralDistrict', 'Municipality'];

    function CustomUserDialogController ($timeout, $scope, $stateParams, $uibModalInstance, entity, CustomUser, ElectoralDistrict, Municipality) {
        var vm = this;

        vm.customUser = entity;
        vm.clear = clear;
        vm.save = save;
        vm.electoraldistricts = ElectoralDistrict.query();
        vm.municipalities = Municipality.query();

        $timeout(function (){
            angular.element('.form-group:eq(1)>input').focus();
        });

        function clear () {
            $uibModalInstance.dismiss('cancel');
        }

        function save () {
            vm.isSaving = true;
            if (vm.customUser.id !== null) {
                CustomUser.update(vm.customUser, onSaveSuccess, onSaveError);
            } else {
                CustomUser.save(vm.customUser, onSaveSuccess, onSaveError);
            }
        }

        function onSaveSuccess (result) {
            $scope.$emit('mygatewayApp:customUserUpdate', result);
            $uibModalInstance.close(result);
            vm.isSaving = false;
        }

        function onSaveError () {
            vm.isSaving = false;
        }


    }
})();
