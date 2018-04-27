(function() {
    'use strict';

    angular
        .module('mygatewayApp')
        .controller('ElectoralDistrictDetailController', ElectoralDistrictDetailController);

    ElectoralDistrictDetailController.$inject = ['$scope', '$rootScope', '$stateParams', 'previousState', 'entity', 'ElectoralDistrict', 'Municipality'];

    function ElectoralDistrictDetailController($scope, $rootScope, $stateParams, previousState, entity, ElectoralDistrict, Municipality) {
        var vm = this;

        vm.electoralDistrict = entity;
        vm.previousState = previousState.name;

        var unsubscribe = $rootScope.$on('mygatewayApp:electoralDistrictUpdate', function(event, result) {
            vm.electoralDistrict = result;
        });
        $scope.$on('$destroy', unsubscribe);
    }
})();
