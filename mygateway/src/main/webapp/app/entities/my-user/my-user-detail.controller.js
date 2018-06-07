(function() {
    'use strict';

    angular
        .module('mygatewayApp')
        .controller('MyUserDetailController', MyUserDetailController);

    MyUserDetailController.$inject = ['$scope', '$rootScope', '$stateParams', 'previousState', 'entity', 'MyUser', 'ElectoralDistrict', 'Municipality'];

    function MyUserDetailController($scope, $rootScope, $stateParams, previousState, entity, MyUser, ElectoralDistrict, Municipality) {
        var vm = this;

        vm.myUser = entity;
        vm.previousState = previousState.name;
        if(vm.myUser.municipalityId){
            vm.myUser.municipality = Municipality.get({id:vm.myUser.municipalityId});
        }
        if(vm.myUser.electoralDistrictId){
            vm.myUser.electoralDistrict = ElectoralDistrict.get({id:vm.myUser.electoralDistrictId});
        }

        var unsubscribe = $rootScope.$on('mygatewayApp:myUserUpdate', function(event, result) {
            vm.myUser = result;
        });
        $scope.$on('$destroy', unsubscribe);
    }
})();
