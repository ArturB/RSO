(function() {
    'use strict';

    angular
        .module('mygatewayApp')
        .factory('PasswordResetInit', PasswordResetInit);

    PasswordResetInit.$inject = ['$resource'];

    function PasswordResetInit($resource) {
        var service = $resource('msapp/api/account/reset-password/init', {}, {});

        return service;
    }
})();
