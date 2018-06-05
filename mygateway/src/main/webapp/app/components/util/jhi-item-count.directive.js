(function() {
    'use strict';

    var jhiItemCount = {
        template: '<div class="info">' +
        'Pokazujemy {{(($ctrl.page - 1) * $ctrl.itemsPerPage) == 0 ? 1 : (($ctrl.page - 1) * $ctrl.itemsPerPage +' +
        ' 1)}} - ' +
        '{{($ctrl.page * $ctrl.itemsPerPage) < $ctrl.queryCount ? ($ctrl.page * $ctrl.itemsPerPage) : $ctrl.queryCount}} ' +
        'z {{$ctrl.queryCount}} elementów.' +
        '</div>',
        bindings: {
            page: '<',
            queryCount: '<total',
            itemsPerPage: '<'
        }
    };

    angular
        .module('mygatewayApp')
        .component('jhiItemCount', jhiItemCount);
})();
