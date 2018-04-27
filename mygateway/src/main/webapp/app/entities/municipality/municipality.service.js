(function() {
    'use strict';
    angular
        .module('mygatewayApp')
        .factory('Municipality', Municipality);

    Municipality.$inject = ['$resource'];

    function Municipality ($resource) {
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
