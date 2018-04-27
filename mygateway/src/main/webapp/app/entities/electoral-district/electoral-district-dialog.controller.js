(function() {
    'use strict';

    angular
        .module('mygatewayApp')
        .controller('ElectoralDistrictDialogController', ElectoralDistrictDialogController);

    ElectoralDistrictDialogController.$inject = ['$timeout', '$scope', '$stateParams', '$uibModalInstance',
        'entity', 'ElectoralDistrict', 'Municipality', 'Principal'];

    function ElectoralDistrictDialogController ($timeout, $scope, $stateParams, $uibModalInstance,
                                                entity, ElectoralDistrict, Municipality, Principal) {
        var vm = this;

        vm.electoralDistrict = entity;
        vm.clear = clear;
        vm.save = save;
        vm.municipalities = Municipality.query();

        $timeout(function (){
            angular.element('.form-group:eq(1)>input').focus();
        });

        function clear () {
            $uibModalInstance.dismiss('cancel');
        }

        function save () {
            vm.isSaving = true;
            if(!vm.electoralDistrict.nrCanVote){
                vm.electoralDistrict.nrCanVote=0;
            }
            if(!vm.electoralDistrict.nrCardsUsed){
                vm.electoralDistrict.nrCardsUsed=0;
            }
            if(!vm.electoralDistrict.votingFinished){
                vm.electoralDistrict.votingFinished=false;
            }

            if(!vm.municipality){
                Principal.identity().then(function(account){
                    return Municipality.get({id:account.municipalityId}).$promise;
                }).then(function(municipality){
                    vm.electoralDistrict.municipality = municipality;
                    finishElectoralDistrictSetting();
                });
            }else{
                finishElectoralDistrictSetting();
            }
        }

        function finishElectoralDistrictSetting(){
            if (vm.electoralDistrict.id !== null) {
                ElectoralDistrict.update(vm.electoralDistrict, onSaveSuccess, onSaveError);
            } else {
                ElectoralDistrict.save(vm.electoralDistrict, onSaveSuccess, onSaveError);
            }
        }

        function onSaveSuccess (result) {
            $scope.$emit('mygatewayApp:electoralDistrictUpdate', result);
            $uibModalInstance.close(result);
            vm.isSaving = false;
        }

        function onSaveError () {
            vm.isSaving = false;
        }


    }
})();
