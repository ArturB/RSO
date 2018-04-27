(function() {
    'use strict';

    angular
        .module('mygatewayApp')
        .controller('MunicipalityDialogController', MunicipalityDialogController);

    MunicipalityDialogController.$inject = ['$timeout', '$scope', '$stateParams', '$uibModalInstance', 'entity', 'Municipality'];

    function MunicipalityDialogController ($timeout, $scope, $stateParams, $uibModalInstance, entity, Municipality) {
        var vm = this;

        vm.municipality = entity;
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
            if (vm.municipality.id !== null) {
                Municipality.update(vm.municipality, onSaveSuccess, onSaveError);
            } else {
                Municipality.save(vm.municipality, onSaveSuccess, onSaveError);
            }
        }

        function onSaveSuccess (result) {
            $scope.$emit('mygatewayApp:municipalityUpdate', result);
            $uibModalInstance.close(result);
            vm.isSaving = false;
        }

        function onSaveError () {
            vm.isSaving = false;
        }


    }
})();
