(function () {
    'use strict';

    angular
        .module('mygatewayApp')
        .factory('Register', Register);

    Register.$inject = ['$resource'];

    function Register ($resource) {
        return $resource('msapp/api/register', {}, {});
    }
})();
