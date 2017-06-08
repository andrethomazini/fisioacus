(function() {
    'use strict';

    angular
        .module('fisioacusApp')
        .controller('ProfissionalMySuffixDetailController', ProfissionalMySuffixDetailController);

    ProfissionalMySuffixDetailController.$inject = ['$scope', '$rootScope', '$stateParams', 'previousState', 'entity', 'Profissional', 'Pessoa'];

    function ProfissionalMySuffixDetailController($scope, $rootScope, $stateParams, previousState, entity, Profissional, Pessoa) {
        var vm = this;

        vm.profissional = entity;
        vm.previousState = previousState.name;

        var unsubscribe = $rootScope.$on('fisioacusApp:profissionalUpdate', function(event, result) {
            vm.profissional = result;
        });
        $scope.$on('$destroy', unsubscribe);
    }
})();
