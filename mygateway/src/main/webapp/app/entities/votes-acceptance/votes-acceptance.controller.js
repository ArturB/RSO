/**
 * Created by defacto on 5/22/2018.
 */
(function() {
    'use strict';

    angular
        .module('mygatewayApp')
        .controller('VotesAcceptanceController', VotesAcceptanceController);

    VotesAcceptanceController.$inject = ['$scope', 'VotesAcceptance', 'VotesSum', 'Candidate', '$q', 'Principal', 'ElectoralPeriod'];

    function VotesAcceptanceController($scope, VotesAcceptance, VotesSum, Candidate, $q, Principal,
                                       ElectoralPeriod) {
        var vm = this;

        vm.candidatesVotes = [];
        vm.invalidVotes = 0;
        vm.isSaving= false;
        vm.saved=false;
        vm.round = -1;
        vm.districtId = -1;
        vm.invalidVotes = -1;

        loadAll();

        function loadAll() {
            ElectoralPeriod.getCurrentPeriod().then(function (result){
                if(result.name === 'MidRoundPeriod'){
                    vm.round = 1;
                }else if (result.name === 'PostElectionPeriod'){
                    vm.round = 2;
                }else{
                    vm.round = 1;
                    console.error('!!! Wrong period to pass votes: '+result.name);
                }
            }); //todo principal must be computed after getting round!!

            Principal.hasAuthority('ROLE_OKW_LEADER').then(function(authorityOk){
                if(!authorityOk) {
                    return $q.reject();
                }
                return Principal.identity();
            }).then(function(account){
                vm.districtId = account.electoralDistrictId;
                return VotesSum.votesSumFromDistrict(
                    {
                        districtId:account.electoralDistrictId,
                        round:vm.round
                    }).$promise;
            }).then(function(result){
                angular.forEach(result, function(votePerCandidate){
                    if(votePerCandidate.candidate_id === -1){
                        vm.invalidVotes = votePerCandidate.number_of_votes;
                    }
                    vm.candidatesVotes.push({
                        candidate:Candidate.get({id:votePerCandidate.candidate_id}),
                        votesCount:votePerCandidate.number_of_votes
                    });
                });
            });
        }

        vm.save = function(){
            vm.isSaving = true;
            VotesAcceptance.acceptVotesFromDistrict({
                    round:vm.round,
                    districtId:vm.districtId
            }, onSaveSuccess, onSaveError);
        };

        function onSaveSuccess (result) {
            vm.isSaving = false;
            vm.saved = true;
        }

        function onSaveError () {
            vm.isSaving = false;
        }
    }
})();
