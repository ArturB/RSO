(function() {
    'use strict';

    angular
        .module('mygatewayApp')
        .config(bootstrapMaterialDesignConfig);

    function bootstrapMaterialDesignConfig() {
        $.material.init();

    }
})();
