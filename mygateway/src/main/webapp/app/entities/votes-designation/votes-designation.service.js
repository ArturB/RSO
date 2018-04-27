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
        var resourceUrl =  'msapp/' + 'api/municipalities/:id';

        return $resource(resourceUrl, {}, {
            'query': { method: 'GET', isArray: true},
            'get': {
                method: 'GET',
                transformResponse: function (data) {
                    if (data) {
                        data = angular.fromJson(data);
                    }
                    return data;
                }
            },
            'update': { method:'PUT' }
        });
    }
})();
