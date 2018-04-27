(function() {
    'use strict';

    angular
        .module('mygatewayApp')
        .controller('PartyDetailController', PartyDetailController);

    PartyDetailController.$inject = ['$scope', '$rootScope', '$stateParams', 'previousState', 'entity', 'Party'];

    function PartyDetailController($scope, $rootScope, $stateParams, previousState, entity, Party) {
        var vm = this;

        vm.party = entity;
        vm.previousState = previousState.name;

        var unsubscribe = $rootScope.$on('mygatewayApp:partyUpdate', function(event, result) {
            vm.party = result;
        });
        $scope.$on('$destroy', unsubscribe);
    }
})();
