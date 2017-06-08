(function() {
    'use strict';

    angular
        .module('fisioacusApp')
        .controller('SessaoMySuffixDialogController', SessaoMySuffixDialogController);

    SessaoMySuffixDialogController.$inject = ['$timeout', '$scope', '$stateParams', '$uibModalInstance', 'entity', 'Sessao', 'Profissional', 'Atendimento'];

    function SessaoMySuffixDialogController ($timeout, $scope, $stateParams, $uibModalInstance, entity, Sessao, Profissional, Atendimento) {
        var vm = this;

        vm.sessao = entity;
        vm.clear = clear;
        vm.datePickerOpenStatus = {};
        vm.openCalendar = openCalendar;
        vm.save = save;
        vm.profissionals = Profissional.query();
        vm.atendimentos = Atendimento.query();

        $timeout(function (){
            angular.element('.form-group:eq(1)>input').focus();
        });

        function clear () {
            $uibModalInstance.dismiss('cancel');
        }

        function save () {
            vm.isSaving = true;
            if (vm.sessao.id !== null) {
                Sessao.update(vm.sessao, onSaveSuccess, onSaveError);
            } else {
                Sessao.save(vm.sessao, onSaveSuccess, onSaveError);
            }
        }

        function onSaveSuccess (result) {
            $scope.$emit('fisioacusApp:sessaoUpdate', result);
            $uibModalInstance.close(result);
            vm.isSaving = false;
        }

        function onSaveError () {
            vm.isSaving = false;
        }

        vm.datePickerOpenStatus.dtInicio = false;

        function openCalendar (date) {
            vm.datePickerOpenStatus[date] = true;
        }
    }
})();
