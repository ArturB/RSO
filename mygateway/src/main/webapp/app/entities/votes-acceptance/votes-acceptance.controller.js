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
        vm.result = {};

        vm.candidatesVotes = [];
        vm.isSaving= false;
        vm.saved=false;
        vm.round = -1;
        vm.districtId = -1;

        vm.outEntity = {
            no_can_vote:0,
            no_cards_used:0
        };

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
            }).then(function(){
                Principal.hasAuthority('ROLE_OKW_LEADER').then(function(authorityOk){
                    if(!authorityOk) {
                        return $q.reject();
                    }
                    return Principal.identity();
                }).then(function(account){
                    vm.districtId = account.electoral_district_id;
                    return VotesSum.votesSumFromDistrict(
                        {
                            districtId:account.electoral_district_id,
                            round:vm.round
                        }).$promise;
                }).then(function(result){
                    vm.result = result;
                    angular.forEach(result.candidate_votes, function(votePerCandidate){
                        votePerCandidate.candidate=Candidate.get({id:votePerCandidate.candidate_id})
                    });
                });

                Principal.hasAuthority('ROLE_GKW_LEADER').then(function(authorityOk){
                    if(!authorityOk) {
                        return $q.reject();
                    }
                    return Principal.identity();
                }).then(function(account){
                    vm.municipalityId= account.municipality_id;
                    return VotesSum.votesSumFromMunicipality(
                        {
                            municipalityId:account.municipality_id,
                            round:vm.round
                        }).$promise;
                }).then(function(result){
                    vm.result = result;
                    angular.forEach(result.candidate_votes, function(votePerCandidate){
                        votePerCandidate.candidate=Candidate.get({id:votePerCandidate.candidate_id})
                    });
                });
            })

        }

        vm.save = function(){
            vm.isSaving = true;
            if(vm.municipalityId){
                VotesAcceptance.acceptVotesFromMunicipality({
                    round:vm.round,
                    municipalityId:vm.municipalityId
                },  onSaveSuccess, onSaveError);
            }else{
                VotesAcceptance.acceptVotesFromDistrict({
                    round:vm.round,
                    districtId:vm.districtId
                }, vm.outEntity, onSaveSuccess, onSaveError);
            }
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
