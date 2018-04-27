/**
 * Created by defacto on 4/22/2018.
 */
(function() {
    'use strict';

    angular
        .module('mygatewayApp')
        .config(stateConfig);

    stateConfig.$inject = ['$stateProvider'];

    function stateConfig($stateProvider) {
        $stateProvider
        .state('votes-designation', {
            parent: 'entity',
            url: '/votes-designation',
            data: {
                authorities: ['ROLE_USER'],
                pageTitle: 'Votes designation'
            },
            views: {
                'content@': {
                    templateUrl: 'app/entities/votes-designation/votes-designation.html',
                    controller: 'VotesDesignationController',
                    controllerAs: 'vm'
                }
            },
            resolve: {
            }
        });
    }
})();
