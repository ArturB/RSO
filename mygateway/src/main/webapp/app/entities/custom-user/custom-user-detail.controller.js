(function() {
    'use strict';

    angular
        .module('mygatewayApp')
        .controller('CustomUserDetailController', CustomUserDetailController);

    CustomUserDetailController.$inject = ['$scope', '$rootScope', '$stateParams', 'previousState', 'entity', 'CustomUser', 'ElectoralDistrict', 'Municipality'];

    function CustomUserDetailController($scope, $rootScope, $stateParams, previousState, entity, CustomUser, ElectoralDistrict, Municipality) {
        var vm = this;

        vm.customUser = entity;
        vm.previousState = previousState.name;

        var unsubscribe = $rootScope.$on('mygatewayApp:customUserUpdate', function(event, result) {
            vm.customUser = result;
        });
        $scope.$on('$destroy', unsubscribe);
    }
})();
