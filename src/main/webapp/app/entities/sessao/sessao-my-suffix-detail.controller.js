(function() {
    'use strict';

    angular
        .module('fisioacusApp')
        .controller('SessaoMySuffixDetailController', SessaoMySuffixDetailController);

    SessaoMySuffixDetailController.$inject = ['$scope', '$rootScope', '$stateParams', 'previousState', 'entity', 'Sessao', 'Profissional', 'Atendimento'];

    function SessaoMySuffixDetailController($scope, $rootScope, $stateParams, previousState, entity, Sessao, Profissional, Atendimento) {
        var vm = this;

        vm.sessao = entity;
        vm.previousState = previousState.name;

        var unsubscribe = $rootScope.$on('fisioacusApp:sessaoUpdate', function(event, result) {
            vm.sessao = result;
        });
        $scope.$on('$destroy', unsubscribe);
    }
})();
