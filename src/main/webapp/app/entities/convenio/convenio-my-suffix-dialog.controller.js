(function() {
    'use strict';

    angular
        .module('fisioacusApp')
        .controller('ConvenioMySuffixDialogController', ConvenioMySuffixDialogController);

    ConvenioMySuffixDialogController.$inject = ['$timeout', '$scope', '$stateParams', '$uibModalInstance', 'entity', 'Convenio'];

    function ConvenioMySuffixDialogController ($timeout, $scope, $stateParams, $uibModalInstance, entity, Convenio) {
        var vm = this;

        vm.convenio = entity;
        vm.clear = clear;
        vm.save = save;

        $timeout(function (){
            angular.element('.form-group:eq(1)>input').focus();
        });

        function clear () {
            $uibModalInstance.dismiss('cancel');
        }

        function save () {
            vm.isSaving = true;
            if (vm.convenio.id !== null) {
                Convenio.update(vm.convenio, onSaveSuccess, onSaveError);
            } else {
                Convenio.save(vm.convenio, onSaveSuccess, onSaveError);
            }
        }

        function onSaveSuccess (result) {
            $scope.$emit('fisioacusApp:convenioUpdate', result);
            $uibModalInstance.close(result);
            vm.isSaving = false;
        }

        function onSaveError () {
            vm.isSaving = false;
        }


    }
})();
