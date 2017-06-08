(function() {
    'use strict';

    angular
        .module('fisioacusApp')
        .controller('AtendimentoMySuffixDetailController', AtendimentoMySuffixDetailController);

    AtendimentoMySuffixDetailController.$inject = ['$scope', '$rootScope', '$stateParams', 'previousState', 'entity', 'Atendimento', 'Convenio', 'Pessoa', 'Procedimento', 'Sessao'];

    function AtendimentoMySuffixDetailController($scope, $rootScope, $stateParams, previousState, entity, Atendimento, Convenio, Pessoa, Procedimento, Sessao) {
        var vm = this;

        vm.atendimento = entity;
        vm.previousState = previousState.name;

        var unsubscribe = $rootScope.$on('fisioacusApp:atendimentoUpdate', function(event, result) {
            vm.atendimento = result;
        });
        $scope.$on('$destroy', unsubscribe);
    }
})();
