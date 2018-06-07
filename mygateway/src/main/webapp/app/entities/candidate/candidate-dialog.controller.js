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
        vm.candidate.party = {};
        if(vm.candidate.party_id){
            vm.candidate.party = Party.get({id: entity.party_id});
        }
        vm.municipalities = Municipality.query();
        if(vm.candidate.municipality_id){
            vm.candidate.municipality = Municipality.get({id: vm.candidate.municipality_id});
        }

        Principal.identity().then(function(account) {
            if(account.municipality_id){
                vm.candidate.municipality = Municipality.get({id: account.municipality_id});
            }
        });

        $timeout(function (){
            angular.element('.form-group:eq(1)>input').focus();
        });

        function clear () {
            $uibModalInstance.dismiss('cancel');
        }

        function save () {
            vm.isSaving = true;
            if (!vm.candidate.municipality) {
                Principal.identity().then(function (account) {
                    return Municipality.get({id: account.municipality_id}).$promise;
                }).then(function (municipality) {
                    vm.candidate.municipality = municipality;
                    finishElectoralDistrictSetting();
                });
            } else {
                finishElectoralDistrictSetting();
            }
        }

        function finishElectoralDistrictSetting() {
            if(vm.candidate.municipality) {
                vm.candidate.municipality_id = vm.candidate.municipality.municipality_id;
            }
            if(vm.candidate.party){
                vm.candidate.party_id = vm.candidate.party.party_id;
            }
            if (vm.candidate.candidate_id !== null) {
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
