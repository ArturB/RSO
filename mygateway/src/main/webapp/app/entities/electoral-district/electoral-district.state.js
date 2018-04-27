(function() {
    'use strict';

    angular
        .module('mygatewayApp')
        .config(stateConfig);

    stateConfig.$inject = ['$stateProvider'];

    function stateConfig($stateProvider) {
        $stateProvider
        .state('electoral-district', {
            parent: 'entity',
            url: '/electoral-district',
            data: {
                authorities: ['ROLE_USER'],
                pageTitle: 'ElectoralDistricts'
            },
            views: {
                'content@': {
                    templateUrl: 'app/entities/electoral-district/electoral-districts.html',
                    controller: 'ElectoralDistrictController',
                    controllerAs: 'vm'
                }
            },
            resolve: {
            }
        })
        .state('electoral-district-detail', {
            parent: 'electoral-district',
            url: '/electoral-district/{id}',
            data: {
                authorities: ['ROLE_USER'],
                pageTitle: 'ElectoralDistrict'
            },
            views: {
                'content@': {
                    templateUrl: 'app/entities/electoral-district/electoral-district-detail.html',
                    controller: 'ElectoralDistrictDetailController',
                    controllerAs: 'vm'
                }
            },
            resolve: {
                entity: ['$stateParams', 'ElectoralDistrict', function($stateParams, ElectoralDistrict) {
                    return ElectoralDistrict.get({id : $stateParams.id}).$promise;
                }],
                previousState: ["$state", function ($state) {
                    var currentStateData = {
                        name: $state.current.name || 'electoral-district',
                        params: $state.params,
                        url: $state.href($state.current.name, $state.params)
                    };
                    return currentStateData;
                }]
            }
        })
        .state('electoral-district-detail.edit', {
            parent: 'electoral-district-detail',
            url: '/detail/edit',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/electoral-district/electoral-district-dialog.html',
                    controller: 'ElectoralDistrictDialogController',
                    controllerAs: 'vm',
                    backdrop: 'static',
                    size: 'lg',
                    resolve: {
                        entity: ['ElectoralDistrict', function(ElectoralDistrict) {
                            return ElectoralDistrict.get({id : $stateParams.id}).$promise;
                        }]
                    }
                }).result.then(function() {
                    $state.go('^', {}, { reload: false });
                }, function() {
                    $state.go('^');
                });
            }]
        })
        .state('electoral-district.new', {
            parent: 'electoral-district',
            url: '/new',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/electoral-district/electoral-district-dialog.html',
                    controller: 'ElectoralDistrictDialogController',
                    controllerAs: 'vm',
                    backdrop: 'static',
                    size: 'lg',
                    resolve: {
                        entity: function () {
                            return {
                                name: null,
                                nrCanVote: null,
                                nrCardsUsed: null,
                                votingFinished: false,
                                id: null
                            };
                        }
                    }
                }).result.then(function() {
                    $state.go('electoral-district', null, { reload: 'electoral-district' });
                }, function() {
                    $state.go('electoral-district');
                });
            }]
        })
        .state('electoral-district.edit', {
            parent: 'electoral-district',
            url: '/{id}/edit',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/electoral-district/electoral-district-dialog.html',
                    controller: 'ElectoralDistrictDialogController',
                    controllerAs: 'vm',
                    backdrop: 'static',
                    size: 'lg',
                    resolve: {
                        entity: ['ElectoralDistrict', function(ElectoralDistrict) {
                            return ElectoralDistrict.get({id : $stateParams.id}).$promise;
                        }]
                    }
                }).result.then(function() {
                    $state.go('electoral-district', null, { reload: 'electoral-district' });
                }, function() {
                    $state.go('^');
                });
            }]
        })
        .state('electoral-district.delete', {
            parent: 'electoral-district',
            url: '/{id}/delete',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/electoral-district/electoral-district-delete-dialog.html',
                    controller: 'ElectoralDistrictDeleteController',
                    controllerAs: 'vm',
                    size: 'md',
                    resolve: {
                        entity: ['ElectoralDistrict', function(ElectoralDistrict) {
                            return ElectoralDistrict.get({id : $stateParams.id}).$promise;
                        }]
                    }
                }).result.then(function() {
                    $state.go('electoral-district', null, { reload: 'electoral-district' });
                }, function() {
                    $state.go('^');
                });
            }]
        });
    }

})();
