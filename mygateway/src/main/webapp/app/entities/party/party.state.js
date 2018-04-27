(function() {
    'use strict';

    angular
        .module('mygatewayApp')
        .config(stateConfig);

    stateConfig.$inject = ['$stateProvider'];

    function stateConfig($stateProvider) {
        $stateProvider
        .state('party', {
            parent: 'entity',
            url: '/party',
            data: {
                authorities: ['ROLE_USER'],
                pageTitle: 'Parties'
            },
            views: {
                'content@': {
                    templateUrl: 'app/entities/party/parties.html',
                    controller: 'PartyController',
                    controllerAs: 'vm'
                }
            },
            resolve: {
            }
        })
        .state('party-detail', {
            parent: 'party',
            url: '/party/{id}',
            data: {
                authorities: ['ROLE_USER'],
                pageTitle: 'Party'
            },
            views: {
                'content@': {
                    templateUrl: 'app/entities/party/party-detail.html',
                    controller: 'PartyDetailController',
                    controllerAs: 'vm'
                }
            },
            resolve: {
                entity: ['$stateParams', 'Party', function($stateParams, Party) {
                    return Party.get({id : $stateParams.id}).$promise;
                }],
                previousState: ["$state", function ($state) {
                    var currentStateData = {
                        name: $state.current.name || 'party',
                        params: $state.params,
                        url: $state.href($state.current.name, $state.params)
                    };
                    return currentStateData;
                }]
            }
        })
        .state('party-detail.edit', {
            parent: 'party-detail',
            url: '/detail/edit',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/party/party-dialog.html',
                    controller: 'PartyDialogController',
                    controllerAs: 'vm',
                    backdrop: 'static',
                    size: 'lg',
                    resolve: {
                        entity: ['Party', function(Party) {
                            return Party.get({id : $stateParams.id}).$promise;
                        }]
                    }
                }).result.then(function() {
                    $state.go('^', {}, { reload: false });
                }, function() {
                    $state.go('^');
                });
            }]
        })
        .state('party.new', {
            parent: 'party',
            url: '/new',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/party/party-dialog.html',
                    controller: 'PartyDialogController',
                    controllerAs: 'vm',
                    backdrop: 'static',
                    size: 'lg',
                    resolve: {
                        entity: function () {
                            return {
                                abbreviation: null,
                                name: null,
                                id: null
                            };
                        }
                    }
                }).result.then(function() {
                    $state.go('party', null, { reload: 'party' });
                }, function() {
                    $state.go('party');
                });
            }]
        })
        .state('party.edit', {
            parent: 'party',
            url: '/{id}/edit',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/party/party-dialog.html',
                    controller: 'PartyDialogController',
                    controllerAs: 'vm',
                    backdrop: 'static',
                    size: 'lg',
                    resolve: {
                        entity: ['Party', function(Party) {
                            return Party.get({id : $stateParams.id}).$promise;
                        }]
                    }
                }).result.then(function() {
                    $state.go('party', null, { reload: 'party' });
                }, function() {
                    $state.go('^');
                });
            }]
        })
        .state('party.delete', {
            parent: 'party',
            url: '/{id}/delete',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/party/party-delete-dialog.html',
                    controller: 'PartyDeleteController',
                    controllerAs: 'vm',
                    size: 'md',
                    resolve: {
                        entity: ['Party', function(Party) {
                            return Party.get({id : $stateParams.id}).$promise;
                        }]
                    }
                }).result.then(function() {
                    $state.go('party', null, { reload: 'party' });
                }, function() {
                    $state.go('^');
                });
            }]
        });
    }

})();
