/**
 * Created by defacto on 5/22/2018.
 */
(function() {
    'use strict';

    angular
        .module('mygatewayApp')
        .config(stateConfig);

    stateConfig.$inject = ['$stateProvider'];

    function stateConfig($stateProvider) {
        $stateProvider
        .state('votes-acceptance', {
            parent: 'entity',
            url: '/votes-acceptance',
            data: {
                authorities: ['ROLE_OKW_LEADER','ROLE_GKW_LEADER'],
                pageTitle: 'Zatwierdzanie głosów'
            },
            views: {
                'content@': {
                    templateUrl: 'app/entities/votes-acceptance/votes-acceptance.html',
                    controller: 'VotesAcceptanceController',
                    controllerAs: 'vm'
                }
            },
            resolve: {
            }
        });
    }
})();
