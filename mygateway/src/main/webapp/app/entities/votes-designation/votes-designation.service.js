/**
 * Created by defacto on 4/22/2018.
 */
(function() {
    'use strict';
    angular
        .module('mygatewayApp')
        .factory('VotesDesignation', VotesDesignation);

    VotesDesignation.$inject = ['$resource'];

    function VotesDesignation($resource) {
        var resourceUrl =  'msapp/' + 'api/votes_designation_pack/:id';

        return $resource(resourceUrl, {}, {
            // 'get': {
            //     method: 'GET',
            //     transformResponse: function (data) {
            //         if (data) {
            //             data = angular.fromJson(data);
            //         }
            //         return data;
            //     }
            // },
            findByUserId: {
                url: 'msapp/api/votes_designation_pack/getFromUser/:round/:userId',
                method: 'GET',
                isArray: true,
                params: {
                    round: '@round',
                    userId: '@userId'
                }
            },
            'update': { method:'PUT' },
            'save': {
                method:'POST',
                url: 'msapp/api/votes_designation_pack/:round',
                params: {
                    round: '@round',
                }
            },
            lol: {
                url: 'msapp/api/XXdistricts/debug',
                method: 'GET',
                isArray: true,
            },
        });
    }
})();
