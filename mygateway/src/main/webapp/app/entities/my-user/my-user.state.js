(function() {
    'use strict';

    angular
        .module('mygatewayApp')
        .config(stateConfig);

    stateConfig.$inject = ['$stateProvider'];

    function stateConfig($stateProvider) {
        $stateProvider
        .state('my-user', {
            parent: 'entity',
            url: '/my-user',
            data: {
                authorities: ['ROLE_USER'],
                pageTitle: 'MyUsers'
            },
            views: {
                'content@': {
                    templateUrl: 'app/entities/my-user/my-users.html',
                    controller: 'MyUserController',
                    controllerAs: 'vm'
                }
            },
            resolve: {
            }
        })
        .state('my-user-detail', {
            parent: 'my-user',
            url: '/my-user/{id}',
            data: {
                authorities: ['ROLE_USER'],
                pageTitle: 'MyUser'
            },
            views: {
                'content@': {
                    templateUrl: 'app/entities/my-user/my-user-detail.html',
                    controller: 'MyUserDetailController',
                    controllerAs: 'vm'
                }
            },
            resolve: {
                entity: ['$stateParams', 'MyUser', function($stateParams, MyUser) {
                    return MyUser.get({id : $stateParams.id}).$promise;
                }],
                previousState: ["$state", function ($state) {
                    var currentStateData = {
                        name: $state.current.name || 'my-user',
                        params: $state.params,
                        url: $state.href($state.current.name, $state.params)
                    };
                    return currentStateData;
                }]
            }
        })
        .state('my-user-detail.edit', {
            parent: 'my-user-detail',
            url: '/detail/edit',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/my-user/my-user-dialog.html',
                    controller: 'MyUserDialogController',
                    controllerAs: 'vm',
                    backdrop: 'static',
                    size: 'lg',
                    resolve: {
                        entity: ['MyUser', function(MyUser) {
                            return MyUser.get({id : $stateParams.id}).$promise;
                        }]
                    }
                }).result.then(function() {
                    $state.go('^', {}, { reload: false });
                }, function() {
                    $state.go('^');
                });
            }]
        })
        .state('my-user.new', {
            parent: 'my-user',
            url: '/new',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/my-user/my-user-dialog.html',
                    controller: 'MyUserDialogController',
                    controllerAs: 'vm',
                    backdrop: 'static',
                    size: 'lg',
                    resolve: {
                        entity: function () {
                            return {
                                username: null,
                                name: null,
                                surname: null,
                                documentType: null,
                                documentNr: null,
                                email: null,
                                birthdate: null,
                                pesel: null,
                                id: null
                            };
                        }
                    }
                }).result.then(function() {
                    $state.go('my-user', null, { reload: 'my-user' });
                }, function() {
                    $state.go('my-user');
                });
            }]
        })
        .state('my-user.edit', {
            parent: 'my-user',
            url: '/{id}/edit',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/my-user/my-user-dialog.html',
                    controller: 'MyUserDialogController',
                    controllerAs: 'vm',
                    backdrop: 'static',
                    size: 'lg',
                    resolve: {
                        entity: ['MyUser', function(MyUser) {
                            return MyUser.get({id : $stateParams.id}).$promise;
                        }]
                    }
                }).result.then(function() {
                    $state.go('my-user', null, { reload: 'my-user' });
                }, function() {
                    $state.go('^');
                });
            }]
        })
        .state('my-user.delete', {
            parent: 'my-user',
            url: '/{id}/delete',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/my-user/my-user-delete-dialog.html',
                    controller: 'MyUserDeleteController',
                    controllerAs: 'vm',
                    size: 'md',
                    resolve: {
                        entity: ['MyUser', function(MyUser) {
                            return MyUser.get({id : $stateParams.id}).$promise;
                        }]
                    }
                }).result.then(function() {
                    $state.go('my-user', null, { reload: 'my-user' });
                }, function() {
                    $state.go('^');
                });
            }]
        });
    }

})();
