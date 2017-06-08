(function() {
    'use strict';

    angular
        .module('fisioacusApp')
        .controller('ProfissionalMySuffixDeleteController',ProfissionalMySuffixDeleteController);

    ProfissionalMySuffixDeleteController.$inject = ['$uibModalInstance', 'entity', 'Profissional'];

    function ProfissionalMySuffixDeleteController($uibModalInstance, entity, Profissional) {
        var vm = this;

        vm.profissional = entity;
        vm.clear = clear;
        vm.confirmDelete = confirmDelete;

        function clear () {
            $uibModalInstance.dismiss('cancel');
        }

        function confirmDelete (id) {
            Profissional.delete({id: id},
                function () {
                    $uibModalInstance.close(true);
                });
        }
    }
})();
