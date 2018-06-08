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
        vm.selectedDistrict = null;
        vm.selectedMunicipality = null;
        vm.invalidVotes = 0;


        vm.selectedRound = 1;
        vm.rounds = [];
        vm.result = null;


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
        vm.cachedSourceOfVotes = null;
        vm.getVotes = function() {
            var district = vm.selectedDistrict;
            var municipality = vm.selectedMunicipality;
            if (district && vm.cachedSourceOfVotes === "district" && vm.idOfLastDistrict === district.electoral_district_id) {
                return vm.votesCache;
            } else if (!district && municipality && vm.cachedSourceOfVotes === "municipality" && vm.idOfLastDistrict === municipality.municipality_id) {
                return vm.votesCache;
            } else if (district || municipality) {
                var f = function () {
                    // console.log("E41 TAKING!!");
                    vm.votesCache = [];
                    if (district) {
                        vm.idOfLastDistrict = district.electoral_district_id;
                        vm.cachedSourceOfVotes = "district";
                        return VotesSum.votesSumFromDistrict(
                            {
                                districtId: district.electoral_district_id,
                                round: vm.selectedRound
                            });
                    } else if (municipality) {
                        vm.cachedSourceOfVotes = "municipality";
                        vm.idOfLastDistrict = municipality.municipality_id;
                        return VotesSum.votesSumFromMunicipality(
                            {
                                municipalityId: municipality.municipality_id,
                                round: vm.selectedRound
                            });
                    }
                };

                f().$promise.then(function (result) {
                    vm.result = result;
                    angular.forEach(result.candidate_votes, function (votePerCandidate) {
                        vm.votesCache.push({
                            candidate: Candidate.get({id: votePerCandidate.candidate_id}),
                            votesCount: votePerCandidate.number_of_votes
                        });
                    });
                });
                return vm.votesCache;
            }
            else {
                vm.idOfLastDistrict = null;
                vm.votesCache = null;
                vm.cachedSourceOfVotes = null;
            }
        };

        vm.getVotesSumTableClass = function(){
            if(vm.selectedRound === 1){
                return "votesSumIntroRoundTable";
            }else{
                return "votesSumFinalRoundTable";
            }
        };

        vm.votingIsFinished = function () {
            if(vm.selectedDistrict) {
                if (vm.selectedRound === 1) {
                    return vm.selectedDistrict.first_round_votes_accepted;
                } else {
                    return vm.selectedDistrict.second_round_votes_accepted;
                }
            }
        };

    }
})();
