'use strict';

describe('Controller Tests', function() {

    describe('Candidate Management Detail Controller', function() {
        var $scope, $rootScope;
        var MockEntity, MockPreviousState, MockCandidate, MockParty, MockMunicipality;
        var createController;

        beforeEach(inject(function($injector) {
            $rootScope = $injector.get('$rootScope');
            $scope = $rootScope.$new();
            MockEntity = jasmine.createSpy('MockEntity');
            MockPreviousState = jasmine.createSpy('MockPreviousState');
            MockCandidate = jasmine.createSpy('MockCandidate');
            MockParty = jasmine.createSpy('MockParty');
            MockMunicipality = jasmine.createSpy('MockMunicipality');
            

            var locals = {
                '$scope': $scope,
                '$rootScope': $rootScope,
                'entity': MockEntity,
                'previousState': MockPreviousState,
                'Candidate': MockCandidate,
                'Party': MockParty,
                'Municipality': MockMunicipality
            };
            createController = function() {
                $injector.get('$controller')("CandidateDetailController", locals);
            };
        }));


        describe('Root Scope Listening', function() {
            it('Unregisters root scope listener upon scope destruction', function() {
                var eventType = 'mygatewayApp:candidateUpdate';

                createController();
                expect($rootScope.$$listenerCount[eventType]).toEqual(1);

                $scope.$destroy();
                expect($rootScope.$$listenerCount[eventType]).toBeUndefined();
            });
        });
    });

});
