/**
 * Created by defacto on 5/22/2018.
 */
(function() {
    'use strict';
    angular
        .module('mygatewayApp')
        .factory('VotesSum', VotesSum);

    VotesSum.$inject = ['$resource'];

    function VotesSum($resource) {
        var resourceUrl =  'msapp/';

        return $resource(resourceUrl, {}, {
            votesSumFromDistrict: {
                url: 'msapp/api/districts/:districtId/:round/votesSum',
                method: 'GET',
                isArray: true,
                params: {
                    districtId: '@districtId',
                    round: '@round'
                }
            },
            votesSumFromMunicipality: {
                url: 'msapp/api/municipalities/:municipalityId/:round/votesSum',
                method: 'GET',
                isArray: true,
                params: {
                    municipalityId: '@municipalityId',
                    round: '@round'
                }
            }
        });
    }
})();
