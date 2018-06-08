/**
 * Created by defacto on 6/3/2018.
 */
(function() {
    'use strict';

    angular
        .module('mygatewayApp')
        .controller('VotesDesignationDialogController', VotesDesignationDialogController);

    VotesDesignationDialogController.$inject = ['$scope', '$uibModalInstance', 'VotesDesignation',
        'Candidate', '$timeout', '$q', 'Principal', 'ElectoralPeriod', 'entity', 'Party'];

    function VotesDesignationDialogController($scope, $uibModalInstance, VotesDesignation,
                                              Candidate, $timeout, $q, Principal, ElectoralPeriod, entity, Party) {
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
                        if(candidate.party_id) {
                            candidateVote.candidate.party = Party.get({id: candidate.party_id})
                        }
                    });
            });
        }else {
            if (vm.entity.candidate_votes.then) {
                vm.entity.candidate_votes.then(function (votes) {
                    vm.entity.candidate_votes = votes;
                    angular.forEach(vm.entity.candidate_votes, function (vote) {
                        if(vote.candidate.party_id) {
                            vote.candidate.party = Party.get({id: vote.candidate.party_id})
                        }
                    });
                });
            } else {
                angular.forEach(vm.entity.candidate_votes, function (vote) {
                    if(vote.candidate.party_id) {
                        vote.candidate.party = Party.get({id: vote.candidate.party_id})
                    }
                });
            }
        }

        function save(){
            vm.isSaving = true;
            if( vm.entity.id ){
                VotesDesignation.update(vm.entity, onSaveSuccess, onSaveError);
            }else{

                ElectoralPeriod.getCurrentPeriod().then(function (result) {
                    if (result.name === 'MidRoundPeriod') {
                        vm.round = 1;
                    } else if (result.name === 'PostElectionPeriod') {
                        vm.round = 2;
                    } else {
                        vm.round = 1;
                        console.error('!!! Wrong period to pass votes: ' + result.name);
                    }
                    VotesDesignation.save({round: vm.round}, vm.entity, onSaveSuccess, onSaveError);
                });
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
