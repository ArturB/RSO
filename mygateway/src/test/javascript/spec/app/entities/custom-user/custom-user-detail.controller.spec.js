'use strict';

describe('Controller Tests', function() {

    describe('CustomUser Management Detail Controller', function() {
        var $scope, $rootScope;
        var MockEntity, MockPreviousState, MockCustomUser, MockElectoralDistrict, MockMunicipality;
        var createController;

        beforeEach(inject(function($injector) {
            $rootScope = $injector.get('$rootScope');
            $scope = $rootScope.$new();
            MockEntity = jasmine.createSpy('MockEntity');
            MockPreviousState = jasmine.createSpy('MockPreviousState');
            MockCustomUser = jasmine.createSpy('MockCustomUser');
            MockElectoralDistrict = jasmine.createSpy('MockElectoralDistrict');
            MockMunicipality = jasmine.createSpy('MockMunicipality');
            

            var locals = {
                '$scope': $scope,
                '$rootScope': $rootScope,
                'entity': MockEntity,
                'previousState': MockPreviousState,
                'CustomUser': MockCustomUser,
                'ElectoralDistrict': MockElectoralDistrict,
                'Municipality': MockMunicipality
            };
            createController = function() {
                $injector.get('$controller')("CustomUserDetailController", locals);
            };
        }));


        describe('Root Scope Listening', function() {
            it('Unregisters root scope listener upon scope destruction', function() {
                var eventType = 'mygatewayApp:customUserUpdate';

                createController();
                expect($rootScope.$$listenerCount[eventType]).toEqual(1);

                $scope.$destroy();
                expect($rootScope.$$listenerCount[eventType]).toBeUndefined();
            });
        });
    });

});
