(function() {
    'use strict';

    angular
        .module('mygatewayApp')
        .controller('CandidateDialogController', CandidateDialogController);

    CandidateDialogController.$inject = ['$timeout', '$scope', '$stateParams',
        '$uibModalInstance', 'entity', 'Candidate', 'Party', 'Municipality', 'Principal'];

    function CandidateDialogController ($timeout, $scope, $stateParams, $uibModalInstance, entity, Candidate, Party, Municipality, Principal) {
        var vm = this;

        vm.candidate = entity;
        vm.clear = clear;
        vm.save = save;
        vm.parties = Party.query();
        vm.municipalities = Municipality.query();

        $timeout(function (){
            angular.element('.form-group:eq(1)>input').focus();
        });

        function clear () {
            $uibModalInstance.dismiss('cancel');
        }

        function save () {
            vm.isSaving = true;
            if (!vm.municipality) {
                Principal.identity().then(function (account) {
                    return Municipality.get({id: account.municipalityId}).$promise;
                }).then(function (municipality) {
                    vm.candidate.municipality = municipality;
                    finishElectoralDistrictSetting();
                });
            } else {
                finishElectoralDistrictSetting();
            }
        }

        function finishElectoralDistrictSetting() {
            if (vm.candidate.id !== null) {
                Candidate.update(vm.candidate, onSaveSuccess, onSaveError);
            } else {
                Candidate.save(vm.candidate, onSaveSuccess, onSaveError);
            }
        }

        function onSaveSuccess (result) {
            $scope.$emit('mygatewayApp:candidateUpdate', result);
            $uibModalInstance.close(result);
            vm.isSaving = false;
        }

        function onSaveError () {
            vm.isSaving = false;
        }


    }
})();
