(function() {
    'use strict';
    angular
        .module('mygatewayApp')
        .factory('MyUser', MyUser);

    MyUser.$inject = ['$resource', 'DateUtils'];

    function MyUser ($resource, DateUtils) {
        var resourceUrl =  'msapp/' + 'api/my-users/:id';

        return $resource(resourceUrl, {}, {
            'query': { method: 'GET', isArray: true},
            'get': {
                method: 'GET',
                transformResponse: function (data) {
                    if (data) {
                        data = angular.fromJson(data);
                        data.birthdate = DateUtils.convertLocalDateFromServer(data.birthdate);
                    }
                    return data;
                }
            },
            'update': {
                method: 'PUT',
                transformRequest: function (data) {
                    var copy = angular.copy(data);
                    copy.birthdate = DateUtils.convertLocalDateToServer(copy.birthdate);
                    return angular.toJson(copy);
                }
            },
            'save': {
                method: 'POST',
                transformRequest: function (data) {
                    var copy = angular.copy(data);
                    copy.birthdate = DateUtils.convertLocalDateToServer(copy.birthdate);
                    return angular.toJson(copy);
                }
            }
        });
    }
})();
