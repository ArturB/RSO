(function() {
    'use strict';
    angular
        .module('mygatewayApp')
        .factory('Candidate', Candidate);

    Candidate.$inject = ['$resource'];

    function Candidate ($resource) {
        var resourceUrl =  'msapp/' + 'api/candidates/:id';

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
            'update': { method:'PUT' },
            findByMunicipalityId: {
                url: 'msapp/api/municipalities/:municipalityId/candidates',
                method: 'GET',
                isArray: true,
                params: {
                    municipalityId: '@municipalityId',
                }
            }
        });
    }
})();
