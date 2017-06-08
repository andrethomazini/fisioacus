(function() {
    'use strict';

    angular
        .module('fisioacusApp')
        .controller('ConvenioMySuffixDeleteController',ConvenioMySuffixDeleteController);

    ConvenioMySuffixDeleteController.$inject = ['$uibModalInstance', 'entity', 'Convenio'];

    function ConvenioMySuffixDeleteController($uibModalInstance, entity, Convenio) {
        var vm = this;

        vm.convenio = entity;
        vm.clear = clear;
        vm.confirmDelete = confirmDelete;

        function clear () {
            $uibModalInstance.dismiss('cancel');
        }

        function confirmDelete (id) {
            Convenio.delete({id: id},
                function () {
                    $uibModalInstance.close(true);
                });
        }
    }
})();
