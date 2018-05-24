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
                authorities: ['ROLE_OKW_LEADER'],
                pageTitle: 'Votes acceptance'
            },
            views: {
                'content@': {
                    templateUrl: 'app/entities/votes-acceptance/votes-sum.html',
                    controller: 'VotesAcceptanceController',
                    controllerAs: 'vm'
                }
            },
            resolve: {
            }
        });
    }
})();
