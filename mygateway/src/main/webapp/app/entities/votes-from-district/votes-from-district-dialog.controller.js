(function() {
    'use strict';

    angular
        .module('mygatewayApp')
        .controller('VotesFromDistrictDialogController', VotesFromDistrictDialogController);

    VotesFromDistrictDialogController.$inject = ['$timeout', '$scope', '$stateParams', '$uibModalInstance', 'entity', 'VotesFromDistrict', 'ElectoralDistrict', 'Candidate', 'MyUser'];

    function VotesFromDistrictDialogController ($timeout, $scope, $stateParams, $uibModalInstance, entity, VotesFromDistrict, ElectoralDistrict, Candidate, MyUser) {
        var vm = this;

        vm.votesFromDistrict = entity;
        vm.clear = clear;
        vm.datePickerOpenStatus = {};
        vm.openCalendar = openCalendar;
        vm.save = save;
        vm.electoraldistricts = ElectoralDistrict.query();
        vm.candidates = Candidate.query();
        vm.myusers = MyUser.query();

        $timeout(function (){
            angular.element('.form-group:eq(1)>input').focus();
        });

        function clear () {
            $uibModalInstance.dismiss('cancel');
        }

        function save () {
            vm.isSaving = true;
            if (vm.votesFromDistrict.id !== null) {
                VotesFromDistrict.update(vm.votesFromDistrict, onSaveSuccess, onSaveError);
            } else {
                VotesFromDistrict.save(vm.votesFromDistrict, onSaveSuccess, onSaveError);
            }
        }

        function onSaveSuccess (result) {
            $scope.$emit('mygatewayApp:votesFromDistrictUpdate', result);
            $uibModalInstance.close(result);
            vm.isSaving = false;
        }

        function onSaveError () {
            vm.isSaving = false;
        }

        vm.datePickerOpenStatus.date = false;

        function openCalendar (date) {
            vm.datePickerOpenStatus[date] = true;
        }
    }
})();
