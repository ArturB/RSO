(function() {
    'use strict';

    angular
        .module('mygatewayApp')
        .config(stateConfig);

    stateConfig.$inject = ['$stateProvider'];

    function stateConfig($stateProvider) {
        $stateProvider.state('gateway', {
            parent: 'admin',
            url: '/gateway',
            data: {
                authorities: ['ROLE_ADMIN'],
                pageTitle: 'Gateway'
            },
            views: {
                'content@': {
                    templateUrl: 'app/admin/gateway/gateway.html',
                    controller: 'GatewayController',
                    controllerAs: 'vm'
                }
            }
        });
    }
})();
