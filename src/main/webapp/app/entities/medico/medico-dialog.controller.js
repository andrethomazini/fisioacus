(function() {
    'use strict';

    angular
        .module('fisioacusApp')
        .controller('MedicoDialogController', MedicoDialogController);

    MedicoDialogController.$inject = ['$timeout', '$scope', '$stateParams', '$uibModalInstance', '$q', 'entity', 'Medico', 'Especialidade'];

    function MedicoDialogController ($timeout, $scope, $stateParams, $uibModalInstance, $q, entity, Medico, Especialidade) {
        var vm = this;

        vm.medico = entity;
        vm.clear = clear;
        vm.save = save;
        vm.especialidades = Especialidade.query({filter: 'medico-is-null'});
        $q.all([vm.medico.$promise, vm.especialidades.$promise]).then(function() {
            if (!vm.medico.especialidadeId) {
                return $q.reject();
            }
            return Especialidade.get({id : vm.medico.especialidadeId}).$promise;
        }).then(function(especialidade) {
            vm.especialidades.push(especialidade);
        });

        $timeout(function (){
            angular.element('.form-group:eq(1)>input').focus();
        });

        function clear () {
            $uibModalInstance.dismiss('cancel');
        }

        function save () {
            vm.isSaving = true;
            if (vm.medico.id !== null) {
                Medico.update(vm.medico, onSaveSuccess, onSaveError);
            } else {
                Medico.save(vm.medico, onSaveSuccess, onSaveError);
            }
        }

        function onSaveSuccess (result) {
            $scope.$emit('fisioacusApp:medicoUpdate', result);
            $uibModalInstance.close(result);
            vm.isSaving = false;
        }

        function onSaveError () {
            vm.isSaving = false;
        }


    }
})();
