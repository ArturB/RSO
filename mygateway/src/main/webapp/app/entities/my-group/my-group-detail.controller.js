(function() {
    'use strict';

    angular
        .module('mygatewayApp')
        .controller('MyGroupDetailController', MyGroupDetailController);

    MyGroupDetailController.$inject = ['$scope', '$rootScope', '$stateParams', 'previousState', 'entity', 'MyGroup'];

    function MyGroupDetailController($scope, $rootScope, $stateParams, previousState, entity, MyGroup) {
        var vm = this;

        vm.myGroup = entity;
        vm.previousState = previousState.name;

        var unsubscribe = $rootScope.$on('mygatewayApp:myGroupUpdate', function(event, result) {
            vm.myGroup = result;
        });
        $scope.$on('$destroy', unsubscribe);
    }
})();
