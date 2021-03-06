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
            url: '/my-user?page',
            data: {
                authorities: ['ROLE_GKW_MEMBER', 'ROLE_ADMIN'],
                pageTitle: 'Użytkownicy'
            },
            views: {
                'content@': {
                    templateUrl: 'app/entities/my-user/my-users.html',
                    controller: 'MyUserController',
                    controllerAs: 'vm'
                }
            },
            params: {
                page: {
                    value: '1',
                    squash: true
                }
            },
            resolve: {
                pagingParams: ['$stateParams', 'PaginationUtil', function ($stateParams, PaginationUtil) {
                    return {
                        page: PaginationUtil.parsePage($stateParams.page)
                    };
                }]
            }
        })
        .state('my-user-detail', {
            parent: 'my-user',
            url: '/my-user/{id}',
            data: {
                authorities: ['ROLE_GKW_MEMBER', 'ROLE_ADMIN'],
                pageTitle: 'Użytkownicy'
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
                }],
                preset:{}
            }
        })
        .state('my-user-detail.edit', {
            parent: 'my-user-detail',
            url: '/detail/edit',
            data: {
                authorities: ['ROLE_GKW_MEMBER', 'ROLE_ADMIN']
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
                            return MyUser.get({id : $stateParams.id});
                        }],
                        preset:{}
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
                authorities: ['ROLE_GKW_MEMBER', 'ROLE_ADMIN']
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
                                role: null,
                                id: null,
                            }
                        },
                        preset: {}
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
                authorities: ['ROLE_GKW_MEMBER', 'ROLE_ADMIN']
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
                        }],
                        preset:{}
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
                authorities: ['ROLE_GKW_MEMBER', 'ROLE_ADMIN']
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
        })
            .state('my-user.disable', {
                parent: 'my-user',
                url: '/{id}/disable',
                data: {
                    authorities: [ 'ROLE_ADMIN']
                },
                onEnter: ['$stateParams', '$state', '$uibModal', function ($stateParams, $state, $uibModal) {
                    $uibModal.open({
                        templateUrl: 'app/entities/custom-user/custom-user-disable-dialog.html',
                        controller: 'CustomUserDisableController',
                        controllerAs: 'vm',
                        size: 'md',
                        resolve: {
                            entity: ['CustomUser', function (CustomUser) {
                                return CustomUser.get({id: $stateParams.id}).$promise;
                            }]
                        }
                    }).result.then(function () {
                        $state.go('my-user', null, {reload: 'my-user'});
                    }, function () {
                        $state.go('^');
                    });
                }]
            })
        ;
    }

})();
