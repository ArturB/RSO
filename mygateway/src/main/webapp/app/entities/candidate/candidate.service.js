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
                transformResponse: ['Municipality', function (Municipality, data) {
                    if (data) {
                        data = angular.fromJson(data);
                    }
                    data.municipality = Municipality.get({id:data.municipality_id});
                    return data;
                }]
            },
            'update': { method:'PUT' },
            findByMunicipalityId: {
                url: 'msapp/api/municipalities/:municipalityId/candidates',
                method: 'GET',
                isArray: true,
                params: {
                    municipalityId: '@municipalityId'
                }
            },
            findByMunicipalityIdAndRound: {
                url: 'msapp/api/municipalities/:municipalityId/:round/candidates',
                method: 'GET',
                isArray: true,
                params: {
                    municipalityId: '@municipalityId',
                    round: '@round'
                }
            }
        });
    }
})();
