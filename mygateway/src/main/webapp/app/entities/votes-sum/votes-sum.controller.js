/**
 * Created by defacto on 5/22/2018.
 */
(function() {
    'use strict';

    angular
        .module('mygatewayApp')
        .controller('VotesSumController', VotesSumController);

    VotesSumController.$inject = ['$scope', 'Municipality', 'ElectoralDistrict', 'ElectoralPeriod',
        'VotesSum', 'Candidate'];

    function VotesSumController($scope, Municipality, ElectoralDistrict, ElectoralPeriod, VotesSum, Candidate) {
        var vm = this;
        vm.municipalities = Municipality.query();
        vm.electoralDistricts = [];
        vm.selectedDistrictId = null;
        vm.invalidVotes = 0;
        vm.result = null;

        vm.selectedRound = 1;
        vm.rounds = [];


        ElectoralPeriod.getCurrentRound().then(function(round ){
           if(round === 0 ){
               vm.rounds = [1]; //todo
           } else if (round === 1){
               vm.rounds = [1];
           }else {
               vm.rounds = [1,2];
           }
        });

        vm.idOfLastMunicipality = null;
        vm.electoralDistrictsCache = null;
        vm.getDistricts = function(municipality){
            if(municipality){
                if(vm.idOfLastMunicipality === municipality.municipality_id){
                    return vm.electoralDistrictsCache;
                }else{
                    vm.idOfLastMunicipality = municipality.municipality_id;
                    vm.electoralDistrictsCache = ElectoralDistrict.findByMunicipalityId({municipalityId:municipality.municipality_id});
                    return vm.electoralDistrictsCache;
                }
            } else{
                vm.idOfLastMunicipality = null;
                vm.electoralDistrictsCache = null;
            }
        };


        vm.idOfLastDistrict = null;
        vm.votesCache = null;
        vm.getVotesFromDisctict = function(district) {
            if(district){
                if(vm.idOfLastDistrict === district.electoral_district_id){
                    return vm.votesCache;
                }else{
                    vm.idOfLastDistrict = district.electoral_district_id;
                    vm.votesCache = [];
                    VotesSum.votesSumFromDistrict(
                    {
                            districtId: district.electoral_district_id,
                            round: vm.selectedRound
                    }).$promise.then(function(result){
                        vm.result = result;
                        angular.forEach(result.candidate_votes, function(votePerCandidate){
                            if(votePerCandidate.candidate_id !== -1){
                                vm.votesCache.push({
                                    candidate:Candidate.get({id:votePerCandidate.candidate_id}),
                                    votesCount:votePerCandidate.number_of_votes
                                });
                            }
                        });
                    });
                    return vm.votesCache;
                }
            }else{
                vm.idOfLastDistrict = null;
                vm.votesCache = null;
            }
        };

        vm.getVotesSumTableClass = function(){
            if(vm.selectedRound === 1){
                return "votesSumIntroRoundTable";
            }else{
                return "votesSumFinalRoundTable";
            }
        };
    }
})();
