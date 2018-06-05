/**
 * Created by defacto on 6/3/2018.
 */
(function() {
    'use strict';

    angular
        .module('mygatewayApp')
        .controller('VotesDesignationDialogController', VotesDesignationDialogController);

    VotesDesignationDialogController.$inject = ['$scope', '$uibModalInstance', 'VotesDesignation',
        'Candidate', '$timeout', '$q', 'Principal', 'ElectoralPeriod', 'entity'];

    function VotesDesignationDialogController($scope, $uibModalInstance, VotesDesignation,
                                              Candidate, $timeout, $q, Principal, ElectoralPeriod, entity) {
        var vm = this;
        vm.entity = entity;

        vm.isSaving= false;

        vm.clear = clear;
        vm.save = save;


        $timeout(function (){
            angular.element('.form-group:eq(1)>input').focus();
        });

        function clear () {
            $uibModalInstance.dismiss('cancel');
        }

        if(vm.entity.id) {
            angular.forEach(vm.entity.candidate_votes, function (candidateVote) {
                Candidate.get({id: candidateVote.candidate_id}).$promise
                    .then(function (candidate) {
                        candidateVote.candidate = candidate;
                    });
            });
        }else{
            vm.entity.candidate_votes.then(function (votes) {
                vm.entity.candidate_votes = votes;
            });
        }

        function save(){
            vm.isSaving = true;
            if( vm.entity.id ){
                VotesDesignation.update(vm.entity, onSaveSuccess, onSaveError);
            }else{
                VotesDesignation.save(vm.entity, onSaveSuccess, onSaveError);
            }
        }

        function onSaveSuccess (result) {
            vm.isSaving = false;
            $uibModalInstance.dismiss('cancel');
        }

        function onSaveError () {
            vm.isSaving = false;
        }
    }
})();
