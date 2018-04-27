(function() {
    'use strict';
    angular
        .module('mygatewayApp')
        .factory('ElectoralDistrict', ElectoralDistrict);

    ElectoralDistrict.$inject = ['$resource'];

    function ElectoralDistrict ($resource) {
        var resourceUrl = 'msapp/' + 'api/electoral-districts/:id';

        return $resource(resourceUrl, {}, {
            'query': {method: 'GET', isArray: true},
            'get': {
                method: 'GET',
                transformResponse: function (data) {
                    if (data) {
                        data = angular.fromJson(data);
                    }
                    return data;
                }
            },
            'update': {method: 'PUT'},
            findByMunicipalityId: {
                url: 'msapp/api/municipalities/:municipalityId/electoral_districts',
                method: 'GET',
                isArray: true,
                params: {
                    municipalityId: '@municipalityId',
                }
            }
        });
    }
})();
