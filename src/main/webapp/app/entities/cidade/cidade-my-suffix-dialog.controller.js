(function() {
    'use strict';

    angular
        .module('fisioacusApp')
        .controller('CidadeMySuffixDialogController', CidadeMySuffixDialogController);

    CidadeMySuffixDialogController.$inject = ['$timeout', '$scope', '$stateParams', '$uibModalInstance', 'entity', 'Cidade'];

    function CidadeMySuffixDialogController ($timeout, $scope, $stateParams, $uibModalInstance, entity, Cidade) {
        var vm = this;

        vm.cidade = entity;
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
            if (vm.cidade.id !== null) {
                Cidade.update(vm.cidade, onSaveSuccess, onSaveError);
            } else {
                Cidade.save(vm.cidade, onSaveSuccess, onSaveError);
            }
        }

        function onSaveSuccess (result) {
            $scope.$emit('fisioacusApp:cidadeUpdate', result);
            $uibModalInstance.close(result);
            vm.isSaving = false;
        }

        function onSaveError () {
            vm.isSaving = false;
        }


    }
})();
