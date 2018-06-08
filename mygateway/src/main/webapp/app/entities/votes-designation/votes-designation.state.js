/**
 * Created by defacto on 4/22/2018.
 */
(function() {
    'use strict';

    angular
        .module('mygatewayApp')
        .config(stateConfig);

    stateConfig.$inject = ['$stateProvider'];

    function stateConfig($stateProvider) {
        $stateProvider
        .state('votes-designation', {
            parent: 'entity',
            url: '/votes-designation',
            data: {
                authorities: ['ROLE_OKW_MEMBER'],
                pageTitle: 'Wpisanie głosów'
            },
            views: {
                'content@': {
                    templateUrl: 'app/entities/votes-designation/votes-designation.html',
                    controller: 'VotesDesignationController',
                    controllerAs: 'vm'
                }
            },
            resolve: {
            }
        })
         .state('votes-designation.new', {
            parent: 'votes-designation',
            url: '/{userId}/{round}/{municipalityId}/{electoralDistrictId}/new',
            data: {
                authorities: ['ROLE_OKW_MEMBER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/votes-designation/votes-designation-dialog.html',
                    controller: 'VotesDesignationDialogController',
                    controllerAs: 'vm',
                    backdrop: 'static',
                    size: 'lg',
                    resolve: {
                        entity: ['Candidate', function (Candidate) {
                            return {
                                user_id:$stateParams.userId,
                                electoral_district_id:$stateParams.electoralDistrictId,
                                too_many_marks_cards_used : 0,
                                none_marks_cards_used : 0,
                                erased_marks_cards_used : 0,
                                candidate_votes :
                                    Candidate.findByMunicipalityIdAndRound(
                                        {
                                            municipalityId: $stateParams.municipalityId,
                                            round: $stateParams.round
                                        }).$promise
                                    .then(function(candidates){
                                        var candidate_votes = [];
                                        angular.forEach(candidates, function(candidate){
                                            candidate_votes.push({
                                                candidate:candidate,
                                                number_of_votes:0,
                                                candidate_id:candidate.candidate_id
                                            });
                                        });
                                        return candidate_votes;
                                    })
                            };
                        }]
                    }
                }).result.then(function() {
                    $state.go('votes-designation', null, { reload: 'votes-designation' });
                }, function() {
                    $state.go('votes-designation');
                });
            }]
        })
        .state('votes-designation.edit', {
            parent: 'votes-designation',
            url: '/{userId}/{round}/{designationPackId}/edit',
            data: {
                authorities: ['ROLE_OKW_MEMBER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/votes-designation/votes-designation-dialog.html',
                    controller: 'VotesDesignationDialogController',
                    controllerAs: 'vm',
                    backdrop: 'static',
                    size: 'lg',
                    resolve: {
                        entity: ['VotesDesignation', function(VotesDesignation) {
                            return VotesDesignation.findByUserId({
                                round: $stateParams.round,
                                userId: $stateParams.userId
                            }).$promise.then(function(result){
                                 for(var i = 0; i < result.length; i++){
                                     if(result[i].date == $stateParams.designationPackDate){
                                         return result[i];
                                     }
                                     if(!result[i].date && !$stateParams.designationPackDate){
                                         return result[i];
                                     }
                                 }
                            });
                        }]
                    }
                }).result.then(function() {
                    $state.go('votes-designation', null, { reload: 'votes-designation' });
                }, function() {
                    $state.go('^');
                });
            }]
        })
    }
})();
