(function() {
    'use strict';

    angular
        .module('fisioacusApp')
        .controller('PessoaMySuffixDialogController', PessoaMySuffixDialogController);

    PessoaMySuffixDialogController.$inject = ['$timeout', '$scope', '$stateParams', '$uibModalInstance', 'entity', 'Pessoa', 'Cidade'];

    function PessoaMySuffixDialogController ($timeout, $scope, $stateParams, $uibModalInstance, entity, Pessoa, Cidade) {
        var vm = this;

        vm.pessoa = entity;
        vm.clear = clear;
        vm.datePickerOpenStatus = {};
        vm.openCalendar = openCalendar;
        vm.save = save;
        vm.cidades = Cidade.query();

        $timeout(function (){
            angular.element('.form-group:eq(1)>input').focus();
        });

        function clear () {
            $uibModalInstance.dismiss('cancel');
        }

        function save () {
            vm.isSaving = true;
            if (vm.pessoa.id !== null) {
                Pessoa.update(vm.pessoa, onSaveSuccess, onSaveError);
            } else {
                Pessoa.save(vm.pessoa, onSaveSuccess, onSaveError);
            }
        }

        function onSaveSuccess (result) {
            $scope.$emit('fisioacusApp:pessoaUpdate', result);
            $uibModalInstance.close(result);
            vm.isSaving = false;
        }

        function onSaveError () {
            vm.isSaving = false;
        }

        vm.datePickerOpenStatus.dtNascimento = false;

        function openCalendar (date) {
            vm.datePickerOpenStatus[date] = true;
        }
    }
})();
