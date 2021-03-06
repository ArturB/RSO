'use strict';

describe('Controller Tests', function() {

    describe('MyUser Management Detail Controller', function() {
        var $scope, $rootScope;
        var MockEntity, MockPreviousState, MockMyUser, MockElectoralDistrict, MockMunicipality;
        var createController;

        beforeEach(inject(function($injector) {
            $rootScope = $injector.get('$rootScope');
            $scope = $rootScope.$new();
            MockEntity = jasmine.createSpy('MockEntity');
            MockPreviousState = jasmine.createSpy('MockPreviousState');
            MockMyUser = jasmine.createSpy('MockMyUser');
            MockElectoralDistrict = jasmine.createSpy('MockElectoralDistrict');
            MockMunicipality = jasmine.createSpy('MockMunicipality');
            

            var locals = {
                '$scope': $scope,
                '$rootScope': $rootScope,
                'entity': MockEntity,
                'previousState': MockPreviousState,
                'MyUser': MockMyUser,
                'ElectoralDistrict': MockElectoralDistrict,
                'Municipality': MockMunicipality
            };
            createController = function() {
                $injector.get('$controller')("MyUserDetailController", locals);
            };
        }));


        describe('Root Scope Listening', function() {
            it('Unregisters root scope listener upon scope destruction', function() {
                var eventType = 'mygatewayApp:myUserUpdate';

                createController();
                expect($rootScope.$$listenerCount[eventType]).toEqual(1);

                $scope.$destroy();
                expect($rootScope.$$listenerCount[eventType]).toBeUndefined();
            });
        });
    });

});
