(function() {
    'use strict';

    angular
        .module('mygatewayApp')
        .controller('CandidateDetailController', CandidateDetailController);

    CandidateDetailController.$inject = ['$scope', '$rootScope', '$stateParams', 'previousState', 'entity', 'Candidate', 'Party', 'Municipality'];

    function CandidateDetailController($scope, $rootScope, $stateParams, previousState, entity, Candidate, Party, Municipality) {
        var vm = this;

        vm.candidate = entity;
        vm.previousState = previousState.name;

        var unsubscribe = $rootScope.$on('mygatewayApp:candidateUpdate', function(event, result) {
            vm.candidate = result;
        });
        $scope.$on('$destroy', unsubscribe);
    }
})();
