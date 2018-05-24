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
        .state('votes-sum', {
            parent: 'entity',
            url: '/votes-sum',
            data: {
                authorities: ['ROLE_OKW_LEADER'],
                pageTitle: 'Votes sum'
            },
            views: {
                'content@': {
                    templateUrl: 'app/entities/votes-sum/votes-sum.html',
                    controller: 'VotesSumController',
                    controllerAs: 'vm'
                }
            },
            resolve: {
            }
        });
    }
})();
