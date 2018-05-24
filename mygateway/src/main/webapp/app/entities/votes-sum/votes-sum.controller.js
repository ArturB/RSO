/**
 * Created by defacto on 5/22/2018.
 */
(function() {
    'use strict';

    angular
        .module('mygatewayApp')
        .controller('VotesSumController', VotesAcceptanceController);

    VotesAcceptanceController.$inject = ['$scope', 'VotesAcceptance', 'Candidate', '$q', 'Principal', 'ElectoralPeriod'];

    function VotesAcceptanceController($scope, VotesAcceptance, Candidate, $q, Principal, ElectoralPeriod) {
        var vm = this;

        vm.candidatesVotes = [];
        vm.invalidVotes = 0;
        vm.isSaving= false;

        loadAll();

        function loadAll() {
            var round = -1;
            ElectoralPeriod.getCurrentPeriod().then(function (result){
                if(result.name === 'MidRoundPeriod'){
                    round = 1;
                }else if (result.name === 'PostElectionPeriod'){
                    round = 2;
                }else{
                    round = 1;
                    console.error('!!! Wrong period to pass votes: '+result.name);
                }
            }); //todo principal must be computed after getting round!!

            Principal.hasAuthority('ROLE_OKW_MEMBER').then(function(authorityOk){
                if(!authorityOk) {
                    return $q.reject();
                }
                return Principal.identity();
            }).then(function(account){
                return Candidate.findByMunicipalityIdAndRound(
                    {
                        municipalityId:account.municipalityId,
                        round:round
                    }).$promise;
            }).then(function(result){
                angular.forEach(result, function(candidate){
                    vm.candidatesVotes.push({
                        candidate:candidate,
                        votesCount:0
                    });
                });
            });
        }

        vm.save = function(){
            Candidate.save(vm.candidate, onSaveSuccess, onSaveError);
        };

        function onSaveSuccess (result) {
            vm.isSaving = false;
        }

        function onSaveError () {
            vm.isSaving = false;
        }
    }
})();
