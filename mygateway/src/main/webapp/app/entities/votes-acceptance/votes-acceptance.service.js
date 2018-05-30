/**
 * Created by defacto on 5/22/2018.
 */
(function() {
    'use strict';
    angular
        .module('mygatewayApp')
        .factory('VotesAcceptance', VotesAcceptance);

    VotesAcceptance.$inject = ['$resource'];

    function VotesAcceptance($resource) {
        var resourceUrl =  'msapp/';

        return $resource(resourceUrl, {}, {
            acceptVotesFromDistrict: {
                url: 'msapp/api/districts/:districtId/:round/acceptVotes',
                method: 'POST',
                params: {
                    districtId: '@districtId',
                    round: '@round'
                }
            }
        });
    }
})();
