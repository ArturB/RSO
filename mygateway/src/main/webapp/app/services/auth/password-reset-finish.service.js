(function() {
    'use strict';

    angular
        .module('mygatewayApp')
        .factory('PasswordResetFinish', PasswordResetFinish);

    PasswordResetFinish.$inject = ['$resource'];

    function PasswordResetFinish($resource) {
        var service = $resource('msapp/api/account/reset-password/finish', {}, {});

        return service;
    }
})();
