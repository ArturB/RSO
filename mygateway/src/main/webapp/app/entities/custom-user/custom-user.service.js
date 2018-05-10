(function() {
    'use strict';
    angular
        .module('mygatewayApp')
        .factory('CustomUser', CustomUser);

    CustomUser.$inject = ['$resource'];

    function CustomUser ($resource) {
        var resourceUrl =  'msapp/' + 'api/custom-users/:id';

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
                url: 'msapp/api/municipalities/:municipalityId/users',
                method: 'GET',
                isArray: true,
                params: {
                    municipalityId: '@municipalityId',
                }
            },
            findByDistrictId: {
                url: 'msapp/api/districts/:districtId/users',
                method: 'GET',
                isArray: true,
                params: {
                    municipalityId: '@districtId',
                }
            }
        });
    }
})();
