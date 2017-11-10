'use strict';

describe('Controller Tests', function() {

    describe('Atendimento Management Detail Controller', function() {
        var $scope, $rootScope;
        var MockEntity, MockPreviousState, MockAtendimento, MockConvenio, MockPessoa, MockProcedimento, MockMedico, MockSessao;
        var createController;

        beforeEach(inject(function($injector) {
            $rootScope = $injector.get('$rootScope');
            $scope = $rootScope.$new();
            MockEntity = jasmine.createSpy('MockEntity');
            MockPreviousState = jasmine.createSpy('MockPreviousState');
            MockAtendimento = jasmine.createSpy('MockAtendimento');
            MockConvenio = jasmine.createSpy('MockConvenio');
            MockPessoa = jasmine.createSpy('MockPessoa');
            MockProcedimento = jasmine.createSpy('MockProcedimento');
            MockMedico = jasmine.createSpy('MockMedico');
            MockSessao = jasmine.createSpy('MockSessao');
            

            var locals = {
                '$scope': $scope,
                '$rootScope': $rootScope,
                'entity': MockEntity,
                'previousState': MockPreviousState,
                'Atendimento': MockAtendimento,
                'Convenio': MockConvenio,
                'Pessoa': MockPessoa,
                'Procedimento': MockProcedimento,
                'Medico': MockMedico,
                'Sessao': MockSessao
            };
            createController = function() {
                $injector.get('$controller')("AtendimentoMySuffixDetailController", locals);
            };
        }));


        describe('Root Scope Listening', function() {
            it('Unregisters root scope listener upon scope destruction', function() {
                var eventType = 'fisioacusApp:atendimentoUpdate';

                createController();
                expect($rootScope.$$listenerCount[eventType]).toEqual(1);

                $scope.$destroy();
                expect($rootScope.$$listenerCount[eventType]).toBeUndefined();
            });
        });
    });

});
