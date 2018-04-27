(function() {
    'use strict';

    angular
        .module('mygatewayApp')
        .config(stateConfig);

    stateConfig.$inject = ['$stateProvider'];

    function stateConfig($stateProvider) {
        $stateProvider
        .state('municipality', {
            parent: 'entity',
            url: '/municipality',
            data: {
                authorities: ['ROLE_USER'],
                pageTitle: 'Municipalities'
            },
            views: {
                'content@': {
                    templateUrl: 'app/entities/municipality/municipalities.html',
                    controller: 'MunicipalityController',
                    controllerAs: 'vm'
                }
            },
            resolve: {
            }
        })
        .state('municipality-detail', {
            parent: 'municipality',
            url: '/municipality/{id}',
            data: {
                authorities: ['ROLE_USER'],
                pageTitle: 'Municipality'
            },
            views: {
                'content@': {
                    templateUrl: 'app/entities/municipality/municipality-detail.html',
                    controller: 'MunicipalityDetailController',
                    controllerAs: 'vm'
                }
            },
            resolve: {
                entity: ['$stateParams', 'Municipality', function($stateParams, Municipality) {
                    return Municipality.get({id : $stateParams.id}).$promise;
                }],
                previousState: ["$state", function ($state) {
                    var currentStateData = {
                        name: $state.current.name || 'municipality',
                        params: $state.params,
                        url: $state.href($state.current.name, $state.params)
                    };
                    return currentStateData;
                }]
            }
        })
        .state('municipality-detail.edit', {
            parent: 'municipality-detail',
            url: '/detail/edit',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/municipality/municipality-dialog.html',
                    controller: 'MunicipalityDialogController',
                    controllerAs: 'vm',
                    backdrop: 'static',
                    size: 'lg',
                    resolve: {
                        entity: ['Municipality', function(Municipality) {
                            return Municipality.get({id : $stateParams.id}).$promise;
                        }]
                    }
                }).result.then(function() {
                    $state.go('^', {}, { reload: false });
                }, function() {
                    $state.go('^');
                });
            }]
        })
        .state('municipality.new', {
            parent: 'municipality',
            url: '/new',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/municipality/municipality-dialog.html',
                    controller: 'MunicipalityDialogController',
                    controllerAs: 'vm',
                    backdrop: 'static',
                    size: 'lg',
                    resolve: {
                        entity: function () {
                            return {
                                name: null,
                                id: null
                            };
                        }
                    }
                }).result.then(function() {
                    $state.go('municipality', null, { reload: 'municipality' });
                }, function() {
                    $state.go('municipality');
                });
            }]
        })
        .state('municipality.edit', {
            parent: 'municipality',
            url: '/{id}/edit',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/municipality/municipality-dialog.html',
                    controller: 'MunicipalityDialogController',
                    controllerAs: 'vm',
                    backdrop: 'static',
                    size: 'lg',
                    resolve: {
                        entity: ['Municipality', function(Municipality) {
                            return Municipality.get({id : $stateParams.id}).$promise;
                        }]
                    }
                }).result.then(function() {
                    $state.go('municipality', null, { reload: 'municipality' });
                }, function() {
                    $state.go('^');
                });
            }]
        })
        .state('municipality.delete', {
            parent: 'municipality',
            url: '/{id}/delete',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/municipality/municipality-delete-dialog.html',
                    controller: 'MunicipalityDeleteController',
                    controllerAs: 'vm',
                    size: 'md',
                    resolve: {
                        entity: ['Municipality', function(Municipality) {
                            return Municipality.get({id : $stateParams.id}).$promise;
                        }]
                    }
                }).result.then(function() {
                    $state.go('municipality', null, { reload: 'municipality' });
                }, function() {
                    $state.go('^');
                });
            }]
        });
    }

})();
