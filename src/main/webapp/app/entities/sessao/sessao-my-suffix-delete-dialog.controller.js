(function() {
    'use strict';

    angular
        .module('fisioacusApp')
        .controller('SessaoMySuffixDeleteController',SessaoMySuffixDeleteController);

    SessaoMySuffixDeleteController.$inject = ['$uibModalInstance', 'entity', 'Sessao'];

    function SessaoMySuffixDeleteController($uibModalInstance, entity, Sessao) {
        var vm = this;

        vm.sessao = entity;
        vm.clear = clear;
        vm.confirmDelete = confirmDelete;

        function clear () {
            $uibModalInstance.dismiss('cancel');
        }

        function confirmDelete (id) {
            Sessao.delete({id: id},
                function () {
                    $uibModalInstance.close(true);
                });
        }
    }
})();
