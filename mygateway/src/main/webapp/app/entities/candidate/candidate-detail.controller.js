(function() {
    'use strict';

    angular
        .module('mygatewayApp')
        .controller('CandidateDetailController', CandidateDetailController);

    CandidateDetailController.$inject = ['$scope', '$rootScope', '$stateParams', 'previousState', 'entity', 'Candidate', 'Party', 'Municipality'];

    function CandidateDetailController($scope, $rootScope, $stateParams, previousState, entity, Candidate, Party, Municipality) {
        var vm = this;

        vm.candidate = entity;
        if(entity.party_id) {
            vm.candidate.party = Party.get({id: entity.party_id});
        }
        if(entity.municipality_id) {
            vm.candidate.municipality = Municipality.get({id: entity.municipality_id});
        }
        vm.previousState = previousState.name;

        var unsubscribe = $rootScope.$on('mygatewayApp:candidateUpdate', function(event, result) {
            vm.candidate = result;
        });
        $scope.$on('$destroy', unsubscribe);
    }
})();
