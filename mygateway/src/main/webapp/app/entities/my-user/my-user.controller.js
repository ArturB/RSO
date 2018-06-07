(function() {
    'use strict';

    angular
        .module('mygatewayApp')
        .controller('MyUserController', MyUserController);

    MyUserController.$inject = ['$state', 'MyUser',  'paginationConstants', 'ParseLinks',
        'pagingParams', 'AlertService', 'Municipality', 'ElectoralDistrict' ];

    function MyUserController($state, MyUser,   paginationConstants, ParseLinks,
                              pagingParams, AlertService, Municipality, ElectoralDistrict) {

        var vm = this;

        vm.myUsers = [];
        vm.itemsPerPage = paginationConstants.itemsPerPage;

        loadAll();

        function loadAll() {
            MyUser.query(
                {
                    page: pagingParams.page - 1,
                    size: vm.itemsPerPage
                }, onSuccess, onError);

            function onSuccess(data, headers) {
                vm.links = ParseLinks.parse(headers('link'));
                vm.totalItems = headers('X-Total-Count');
                vm.queryCount = vm.totalItems;
                vm.myUsers = data;
                vm.page = pagingParams.page;


                angular.forEach(data, function (user) {
                    if(user.municipalityId) {
                        user.municipality = Municipality.get({id: user.municipalityId});
                    }
                    if(user.electoralDistrictId){
                        user.electoralDistrict = ElectoralDistrict.get({id: user.electoralDistrictId});
                    }
                });
            }
            function onError(error) {
                AlertService.error(error.data.message);
            }
        }

        vm.transition = function() {
            $state.transitionTo($state.$current, {
                page: vm.page
            });
        }
    }
})();
