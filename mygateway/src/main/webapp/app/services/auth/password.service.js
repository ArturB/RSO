(function() {
    'use strict';

    angular
        .module('mygatewayApp')
        .factory('Password', Password);

    Password.$inject = ['$resource'];

    function Password($resource) {
        var service = $resource('msapp/api/account/change-password', {}, {});

        return service;
    }
})();
