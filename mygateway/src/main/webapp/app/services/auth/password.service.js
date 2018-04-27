(function() {
    'use strict';

    angular
        .module('mygatewayApp')
        .factory('Password', Password);

    Password.$inject = ['$resource'];

    function Password($resource) {
        var service = $resource('myuaa/api/account/change-password', {}, {});

        return service;
    }
})();
