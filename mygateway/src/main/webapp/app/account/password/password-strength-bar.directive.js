/* globals $ */
(function() {
    'use strict';

    angular
        .module('mygatewayApp')
        .directive('passwordStrengthBar', passwordStrengthBar);

    function passwordStrengthBar () {
        var directive = {
            replace: true,
            restrict: 'E',
            template: '<div id="strength">' +
                '<small>Siła hasła:</small>' +
                '<ul id="strengthBar">' +
                '<li class="point"></li><li class="point"></li><li class="point"></li><li class="point"></li><li class="point"></li>' +
                '</ul>' +
                '</div>',
            scope: {
                passwordToCheck: '='
            },
            link: linkFunc
        };

        return directive;

        /* private helper methods*/

        function linkFunc(scope, iElement) {
            var strength = {
                colors: ['#F00', '#F33', '#F66', '#F88', '#FAA', '#0F0'],
                mesureStrength: function (p) {

                    var _force = 0;
                    var _regex = /[$-/:-?{-~!"^_`\[\]]/g; // "

                    var _lowerLetters = /[a-z]+/.test(p);
                    var _upperLetters = /[A-Z]+/.test(p);
                    var _numbers = /[0-9]+/.test(p);
                    // var _symbols = _regex.test(p);
                    var _symbols =  /[ !@#$%^&*()_+\-=\[\]{};':"\\|,.<>\/?]/.test(p);

                    var _flags = [_lowerLetters, _upperLetters, _numbers, _symbols];
                    var _passedMatches = $.grep(_flags, function (el) {
                        return el === true;
                    }).length;

                    if(p.length >= 8 ){
                        _passedMatches +=1 ;
                    }

                    return _passedMatches;

                },
                getColor: function (s) {
                    return { idx: s , col: this.colors[s] };
                }
            };
            scope.$watch('passwordToCheck', function (password) {
                if (password) {
                    var c = strength.getColor(strength.mesureStrength(password));
                    iElement.removeClass('ng-hide');
                    iElement.find('ul').children('li')
                        .css({ 'background-color': '#DDD' })
                        .slice(0, c.idx)
                        .css({ 'background-color': c.col });
                }
            });
        }
    }
})();
