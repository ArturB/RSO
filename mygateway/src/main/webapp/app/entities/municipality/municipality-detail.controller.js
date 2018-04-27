(function() {
    'use strict';

    angular
        .module('mygatewayApp')
        .controller('MunicipalityDetailController', MunicipalityDetailController);

    MunicipalityDetailController.$inject = ['$scope', '$rootScope', '$stateParams', 'previousState', 'entity', 'Municipality'];

    function MunicipalityDetailController($scope, $rootScope, $stateParams, previousState, entity, Municipality) {
        var vm = this;

        vm.municipality = entity;
        vm.previousState = previousState.name;

        var unsubscribe = $rootScope.$on('mygatewayApp:municipalityUpdate', function(event, result) {
            vm.municipality = result;
        });
        $scope.$on('$destroy', unsubscribe);
    }
})();
