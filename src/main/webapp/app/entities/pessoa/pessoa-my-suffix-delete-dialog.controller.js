(function() {
    'use strict';

    angular
        .module('fisioacusApp')
        .controller('PessoaMySuffixDeleteController',PessoaMySuffixDeleteController);

    PessoaMySuffixDeleteController.$inject = ['$uibModalInstance', 'entity', 'Pessoa'];

    function PessoaMySuffixDeleteController($uibModalInstance, entity, Pessoa) {
        var vm = this;

        vm.pessoa = entity;
        vm.clear = clear;
        vm.confirmDelete = confirmDelete;

        function clear () {
            $uibModalInstance.dismiss('cancel');
        }

        function confirmDelete (id) {
            Pessoa.delete({id: id},
                function () {
                    $uibModalInstance.close(true);
                });
        }
    }
})();
