(function() {
    'use strict';

    angular
        .module('fisioacusApp')
        .controller('ProcedimentoMySuffixDetailController', ProcedimentoMySuffixDetailController);

    ProcedimentoMySuffixDetailController.$inject = ['$scope', '$rootScope', '$stateParams', 'previousState', 'entity', 'Procedimento'];

    function ProcedimentoMySuffixDetailController($scope, $rootScope, $stateParams, previousState, entity, Procedimento) {
        var vm = this;

        vm.procedimento = entity;
        vm.previousState = previousState.name;

        var unsubscribe = $rootScope.$on('fisioacusApp:procedimentoUpdate', function(event, result) {
            vm.procedimento = result;
        });
        $scope.$on('$destroy', unsubscribe);
    }
})();
