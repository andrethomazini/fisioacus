(function() {
    'use strict';

    angular
        .module('fisioacusApp')
        .controller('ProfissionalMySuffixDialogController', ProfissionalMySuffixDialogController);

    ProfissionalMySuffixDialogController.$inject = ['$timeout', '$scope', '$stateParams', '$uibModalInstance', '$q', 'entity', 'Profissional', 'Pessoa'];

    function ProfissionalMySuffixDialogController ($timeout, $scope, $stateParams, $uibModalInstance, $q, entity, Profissional, Pessoa) {
        var vm = this;

        vm.profissional = entity;
        vm.clear = clear;
        vm.datePickerOpenStatus = {};
        vm.openCalendar = openCalendar;
        vm.save = save;
        vm.pessoas = Pessoa.query({filter: 'profissional-is-null'});
        $q.all([vm.profissional.$promise, vm.pessoas.$promise]).then(function() {
            if (!vm.profissional.pessoaId) {
                return $q.reject();
            }
            return Pessoa.get({id : vm.profissional.pessoaId}).$promise;
        }).then(function(pessoa) {
            vm.pessoas.push(pessoa);
        });

        $timeout(function (){
            angular.element('.form-group:eq(1)>input').focus();
        });

        function clear () {
            $uibModalInstance.dismiss('cancel');
        }

        function save () {
            vm.isSaving = true;
            if (vm.profissional.id !== null) {
                Profissional.update(vm.profissional, onSaveSuccess, onSaveError);
            } else {
                Profissional.save(vm.profissional, onSaveSuccess, onSaveError);
            }
        }

        function onSaveSuccess (result) {
            $scope.$emit('fisioacusApp:profissionalUpdate', result);
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
