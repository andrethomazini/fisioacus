'use strict';

describe('Controller Tests', function() {

    describe('Sessao Management Detail Controller', function() {
        var $scope, $rootScope;
        var MockEntity, MockPreviousState, MockSessao, MockProfissional, MockAtendimento;
        var createController;

        beforeEach(inject(function($injector) {
            $rootScope = $injector.get('$rootScope');
            $scope = $rootScope.$new();
            MockEntity = jasmine.createSpy('MockEntity');
            MockPreviousState = jasmine.createSpy('MockPreviousState');
            MockSessao = jasmine.createSpy('MockSessao');
            MockProfissional = jasmine.createSpy('MockProfissional');
            MockAtendimento = jasmine.createSpy('MockAtendimento');
            

            var locals = {
                '$scope': $scope,
                '$rootScope': $rootScope,
                'entity': MockEntity,
                'previousState': MockPreviousState,
                'Sessao': MockSessao,
                'Profissional': MockProfissional,
                'Atendimento': MockAtendimento
            };
            createController = function() {
                $injector.get('$controller')("SessaoMySuffixDetailController", locals);
            };
        }));


        describe('Root Scope Listening', function() {
            it('Unregisters root scope listener upon scope destruction', function() {
                var eventType = 'fisioacusApp:sessaoUpdate';

                createController();
                expect($rootScope.$$listenerCount[eventType]).toEqual(1);

                $scope.$destroy();
                expect($rootScope.$$listenerCount[eventType]).toBeUndefined();
            });
        });
    });

});
