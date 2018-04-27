(function() {
    'use strict';
    angular
        .module('mygatewayApp')
        .factory('MyGroup', MyGroup);

    MyGroup.$inject = ['$resource'];

    function MyGroup ($resource) {
        var resourceUrl =  'msapp/' + 'api/my-groups/:id';

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
