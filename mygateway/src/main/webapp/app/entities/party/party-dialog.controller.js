(function() {
    'use strict';

    angular
        .module('mygatewayApp')
        .controller('PartyDialogController', PartyDialogController);

    PartyDialogController.$inject = ['$timeout', '$scope', '$stateParams', '$uibModalInstance', 'entity', 'Party'];

    function PartyDialogController ($timeout, $scope, $stateParams, $uibModalInstance, entity, Party) {
        var vm = this;

        vm.party = entity;
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
            if (vm.party.id !== null) {
                Party.update(vm.party, onSaveSuccess, onSaveError);
            } else {
                Party.save(vm.party, onSaveSuccess, onSaveError);
            }
        }

        function onSaveSuccess (result) {
            $scope.$emit('mygatewayApp:partyUpdate', result);
            $uibModalInstance.close(result);
            vm.isSaving = false;
        }

        function onSaveError () {
            vm.isSaving = false;
        }


    }
})();
