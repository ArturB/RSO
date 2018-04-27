(function() {
    'use strict';

    angular
        .module('mygatewayApp')
        .config(stateConfig);

    stateConfig.$inject = ['$stateProvider'];

    function stateConfig($stateProvider) {
        $stateProvider
        .state('my-group', {
            parent: 'entity',
            url: '/my-group',
            data: {
                authorities: ['ROLE_USER'],
                pageTitle: 'MyGroups'
            },
            views: {
                'content@': {
                    templateUrl: 'app/entities/my-group/my-groups.html',
                    controller: 'MyGroupController',
                    controllerAs: 'vm'
                }
            },
            resolve: {
            }
        })
        .state('my-group-detail', {
            parent: 'my-group',
            url: '/my-group/{id}',
            data: {
                authorities: ['ROLE_USER'],
                pageTitle: 'MyGroup'
            },
            views: {
                'content@': {
                    templateUrl: 'app/entities/my-group/my-group-detail.html',
                    controller: 'MyGroupDetailController',
                    controllerAs: 'vm'
                }
            },
            resolve: {
                entity: ['$stateParams', 'MyGroup', function($stateParams, MyGroup) {
                    return MyGroup.get({id : $stateParams.id}).$promise;
                }],
                previousState: ["$state", function ($state) {
                    var currentStateData = {
                        name: $state.current.name || 'my-group',
                        params: $state.params,
                        url: $state.href($state.current.name, $state.params)
                    };
                    return currentStateData;
                }]
            }
        })
        .state('my-group-detail.edit', {
            parent: 'my-group-detail',
            url: '/detail/edit',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/my-group/my-group-dialog.html',
                    controller: 'MyGroupDialogController',
                    controllerAs: 'vm',
                    backdrop: 'static',
                    size: 'lg',
                    resolve: {
                        entity: ['MyGroup', function(MyGroup) {
                            return MyGroup.get({id : $stateParams.id}).$promise;
                        }]
                    }
                }).result.then(function() {
                    $state.go('^', {}, { reload: false });
                }, function() {
                    $state.go('^');
                });
            }]
        })
        .state('my-group.new', {
            parent: 'my-group',
            url: '/new',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/my-group/my-group-dialog.html',
                    controller: 'MyGroupDialogController',
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
                    $state.go('my-group', null, { reload: 'my-group' });
                }, function() {
                    $state.go('my-group');
                });
            }]
        })
        .state('my-group.edit', {
            parent: 'my-group',
            url: '/{id}/edit',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/my-group/my-group-dialog.html',
                    controller: 'MyGroupDialogController',
                    controllerAs: 'vm',
                    backdrop: 'static',
                    size: 'lg',
                    resolve: {
                        entity: ['MyGroup', function(MyGroup) {
                            return MyGroup.get({id : $stateParams.id}).$promise;
                        }]
                    }
                }).result.then(function() {
                    $state.go('my-group', null, { reload: 'my-group' });
                }, function() {
                    $state.go('^');
                });
            }]
        })
        .state('my-group.delete', {
            parent: 'my-group',
            url: '/{id}/delete',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/my-group/my-group-delete-dialog.html',
                    controller: 'MyGroupDeleteController',
                    controllerAs: 'vm',
                    size: 'md',
                    resolve: {
                        entity: ['MyGroup', function(MyGroup) {
                            return MyGroup.get({id : $stateParams.id}).$promise;
                        }]
                    }
                }).result.then(function() {
                    $state.go('my-group', null, { reload: 'my-group' });
                }, function() {
                    $state.go('^');
                });
            }]
        });
    }

})();
