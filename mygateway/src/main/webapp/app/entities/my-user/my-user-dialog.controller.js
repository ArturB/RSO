(function() {
    'use strict';

    angular
        .module('mygatewayApp')
        .controller('MyUserDialogController', MyUserDialogController);

    MyUserDialogController.$inject = ['$timeout', '$scope', '$stateParams', '$uibModalInstance', 'entity',
        'MyUser', 'ElectoralDistrict', 'Municipality', 'preset'];

    function MyUserDialogController ($timeout, $scope, $stateParams, $uibModalInstance, entity, MyUser, ElectoralDistrict, Municipality, preset) {
        var vm = this;

        vm.myUser = entity;
        vm.clear = clear;
        vm.datePickerOpenStatus = {};
        vm.openCalendar = openCalendar;
        vm.save = save;
        vm.electoraldistricts = ElectoralDistrict.query();
        vm.municipalities = Municipality.query();
        vm.preset=[];

        if(preset.municipalityId){
            vm.myUser.municipality = Municipality.get({id:preset.municipalityId});
            vm.presetMunicipality = true;
        }
        if(preset.electoralDistrictId && preset.electoralDistrictId !== '-1'){
            vm.myUser.electoralDistrict = ElectoralDistrict.get({id:preset.electoralDistrictId});
            vm.presetElectoralDistrict = true;

            vm.roles = [
                {name:"Przewodniczący okręgowej komisji wyborczej", code:"ROLE_OKW_LEADER"}  ,
                {name:"Członek okręgowej komisji wyborczej", code:"ROLE_OKW_MEMBER"}
            ];
        }else if(preset.municipalityId) {
            vm.roles = [
                // {name:"Przewodniczący gminnej komisji wyborczej", code:"ROLE_GKW_LEADER"}  ,
                {name:"Członek gminnej komisji wyborczej", code:"ROLE_GKW_MEMBER"}  ,
            ];
        } else{
            vm.roles = [
                {name:"Przewodniczący gminnej komisji wyborczej", code:"ROLE_GKW_LEADER"}  ,
                {name:"Członek gminnej komisji wyborczej", code:"ROLE_GKW_MEMBER"}  ,
                {name:"Przewodniczący okręgowej komisji wyborczej", code:"ROLE_OKW_LEADER"}  ,
                {name:"Członek okręgowej komisji wyborczej", code:"ROLE_OKW_MEMBER"}
            ];
        }

        $timeout(function (){
            angular.element('.form-group:eq(1)>input').focus();
        });

        function clear () {
            $uibModalInstance.dismiss('cancel');
        }

        function save () {
            vm.isSaving = true;
            vm.myUser.username = vm.generateUsername();
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

        vm.generateUsername = function(){
            var finalName = "";
            if(vm.myUser.name){
                finalName += vm.myUser.name.charAt(0);
            }
            if(vm.myUser.surname){
                finalName += vm.myUser.surname;
            }
            return finalName.toLowerCase();
        }
    }
})();
