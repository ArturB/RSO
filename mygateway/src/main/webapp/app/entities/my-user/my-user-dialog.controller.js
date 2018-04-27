(function() {
    'use strict';

    angular
        .module('mygatewayApp')
        .controller('MyUserDialogController', MyUserDialogController);

    MyUserDialogController.$inject = ['$timeout', '$scope', '$stateParams', '$uibModalInstance', 'entity', 'MyUser', 'MyGroup', 'ElectoralDistrict', 'Municipality'];

    function MyUserDialogController ($timeout, $scope, $stateParams, $uibModalInstance, entity, MyUser, MyGroup, ElectoralDistrict, Municipality) {
        var vm = this;

        vm.myUser = entity;
        vm.clear = clear;
        vm.datePickerOpenStatus = {};
        vm.openCalendar = openCalendar;
        vm.save = save;
        vm.mygroups = MyGroup.query();
        vm.electoraldistricts = ElectoralDistrict.query();
        vm.municipalities = Municipality.query();

        $timeout(function (){
            angular.element('.form-group:eq(1)>input').focus();
        });

        function clear () {
            $uibModalInstance.dismiss('cancel');
        }

        function save () {
            vm.isSaving = true;
            if (vm.myUser.id !== null) {
                MyUser.update(vm.myUser, onSaveSuccess, onSaveError);
            } else {
                MyUser.save(vm.myUser, onSaveSuccess, onSaveError);
            }
        }

        function onSaveSuccess (result) {
            $scope.$emit('mygatewayApp:myUserUpdate', result);
            $uibModalInstance.close(result);
            vm.isSaving = false;
        }

        function onSaveError () {
            vm.isSaving = false;
        }

        vm.datePickerOpenStatus.birthdate = false;

        function openCalendar (date) {
            vm.datePickerOpenStatus[date] = true;
        }
    }
})();
