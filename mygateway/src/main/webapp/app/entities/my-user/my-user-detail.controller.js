(function() {
    'use strict';

    angular
        .module('mygatewayApp')
        .controller('MyUserDetailController', MyUserDetailController);

    MyUserDetailController.$inject = ['$scope', '$rootScope', '$stateParams', 'previousState', 'entity', 'MyUser', 'MyGroup', 'ElectoralDistrict', 'Municipality'];

    function MyUserDetailController($scope, $rootScope, $stateParams, previousState, entity, MyUser, MyGroup, ElectoralDistrict, Municipality) {
        var vm = this;

        vm.myUser = entity;
        vm.previousState = previousState.name;

        var unsubscribe = $rootScope.$on('mygatewayApp:myUserUpdate', function(event, result) {
            vm.myUser = result;
        });
        $scope.$on('$destroy', unsubscribe);
    }
})();
