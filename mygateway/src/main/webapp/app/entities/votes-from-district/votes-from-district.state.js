(function() {
    'use strict';

    angular
        .module('mygatewayApp')
        .config(stateConfig);

    stateConfig.$inject = ['$stateProvider'];

    function stateConfig($stateProvider) {
        $stateProvider
        .state('votes-from-district', {
            parent: 'entity',
            url: '/votes-from-district',
            data: {
                authorities: ['ROLE_USER'],
                pageTitle: 'VotesFromDistricts'
            },
            views: {
                'content@': {
                    templateUrl: 'app/entities/votes-from-district/votes-from-districts.html',
                    controller: 'VotesFromDistrictController',
                    controllerAs: 'vm'
                }
            },
            resolve: {
            }
        })
        .state('votes-from-district-detail', {
            parent: 'votes-from-district',
            url: '/votes-from-district/{id}',
            data: {
                authorities: ['ROLE_USER'],
                pageTitle: 'VotesFromDistrict'
            },
            views: {
                'content@': {
                    templateUrl: 'app/entities/votes-from-district/votes-from-district-detail.html',
                    controller: 'VotesFromDistrictDetailController',
                    controllerAs: 'vm'
                }
            },
            resolve: {
                entity: ['$stateParams', 'VotesFromDistrict', function($stateParams, VotesFromDistrict) {
                    return VotesFromDistrict.get({id : $stateParams.id}).$promise;
                }],
                previousState: ["$state", function ($state) {
                    var currentStateData = {
                        name: $state.current.name || 'votes-from-district',
                        params: $state.params,
                        url: $state.href($state.current.name, $state.params)
                    };
                    return currentStateData;
                }]
            }
        })
        .state('votes-from-district-detail.edit', {
            parent: 'votes-from-district-detail',
            url: '/detail/edit',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/votes-from-district/votes-from-district-dialog.html',
                    controller: 'VotesFromDistrictDialogController',
                    controllerAs: 'vm',
                    backdrop: 'static',
                    size: 'lg',
                    resolve: {
                        entity: ['VotesFromDistrict', function(VotesFromDistrict) {
                            return VotesFromDistrict.get({id : $stateParams.id}).$promise;
                        }]
                    }
                }).result.then(function() {
                    $state.go('^', {}, { reload: false });
                }, function() {
                    $state.go('^');
                });
            }]
        })
        .state('votes-from-district.new', {
            parent: 'votes-from-district',
            url: '/new',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/votes-from-district/votes-from-district-dialog.html',
                    controller: 'VotesFromDistrictDialogController',
                    controllerAs: 'vm',
                    backdrop: 'static',
                    size: 'lg',
                    resolve: {
                        entity: function () {
                            return {
                                nrOfVotes: null,
                                date: null,
                                type: null,
                                id: null
                            };
                        }
                    }
                }).result.then(function() {
                    $state.go('votes-from-district', null, { reload: 'votes-from-district' });
                }, function() {
                    $state.go('votes-from-district');
                });
            }]
        })
        .state('votes-from-district.edit', {
            parent: 'votes-from-district',
            url: '/{id}/edit',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/votes-from-district/votes-from-district-dialog.html',
                    controller: 'VotesFromDistrictDialogController',
                    controllerAs: 'vm',
                    backdrop: 'static',
                    size: 'lg',
                    resolve: {
                        entity: ['VotesFromDistrict', function(VotesFromDistrict) {
                            return VotesFromDistrict.get({id : $stateParams.id}).$promise;
                        }]
                    }
                }).result.then(function() {
                    $state.go('votes-from-district', null, { reload: 'votes-from-district' });
                }, function() {
                    $state.go('^');
                });
            }]
        })
        .state('votes-from-district.delete', {
            parent: 'votes-from-district',
            url: '/{id}/delete',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/votes-from-district/votes-from-district-delete-dialog.html',
                    controller: 'VotesFromDistrictDeleteController',
                    controllerAs: 'vm',
                    size: 'md',
                    resolve: {
                        entity: ['VotesFromDistrict', function(VotesFromDistrict) {
                            return VotesFromDistrict.get({id : $stateParams.id}).$promise;
                        }]
                    }
                }).result.then(function() {
                    $state.go('votes-from-district', null, { reload: 'votes-from-district' });
                }, function() {
                    $state.go('^');
                });
            }]
        });
    }

})();
