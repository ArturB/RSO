(function() {
    'use strict';

    angular
        .module('mygatewayApp')
        .config(stateConfig);

    stateConfig.$inject = ['$stateProvider'];

    function stateConfig($stateProvider) {

        $stateProvider
            .state('custom-user', {
                parent: 'entity',
                url: '/custom-user',
                data: {
                    authorities: ['ROLE_GKW_MEMBER', 'ROLE_ADMIN'],
                    pageTitle: 'Użytkownicy'
                },
                views: {
                    'content@': {
                        templateUrl: 'app/entities/custom-user/custom-users.html',
                        controller: 'CustomUserController',
                        controllerAs: 'vm'
                    }
                },
                resolve: {}
            })
            .state('custom-user-detail', {
                parent: 'custom-user',
                url: '/custom-user/{id}',
                data: {
                    authorities: ['ROLE_GKW_MEMBER', 'ROLE_ADMIN'],
                    pageTitle: 'CustomUser'
                },
                views: {
                    'content@': {
                        templateUrl: 'app/entities/custom-user/custom-user-detail.html',
                        controller: 'CustomUserDetailController',
                        controllerAs: 'vm'
                    }
                },
                resolve: {
                    entity: ['$stateParams', 'CustomUser', function ($stateParams, CustomUser) {
                        return CustomUser.get({id: $stateParams.id}).$promise;
                    }],
                    previousState: ["$state", function ($state) {
                        var currentStateData = {
                            name: $state.current.name || 'custom-user',
                            params: $state.params,
                            url: $state.href($state.current.name, $state.params)
                        };
                        return currentStateData;
                    }]
                }
            })
            .state('custom-user-detail.edit', {
                parent: 'custom-user-detail',
                url: '/detail/edit',
                data: {
                    authorities: ['ROLE_GKW_MEMBER', 'ROLE_ADMIN']
                },
                onEnter: ['$stateParams', '$state', '$uibModal', function ($stateParams, $state, $uibModal) {
                    $uibModal.open({
                        templateUrl: 'app/entities/custom-user/custom-user-dialog.html',
                        controller: 'CustomUserDialogController',
                        controllerAs: 'vm',
                        backdrop: 'static',
                        size: 'lg',
                        resolve: {
                            entity: ['CustomUser', function (CustomUser) {
                                return CustomUser.get({id: $stateParams.id}).$promise;
                            }]
                        }
                    }).result.then(function () {
                        $state.go('^', {}, {reload: false});
                    }, function () {
                        $state.go('^');
                    });
                }]
            })
            .state('custom-user.new', {
                parent: 'custom-user',
                url: '/new',
                data: {
                    authorities: ['ROLE_GKW_MEMBER', 'ROLE_ADMIN']
                },
                onEnter: ['$stateParams', '$state', '$uibModal', function ($stateParams, $state, $uibModal) {
                    $uibModal.open({
                        templateUrl: 'app/entities/custom-user/custom-user-dialog.html',
                        controller: 'CustomUserDialogController',
                        controllerAs: 'vm',
                        backdrop: 'static',
                        size: 'lg',
                        resolve: {
                            entity: function () {
                                return {
                                    username: null,
                                    role: null,
                                    id: null
                                };
                            }
                        }
                    }).result.then(function () {
                        $state.go('custom-user', null, {reload: 'custom-user'});
                    }, function () {
                        $state.go('custom-user');
                    });
                }]
            })
            .state('custom-user.edit', {
                parent: 'custom-user',
                url: '/{id}/edit',
                data: {
                    authorities: ['ROLE_GKW_MEMBER', 'ROLE_ADMIN']
                },
                onEnter: ['$stateParams', '$state', '$uibModal', function ($stateParams, $state, $uibModal) {
                    $uibModal.open({
                        templateUrl: 'app/entities/custom-user/custom-user-dialog.html',
                        controller: 'CustomUserDialogController',
                        controllerAs: 'vm',
                        backdrop: 'static',
                        size: 'lg',
                        resolve: {
                            entity: ['CustomUser', function (CustomUser) {
                                return CustomUser.get({id: $stateParams.id}).$promise;
                            }]
                        }
                    }).result.then(function () {
                        $state.go('custom-user', null, {reload: 'custom-user'});
                    }, function () {
                        $state.go('^');
                    });
                }]
            })
            .state('custom-user.delete', {
                parent: 'custom-user',
                url: '/{id}/delete',
                data: {
                    authorities: ['ROLE_GKW_MEMBER', 'ROLE_ADMIN']
                },
                onEnter: ['$stateParams', '$state', '$uibModal', function ($stateParams, $state, $uibModal) {
                    $uibModal.open({
                        templateUrl: 'app/entities/custom-user/custom-user-delete-dialog.html',
                        controller: 'CustomUserDeleteController',
                        controllerAs: 'vm',
                        size: 'md',
                        resolve: {
                            entity: ['CustomUser', function (CustomUser) {
                                return CustomUser.get({id: $stateParams.id}).$promise;
                            }]
                        }
                    }).result.then(function () {
                        $state.go('custom-user', null, {reload: 'custom-user'});
                    }, function () {
                        $state.go('^');
                    });
                }]
            })
            .state('custom-user.disable', {
                parent: 'custom-user',
                url: '/{id}/disable',
                data: {
                    authorities: ['ROLE_GKW_MEMBER', 'ROLE_ADMIN']
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
                        $state.go('custom-user', null, {reload: 'custom-user'});
                    }, function () {
                        $state.go('^');
                    });
                }]
            })
            .state('custom-user.bigNew', {
                parent: 'custom-user',
                url: '/bignew/{municipalityId}/{electoralDistrictId}',
                data: {
                    authorities: ['ROLE_GKW_MEMBER', 'ROLE_ADMIN']
                },
                onEnter: ['$stateParams', '$state', '$uibModal', function ($stateParams, $state, $uibModal) {
                    $uibModal.open({
                        templateUrl: 'app/entities/my-user/my-user-dialog.html',
                        controller: 'MyUserDialogController',
                        controllerAs: 'vm',
                        backdrop: 'static',
                        size: 'lg',
                        resolve: {
                            preset: {
                                municipalityId: $stateParams.municipalityId,
                                electoralDistrictId: $stateParams.electoralDistrictId
                            },
                            entity:{}
                        }
                    }).result.then(function () {
                        $state.go('custom-user', null, {reload: 'custom-user'});
                    }, function () {
                        $state.go('custom-user');
                    });
                }]
            })
            .state('custom-user.bigEdit', {
                parent: 'custom-user',
                url: '/bigEdit/{userId}/{municipalityId}/{electoralDistrictId}',
                data: {
                    authorities: ['ROLE_GKW_MEMBER', 'ROLE_ADMIN']
                },
                onEnter: ['$stateParams', '$state', '$uibModal', function ($stateParams, $state, $uibModal) {
                    $uibModal.open({
                        templateUrl: 'app/entities/my-user/my-user-dialog.html',
                        controller: 'MyUserDialogController',
                        controllerAs: 'vm',
                        backdrop: 'static',
                        size: 'lg',
                        resolve: {
                            entity: ['MyUser', function (MyUser) {
                                return MyUser.get({id: $stateParams.userId}).$promise;
                            }],
                            preset: {
                                municipalityId: $stateParams.municipalityId,
                                electoralDistrictId: $stateParams.electoralDistrictId
                            }
                        }
                    }).result.then(function () {
                        $state.go('custom-user', null, {reload: 'custom-user'});
                    }, function () {
                        $state.go('custom-user');
                    });
                }]
            });
    }

})();
