/**
 * Created by defacto on 4/22/2018.
 */
(function() {
    'use strict';

    angular
        .module('mygatewayApp')
        .controller('VotesDesignationController', VotesDesignationController);

    VotesDesignationController.$inject = ['$scope', 'VotesDesignation', 'ElectoralDistrict', '$q', 'Principal', 'ElectoralPeriod'];

    function VotesDesignationController($scope, VotesDesignation, ElectoralDistrict, $q, Principal, ElectoralPeriod) {
        var vm = this;
        vm.round = 0;
        vm.userId = 0;
        vm.account = {};

        vm.designationPacks = [];
        vm.canDesignateVotes = false;

        loadAll();

        function loadAll() {
            var round = -1;
            ElectoralPeriod.getCurrentPeriod().then(function (result) {
                if (result.name === 'MidRoundPeriod') {
                    round = 1;
                } else if (result.name === 'PostElectionPeriod') {
                    round = 2;
                } else {
                    round = 1;
                    console.error('!!! Wrong period to pass votes: ' + result.name);
                }
                vm.round = round;

                Principal.hasAuthority('ROLE_OKW_MEMBER').then(function (authorityOk) {
                    if (!authorityOk) {
                        return $q.reject();
                    }
                    return Principal.identity();
                }).then(function (account) {
                    vm.account = account;
                    vm.userId = account.id;

                    ElectoralDistrict.query({id: account.electoralDistrict}, function (district) {
                        if (vm.round === 1) {
                            vm.canDesignateVotes = !district.first_round_votes_accepted;
                        } else {
                            vm.canDesignateVotes = !district.second_round_votes_accepted;
                        }
                    });

                    return VotesDesignation.findByUserId({
                        round: round,
                        userId: account.id
                    }).$promise
                }).then(function (result) {
                    vm.designationPacks = result;
                });

            });
        }
    }
})();
