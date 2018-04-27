'use strict';

describe('Controller Tests', function() {

    describe('ElectoralDistrict Management Detail Controller', function() {
        var $scope, $rootScope;
        var MockEntity, MockPreviousState, MockElectoralDistrict, MockMunicipality;
        var createController;

        beforeEach(inject(function($injector) {
            $rootScope = $injector.get('$rootScope');
            $scope = $rootScope.$new();
            MockEntity = jasmine.createSpy('MockEntity');
            MockPreviousState = jasmine.createSpy('MockPreviousState');
            MockElectoralDistrict = jasmine.createSpy('MockElectoralDistrict');
            MockMunicipality = jasmine.createSpy('MockMunicipality');
            

            var locals = {
                '$scope': $scope,
                '$rootScope': $rootScope,
                'entity': MockEntity,
                'previousState': MockPreviousState,
                'ElectoralDistrict': MockElectoralDistrict,
                'Municipality': MockMunicipality
            };
            createController = function() {
                $injector.get('$controller')("ElectoralDistrictDetailController", locals);
            };
        }));


        describe('Root Scope Listening', function() {
            it('Unregisters root scope listener upon scope destruction', function() {
                var eventType = 'mygatewayApp:electoralDistrictUpdate';

                createController();
                expect($rootScope.$$listenerCount[eventType]).toEqual(1);

                $scope.$destroy();
                expect($rootScope.$$listenerCount[eventType]).toBeUndefined();
            });
        });
    });

});
