(function() {
    'use strict';

    angular
        .module('fisioacusApp')
        .controller('PessoaMySuffixDetailController', PessoaMySuffixDetailController);

    PessoaMySuffixDetailController.$inject = ['$scope', '$rootScope', '$stateParams', 'previousState', 'entity', 'Pessoa', 'Cidade'];

    function PessoaMySuffixDetailController($scope, $rootScope, $stateParams, previousState, entity, Pessoa, Cidade) {
        var vm = this;

        vm.pessoa = entity;
        vm.previousState = previousState.name;

        var unsubscribe = $rootScope.$on('fisioacusApp:pessoaUpdate', function(event, result) {
            vm.pessoa = result;
        });
        $scope.$on('$destroy', unsubscribe);
    }
})();
