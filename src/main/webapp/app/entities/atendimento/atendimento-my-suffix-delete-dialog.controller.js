(function() {
    'use strict';

    angular
        .module('fisioacusApp')
        .controller('AtendimentoMySuffixDeleteController',AtendimentoMySuffixDeleteController);

    AtendimentoMySuffixDeleteController.$inject = ['$uibModalInstance', 'entity', 'Atendimento'];

    function AtendimentoMySuffixDeleteController($uibModalInstance, entity, Atendimento) {
        var vm = this;

        vm.atendimento = entity;
        vm.clear = clear;
        vm.confirmDelete = confirmDelete;

        function clear () {
            $uibModalInstance.dismiss('cancel');
        }

        function confirmDelete (id) {
            Atendimento.delete({id: id},
                function () {
                    $uibModalInstance.close(true);
                });
        }
    }
})();
