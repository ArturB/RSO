/**
 * Created by defacto on 5/22/2018.
 */
(function() {
    'use strict';

    angular
        .module('mygatewayApp')
        .directive('isPeriod', isPeriod);

    isPeriod.$inject = ['ElectoralPeriod', 'PERIOD_FILTERING'];

    function isPeriod(ElectoralPeriod, PERIOD_FILTERING) {
        var directive = {
            restrict: 'A',
            link: linkFunc
        };

        return directive;

        function linkFunc(scope, element, attrs) {
            var  periods = attrs.isPeriod.replace(/\s+/g, '').split(',');

            var setVisible = function () {
                    element.removeClass('hidden');
                },
                setHidden = function () {
                    element.addClass('hidden');
                },
                defineVisibility = function (reset) {

                    if (reset) {
                        setVisible();
                    }

                    if(PERIOD_FILTERING) {
                        ElectoralPeriod.getCurrentPeriod().then(function (currentPeriod) {
                            if (periods.indexOf( currentPeriod.name) !== -1){
                                setVisible();
                            } else {
                                setHidden();
                            }
                        });
                    }
                };

            if (periods.length > 0) {
                defineVisibility(true);
            }
        }
    }
})();
