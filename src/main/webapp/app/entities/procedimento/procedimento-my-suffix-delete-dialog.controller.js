(function() {
    'use strict';

    angular
        .module('fisioacusApp')
        .controller('ProcedimentoMySuffixDeleteController',ProcedimentoMySuffixDeleteController);

    ProcedimentoMySuffixDeleteController.$inject = ['$uibModalInstance', 'entity', 'Procedimento'];

    function ProcedimentoMySuffixDeleteController($uibModalInstance, entity, Procedimento) {
        var vm = this;

        vm.procedimento = entity;
        vm.clear = clear;
        vm.confirmDelete = confirmDelete;

        function clear () {
            $uibModalInstance.dismiss('cancel');
        }

        function confirmDelete (id) {
            Procedimento.delete({id: id},
                function () {
                    $uibModalInstance.close(true);
                });
        }
    }
})();
