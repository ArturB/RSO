/**
 * Created by defacto on 4/22/2018.
 */
(function() {
    'use strict';

    angular
        .module('mygatewayApp')
        .controller('VotesDesignationController', VotesDesignationController);

    VotesDesignationController.$inject = ['$scope', 'VotesDesignation', 'Candidate', '$q', 'Principal', 'ElectoralPeriod'];

    function VotesDesignationController($scope, VotesDesignation, Candidate, $q, Principal, ElectoralPeriod) {
        var vm = this;
        vm.round = 0;
        vm.userId = 0;
        vm.account = {};

        vm.designationPacks = [];

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
                vm.round = round;
            }); //todo principal must be computed after getting round!!

            Principal.hasAuthority('ROLE_OKW_MEMBER').then(function(authorityOk){
                if(!authorityOk) {
                    return $q.reject();
                }
                return Principal.identity();
            }).then(function(account) {
                vm.account = account;
                vm.userId = account.id;
                return VotesDesignation.findByUserId({
                    round: round,
                    userId: account.id
                }).$promise
            }).then(function(result){
                vm.designationPacks = result;
            });
        }
    }
})();
