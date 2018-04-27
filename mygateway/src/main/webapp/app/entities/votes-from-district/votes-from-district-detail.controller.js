(function() {
    'use strict';

    angular
        .module('mygatewayApp')
        .controller('VotesFromDistrictDetailController', VotesFromDistrictDetailController);

    VotesFromDistrictDetailController.$inject = ['$scope', '$rootScope', '$stateParams', 'previousState', 'entity', 'VotesFromDistrict', 'ElectoralDistrict', 'Candidate', 'MyUser'];

    function VotesFromDistrictDetailController($scope, $rootScope, $stateParams, previousState, entity, VotesFromDistrict, ElectoralDistrict, Candidate, MyUser) {
        var vm = this;

        vm.votesFromDistrict = entity;
        vm.previousState = previousState.name;

        var unsubscribe = $rootScope.$on('mygatewayApp:votesFromDistrictUpdate', function(event, result) {
            vm.votesFromDistrict = result;
        });
        $scope.$on('$destroy', unsubscribe);
    }
})();
