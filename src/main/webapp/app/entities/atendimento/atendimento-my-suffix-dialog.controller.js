(function() {
    'use strict';

    angular
        .module('fisioacusApp')
        .controller('AtendimentoMySuffixDialogController', AtendimentoMySuffixDialogController);

    AtendimentoMySuffixDialogController.$inject = ['$timeout', '$scope', '$stateParams', '$uibModalInstance', '$q', 'entity', 'Atendimento', 'Convenio', 'Pessoa', 'Procedimento', 'Medico', 'Sessao'];

    function AtendimentoMySuffixDialogController ($timeout, $scope, $stateParams, $uibModalInstance, $q, entity, Atendimento, Convenio, Pessoa, Procedimento, Medico, Sessao) {
        var vm = this;

        vm.atendimento = entity;
        vm.clear = clear;
        vm.datePickerOpenStatus = {};
        vm.openCalendar = openCalendar;
        vm.save = save;
        vm.convenios = Convenio.query({filter: 'atendimento-is-null'});
        $q.all([vm.atendimento.$promise, vm.convenios.$promise]).then(function() {
            if (!vm.atendimento.convenioId) {
                return $q.reject();
            }
            return Convenio.get({id : vm.atendimento.convenioId}).$promise;
        }).then(function(convenio) {
            vm.convenios.push(convenio);
        });
        vm.pessoas = Pessoa.query({filter: 'atendimento-is-null'});
        $q.all([vm.atendimento.$promise, vm.pessoas.$promise]).then(function() {
            if (!vm.atendimento.pessoaId) {
                return $q.reject();
            }
            return Pessoa.get({id : vm.atendimento.pessoaId}).$promise;
        }).then(function(pessoa) {
            vm.pessoas.push(pessoa);
        });
        vm.procedimentos = Procedimento.query({filter: 'atendimento-is-null'});
        $q.all([vm.atendimento.$promise, vm.procedimentos.$promise]).then(function() {
            if (!vm.atendimento.procedimentoId) {
                return $q.reject();
            }
            return Procedimento.get({id : vm.atendimento.procedimentoId}).$promise;
        }).then(function(procedimento) {
            vm.procedimentos.push(procedimento);
        });
        vm.medicos = Medico.query({filter: 'atendimento-is-null'});
        $q.all([vm.atendimento.$promise, vm.medicos.$promise]).then(function() {
            if (!vm.atendimento.medicoId) {
                return $q.reject();
            }
            return Medico.get({id : vm.atendimento.medicoId}).$promise;
        }).then(function(medico) {
            vm.medicos.push(medico);
        });
        vm.sessaos = Sessao.query();

        $timeout(function (){
            angular.element('.form-group:eq(1)>input').focus();
        });

        function clear () {
            $uibModalInstance.dismiss('cancel');
        }

        function save () {
            vm.isSaving = true;
            if (vm.atendimento.id !== null) {
                Atendimento.update(vm.atendimento, onSaveSuccess, onSaveError);
            } else {
                Atendimento.save(vm.atendimento, onSaveSuccess, onSaveError);
            }
        }

        function onSaveSuccess (result) {
            $scope.$emit('fisioacusApp:atendimentoUpdate', result);
            $uibModalInstance.close(result);
            vm.isSaving = false;
        }

        function onSaveError () {
            vm.isSaving = false;
        }

        vm.datePickerOpenStatus.dtInicio = false;
        vm.datePickerOpenStatus.dtTermino = false;

        function openCalendar (date) {
            vm.datePickerOpenStatus[date] = true;
        }
    }
})();
