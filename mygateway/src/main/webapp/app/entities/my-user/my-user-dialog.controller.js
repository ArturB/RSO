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

        if(vm.myUser.municipalityId){
            vm.myUser.municipality = Municipality.get({id: vm.myUser.municipalityId});
        }
        if(vm.myUser.electoralDistrictId){
            vm.myUser.electoralDistrict = ElectoralDistrict.get({id:vm.myUser.electoralDistrictId})
        }

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

        if(vm.myUser.role ){
            vm.myUser.roleCode = vm.myUser.role;
            for(var i = 0; i < vm.roles.length; i++){
                if(vm.roles[i].code === vm.myUser.role){
                    vm.initRole = vm.roles[i];
                }
            }
        }else{
            vm.initRole = vm.roles[0];
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
            if(vm.myUser.municipality){
                vm.myUser.municipalityId = vm.myUser.municipality.municipality_id;
            }
            if(vm.myUser.electoralDistrict){
                vm.myUser.electoralDistrictId= vm.myUser.electoralDistrict.electoral_district_id;
            }
            var entityToSend = angular.copy(vm.myUser);
            entityToSend.role =  vm.myUser.role.code;
            if (vm.myUser.id !== null) {
                MyUser.update(entityToSend, onSaveSuccess, onSaveError);
            } else {
                MyUser.save(entityToSend, onSaveSuccess, onSaveError);
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
